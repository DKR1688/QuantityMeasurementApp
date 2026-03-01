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