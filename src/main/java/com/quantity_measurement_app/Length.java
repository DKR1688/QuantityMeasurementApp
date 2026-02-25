package com.quantity_measurement_app;

import java.util.Objects;

//this length class supports equality comparison and unit to unit conversion
public class Length {
	private final double value;
	private final LengthUnit unit;

	// it defining length units and conversion factor
	public enum LengthUnit {
		INCHES(1.0), // Base unit
		FEET(12.0), // 1 foot = 12 inches
		YARDS(36.0), // 1 yard = 36 inches
		CENTIMETERS(0.393701); // 1 cm = 0.393701 inches

		private final double conversionFactor;

		LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	public Length(double value, LengthUnit unit) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}

	// UC5 - static conversion method
	// it converts a numeric value from source unit to target unit.
	public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {

		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		if (sourceUnit == null || targetUnit == null) {
			throw new IllegalArgumentException("Units cannot be null");
		}

		// converting to base unit (inches)
		double baseValue = value * sourceUnit.getConversionFactor();

		// converting to target unit
		return baseValue / targetUnit.getConversionFactor();
	}

	// UC5 - instance conversion method
	// it converts this Length object to target unit and returns new immutable
	// Length instance.
	public Length convertTo(LengthUnit targetUnit) {
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new Length(convertedValue, targetUnit);
	}

	private double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

	public boolean compare(Length other) {
		return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		Length other = (Length) obj;
		return this.compare(other);
	}

	@Override
	public int hashCode() {
		return Objects.hash(convertToBaseUnit());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}
}