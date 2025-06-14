package org.dongguk.dambo.dto.musiccopyright.response;

import lombok.Builder;

@Builder
public record CopyrightDetailResponse(
        Long copyrightId,
        String status,
        String imageUrl,
        String title,
        String type,
        String ethPrice,
        String wonPrice,
        String singers,
        String composers,
        String lyricists,
        String streamingUrls,
        String isRegistered,
        String registrationDoc
) {}
