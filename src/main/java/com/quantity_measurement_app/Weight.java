package com.quantity_measurement_app;

import java.util.Objects;

import com.quantity_measurement_app.units.WeightUnit;

public final class Weight {
	private static final double EPSILON = 1e-6;
	private final double value;
	private final WeightUnit unit;

	public Weight(double value, WeightUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}

		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Invalid numeric value");
		}

		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public WeightUnit getUnit() {
		return unit;
	}

	// public API methods converts this quantity to target unit
	public Weight convertTo(WeightUnit targetUnit) {
		Objects.requireNonNull(targetUnit, "Target unit cannot be null");

		if (this.unit == targetUnit) {
			return this;
		}

		double baseValue = toBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(baseValue);

		return new Weight(converted, targetUnit);
	}

	// Adds two quantities result will be returned in first operand unit.

	public Weight add(Weight other) {
		return add(other, this.unit);
	}

	// Adds two quantities and returns result in target unit.

	public Weight add(Weight other, WeightUnit targetUnit) {
		Objects.requireNonNull(other, "Other quantity cannot be null");
		Objects.requireNonNull(targetUnit, "Target unit cannot be null");

		double sumBase = this.toBaseUnit() + other.toBaseUnit();
		double finalValue = targetUnit.convertFromBaseUnit(sumBase);

		return new Weight(finalValue, targetUnit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Weight other)) {
			return false;
		}

		return Math.abs(this.toBaseUnit() - other.toBaseUnit()) <= EPSILON;
	}

	@Override
	public int hashCode() {
		long normalized = Math.round(toBaseUnit() / EPSILON);
		return Long.hashCode(normalized);
	}

	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	@Override
	public String toString() {
		return value + " " + unit.name();
	}
}