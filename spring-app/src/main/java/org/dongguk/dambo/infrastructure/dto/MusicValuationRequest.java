package org.dongguk.dambo.infrastructure.dto;

public record MusicValuationRequest(
        Long streamCount,
        Integer snsMentions,
        Double searchTrendScore,
        Double musicMetadataScore,
        Double chartRankAvg,
        Integer isMajorDistributor,
        Long artistFollowerCount
) {
}
