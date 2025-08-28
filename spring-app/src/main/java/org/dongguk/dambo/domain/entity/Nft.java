package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "nfts")
public class Nft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "wallet_addr", nullable = false, length = 500)
    private String walletAddr;

    @Column(name = "token_id", nullable = false)
    private Long tokenId;

    @Column(name = "ipfs_url", nullable = false, length = 800)
    private String ipfsUrl;

    @Column(name = "tx_hash", nullable = false, length = 800)
    private String txHash;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "copyright_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MusicCopyright musicCopyright;

    @Builder
    private Nft(String walletAddr, Long tokenId, String ipfsUrl, String txHash, MusicCopyright musicCopyright) {
        this.walletAddr = walletAddr;
        this.tokenId = tokenId;
        this.ipfsUrl = ipfsUrl;
        this.txHash = txHash;
        this.musicCopyright = musicCopyright;
    }

    public static Nft create(String walletAddr, Long tokenId, String ipfsUrl, String txHash, MusicCopyright copyright) {
        return Nft.builder()
                .walletAddr(walletAddr)
                .tokenId(tokenId)
                .ipfsUrl(ipfsUrl)
                .txHash(txHash)
                .musicCopyright(copyright)
                .build();
    }
}
