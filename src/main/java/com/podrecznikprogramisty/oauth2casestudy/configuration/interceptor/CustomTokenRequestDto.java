package com.podrecznikprogramisty.oauth2casestudy.configuration.interceptor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class CustomTokenRequestDto {
    private String clientId;
    private String clientSecret;
    private String grantType;

    @JsonCreator
    CustomTokenRequestDto(
            @JsonProperty(value = "clientId") String clientId,
            @JsonProperty(value = "clientSecret") String clientSecret,
            @JsonProperty(value = "grantType") String grantType) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
    }
}
