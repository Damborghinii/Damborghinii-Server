package org.dongguk.dambo.dto.gemini;

import java.util.List;

public record Content(
        String role,
        List<Part> parts
) {
}

