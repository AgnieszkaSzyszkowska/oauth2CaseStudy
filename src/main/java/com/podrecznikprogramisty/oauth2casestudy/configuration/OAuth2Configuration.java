package com.podrecznikprogramisty.oauth2casestudy.configuration;

import com.podrecznikprogramisty.oauth2casestudy.configuration.converter.CustomHttpMessageConverter;
import com.podrecznikprogramisty.oauth2casestudy.configuration.interceptor.CustomRequestTokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;

@Configuration
public class OAuth2Configuration {

    @Value("${accessTokenUri}")
    private String accessTokenUri;

    @Value("${clientID}")
    private String clientID;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${host}")
    private String host;

    @Bean
    ClientCredentialsResourceDetails getDetails() {
        final ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(clientID);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setAuthenticationScheme(AuthenticationScheme.form);
        details.setAuthenticationScheme(AuthenticationScheme.form);

        return details;
    }

    @Bean
    public RestOperations client() {
        final OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(getDetails(), new DefaultOAuth2ClientContext());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(host));
        final ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
        provider.setMessageConverters(Collections.singletonList(new CustomHttpMessageConverter()));
        provider.setInterceptors(Collections.singletonList(new CustomRequestTokenInterceptor()));
        restTemplate.setAccessTokenProvider(provider);

        return restTemplate;
    }
}
