package org.dongguk.dambo.dto.gemini;

import java.util.List;

public record GeminiResponseDto(
        List<Candidate> candidates
) {
}

