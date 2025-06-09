package org.dongguk.dambo.repository.usercontract;

import org.dongguk.dambo.domain.entity.UserContract;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserContractRepository extends JpaRepository<UserContract, Long> {
    @Query("""
            SELECT
                c.loanAmount as loanAmount,
                c.interestRate as interestRate,
                mc.owner.name as owner,
                mc.price as price,
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
            AND uc.role = :eRole""")
    List<ActiveContractProjection> findActiveContractsByUserIdAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole eRole
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
            AND uc.role = :eRole""")
    Long findActiveContractsCountByUserIdAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole eRole
    );
}
