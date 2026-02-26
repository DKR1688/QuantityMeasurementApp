package com.quantity_measurement_app;

import java.util.Objects;

//this length class supports equality comparison and unit to unit conversion
public class Length {
	private final double value;
	private final LengthUnit unit;
	private static final double EPSILON = 0.0001;

	// enum defining length units and conversion factor
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

	// UC5 --- unit conversion
	// static conversion method, it converts a numeric value from source unit to
	// target unit.
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

	// instance conversion method, it converts this Length object to target unit and
	// returns new immutable
	public Length convertTo(LengthUnit targetUnit) {
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new Length(convertedValue, targetUnit);
	}

	private double convertToBaseUnit() {
		return value * unit.getConversionFactor();
	}

//	public boolean compare(Length other) {
//		return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
//	}

	// UC6 --- addition
	// instance method
	public Length add(Length other) {

		if (other == null) {
			throw new IllegalArgumentException("Length to add cannot be null");
		}

		// Convert both to base unit (inches)
		double baseSum = this.convertToBaseUnit() + other.convertToBaseUnit();

		// Convert sum back to unit of first operand
		double finalValue = baseSum / this.unit.getConversionFactor();

		return new Length(finalValue, this.unit);
	}

	// static overloaded version
	public static Length add(Length l1, Length l2) {
		if (l1 == null || l2 == null) {
			throw new IllegalArgumentException("Lengths cannot be null");
		}
		return l1.add(l2);
	}

	// raw value overloaded version
	public static Length add(double v1, LengthUnit u1, double v2, LengthUnit u2) {
		return new Length(v1, u1).add(new Length(v2, u2));
	}

	// equality
	public boolean compare(Length other) {
		if (other == null)
			return false;

		return Math.abs(this.convertToBaseUnit() - other.convertToBaseUnit()) < EPSILON;
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