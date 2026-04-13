package com.quantity_measurement_app.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.quantity_measurement_app.auth.model.User;
import com.quantity_measurement_app.auth.repository.UserRepository;
import com.quantity_measurement_app.auth.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthController(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// api to user login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		try {
			User dbUser = userRepository.findByEmail(user.getEmail())
					.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

			if (!"LOCAL".equalsIgnoreCase(dbUser.getProvider())) {
				return ResponseEntity.status(400).body(Map.of("error", "This account uses Google login. Please continue with Google."));
			}

			if (dbUser.getPassword() == null || dbUser.getPassword().isBlank()
					|| !passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
				throw new BadCredentialsException("Invalid credentials");
			}

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
