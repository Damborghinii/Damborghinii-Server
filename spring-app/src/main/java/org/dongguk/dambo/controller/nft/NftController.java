package org.dongguk.dambo.controller.nft;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.annotation.UserId;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.dto.nft.NftCreateRequest;
import org.dongguk.dambo.service.nft.NftService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class NftController {
    private final NftService nftService;

    @PostMapping("/nfts")
    public BaseResponse<Void> createNft(
            @UserId Long userId,
            @RequestPart(value = "registrationDoc", required = false) MultipartFile registrationDoc,
            @RequestPart("image") MultipartFile image,
            @RequestPart("audio") MultipartFile audio,
            @RequestPart("nftCreateRequest") NftCreateRequest nftCreateRequest
    ) {
        return BaseResponse.success(nftService.createNft(userId, registrationDoc, image, audio, nftCreateRequest));
    }
}
