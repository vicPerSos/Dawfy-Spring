package com.dawfy.web.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class SpotifyAuthInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    private SpotifyTokenManager tokenManager;

    @Override
    @NonNull
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        request.getHeaders().setBearerAuth(tokenManager.getValidToken());
        return execution.execute(request, body);
    }

}
