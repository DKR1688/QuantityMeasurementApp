package com.quantity_measurement_app.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class OAuth2FallbackController {

	private final String frontendUrl;

	public OAuth2FallbackController(@Value("${app.frontend.url:http://localhost:3000}") String frontendUrl) {
		this.frontendUrl = frontendUrl;
	}

	@GetMapping("/oauth2/authorization/google")
	public void googleAuthorizationFallback(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl)
				.queryParam("error", "Google login is not configured. Set GOOGLE_CLIENT_ID and GOOGLE_CLIENT_SECRET.")
				.build()
				.toUriString();
		response.sendRedirect(redirectUrl);
	}
}
