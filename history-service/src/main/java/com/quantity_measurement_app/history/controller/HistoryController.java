package com.quantity_measurement_app.history.controller;

import com.quantity_measurement_app.history.model.QuantityMeasurementEntity;
import com.quantity_measurement_app.history.security.JwtUtil;
import com.quantity_measurement_app.history.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/measurements")
public class HistoryController {

    private final IHistoryService service;
    private final JwtUtil jwtUtil;

    @Autowired
    public HistoryController(IHistoryService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<?> getAllMeasurements(@RequestHeader("Authorization") String authHeader) {
        try {
            validateAuthHeader(authHeader);
            String token = authHeader.replace("Bearer ", "");
            String userEmail = jwtUtil.extractEmail(token);
            List<QuantityMeasurementEntity> measurements = service.getAllMeasurements(userEmail);
            return buildResponse(measurements);
        } catch (Exception e) {
            return handleException("Getting all measurements", e);
        }
    }

    @GetMapping("/operation/{operation}")
    public ResponseEntity<?> getMeasurementsByOperation(@PathVariable String operation, @RequestHeader("Authorization") String authHeader) {
        try {
            validateAuthHeader(authHeader);
            String token = authHeader.replace("Bearer ", "");
            String userEmail = jwtUtil.extractEmail(token);
            List<QuantityMeasurementEntity> measurements = service.getMeasurementsByOperation(operation, userEmail);
            return buildResponse(measurements);
        } catch (Exception e) {
            return handleException("Getting measurements by operation", e);
        }
    }

    @GetMapping("/type/{measurementType}")
    public ResponseEntity<?> getMeasurementsByType(@PathVariable String measurementType, @RequestHeader("Authorization") String authHeader) {
        try {
            validateAuthHeader(authHeader);
            String token = authHeader.replace("Bearer ", "");
            String userEmail = jwtUtil.extractEmail(token);
            List<QuantityMeasurementEntity> measurements = service.getMeasurementsByType(measurementType, userEmail);
            return buildResponse(measurements);
        } catch (Exception e) {
            return handleException("Getting measurements by type", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        try {
            validateAuthHeader(authHeader);
            String token = authHeader.replace("Bearer ", "");
            String userEmail = jwtUtil.extractEmail(token);
            service.deleteById(id, userEmail);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Deleted successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return handleException("Deleting measurement", e);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String measurementType,
            @RequestHeader("Authorization") String authHeader) {

        try {
            validateAuthHeader(authHeader);
            String token = authHeader.replace("Bearer ", "");
            String userEmail = jwtUtil.extractEmail(token);

            service.deleteFiltered(userEmail, operation, measurementType);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Filtered history deleted");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return handleException("Deleting filtered measurements", e);
        }
    }

    // COMMON RESPONSE BUILDER
    private ResponseEntity<?> buildResponse(List<QuantityMeasurementEntity> measurements) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("total", measurements.size());
        response.put("measurements", measurements);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMeasurement(@RequestBody Map<String, Object> data) {
        try {
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
            entity.setOperation((String) data.get("operation"));
            entity.setMeasurementType((String) data.get("measurementType"));
            entity.setOperand1((String) data.get("operand1"));
            entity.setOperand2((String) data.get("operand2"));
            entity.setResult((String) data.get("result"));
            entity.setUserEmail((String) data.get("userEmail"));
            String error = (String) data.get("error");
            if (error != null && !error.isEmpty()) {
                entity.setError(error);
            }
            service.saveMeasurement(entity);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return handleException("Saving measurement", e);
        }
    }

    private void validateAuthHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }
    }

    private ResponseEntity<?> handleException(String operation, Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Error during " + operation + ": " + e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}