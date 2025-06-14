package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.dongguk.dambo.constant.LoanConstants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "music_copyrights")
public class MusicCopyright {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "singer", nullable = false)
    private String singer;

    @Column(name = "composer", nullable = false)
    private String composer;

    @Column(name = "lyricist", nullable = false)
    private String lyricist;

    @Column(name = "streaming_url", nullable = false, length = 800)
    private String streamingUrl;

    @Column(name = "is_registered", nullable = false)
    private Boolean isRegistered;

    @Column(name = "registration_doc", nullable = false, length = 800)
    private String registrationDoc;

    @Column(name = "image_url", nullable = false, length = 800)
    private String imageUrl;

    @Column(name = "eth_price", nullable = false, precision = 8, scale = 4)
    private BigDecimal ethPrice;

    @Column(name = "won_price", nullable = false)
    private Long wonPrice;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @Builder
    private MusicCopyright(String title, String singer, String composer, String lyricist,
                           String streamingUrl, Boolean isRegistered, String registrationDoc,
                           String imageUrl, BigDecimal ethPrice, Long wonPrice, User owner) {
        this.title = title;
        this.singer = singer;
        this.composer = composer;
        this.lyricist = lyricist;
        this.streamingUrl = streamingUrl;
        this.isRegistered = isRegistered;
        this.registrationDoc = registrationDoc;
        this.imageUrl = imageUrl;
        this.ethPrice = ethPrice;
        this.wonPrice = wonPrice;
        this.owner = owner;
    }

    public static MusicCopyright create(String title, String singer, String composer, String lyricist,
                                        String streamingUrl, Boolean isRegistered, String registrationDoc,
                                        String imageUrl, BigDecimal ethPrice, User owner) {
        return MusicCopyright.builder()
                .title(title)
                .singer(singer)
                .composer(composer)
                .lyricist(lyricist)
                .streamingUrl(streamingUrl)
                .isRegistered(isRegistered)
                .registrationDoc(registrationDoc)
                .imageUrl(imageUrl)
                .ethPrice(ethPrice)
                .wonPrice(ethPrice.multiply(BigDecimal.valueOf(LoanConstants.EthereumMarketPrice)).longValue())
                .owner(owner)
                .build();
    }

    public void updateRegistrationDoc(String registrationDoc) {
        this.registrationDoc = registrationDoc;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
