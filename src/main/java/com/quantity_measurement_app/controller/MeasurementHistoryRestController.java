package com.quantity_measurement_app.controller;

import com.quantity_measurement_app.dto.QuantityMeasurementDTO;
import com.quantity_measurement_app.service.IQuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
			return buildResponse(measurements);
		} catch (Exception e) {
			return handleException("Getting all measurements", e);
		}
	}

	@GetMapping("/operation/{operation}")
	public ResponseEntity<?> getMeasurementsByOperation(@PathVariable String operation) {
		try {
			List<QuantityMeasurementDTO> measurements = service.getMeasurementsByOperation(operation);
			return buildResponse(measurements);
		} catch (Exception e) {
			return handleException("Getting measurements by operation", e);
		}
	}

	@GetMapping("/type/{measurementType}")
	public ResponseEntity<?> getMeasurementsByType(@PathVariable String measurementType) {
		try {
			List<QuantityMeasurementDTO> measurements = service.getMeasurementsByType(measurementType);
			return buildResponse(measurements);
		} catch (Exception e) {
			return handleException("Getting measurements by type", e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		try {
			service.deleteById(id);

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Deleted successfully");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			return handleException("Deleting measurement", e);
		}
	}

//	@DeleteMapping
//	public ResponseEntity<?> deleteAll(Authentication authentication) {
//		try {
//			String email = authentication.getName();
//
//			service.deleteAllByUserEmail(email);
//
//			Map<String, Object> response = new HashMap<>();
//			response.put("success", true);
//			response.put("message", "All history deleted");
//
//			return ResponseEntity.ok(response);
//
//		} catch (Exception e) {
//			return handleException("Deleting all measurements", e);
//		}
//	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteAll(
	        @RequestParam(required = false) String operation,
	        @RequestParam(required = false) String measurementType,
	        Authentication authentication) {

	    try {
	        String email = authentication.getName();

	        service.deleteFiltered(email, operation, measurementType);

	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("message", "Filtered history deleted");

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        return handleException("Deleting filtered measurements", e);
	    }
	}

	// COMMON RESPONSE BUILDER
	private ResponseEntity<?> buildResponse(List<QuantityMeasurementDTO> measurements) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("total", measurements.size());
		response.put("measurements", measurements);
		return ResponseEntity.ok(response);
	}

	// ERROR HANDLER
	private ResponseEntity<?> handleException(String operation, Exception e) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("success", false);
		errorResponse.put("operation", operation);
		errorResponse.put("error", e.getMessage());
		return ResponseEntity.status(500).body(errorResponse);
	}
}