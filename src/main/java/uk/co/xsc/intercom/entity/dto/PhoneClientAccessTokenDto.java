package uk.co.xsc.intercom.entity.dto;

import lombok.Getter;

public class PhoneClientAccessTokenDto {

    // Getters are required in the DTO

    @Getter
    private final String phoneClientAccessToken;

    public PhoneClientAccessTokenDto(String token) {
        this.phoneClientAccessToken = token;
    }

}
