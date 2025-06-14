package org.dongguk.dambo.repository.nft;

import org.dongguk.dambo.domain.entity.Nft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftRepository extends JpaRepository<Nft, Long> {
}
