package com.quantity_measurement_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("error", "Validation Failed");

		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

		response.put("fieldErrors", fieldErrors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleMissingRequestParameters(
			org.springframework.web.bind.MissingServletRequestParameterException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("error", "Missing Request Parameter");
		response.put("message", ex.getParameterName() + " is required");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("error", "Invalid Parameter Type");
		response.put("message", "Parameter " + ex.getName() + " is invalid");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(QuantityMeasurementException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleQuantityMeasurementException(QuantityMeasurementException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("error", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DatabaseException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleDatabaseException(DatabaseException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.put("error", "Database operation failed");
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleGeneralException(Exception ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("success", false);
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.put("error", "Internal Server Error");
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
