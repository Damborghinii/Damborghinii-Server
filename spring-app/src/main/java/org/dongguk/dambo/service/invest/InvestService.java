package org.dongguk.dambo.service.invest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.dambo.core.annotation.UserId;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.*;
import org.dongguk.dambo.domain.exception.contract.ContractErrorCode;
import org.dongguk.dambo.domain.exception.investementprogress.InvestmentProgressErrorCode;
import org.dongguk.dambo.domain.exception.user.UserErrorCode;
import org.dongguk.dambo.domain.exception.usercontract.UserContractErrorCode;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.domain.type.ERepaymentStatus;
import org.dongguk.dambo.domain.type.ESearchFilter;
import org.dongguk.dambo.dto.contract.response.ContractDetailResponse;
import org.dongguk.dambo.dto.contract.response.ContractListResponse;
import org.dongguk.dambo.dto.invest.request.InvestmentRequest;
import org.dongguk.dambo.dto.invest.response.InvestmentInputMetaResponse;
import org.dongguk.dambo.repository.contract.ContractProjection;
import org.dongguk.dambo.repository.contract.ContractRepository;
import org.dongguk.dambo.repository.investmentProgress.InvestmentProgressRepository;
import org.dongguk.dambo.repository.repaymentschedule.RepaymentScheduleRepository;
import org.dongguk.dambo.repository.user.UserRepository;
import org.dongguk.dambo.repository.usercontract.UserContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestService {
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;
    private final InvestmentProgressRepository investmentProgressRepository;
    private final UserContractRepository userContractRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final NumberFormat nf = NumberFormat.getInstance(Locale.KOREA);

    @Transactional(readOnly = true)
    public ContractListResponse getAllContracts(ESearchFilter searchFilter) {
        List<ContractProjection> contractProjections;

        if(searchFilter.equals(ESearchFilter.HIGH_RETURN))
            contractProjections = contractRepository.findHighReturnWithCopyrightAndProgress();
        else if(searchFilter.equals(ESearchFilter.LOW_RISK))
            contractProjections = contractRepository.findLowRiskWithCopyrightAndProgress();
        else if(searchFilter.equals(ESearchFilter.LONG_TERM))
            contractProjections = contractRepository.findLongTermWithCopyrightAndProgress();
        else if(searchFilter.equals(ESearchFilter.SHORT_TERM))
            contractProjections = contractRepository.findShortTermWithCopyrightAndProgress();
        else
            contractProjections = contractRepository.findAllWithCopyrightAndProgress();


        List<ContractListResponse.ContractItem> items = contractProjections.stream()
                .map(contractProjection -> ContractListResponse.ContractItem.builder()
                        .contractId(contractProjection.getContractId())
                        .loanAmount(nf.format(contractProjection.getLoanAmount()) + "원")
                        .interestRate(contractProjection.getInterestRate().toString())
                        .copyright(
                                ContractListResponse.CopyrightInfo.builder()
                                        .imageUrl(contractProjection.getCopyrightImageUrl())
                                        .name(contractProjection.getCopyrightName())
                                        .ethPrice(contractProjection.getCopyrightEthPrice()
                                                .stripTrailingZeros().toPlainString() + "ETH")
                                        .build()
                        )
                        .progress(contractProjection.getProgress() == null
                                ? "0"
                                : contractProjection.getProgress().stripTrailingZeros().toPlainString() + "%")
                        .expirationTime(contractProjection.getExpirationTime().toString())
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

    @Transactional
    public Void investContract(Long userId, Long contractId, InvestmentRequest request) {
        // 1. 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ContractErrorCode.NOT_FOUND_CONTRACT));

        InvestmentProgress investmentProgress = investmentProgressRepository.findByContractId(contractId)
                .orElseThrow(() -> new CustomException(InvestmentProgressErrorCode.NOT_FOUND_INVESTMENT_PROGRESS));

        // 2. 투자 가능 여부 검증
        Long investment = request.investment();
        if (user.getCash() < investment) {
            throw new CustomException(UserErrorCode.INSUFFICIENT_BALANCE);
        }
        if (contract.getStatus() != EContractStatus.INVESTING) {
            throw new CustomException(ContractErrorCode.INVALID_CONTRACT_STATUS);
        }
        Long remainingAmount = contract.getLoanAmount() - investmentProgress.getProgressAmount();
        if (investment > remainingAmount) {
            throw new CustomException(ContractErrorCode.OVER_INVESTMENT_LIMIT);
        }
        if (Objects.equals(user.getId(), contract.getMusicCopyright().getId())) {
            throw new RuntimeException("자기 자신에게 투자할 수 없습니다.");
        }

        // 3. UserContract 생성
        BigDecimal stake = BigDecimal.valueOf(investment)
                .divide(BigDecimal.valueOf(contract.getLoanAmount()), 8, RoundingMode.HALF_UP);

        UserContract userContract = UserContract.create(
                EContractRole.LENDER,
                investment,
                stake,
                contract.getRepaymentCount(),
                1,
                EContractStatus.INVESTING,
                user,
                contract
        );
        userContractRepository.save(userContract);

        // 4. InvestmentProgress 갱신
        Long updatedAmount = investmentProgress.getProgressAmount() + investment;
        investmentProgress.updateProgressAmount(updatedAmount);
        BigDecimal progress = BigDecimal.valueOf(updatedAmount)
                .divide(BigDecimal.valueOf(contract.getLoanAmount()), 3, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        investmentProgress.updateProgress(progress);

        // 5. 투자 완료 여부 판단
        if (updatedAmount.equals(contract.getLoanAmount())) {
            // 5-1. 모든 UserContract 상태 변경
            List<UserContract> userContracts = userContractRepository.findAllByContractId(contractId);
            userContracts.forEach(uc -> uc.updateStatus(EContractStatus.COMPLETED));

            // 5-2. 자산 이전 (만약 빌려주는 사람이 어디에 계약 걸어놓은 상태로 또 걸어놓으면 예외상황 발생함. 대처 필요)
            userContracts.stream()
                    .filter(uc -> uc.getRole() == EContractRole.LENDER)
                    .forEach(uc -> {
                        User lender = uc.getUser();
                        lender.updateCashOnRepayment(uc.getInvestment());
                    });
            User borrower = userContractRepository.findBorrowerByContractId(contractId)
                    .orElseThrow(() -> new CustomException(UserContractErrorCode.BORROWER_NOT_FOUND));
            borrower.updateCashOnReceiveRepayment(contract.getLoanAmount());

            // 5-3. 계약 상태 변경 및 날짜 설정
            LocalDate startDate = LocalDate.now();
            contract.updateStatus(EContractStatus.MATCHED);
            contract.updateLoanStartDate(startDate);
            contract.updateLoanEndDate(startDate.plusMonths(contract.getRepaymentCount()));

            // 5-4. RepaymentSchedule 생성 (LENDER 기준)
            userContracts.stream()
                    .filter(uc -> uc.getRole() == EContractRole.LENDER)
                    .forEach(uc -> {
                        for (int round = 1; round <= contract.getRepaymentCount(); round++) {
                            LocalDate repaymentDate = calculateRepaymentDate(startDate, round);
                            BigDecimal totalInterest = contract.getInterestRate()
                                    .multiply(BigDecimal.valueOf(uc.getInvestment()));
                            long repaymentAmount = totalInterest
                                    .add(BigDecimal.valueOf(uc.getInvestment()))
                                    .divide(BigDecimal.valueOf(contract.getRepaymentCount()), RoundingMode.DOWN)
                                    .longValue();
                            long lateFee = Math.round(repaymentAmount * 0.05);

                            RepaymentSchedule schedule = RepaymentSchedule.create(
                                    round,
                                    repaymentDate,
                                    null,
                                    repaymentAmount,
                                    lateFee,
                                    ERepaymentStatus.UPCOMING,
                                    uc
                            );
                            repaymentScheduleRepository.save(schedule);
                        }
                    });

            // 5-5. RepaymentSchedule 생성 (BORROWER 기준)
            UserContract borrowerContract = userContracts.stream()
                    .filter(uc -> uc.getRole() == EContractRole.BORROWER)
                    .findFirst()
                    .orElseThrow(() -> new CustomException(UserContractErrorCode.NOT_FOUND_USER_CONTRACT));

            for (int round = 1; round <= contract.getRepaymentCount(); round++) {
                LocalDate repaymentDate = calculateRepaymentDate(startDate, round);
                long repaymentAmount = contract.getLoanAmount()
                        + contract.getInterestRate()
                        .multiply(BigDecimal.valueOf(contract.getLoanAmount()))
                        .longValue();
                repaymentAmount /= contract.getRepaymentCount();
                long lateFee = Math.round(repaymentAmount * 0.05);

                RepaymentSchedule borrowerSchedule = RepaymentSchedule.create(
                        round,
                        repaymentDate,
                        null,
                        repaymentAmount,
                        lateFee,
                        ERepaymentStatus.UPCOMING,
                        borrowerContract
                );
                repaymentScheduleRepository.save(borrowerSchedule);
            }
        }

        return null;
    }

    private LocalDate calculateRepaymentDate(LocalDate startDate, int round) {
        LocalDate base = startDate.plusMonths(round - 1);
        int dayOfMonth = startDate.getDayOfMonth();
        int lastDayOfMonth = YearMonth.from(base).lengthOfMonth();
        return base.withDayOfMonth(Math.min(dayOfMonth, lastDayOfMonth));
    }

}
