package com.podrecznikprogramisty.oauth2casestudy.configuration.converter;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CustomHttpMessageConverter implements HttpMessageConverter<OAuth2AccessToken> {

    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return OAuth2AccessToken.class == aClass;
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_JSON);
    }

    @Override
    public OAuth2AccessToken read(Class<? extends OAuth2AccessToken> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        ObjectMapper mapper = new ObjectMapper();
        String retrievedToken = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.defaultCharset());
        return createAccessToken(mapper.readValue(retrievedToken, CustomAccessTokenDto.class));
    }

    @Override
    public void write(OAuth2AccessToken oAuth2AccessToken, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }

    private DefaultOAuth2AccessToken createAccessToken(final CustomAccessTokenDto accessTokenDto){
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessTokenDto.getAccessToken());
        if (Objects.nonNull(accessTokenDto.getExpiresIn())) {
            token.setExpiration(new Date(System.currentTimeMillis() + (accessTokenDto.getExpiresIn() * 1000)));
        }
        return token;
    }
}
