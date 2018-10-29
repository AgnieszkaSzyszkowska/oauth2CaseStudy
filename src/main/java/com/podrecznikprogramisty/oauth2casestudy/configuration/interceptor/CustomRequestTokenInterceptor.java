package com.podrecznikprogramisty.oauth2casestudy.configuration.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;

public class CustomRequestTokenInterceptor implements ClientHttpRequestInterceptor {

    private static final String REQUEST_TOKEN_MARKER = "getToken";
    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "clientSecret";
    private static final String GRANT_TYPE = "grantType";

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        if (httpRequest.getURI().toString().contains(REQUEST_TOKEN_MARKER)){
            httpRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            bytes = parseBodyToJson(bytes);
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

    private byte[] parseBodyToJson(byte[] bytes) throws JsonProcessingException {

        final String deserializedBody = new String(bytes, Charset.defaultCharset());
        final ObjectMapper objectMapper = new ObjectMapper();

        int clientIdLength = CLIENT_ID.length();
        int clientSecretLength = CLIENT_SECRET.length();
        int grantTypeLength = GRANT_TYPE.length();

        int clientSecretPos = deserializedBody.indexOf(CLIENT_SECRET);
        int grantTypePos = deserializedBody.indexOf(GRANT_TYPE);

        final String clientId = deserializedBody.substring(clientIdLength + 1, clientSecretLength - 1);
        final String clientSecret = deserializedBody.substring(clientSecretPos + clientSecretLength + 1, grantTypePos - 1);
        final String grantType = deserializedBody.substring(grantTypePos + grantTypeLength + 1);

        return objectMapper.writeValueAsBytes(new CustomTokenRequestDto(clientId, clientSecret, grantType));
    }
}
