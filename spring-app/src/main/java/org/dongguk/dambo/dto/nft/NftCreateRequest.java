package org.dongguk.dambo.dto.nft;

import java.util.List;

public record NftCreateRequest(
        String nftName,
        String title,
        List<String> singers,
        List<String> composers,
        List<String> lyricists,
        List<String> streamingUrls,
        Boolean isRegistered
) {
}
