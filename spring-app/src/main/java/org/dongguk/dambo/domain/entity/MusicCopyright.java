package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @Builder
    private MusicCopyright(String title, String singer, String composer, String lyricist,
                           String streamingUrl, Boolean isRegistered, String registrationDoc,
                           String imageUrl, User owner) {
        this.title = title;
        this.singer = singer;
        this.composer = composer;
        this.lyricist = lyricist;
        this.streamingUrl = streamingUrl;
        this.isRegistered = isRegistered;
        this.registrationDoc = registrationDoc;
        this.imageUrl = imageUrl;
        this.owner = owner;
    }

    public static MusicCopyright create(String title, String singer, String composer, String lyricist,
                                        String streamingUrl, Boolean isRegistered, String registrationDoc,
                                        String imageUrl, User owner) {
        return MusicCopyright.builder()
                .title(title)
                .singer(singer)
                .composer(composer)
                .lyricist(lyricist)
                .streamingUrl(streamingUrl)
                .isRegistered(isRegistered)
                .registrationDoc(registrationDoc)
                .imageUrl(imageUrl)
                .owner(owner)
                .build();
    }
}
