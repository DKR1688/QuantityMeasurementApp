package com.quantity_measurement_app.controller;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.dto.QuantityInputDTO;
import com.quantity_measurement_app.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("operation", "CONVERT");
			response.put("input", input);
			response.put("targetUnit", targetUnit);
			response.put("result", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Conversion", e);
		}
	}

	@PostMapping("/compare")
	public ResponseEntity<?> compare(@Valid @RequestBody QuantityInputDTO request) {
		try {
			if (request.getThisQuantityDTO() == null || request.getThatQuantityDTO() == null) {
				return ResponseEntity.badRequest().body(
						Map.of("success", false, "error", "Both thisQuantityDTO and thatQuantityDTO are required"));
			}

			boolean result = service.compare(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("operation", "COMPARE");
			response.put("quantity1", request.getThisQuantityDTO());
			response.put("quantity2", request.getThatQuantityDTO());
			response.put("isEqual", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Comparison", e);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody QuantityInputDTO request) {
		try {
			if (request.getThisQuantityDTO() == null || request.getThatQuantityDTO() == null) {
				return ResponseEntity.badRequest().body(
						Map.of("success", false, "error", "Both thisQuantityDTO and thatQuantityDTO are required"));
			}

			QuantityDTO result = service.add(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("operation", "ADD");
			response.put("quantity1", request.getThisQuantityDTO());
			response.put("quantity2", request.getThatQuantityDTO());
			response.put("result", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Addition", e);
		}
	}

	@PostMapping("/subtract")
	public ResponseEntity<?> subtract(@Valid @RequestBody QuantityInputDTO request) {
		try {
			if (request.getThisQuantityDTO() == null || request.getThatQuantityDTO() == null) {
				return ResponseEntity.badRequest().body(
						Map.of("success", false, "error", "Both thisQuantityDTO and thatQuantityDTO are required"));
			}

			QuantityDTO result = service.subtract(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("operation", "SUBTRACT");
			response.put("quantity1", request.getThisQuantityDTO());
			response.put("quantity2", request.getThatQuantityDTO());
			response.put("result", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Subtraction", e);
		}
	}

	@PostMapping("/divide")
	public ResponseEntity<?> divide(@Valid @RequestBody QuantityInputDTO request) {
		try {
			if (request.getThisQuantityDTO() == null || request.getThatQuantityDTO() == null) {
				return ResponseEntity.badRequest().body(
						Map.of("success", false, "error", "Both thisQuantityDTO and thatQuantityDTO are required"));
			}

			double result = service.divide(request.getThisQuantityDTO(), request.getThatQuantityDTO());
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("operation", "DIVIDE");
			response.put("quantity1", request.getThisQuantityDTO());
			response.put("quantity2", request.getThatQuantityDTO());
			response.put("result", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Division", e);
		}
	}

	@GetMapping("/health")
	public ResponseEntity<?> health() {
		return ResponseEntity.ok(Map.of("status", "UP", "message", "Quantity Measurement Service is running"));
	}

	private ResponseEntity<?> handleException(String operation, Exception e) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("success", false);
		errorResponse.put("operation", operation);
		errorResponse.put("error", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
