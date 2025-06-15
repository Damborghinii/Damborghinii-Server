package org.dongguk.dambo.dto.musiccopyright.request;

import java.util.List;

public record EvaluateCopyrightValueRequest(
        String nftName,
        String title,
        List<String> singers,
        List<String> composers,
        List<String> lyricists,
        List<String> streamingUrls,
        Boolean isRegistered
) {
}
