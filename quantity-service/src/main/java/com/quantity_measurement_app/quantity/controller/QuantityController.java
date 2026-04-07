package com.quantity_measurement_app.quantity.controller;

import com.quantity_measurement_app.quantity.dto.QuantityDTO;
import com.quantity_measurement_app.quantity.dto.QuantityInputDTO;
import com.quantity_measurement_app.quantity.security.JwtUtil;
import com.quantity_measurement_app.quantity.service.IQuantityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityController {

	private final IQuantityService service;
	private final JwtUtil jwtUtil;
	private final RestTemplate restTemplate;

	@Autowired
	public QuantityController(IQuantityService service, JwtUtil jwtUtil, RestTemplate restTemplate) {
		this.service = service;
		this.jwtUtil = jwtUtil;
		this.restTemplate = restTemplate;
	}

	@PostMapping("/convert")
	public ResponseEntity<?> convert(@Valid @RequestBody QuantityDTO input, @RequestParam String targetUnit, @RequestHeader("Authorization") String authHeader) {
		try {
			validateAuthHeader(authHeader);
			String token = authHeader.replace("Bearer ", "");
			String userEmail = jwtUtil.extractEmail(token);
			QuantityDTO result = service.convert(input, targetUnit, userEmail);
			return ResponseEntity.ok(Map.of("success", true, "operation", "CONVERT", "input", input, "targetUnit",
					targetUnit, "result", result));
		} catch (Exception e) {
			return handleException("CONVERT", e);
		}
	}

	@PostMapping("/compare")
	public ResponseEntity<?> compare(@Valid @RequestBody QuantityInputDTO request, @RequestHeader("Authorization") String authHeader) {
		try {
			validateAuthHeader(authHeader);
			validateRequest(request);
			String token = authHeader.replace("Bearer ", "");
			String userEmail = jwtUtil.extractEmail(token);
			boolean result = service.compare(request.getThisQuantityDTO(), request.getThatQuantityDTO(), userEmail);
			return ResponseEntity.ok(Map.of("success", true, "operation", "COMPARE", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "isEqual", result));
		} catch (Exception e) {
			return handleException("COMPARE", e);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody QuantityInputDTO request, @RequestHeader("Authorization") String authHeader) {
		try {
			validateAuthHeader(authHeader);
			validateRequest(request);
			String token = authHeader.replace("Bearer ", "");
			String userEmail = jwtUtil.extractEmail(token);
			QuantityDTO result = service.add(request.getThisQuantityDTO(), request.getThatQuantityDTO(), userEmail);
			return ResponseEntity.ok(Map.of("success", true, "operation", "ADD", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "result", result));
		} catch (Exception e) {
			return handleException("ADD", e);
		}
	}

	@PostMapping("/subtract")
	public ResponseEntity<?> subtract(@Valid @RequestBody QuantityInputDTO request, @RequestHeader("Authorization") String authHeader) {
		try {
			validateAuthHeader(authHeader);
			validateRequest(request);
			String token = authHeader.replace("Bearer ", "");
			String userEmail = jwtUtil.extractEmail(token);
			QuantityDTO result = service.subtract(request.getThisQuantityDTO(), request.getThatQuantityDTO(), userEmail);
			return ResponseEntity.ok(Map.of("success", true, "operation", "SUBTRACT", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "result", result));
		} catch (Exception e) {
			return handleException("SUBTRACT", e);
		}
	}

	@PostMapping("/divide")
	public ResponseEntity<?> divide(@Valid @RequestBody QuantityInputDTO request, @RequestHeader("Authorization") String authHeader) {
		try {
			validateAuthHeader(authHeader);
			validateRequest(request);
			String token = authHeader.replace("Bearer ", "");
			String userEmail = jwtUtil.extractEmail(token);
			double result = service.divide(request.getThisQuantityDTO(), request.getThatQuantityDTO(), userEmail);
			return ResponseEntity.ok(Map.of("success", true, "operation", "DIVIDE", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "result", result));
		} catch (Exception e) {
			return handleException("DIVIDE", e);
		}
	}

	@PostMapping("/multiply")
	public ResponseEntity<?> multiply(@Valid @RequestBody QuantityInputDTO request, @RequestHeader("Authorization") String authHeader) {
		try {
			validateAuthHeader(authHeader);
			validateRequest(request);
			String token = authHeader.replace("Bearer ", "");
			String userEmail = jwtUtil.extractEmail(token);
			QuantityDTO result = service.multiply(request.getThisQuantityDTO(), request.getThatQuantityDTO(), userEmail);
			return ResponseEntity.ok(Map.of("success", true, "operation", "MULTIPLY", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "result", result));
		} catch (Exception e) {
			return handleException("MULTIPLY", e);
		}
	}

	@GetMapping("/health")
	public ResponseEntity<?> health() {
		return ResponseEntity.ok(Map.of("status", "UP", "message", "Quantity Service is running"));
	}

	private void validateRequest(QuantityInputDTO request) {
		if (request.getThisQuantityDTO() == null || request.getThatQuantityDTO() == null) {
			throw new IllegalArgumentException("Both thisQuantityDTO and thatQuantityDTO are required");
		}
	}

	private void validateAuthHeader(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Authorization header is missing or invalid");
		}
	}

	private ResponseEntity<?> handleException(String operation, Exception e) {
		HttpStatus status = (e instanceof IllegalArgumentException) ? HttpStatus.BAD_REQUEST
				: HttpStatus.INTERNAL_SERVER_ERROR;
		return ResponseEntity.status(status)
				.body(Map.of("success", false, "operation", operation, "error", e.getMessage()));
	}
}