package org.dongguk.dambo.dto.gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.dongguk.dambo.dto.musiccopyright.request.EvaluateCopyrightValueRequest;

import java.util.List;

@Builder
public record MusicValuationRequestDto(
        SystemInstruction systemInstruction,
        List<Content> contents
) {
    // 시스템 프롬프트
    private static final Part PROMPT = new Part("""
        당신은 음원의 잠재적 가치를 추정하는 AI 전문가입니다.

        지침:
        - 입력으로 곡의 메타데이터(JSON: 제목, 아티스트, 작곡가, 작사가, 스트리밍 URL, 등록 여부)가 주어집니다.
        - 당신은 주어진 데이터뿐 아니라 외부 정보를 적극적으로 활용해야 합니다.
          예: 작곡가, 작사가, 아티스트가 유명 인물인지, 유통 플랫폼의 영향력, 업계 평균 스트리밍 수익 등.
        - 최종적으로 이 음원의 잠재적 가치를 **ETH(이더리움) 단위**로 추정해야 합니다.
        - 반드시 하나의 숫자만 출력하세요. 설명, 단위 표기, 텍스트는 포함하지 마세요.
          예: 0.12
        - 불확실성이 크더라도 반드시 수치를 제시해야 합니다.
    """);

    private static final SystemInstruction DEFAULT_SYSTEM_INSTRUCTION =
            new SystemInstruction(List.of(PROMPT));

    public static MusicValuationRequestDto from(EvaluateCopyrightValueRequest request) {
        try {
            String json = new ObjectMapper().writeValueAsString(request);
            Part userPart = new Part("이 음원의 가치를 이더리움으로 추정해줘: " + json);
            Content content = new Content("user", List.of(userPart));

            return MusicValuationRequestDto.builder()
                    .systemInstruction(DEFAULT_SYSTEM_INSTRUCTION)
                    .contents(List.of(content))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("직렬화 실패", e);
        }
    }
}