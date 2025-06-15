package org.dongguk.dambo.dto;

public record MintResponse(
        String txHash,
        Long tokenId
) {
    public static MintResponse of(String txHash, Long tokenId) {
        return new MintResponse(txHash, tokenId);
    }
}
