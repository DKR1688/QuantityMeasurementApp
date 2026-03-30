package com.quantity_measurement_app.controller;

import com.quantity_measurement_app.dto.QuantityMeasurementDTO;
import com.quantity_measurement_app.service.IQuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/measurements")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MeasurementHistoryRestController {

	private final IQuantityMeasurementService service;

	@Autowired
	public MeasurementHistoryRestController(IQuantityMeasurementService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> getAllMeasurements() {
		try {
			List<QuantityMeasurementDTO> measurements = service.getAllMeasurements();
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("total", measurements.size());
			response.put("measurements", measurements);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Getting all measurements", e);
		}
	}

	@GetMapping("/operation/{operation}")
	public ResponseEntity<?> getMeasurementsByOperation(@PathVariable String operation) {
		try {
			List<QuantityMeasurementDTO> measurements = service.getMeasurementsByOperation(operation);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("operation", operation);
			response.put("total", measurements.size());
			response.put("measurements", measurements);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Getting measurements by operation", e);
		}
	}

	@GetMapping("/type/{measurementType}")
	public ResponseEntity<?> getMeasurementsByType(@PathVariable String measurementType) {
		try {
			List<QuantityMeasurementDTO> measurements = service.getMeasurementsByType(measurementType);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("measurementType", measurementType);
			response.put("total", measurements.size());
			response.put("measurements", measurements);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Getting measurements by type", e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getMeasurementById(@PathVariable Long id) {
		try {
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Measurement retrieval by ID endpoint - to be implemented");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Getting measurement by ID", e);
		}
	}

	@DeleteMapping("/clear")
	public ResponseEntity<?> clearHistory() {
		try {
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Clear history endpoint - to be implemented");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return handleException("Clearing history", e);
		}
	}

	private ResponseEntity<?> handleException(String operation, Exception e) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("success", false);
		errorResponse.put("operation", operation);
		errorResponse.put("error", e.getMessage());
		return ResponseEntity.status(500).body(errorResponse);
	}
}
