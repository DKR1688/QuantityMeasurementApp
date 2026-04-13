package com.quantity_measurement_app.dto;

import jakarta.validation.constraints.*;

public class QuantityDTO {

	@NotNull(message = "Value is required")
	private Double value;

	@NotBlank(message = "Unit is required")
	@Size(min = 1, max = 50, message = "Unit name must be between 1 and 50 characters")
	private String unit;

	@NotBlank(message = "Measurement type is required")
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
		return value != null && Double.isFinite(value);
	}

	@AssertTrue(message = "For length, weight, and volume, value must be greater than 0. Temperature allows zero or negative values.")
	public boolean isValidRangeByMeasurementType() {
		if (value == null || measurementType == null) {
			return false;
		}

		if ("TEMPERATUREUNIT".equalsIgnoreCase(measurementType)) {
			return true;
		}

		return value > 0;
	}

	@Override
	public String toString() {
		return "QuantityDTO{" + "value=" + value + ", unit='" + unit + '\'' + ", measurementType='" + measurementType
				+ '\'' + '}';
	}
}
