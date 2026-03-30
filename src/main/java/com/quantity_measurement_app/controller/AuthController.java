package com.quantity_measurement_app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.quantity_measurement_app.model.User;
import com.quantity_measurement_app.repository.UserRepository;
import com.quantity_measurement_app.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// api to user login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

			// it include role in JWT claims
			User dbUser = userRepository.findByEmail(user.getEmail())
					.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

			String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getRole());

			return ResponseEntity.ok(Map.of("token", token));

		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(Map.of("error", "Authentication failed", "details", e.getMessage()));
		}
	}

	// this is to register user
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER"); //here spring security expects ROLE_ prefix
		user.setProvider("LOCAL");

		userRepository.save(user);

		return ResponseEntity.ok(Map.of("message", "User registered successfully"));
	}
}