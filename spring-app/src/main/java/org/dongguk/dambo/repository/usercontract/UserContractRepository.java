package org.dongguk.dambo.repository.usercontract;

import org.dongguk.dambo.domain.entity.UserContract;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserContractRepository extends JpaRepository<UserContract, Long> {
    @Query("""
            SELECT new org.dongguk.dambo.dto.usercontract.response.ActiveContractResponse(
                c.loanAmount,
                c.interestRate,
                mc.owner.name,
                mc.price,
                c.status,
                ip.progress,
                uc.investment,
                uc.stake
            )
            FROM UserContract uc
            JOIN uc.contract c
            JOIN c.musicCopyright mc
            JOIN InvestmentProgress ip ON ip.contract.id = c.id
            WHERE uc.user.id = :userId
            AND c.status IN :statuses
            AND uc.role = :role""")
    List<ActiveContractResponse> findActiveContractsByUserIdAndStatusesAndRole(
            Long userId,
            List<String> statuses,
            EContractRole role
    );


}
