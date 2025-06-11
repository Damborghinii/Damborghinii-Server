package org.dongguk.dambo.dto;

import lombok.Data;

@Data
public class MetadataMintRequest {
    private String recipient;
    private String name;
    private String description;
    private String image;
}