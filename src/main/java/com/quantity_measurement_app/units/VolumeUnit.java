package com.quantity_measurement_app.units;

// UC11 --- Volume measurement units
// base unit is LITRE
public enum VolumeUnit implements IMeasurable {
	LITRE(1.0), // base unit
	MILLILITRE(0.001), // 1 mL = 0.001 L
	GALLON(3.78541); // 1 US gallon ≈ 3.78541 L

	private final double conversionFactorToLitre;
	VolumeUnit(double conversionFactorToLitre) {
		this.conversionFactorToLitre = conversionFactorToLitre;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactorToLitre;
	}

	@Override
	public double convertToBaseUnit(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		return value * conversionFactorToLitre;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		if (!Double.isFinite(baseValue)) {
			throw new IllegalArgumentException("Base value must be finite");
		}
		return baseValue / conversionFactorToLitre;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}
}