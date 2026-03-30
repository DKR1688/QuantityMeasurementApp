package com.quantity_measurement_app.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Optional;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthorizationRequestCookieRepository
		implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	private static final String AUTH_REQUEST_COOKIE_NAME = "oauth2_auth_request";
	private static final int COOKIE_EXPIRE_SECONDS = 180;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		return getCookieValue(request, AUTH_REQUEST_COOKIE_NAME).map(this::deserialize).orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
			HttpServletResponse response) {
		if (authorizationRequest == null) {
			deleteCookie(request, response, AUTH_REQUEST_COOKIE_NAME);
			return;
		}

		addCookie(response, AUTH_REQUEST_COOKIE_NAME, serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
			HttpServletResponse response) {
		OAuth2AuthorizationRequest authorizationRequest = loadAuthorizationRequest(request);
		deleteCookie(request, response, AUTH_REQUEST_COOKIE_NAME);
		return authorizationRequest;
	}

	private Optional<String> getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return Optional.empty();
		}

		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return Optional.ofNullable(cookie.getValue());
			}
		}

		return Optional.empty();
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
		deleteCookie(request, response, AUTH_REQUEST_COOKIE_NAME);
	}

	private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	private void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	private String serialize(OAuth2AuthorizationRequest authorizationRequest) {
		try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
				ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
			objectStream.writeObject(authorizationRequest);
			objectStream.flush();
			return Base64.getUrlEncoder().encodeToString(byteStream.toByteArray());
		} catch (IOException e) {
			throw new IllegalStateException("Failed to serialize OAuth2 authorization request", e);
		}
	}

	private OAuth2AuthorizationRequest deserialize(String cookieValue) {
		byte[] bytes = Base64.getUrlDecoder().decode(cookieValue);
		try (ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
			return (OAuth2AuthorizationRequest) objectStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new IllegalStateException("Failed to deserialize OAuth2 authorization request", e);
		}
	}
}
