package org.dongguk.dambo.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.dto.MintRequest;
import org.dongguk.dambo.dto.MetadataMintRequest;
import org.dongguk.dambo.service.MusicNFTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MusicNFTController {

    private final MusicNFTService musicNFTService;

    @PostMapping("nft/mint/tokenURI")
    public ResponseEntity<String> mintWithTokenURI(@RequestBody MintRequest request) {
        String txHash = musicNFTService.mintNFT(request.getRecipient(), request.getTokenURI());
        return ResponseEntity.ok(txHash);
    }

    @PostMapping("nft/mint/metadata")
    public ResponseEntity<String> mintWithMetadata(@RequestBody MetadataMintRequest request) {
        String metadataUrl = musicNFTService.uploadMetadataToIPFS(request);
        String txHash = musicNFTService.mintNFT(request.getRecipient(), metadataUrl);
        return ResponseEntity.ok(txHash);
    }
}