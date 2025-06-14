package org.dongguk.dambo.dto.musiccopyright.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MyCopyrightsResponse(
        List<MyCopyrightResponse> copyrights
) {
}
