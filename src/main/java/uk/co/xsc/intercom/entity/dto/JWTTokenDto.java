package uk.co.xsc.intercom.entity.dto;

import lombok.Getter;

public class JWTTokenDto {

    @Getter
    private final String jwtToken;

    public JWTTokenDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
