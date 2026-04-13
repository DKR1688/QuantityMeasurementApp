package com.quantity_measurement_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userService;

	public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userService) {
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {

		String path = req.getRequestURI();
		if (isPublicPath(path)) {
			chain.doFilter(req, res);
			return;
		}

		String header = req.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				String token = header.substring(7);
				String username = jwtUtil.extractEmail(token);
				var userDetails = userService.loadUserByUsername(username);

				if (jwtUtil.validateToken(token, username)) {
					var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (Exception ignored) {
				SecurityContextHolder.clearContext();
			}
		}

		chain.doFilter(req, res);
	}

	private boolean isPublicPath(String path) {
		return path.startsWith("/api/auth/") || path.startsWith("/oauth2/") || path.startsWith("/login/")
				|| path.equals("/api/v1/quantities/health");
	}
}
