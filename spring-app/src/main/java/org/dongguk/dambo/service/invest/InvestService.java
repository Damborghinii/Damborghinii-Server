package org.dongguk.dambo.service.invest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.entity.InvestmentProgress;
import org.dongguk.dambo.domain.entity.MusicCopyright;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.exception.contract.ContractErrorCode;
import org.dongguk.dambo.domain.exception.investementprogress.InvestmentProgressErrorCode;
import org.dongguk.dambo.dto.contract.response.ContractDetailResponse;
import org.dongguk.dambo.dto.contract.response.ContractListResponse;
import org.dongguk.dambo.dto.invest.response.InvestmentInputMetaResponse;
import org.dongguk.dambo.repository.contract.ContractProjection;
import org.dongguk.dambo.repository.contract.ContractRepository;
import org.dongguk.dambo.repository.investmentProgress.InvestmentProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestService {
    private final ContractRepository contractRepository;
    private final InvestmentProgressRepository investmentProgressRepository;
    private final NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);

    @Transactional(readOnly = true)
    public ContractListResponse getAllContracts() {
        List<ContractProjection> contractProjections = contractRepository.findAllWithCopyrightAndProgress();

        List<ContractListResponse.ContractItem> items = contractProjections.stream()
                .map(contractProjection -> ContractListResponse.ContractItem.builder()
                        .contractId(contractProjection.getContractId())
                        .loanAmount(nf.format(contractProjection.getLoanAmount()) + "원")
                        .interestRate("5%")
                        .copyright(
                                ContractListResponse.CopyrightInfo.builder()
                                        .name(contractProjection.getCopyrightName())
                                        .ethPrice(contractProjection.getCopyrightEthPrice()
                                                .stripTrailingZeros().toPlainString() + "ETH")
                                        .build()
                        )
                        .progress(contractProjection.getProgress() == null
                                ? "0"
                                : contractProjection.getProgress().stripTrailingZeros().toPlainString() + "%")
                        .build()
                ).toList();

        return new ContractListResponse(items);
    }

    @Transactional(readOnly = true)
    public ContractDetailResponse getContractDetail(Long userId, Long contractId) {
        Contract contract = contractRepository.findByIdWithCopyright(contractId)
                .orElseThrow(() -> new IllegalArgumentException("해당 계약이 없습니다."));

        MusicCopyright musicCopyright = contract.getMusicCopyright();
        User user = musicCopyright.getOwner();
        InvestmentProgress progress = investmentProgressRepository.findByContractId(contractId).orElse(null);

        BigDecimal interestRate = contract.getInterestRate();
        Long loanAmount = contract.getLoanAmount();
        int count = contract.getRepaymentCount();
        BigDecimal monthlyInterest = interestRate.multiply(BigDecimal.valueOf(loanAmount))
                .divide(BigDecimal.valueOf(count), 0, RoundingMode.DOWN);
        Long totalRepayment = loanAmount + monthlyInterest.longValue() * count;

        return ContractDetailResponse.builder()
                .contract(ContractDetailResponse.ContractInfo.builder()
                        .loanAmount(nf.format(loanAmount) + "원")
                        .monthlyInterest(nf.format(monthlyInterest.longValue()) + "원")
                        .loanType("만기상환방식")
                        .interestRate(interestRate.multiply(BigDecimal.valueOf(100)).stripTrailingZeros().toPlainString() + "%")
                        .paymentAmount(nf.format(totalRepayment) + "원")
                        .overdueRate("5%")
                        .repaymentPeriod(count + "개월")
                        .repaymentCount(count + "회차")
                        .build())
                .user(ContractDetailResponse.UserInfo.builder()
                        .name(user.getName())
                        .job(user.getJob().getLabel())  // EJob 안에 displayName 있다고 가정
                        .build())
                .copyright(ContractDetailResponse.CopyrightInfo.builder()
                        .imageUrl(musicCopyright.getImageUrl())
                        .title(musicCopyright.getTitle())
                        .type("음원 NFT")
                        .ethPrice(musicCopyright.getEthPrice().stripTrailingZeros().toPlainString() + "ETH")
                        .wonPrice(nf.format(musicCopyright.getWonPrice()) + "원")
                        .singers(musicCopyright.getSinger())
                        .composers(musicCopyright.getComposer())
                        .lyricists(musicCopyright.getLyricist())
                        .streamingUrls(musicCopyright.getStreamingUrl())
                        .isRegistered(musicCopyright.getIsRegistered() ? "저작권이 등록되어 있는 음원" : "저작권 미등록")
                        .registrationDoc(musicCopyright.getRegistrationDoc())
                        .build())
                .progress(ContractDetailResponse.ProgressInfo.builder()
                        .currentProgress(progress == null
                                ? "0%"
                                : progress.getProgress().stripTrailingZeros().toPlainString() + "%")
                        .remainingInvestingMoney(progress == null
                                ? nf.format(loanAmount) + "원"
                                : nf.format(
                                loanAmount - loanAmount * progress.getProgress().doubleValue() / 100
                        ) + "원")
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    public InvestmentInputMetaResponse getInvestmentInputMeta(Long userId, Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> CustomException.type(ContractErrorCode.NOT_FOUND_CONTRACT));
        InvestmentProgress progress = investmentProgressRepository.findByContractId(contractId)
                .orElseThrow(() -> CustomException.type(InvestmentProgressErrorCode.NOT_FOUND_INVESTMENT_PROGRESS));

        Long totalLoanAmount = contract.getLoanAmount();
        Long progressAmount = progress.getProgressAmount();
        Long remainingAmount = totalLoanAmount - progressAmount;
        BigDecimal interestRate = contract.getInterestRate();

        // 대출 지분 비율 = (1 / 총 대출금액)
        double shareCalcRatio = 1.0 / totalLoanAmount * 100;
        // 매달 지급받는 이자 비율 = (이자율 / 12개월)
        double interestCalcRatio = interestRate.divide(BigDecimal.valueOf(12), 8, RoundingMode.HALF_UP).doubleValue();

        return InvestmentInputMetaResponse.builder()
                .minimumLoanAmount("0")
                .maximumLoanAmount(nf.format(remainingAmount))
                .shareCalculationRatio(BigDecimal.valueOf(shareCalcRatio))
                .interestCalculationRatio(BigDecimal.valueOf(interestCalcRatio))
                .build();
    }

}
