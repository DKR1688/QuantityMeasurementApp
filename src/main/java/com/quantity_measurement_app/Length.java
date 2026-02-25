package com.quantity_measurement_app;

import java.util.Objects;

public class Length {
	private final double value;
	private final LengthUnit unit;

	// enum for units (base unit=inches)
	public enum LengthUnit {
		FEET(12.0), INCHES(1.0);

		private final double conversionFactor;

		LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	public Length(double value, LengthUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	private double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

	public boolean compare(Length other) {
		return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Length other = (Length) obj;
		return this.compare(other);
	}

	@Override
	public int hashCode() {
		return Objects.hash(convertToBaseUnit());
	}
}
