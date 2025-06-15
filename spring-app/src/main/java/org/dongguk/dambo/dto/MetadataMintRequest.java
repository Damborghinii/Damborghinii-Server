package org.dongguk.dambo.dto;

import java.math.BigDecimal;

public record MetadataMintRequest(
        String recipient,
        BigDecimal ethPrice,
        String name,
        String singer,
        String composer,
        String lyricist,
        String streamingUrl,
        String image
) {
    public static MetadataMintRequest of(
            String recipient, BigDecimal ethPrice, String name,
            String singer, String composer, String lyricist,
            String streamingUrl, String image
    ) {
        return new MetadataMintRequest(
                recipient, ethPrice, name,
                singer, composer, lyricist,
                streamingUrl, image
        );
    }
}