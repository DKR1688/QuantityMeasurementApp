package com.quantity_measurement_app.auth.controller;

import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.quantity_measurement_app.auth.dto.AuthRequest;
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
	public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
		try {
			User dbUser = userRepository.findByEmail(request.getEmail().trim())
					.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

			if (!"LOCAL".equalsIgnoreCase(dbUser.getProvider())) {
				return ResponseEntity.status(400).body(Map.of("error", "This account uses Google login. Please continue with Google."));
			}

			if (dbUser.getPassword() == null || dbUser.getPassword().isBlank()
					|| !passwordEncoder.matches(request.getPassword(), dbUser.getPassword())) {
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
	public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
		String email = request.getEmail().trim();

		if (userRepository.findByEmail(email).isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("error", "User already exists"));
		}

		User user = new User();
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("ROLE_USER"); //here spring security expects ROLE_ prefix
		user.setProvider("LOCAL");

		userRepository.save(user);

		return ResponseEntity.ok(Map.of("message", "User registered successfully"));
	}
}
