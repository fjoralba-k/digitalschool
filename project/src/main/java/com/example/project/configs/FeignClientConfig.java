package org.zerogravitysolutions.digitalschool.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zerogravitysolutions.digitalschool.utilities.UserContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignClientConfig {
    
    private String authToken = UserContextHolder.getContext().getAuthToken();

    @Bean
    public RequestInterceptor requestInterceptor() {

        return new AuthTokenRequestInterceptor(authToken);
    }

    private static class AuthTokenRequestInterceptor implements RequestInterceptor {

        private final String authToken;

        public AuthTokenRequestInterceptor(String authToken) {
            this.authToken = authToken;
        }

        @Override
        public void apply(RequestTemplate template) {
            
            template.header("Authorization", "Bearer " + authToken);
        }
    }
}
