package org.dongguk.dambo.controller.musiccopyright;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.annotation.UserId;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.dto.musiccopyright.request.EvaluateCopyrightValueRequest;
import org.dongguk.dambo.dto.musiccopyright.response.CopyrightDetailResponse;
import org.dongguk.dambo.dto.musiccopyright.response.EvaluateCopyrightValueResponse;
import org.dongguk.dambo.dto.musiccopyright.response.MyCopyrightsResponse;
import org.dongguk.dambo.service.musiccopyright.MusicCopyrightService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MusicCopyrightController {
    private final MusicCopyrightService musicCopyrightService;

    @GetMapping("/me/copyrights")
    public BaseResponse<MyCopyrightsResponse> getMyNfts(
            @UserId Long userId,
            @RequestParam String status
    ) {
        return BaseResponse.success(musicCopyrightService.getMyNfts(userId, status));
    }

    @GetMapping("/me/invest/copyrights")
    public BaseResponse<MyCopyrightsResponse> getInvestNfts(
            @UserId Long userId,
            @RequestParam String status
    ) {
        return BaseResponse.success(musicCopyrightService.getInvestNfts(userId, status));
    }

    @GetMapping("/copyrights/{copyrightId}")
    public BaseResponse<CopyrightDetailResponse> getCopyrightDetail(
            @UserId Long userId,
            @PathVariable Long copyrightId
    ) {
        return BaseResponse.success(musicCopyrightService.getCopyrightDetail(copyrightId));
    }

    @PostMapping("/copyrights/predict")
    public BaseResponse<EvaluateCopyrightValueResponse> evaluateCopyright(
            @RequestBody EvaluateCopyrightValueRequest request
    ) {
        return BaseResponse.success(musicCopyrightService.evaluateCopyright(request));
    }
}
