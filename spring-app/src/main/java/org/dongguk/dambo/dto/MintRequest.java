package org.dongguk.dambo.dto;

import lombok.Data;

@Data
public class MintRequest {
    private String recipient;
    private String tokenURI;
}