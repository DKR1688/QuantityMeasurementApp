package com.quantity_measurement_app.dto;

import jakarta.validation.constraints.*;

public class QuantityDTO {
	@NotNull(message = "Value cannot be null")
	@DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than 0")
	private Double value;

	@NotEmpty(message = "Unit cannot be empty")
	@Size(min = 1, max = 50, message = "Unit name must be between 1 and 50 characters")
	private String unit;

	@NotEmpty(message = "Measurement type cannot be empty")
	@Pattern(regexp = "LENGTHUNIT|WEIGHTUNIT|VOLUMEUNIT|TEMPERATUREUNIT", message = "Measurement type must be one of: LENGTHUNIT, WEIGHTUNIT, VOLUMEUNIT, TEMPERATUREUNIT")
	private String measurementType;

	public QuantityDTO() {
	}

	public QuantityDTO(Double value, String unit, String measurementType) {
		this.value = value;
		this.unit = unit;
		this.measurementType = measurementType;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getMeasurementType() {
		return measurementType;
	}

	public void setMeasurementType(String measurementType) {
		this.measurementType = measurementType;
	}

	@AssertTrue(message = "Value must be a finite number")
	public boolean isValidValue() {
		if (value == null) {
			return false;
		}
		return Double.isFinite(value);
	}
}
