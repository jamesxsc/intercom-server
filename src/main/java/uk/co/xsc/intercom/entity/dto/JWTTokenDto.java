package uk.co.xsc.intercom.entity.dto;

import lombok.Getter;

public class JWTTokenDto {

    @Getter
    private final String accessToken;

    public JWTTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
