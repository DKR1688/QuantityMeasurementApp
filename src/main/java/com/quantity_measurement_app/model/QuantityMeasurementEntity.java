package com.quantity_measurement_app.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements", indexes = { @Index(name = "idx_operation", columnList = "operation"),
		@Index(name = "idx_measurement_type", columnList = "measurement_type"),
		@Index(name = "idx_created_at", columnList = "created_at") })
public class QuantityMeasurementEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public QuantityMeasurementEntity() {
	}

	public QuantityMeasurementEntity(Long id, String operation, String operand1, String operand2, String result,
			String error, String measurementType, Boolean errorFlag, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.operation = operation;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.result = result;
		this.error = error;
		this.measurementType = measurementType;
		this.errorFlag = errorFlag;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public QuantityMeasurementEntity(String operation, String operand1, String operand2, String result) {
		this.operation = operation;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.result = result;
		this.errorFlag = false;
	}

	public QuantityMeasurementEntity(String operation, String error) {
		this.operation = operation;
		this.error = error;
		this.errorFlag = true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String operation;

	@Column(name = "operand1", nullable = true)
	private String operand1;

	@Column(name = "operand2", nullable = true)
	private String operand2;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String result;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String error;

	@Column(name = "measurement_type", nullable = true, length = 50)
	private String measurementType;

	@Column(nullable = false)
	private Boolean errorFlag = false;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = true)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		errorFlag = (error != null);
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
		errorFlag = (error != null);
	}

	public boolean isError() {
		return errorFlag != null && errorFlag;
	}

	public boolean hasError() {
		return error != null;
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

	public Boolean getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(Boolean errorFlag) {
		this.errorFlag = errorFlag;
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
}
