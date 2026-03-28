package com.quantity_measurement_app.controller;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.dto.QuantityInputDTO;
import com.quantity_measurement_app.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/quantities")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuantityMeasurementRestController {

	private final IQuantityMeasurementService service;

	@Autowired
	public QuantityMeasurementRestController(IQuantityMeasurementService service) {
		this.service = service;
	}

	@PostMapping("/convert")
	public ResponseEntity<?> convert(@Valid @RequestBody QuantityDTO input, @RequestParam String targetUnit) {
		try {
			QuantityDTO result = service.convert(input, targetUnit);
			return ResponseEntity.ok(Map.of("success", true, "operation", "CONVERT", "input", input, "targetUnit",
					targetUnit, "result", result));
		} catch (Exception e) {
			return handleException("CONVERT", e);
		}
	}

	@PostMapping("/compare")
	public ResponseEntity<?> compare(@Valid @RequestBody QuantityInputDTO request) {
		try {
			validateRequest(request);
			boolean result = service.compare(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			return ResponseEntity.ok(Map.of("success", true, "operation", "COMPARE", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "isEqual", result));
		} catch (Exception e) {
			return handleException("COMPARE", e);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody QuantityInputDTO request) {
		try {
			validateRequest(request);
			QuantityDTO result = service.add(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			return ResponseEntity.ok(Map.of("success", true, "operation", "ADD", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "result", result));
		} catch (Exception e) {
			return handleException("ADD", e);
		}
	}

	@PostMapping("/subtract")
	public ResponseEntity<?> subtract(@Valid @RequestBody QuantityInputDTO request) {
		try {
			validateRequest(request);
			QuantityDTO result = service.subtract(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			return ResponseEntity.ok(Map.of("success", true, "operation", "SUBTRACT", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "result", result));
		} catch (Exception e) {
			return handleException("SUBTRACT", e);
		}
	}

	@PostMapping("/divide")
	public ResponseEntity<?> divide(@Valid @RequestBody QuantityInputDTO request) {
		try {
			validateRequest(request);
			double result = service.divide(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			return ResponseEntity.ok(Map.of("success", true, "operation", "DIVIDE", "quantity1",
					request.getThisQuantityDTO(), "quantity2", request.getThatQuantityDTO(), "result", result));
		} catch (Exception e) {
			return handleException("DIVIDE", e);
		}
	}

	@GetMapping("/health")
	public ResponseEntity<?> health() {
		return ResponseEntity.ok(Map.of("status", "UP", "message", "Quantity Measurement Service is running"));
	}

	private void validateRequest(QuantityInputDTO request) {
		if (request.getThisQuantityDTO() == null || request.getThatQuantityDTO() == null) {
			throw new IllegalArgumentException("Both thisQuantityDTO and thatQuantityDTO are required");
		}
	}

	private ResponseEntity<?> handleException(String operation, Exception e) {
		HttpStatus status = (e instanceof IllegalArgumentException) ? HttpStatus.BAD_REQUEST
				: HttpStatus.INTERNAL_SERVER_ERROR;
		return ResponseEntity.status(status)
				.body(Map.of("success", false, "operation", operation, "error", e.getMessage()));
	}
}