package com.quantity_measurement_app.dto;

import com.quantity_measurement_app.model.QuantityMeasurementEntity;

import com.quantity_measurement_app.model.OperationType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementDTO {
	private Long id;

	@NotEmpty(message = "Operation cannot be empty")
	@Size(min = 1, max = 50, message = "Operation must be between 1 and 50 characters")
	private String operation;

	@NotEmpty(message = "Operand1 cannot be empty")
	private String operand1;

	private String operand2;

	private String result;

	private String error;

	@NotEmpty(message = "Measurement type cannot be empty")
	@Pattern(regexp = "LENGTHUNIT|WEIGHTUNIT|VOLUMEUNIT|TEMPERATUREUNIT", message = "Measurement type must be one of: LENGTHUNIT, WEIGHTUNIT, VOLUMEUNIT, TEMPERATUREUNIT")
	private String measurementType;

	@NotNull(message = "Operation type cannot be null")
	private OperationType operationType;

	@NotNull(message = "IsError flag cannot be null")
	private Boolean isError;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public QuantityMeasurementDTO() {
	}

	public QuantityMeasurementDTO(Long id, String operation, String operand1, String operand2, String result,
			String error, String measurementType, OperationType operationType, Boolean isError, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.operation = operation;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.result = result;
		this.error = error;
		this.measurementType = measurementType;
		this.operationType = operationType;
		this.isError = isError;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperand1() {
		return operand1;
	}

	public void setOperand1(String operand1) {
		this.operand1 = operand1;
	}

	public String getOperand2() {
		return operand2;
	}

	public void setOperand2(String operand2) {
		this.operand2 = operand2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	// it convert QuantityMeasurementEntity to QuantityMeasurementDTO
	public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
		if (entity == null) {
			return null;
		}

		QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
		dto.setId(entity.getId());
		dto.setOperation(entity.getOperation());
		dto.setOperand1(entity.getOperand1());
		dto.setOperand2(entity.getOperand2());
		dto.setResult(entity.getResult());
		dto.setError(entity.getError());
		dto.setMeasurementType(entity.getMeasurementType());
		dto.setIsError(entity.isError());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setUpdatedAt(entity.getUpdatedAt());

		// it try to map operation string to OperationType enum
		try {
			dto.setOperationType(OperationType.fromString(entity.getOperation()));
		} catch (IllegalArgumentException e) {
			dto.setOperationType(null);
		}

		return dto;
	}

	// it convert QuantityMeasurementDTO to QuantityMeasurementEntity
	public QuantityMeasurementEntity toEntity() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
		entity.setId(this.id);
		entity.setOperation(this.operation);
		entity.setOperand1(this.operand1);
		entity.setOperand2(this.operand2);
		entity.setResult(this.result);
		entity.setError(this.error);
		entity.setMeasurementType(this.measurementType);
		entity.setError(this.isError ? this.error : null);
		entity.setCreatedAt(this.createdAt);
		entity.setUpdatedAt(this.updatedAt);

		return entity;
	}

	// it convert list of QuantityMeasurementEntity to list of quantity measurement dto
	public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
		if (entities == null) {
			return List.of();
		}

		return entities.stream().map(QuantityMeasurementDTO::fromEntity).collect(Collectors.toList());
	}

	// it convert list of QuantityMeasurementDTO to list of quantity measurement entity
	public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
		if (dtos == null) {
			return List.of();
		}

		return dtos.stream().map(dto -> {
			QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
			entity.setId(dto.id);
			entity.setOperation(dto.operation);
			entity.setOperand1(dto.operand1);
			entity.setOperand2(dto.operand2);
			entity.setResult(dto.result);
			entity.setError(dto.error);
			entity.setMeasurementType(dto.measurementType);
			entity.setCreatedAt(dto.createdAt);
			entity.setUpdatedAt(dto.updatedAt);
			return entity;
		}).collect(Collectors.toList());
	}
}
