package com.quantity_measurement_app.units;

//UC8 --- standalone enum for conversion

// UC10 ---
// this enum implements IMeasurable to allow generic handling within Quantity<U>.
// it encapsulate conversion logic
public enum LengthUnit implements IMeasurable {
	INCHES(1.0), // Base unit
	FEET(12.0), // 1 foot = 12 inches
	YARDS(36.0), // 1 yard = 36 inches
	CENTIMETERS(0.393701); // 1 cm = 0.393701 inches

	private final double conversionFactor;

	LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	// it convert value this to base unit
	@Override
	public double convertToBaseUnit(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		return value * conversionFactor;
	}

	// it convert value from base to this unit
	@Override
	public double convertFromBaseUnit(double baseValue) {
		if (!Double.isFinite(baseValue)) {
			throw new IllegalArgumentException("Base value must be finite");
		}
		return baseValue / conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}
}
