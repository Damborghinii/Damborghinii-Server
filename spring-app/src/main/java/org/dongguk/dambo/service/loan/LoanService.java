package org.dongguk.dambo.service.loan;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.entity.MusicCopyright;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.exception.contract.ContractErrorCode;
import org.dongguk.dambo.domain.exception.musiccopyright.MusicCopyrightErrorCode;
import org.dongguk.dambo.domain.exception.user.UserErrorCode;
import org.dongguk.dambo.dto.loan.LoanEvaluationCheckResponse;
import org.dongguk.dambo.dto.loan.LoanEvaluationResponse;
import org.dongguk.dambo.repository.contract.ContractRepository;
import org.dongguk.dambo.repository.musiccopyright.MusicCopyrightRepository;
import org.dongguk.dambo.repository.user.UserRepository;
import org.dongguk.dambo.util.InterestRateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LoanService {
    private static final Double LOAN_RATIO = 0.3;
    private final UserRepository userRepository;
    private final MusicCopyrightRepository musicCopyrightRepository;
    private final ContractRepository contractRepository;

    @Transactional(readOnly = true)
    public LoanEvaluationResponse evaluateLoan(Long userId, Long copyrightId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.type(UserErrorCode.NOT_FOUND_USER));
        MusicCopyright musicCopyright = musicCopyrightRepository.findById(copyrightId)
                .orElseThrow(() -> CustomException.type(MusicCopyrightErrorCode.NOT_FOUND_MUSIC_COPYRIGHT));

        String ethPriceStr = musicCopyright.getEthPrice().stripTrailingZeros().toPlainString() + "ETH";
        String wonPriceStr = NumberFormat.getInstance(Locale.KOREA).format(musicCopyright.getWonPrice()) + "원";
        var copyrightDto = new LoanEvaluationResponse.CopyrightInfo(
                musicCopyright.getImageUrl(),
                "레전드 nft",
                "음원 NFT",
                ethPriceStr,
                wonPriceStr,
                musicCopyright.getSinger(),
                musicCopyright.getComposer(),
                musicCopyright.getLyricist(),
                musicCopyright.getStreamingUrl(),
                musicCopyright.getIsRegistered() ? "저작권이 등록되어 있는 음원" : "저작권 미등록",
                musicCopyright.getRegistrationDoc()
        );

        Long maxLoan = Math.round(musicCopyright.getWonPrice() * LOAN_RATIO);
        String loanAmountRange = "0원 ~ " + NumberFormat.getInstance(Locale.KOREA).format(maxLoan) + "원";
        LoanEvaluationResponse.LoanCondition loanConditionDto = new LoanEvaluationResponse.LoanCondition(
                "만기상환방식",
                "1년 이하",
                loanAmountRange,
                "최대 12%",
                "5%"
        );

        BigDecimal interestCalculationRatio = BigDecimal.valueOf(0.01);

        return new LoanEvaluationResponse(
                copyrightDto,
                loanConditionDto,
                "0",
                NumberFormat.getInstance(Locale.KOREA).format(maxLoan),
                interestCalculationRatio
        );
    }

    @Transactional(readOnly = true)
    public LoanEvaluationCheckResponse evaluateLoanCheck(
            Long userId,
            Long contractId,
            Long amount,
            Long count
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.type(UserErrorCode.NOT_FOUND_USER));
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> CustomException.type(ContractErrorCode.NOT_FOUND_CONTRACT));
        MusicCopyright musicCopyright = contract.getMusicCopyright();
        BigDecimal interestRate = InterestRateUtil.getInterestRate(musicCopyright.getWonPrice());

        BigDecimal monthlyInterest = BigDecimal.valueOf(amount)
                .multiply(interestRate)
                .divide(BigDecimal.valueOf(12), 0, RoundingMode.HALF_UP);
        BigDecimal totalPayment = BigDecimal.valueOf(amount)
                .add(monthlyInterest.multiply(BigDecimal.valueOf(count)));

        NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);
        String loanAmountStr = nf.format(amount) + "원";
        String monthlyIntStr = nf.format(monthlyInterest) + "원";
        String totalPaymentStr = nf.format(totalPayment) + "원";
        String interestRateStr = interestRate.multiply(BigDecimal.valueOf(100))
                .stripTrailingZeros().toPlainString() + "%";

        LoanEvaluationCheckResponse.LoanCondition loanCond =
                new LoanEvaluationCheckResponse.LoanCondition(
                        "만기상환방식",
                        loanAmountStr,
                        interestRateStr,
                        monthlyIntStr,
                        totalPaymentStr,
                        "5%",
                        count + "개월",
                        count + "회차"
                );

        String ethPriceStr = musicCopyright.getEthPrice().stripTrailingZeros().toPlainString() + "ETH";
        String wonPriceStr = nf.format(musicCopyright.getWonPrice()) + "원";

        LoanEvaluationCheckResponse.CopyrightInfo copyInfo =
                new LoanEvaluationCheckResponse.CopyrightInfo(
                        musicCopyright.getImageUrl(),
                        musicCopyright.getTitle(),
                        ethPriceStr,
                        wonPriceStr
                );

        return new LoanEvaluationCheckResponse(loanCond, copyInfo);
    }
}
