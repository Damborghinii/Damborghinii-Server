package org.dongguk.dambo.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.infrastructure.dto.MusicValuationRequest;
import org.dongguk.dambo.infrastructure.dto.MusicValuationResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class MusicValuationApiClient {
    private final RestClient flaskRestClient;

    public MusicValuationResponse evaluate(MusicValuationRequest request) {
        return flaskRestClient.post()
                .uri("/predict")
                .body(request)
                .retrieve()
                .body(MusicValuationResponse.class);
    }
}
