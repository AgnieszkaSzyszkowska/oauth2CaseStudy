package com.podrecznikprogramisty.oauth2casestudy.configuration.converter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class CustomAccessTokenDto {
    private String accessToken;
    private Integer expiresIn;

    @JsonCreator
    CustomAccessTokenDto(
            @JsonProperty(value = "accessToken") String accessToken,
            @JsonProperty(value = "expiresIn") Integer expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
