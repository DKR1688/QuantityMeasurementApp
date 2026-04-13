package com.quantity_measurement_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final OAuth2AuthorizationRequestCookieRepository authorizationRequestRepository;
	private final String redirectUri;

	public OAuth2FailureHandler(OAuth2AuthorizationRequestCookieRepository authorizationRequestRepository,
			@Value("${app.oauth2.redirect-uri:http://localhost:3000}") String redirectUri) {
		this.authorizationRequestRepository = authorizationRequestRepository;
		this.redirectUri = redirectUri;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
		String targetUrl = redirectUri + "?error="
				+ URLEncoder.encode(exception.getLocalizedMessage(), StandardCharsets.UTF_8);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
