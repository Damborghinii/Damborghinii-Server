package org.dongguk.dambo.repository.usercontract;

import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.entity.UserContract;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.domain.type.ERepaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserContractRepository extends JpaRepository<UserContract, Long> {
    @Query("""
            SELECT
                c.loanAmount as loanAmount,
                c.interestRate as interestRate,
                mc.owner.name as owner,
                mc.ethPrice as ethPrice,
                c.status as status,
                ip.progress as progress,
                uc.investment as investment,
                uc.stake as stake
            FROM UserContract uc
            JOIN uc.contract c
            JOIN c.musicCopyright mc
            JOIN InvestmentProgress ip ON ip.contract.id = c.id
            WHERE uc.user.id = :userId
            AND c.status IN :statuses
            AND uc.role = :role""")
    List<ActiveContractProjection> findActiveContractsByUserIdAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole role
    );

    @Query("""
            SELECT
                count(distinct uc.id)
            FROM UserContract uc
            JOIN uc.contract c
            JOIN c.musicCopyright mc
            JOIN InvestmentProgress ip ON ip.contract.id = c.id
            WHERE uc.user.id = :userId
            AND c.status IN :statuses
            AND uc.role = :role""")
    Long findActiveContractsCountByUserIdAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole role
    );

    @Query("""
    SELECT
        rs.id as repaymentScheduleId,
        rs.repaymentAmount
        +
        CASE WHEN rs.lateFee IS NULL THEN 0 ELSE rs.lateFee END
        as totalRepaymentAmount,
        rs.repaymentAmount as repaymentAmount,
        c.interestRate as interestRate,
        rs.lateFee as lateFee,
        rs.round as round,
        rs.repaymentDate as repaymentDate,
        rs.settlementDate as settlementDate,
        DATEDIFF(
            rs.repaymentDate,
            CASE WHEN rs.settlementDate IS NULL THEN CURDATE()
                 ELSE rs.settlementDate
            END
        ) as relativeDays,
        nf.name as nftName,
        uc.stake as stake,
        c.musicCopyright.ethPrice as ethPrice
    FROM UserContract uc
    JOIN uc.contract c
    JOIN RepaymentSchedule rs ON rs.userContract.id = uc.id
    JOIN Nft nf ON nf.musicCopyright.id = c.musicCopyright.id
    WHERE uc.user.id = :userId
    AND rs.repaymentStatus = :status
    AND uc.role = :role
    """)
    List<RepaymentScheduleProjection> findRepaymentScheduleByUserIdAndStatusAndRole(
            Long userId,
            ERepaymentStatus status,
            EContractRole role
    );

    @Query("""
            SELECT
               u.cash
            FROM User u
            WHERE u.id = :userId""")
    Long findCashByUserId(
            Long userId
    );

    @Query("""
            SELECT
               COUNT(distinct c.id) as totalContracts
            FROM UserContract uc
            JOIN uc.contract c ON c.id = uc.contract.id
            WHERE uc.user.id = :userId
            AND uc.role = :role
            GROUP BY uc.id""")
    Integer findTotalContractsByUserIdAndRole(
            Long userId,
            EContractRole role
    );

    @Query("""
            SELECT
               SUM(
                   CASE WHEN rs.lateFee IS NULL THEN 0
                   ELSE rs.lateFee
                   END
               ) + SUM(rs.repaymentAmount) as totalAmount
            FROM UserContract uc
            JOIN RepaymentSchedule rs ON rs.userContract.id = uc.id
            WHERE uc.user.id = :userId
            AND rs.repaymentStatus = :status
            AND uc.role = :role
            """)
    Long findTotalAmountByUserIdAndStatusAndRole(
            Long userId,
            ERepaymentStatus status,
            EContractRole role
    );

    Optional<List<UserContract>> findByRoleAndContract(EContractRole role, Contract contract);
}
