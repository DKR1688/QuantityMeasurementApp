package com.quantity_measurement_app;

import java.util.Objects;
// UC10 ---
// generic measurement container capable of handling multiple unit categories like Length, Weight without duplication.
// this class replaces category-specific implementations from UC1–UC9
public final class Quantity<U extends IMeasurable> {
    private static final double EPSILON = 1e-4;
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
        	throw new IllegalArgumentException("Unit cannot be null");
        }

        if (!Double.isFinite(value)) {
        	throw new IllegalArgumentException("Value must be finite");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // conversion
    public Quantity<U> convertTo(U targetUnit) {
        Objects.requireNonNull(targetUnit, "Target unit cannot be null");

        double baseValue = toBaseUnit();
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(round(converted), targetUnit);
    }

    // addition
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        Objects.requireNonNull(other, "Other quantity cannot be null");
        Objects.requireNonNull(targetUnit, "Target unit cannot be null");

        double sumBase = this.toBaseUnit() + other.toBaseUnit();
        double finalValue = targetUnit.convertFromBaseUnit(sumBase);

        return new Quantity<>(round(finalValue), targetUnit);
    }
    
    // UC12 ---
    // subtraction
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }
    
    // it subtracts another quantity from this quantity and ans will be returned in target unit
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        if (other == null) {
        	throw new IllegalArgumentException("Other quantity cannot be null");
        }

        if (targetUnit == null) {
        	throw new IllegalArgumentException("Target unit cannot be null");
        }

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
        	throw new IllegalArgumentException("Values must be finite numbers");
        }

        if (!this.unit.getClass().equals(other.unit.getClass())) {
        	throw new IllegalArgumentException("Cross-category subtraction not allowed");
        }

        // converting into base Unit
        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        double baseResult = thisBase - otherBase;

        // converting into target Unit
        double resultValue = targetUnit.convertFromBaseUnit(baseResult);
        resultValue = Math.round(resultValue * 100.0) / 100.0;

        return new Quantity<>(resultValue, targetUnit);
    }
    
    // it divides this quantity by another quantity to return a dimensionless ans
    public double divide(Quantity<U> other) {
        if (other == null) {
        	throw new IllegalArgumentException("Other quantity cannot be null");
        }

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
        	throw new IllegalArgumentException("Values must be finite numbers");
        }

        if (!this.unit.getClass().equals(other.unit.getClass())) {
        	throw new IllegalArgumentException("Cross-category division not allowed");
        }

        double otherBase = other.unit.convertToBaseUnit(other.value);
        if (otherBase == 0.0) {
        	throw new ArithmeticException("Division by zero not allowed");
        }

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double result = thisBase / otherBase;

        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
        	return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
        	return false;
        }

        Quantity<?> other = (Quantity<?>) obj;

        // prevent cross-category comparison
        if (!this.unit.getClass().equals(other.unit.getClass())) {
        	return false;
        }

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        long rounded = Math.round(baseValue / EPSILON);
        return Long.hashCode(rounded);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}