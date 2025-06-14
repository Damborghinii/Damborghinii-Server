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
        SELECT mc.id AS id, mc.imageUrl AS imageUrl, mc.title AS title,
               mc.ethPrice AS ethPrice, c.status AS status
        FROM Contract c
        JOIN c.musicCopyright mc
        WHERE mc.owner.id = :ownerId
          AND c.status IN :statuses
    """)
    List<CopyrightSummaryProjection> findAllByOwnerIdAndStatuses(
            @Param("ownerId") Long ownerId,
            @Param("statuses") List<EContractStatus> statuses
    );
}
