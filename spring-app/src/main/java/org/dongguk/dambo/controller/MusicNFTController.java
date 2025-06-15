package org.dongguk.dambo.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.dto.MintRequest;
import org.dongguk.dambo.dto.MetadataMintRequest;
import org.dongguk.dambo.dto.MintResponse;
import org.dongguk.dambo.service.MusicNFTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MusicNFTController {

    private final MusicNFTService musicNFTService;

    @PostMapping("nft/mint/tokenURI")
    public ResponseEntity<MintResponse> mintWithTokenURI(@RequestBody MintRequest request) {
        MintResponse mintResponse = musicNFTService.mintNFT(request.getRecipient(), request.getTokenURI());
        return ResponseEntity.ok(mintResponse);
    }

    @PostMapping("nft/mint/metadata")
    public ResponseEntity<MintResponse> mintWithMetadata(@RequestBody MetadataMintRequest request) {
        String metadataUrl = musicNFTService.uploadMetadataToIPFS(request);
        MintResponse mintResponse = musicNFTService.mintNFT(request.recipient(), metadataUrl);
        return ResponseEntity.ok(mintResponse);
    }
}