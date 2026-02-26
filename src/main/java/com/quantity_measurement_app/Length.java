package com.quantity_measurement_app;

import java.util.Objects;

//this length class supports equality comparison and unit to unit conversion
public class Length {
	private final double value;
	private final LengthUnit unit;
	private static final double EPSILON = 1e-4;

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

	private double toBase() {
		return unit.convertToBaseUnit(value);
	}

	@Override
	public boolean equals(Object obj) {

	    if (this == obj) return true;
	    if (!(obj instanceof Length)) return false;

	    Length other = (Length) obj;

	    // Convert other into this unit before comparing
	    Length converted = other.convertTo(this.unit);

	    return Math.abs(this.value - converted.value) < EPSILON;
	}

	@Override
	public int hashCode() {
		return Objects.hash(toBase());
	}

	// UC5 --- unit conversion
	// static conversion method, it converts a numeric value from source unit to
	// target unit.
	public static double convert(double value, LengthUnit from, LengthUnit to) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}

		if (from == null || to == null) {
			throw new IllegalArgumentException("Units cannot be null");
		}

		double baseValue = from.convertToBaseUnit(value);
		return to.convertFromBaseUnit(baseValue);
	}

	// instance conversion method, it converts this Length object to target unit and
	// returns new immutable
	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double baseValue = this.toBase();
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new Length(convertedValue, targetUnit);
	}

	// UC6 --- addition
	// instance method
	public Length add(Length other) {
		if (other == null) {
			throw new IllegalArgumentException("Length cannot be null");
		}

		double baseSum = this.toBase() + other.toBase();
		double resultValue = unit.convertFromBaseUnit(baseSum);

		return new Length(resultValue, unit);
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

	// UC7 --- addition with target
	public static Length add(Length l1, Length l2, LengthUnit targetUnit) {
		if (l1 == null || l2 == null || targetUnit == null) {
			throw new IllegalArgumentException("Invalid input");
		}

		double baseSum = l1.toBase() + l2.toBase();
		double resultValue = targetUnit.convertFromBaseUnit(baseSum);

		return new Length(resultValue, targetUnit);
	}

	// equality
	public boolean compare(Length other) {
		if (other == null)
			return false;

		return Math.abs(this.toBase() - other.toBase()) < EPSILON;
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}
}