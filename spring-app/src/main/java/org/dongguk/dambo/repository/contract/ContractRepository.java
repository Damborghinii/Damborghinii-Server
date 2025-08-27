package org.dongguk.dambo.repository.contract;

import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByIdAndStatus(Long id, EContractStatus status);
    Optional<Contract> findByMusicCopyright_Id(Long copyrightId);

    @Query("""
        SELECT
            mc.id            AS id,
            c.id             AS contractId,
            mc.imageUrl      AS imageUrl,
            mc.title         AS title,
            mc.ethPrice      AS ethPrice,
            c.status         AS status,
            ip.progress      AS progress,
            c.repaymentCount AS repaymentCount,
            uc.round         AS round
        FROM Contract c
        JOIN c.musicCopyright mc
        LEFT JOIN UserContract uc
               ON uc.contract = c
              AND uc.user.id = :ownerId
        LEFT JOIN InvestmentProgress ip
               ON ip.contract = c
        WHERE mc.owner.id = :ownerId
          AND c.status IN :statuses
    """)
    List<CopyrightSummaryProjection> findAllByOwnerIdAndStatuses(
            @Param("ownerId") Long ownerId,
            @Param("statuses") List<EContractStatus> statuses
    );

    @Query("""
        SELECT
          c.id AS contractId,
          c.loanAmount AS loanAmount,
          c.repaymentCount as repaymentCount,
          c.interestRate AS interestRate,
          c.expirationTime AS expirationTime,
          mc.imageUrl AS copyrightImageUrl,
          mc.title AS copyrightName,
          mc.ethPrice AS copyrightEthPrice,
          ip.progress AS progress
        FROM Contract c
        JOIN c.musicCopyright mc
        LEFT JOIN InvestmentProgress ip ON ip.contract = c
        WHERE c.status = 'INVESTING'
    """)
    List<ContractProjection> findAllWithCopyrightAndProgress();

    @Query("""
        SELECT
          c.id AS contractId,
          c.loanAmount AS loanAmount,
          c.repaymentCount as repaymentCount,
          c.interestRate AS interestRate,
          c.expirationTime AS expirationTime,
          mc.imageUrl AS copyrightImageUrl,
          mc.title AS copyrightName,
          mc.ethPrice AS copyrightEthPrice,
          ip.progress AS progress
        FROM Contract c
        JOIN c.musicCopyright mc
        LEFT JOIN InvestmentProgress ip ON ip.contract = c
        WHERE c.status = 'INVESTING'
        ORDER BY interestRate DESC
    """)
    List<ContractProjection> findHighReturnWithCopyrightAndProgress();


    @Query("""
        SELECT
          c.id AS contractId,
          c.loanAmount AS loanAmount,
          c.repaymentCount as repaymentCount,
          c.interestRate AS interestRate,
          c.expirationTime AS expirationTime,
          mc.imageUrl AS copyrightImageUrl,
          mc.title AS copyrightName,
          mc.ethPrice AS copyrightEthPrice,
          ip.progress AS progress
        FROM Contract c
        JOIN c.musicCopyright mc
        LEFT JOIN InvestmentProgress ip ON ip.contract = c
        WHERE c.status = 'INVESTING'
        ORDER BY interestRate ASC
    """)
    List<ContractProjection> findLowRiskWithCopyrightAndProgress();

    @Query("""
        SELECT
          c.id AS contractId,
          c.loanAmount AS loanAmount,
          c.repaymentCount as repaymentCount,
          c.interestRate AS interestRate,
          c.expirationTime AS expirationTime,
          mc.imageUrl AS copyrightImageUrl,
          mc.title AS copyrightName,
          mc.ethPrice AS copyrightEthPrice,
          ip.progress AS progress
        FROM Contract c
        JOIN c.musicCopyright mc
        LEFT JOIN InvestmentProgress ip ON ip.contract = c
        WHERE c.status = 'INVESTING'
        ORDER BY c.repaymentCount DESC
    """)
    List<ContractProjection> findLongTermWithCopyrightAndProgress();

    @Query("""
        SELECT
          c.id AS contractId,
          c.loanAmount AS loanAmount,
          c.repaymentCount as repaymentCount,
          c.interestRate AS interestRate,
          c.expirationTime AS expirationTime,
          mc.imageUrl AS copyrightImageUrl,
          mc.title AS copyrightName,
          mc.ethPrice AS copyrightEthPrice,
          ip.progress AS progress
        FROM Contract c
        JOIN c.musicCopyright mc
        LEFT JOIN InvestmentProgress ip ON ip.contract = c
        WHERE c.status = 'INVESTING'
        ORDER BY c.repaymentCount ASC
    """)
    List<ContractProjection> findShortTermWithCopyrightAndProgress();

    @Query("""
        SELECT c FROM Contract c
        JOIN FETCH c.musicCopyright mc
        JOIN FETCH mc.owner
        WHERE c.id = :contractId
    """)
    Optional<Contract> findByIdWithCopyright(@Param("contractId") Long contractId);
}
