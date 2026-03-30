package com.quantity_measurement_app.units;

// enum representing weight units.
// base unit is GRAM

// UC10 ---
// Implements IMeasurable to enable generic processing within Quantity<U>.
public enum WeightUnit implements IMeasurable{
	MILLIGRAM(0.001), 
	GRAM(1.0), 
	KILOGRAM(1000.0), 
	POUND(453.592), 
	TONNE(1_000_000.0);

	// conversion factor relative to base unit
	private final double conversionFactorToGram;

	WeightUnit(double conversionFactorToGram) {
		this.conversionFactorToGram = conversionFactorToGram;
	}
	
	@Override
    public double getConversionFactor() {
        return conversionFactorToGram;
    }

	@Override
	public double convertToBaseUnit(double value) {
		return value * conversionFactorToGram;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactorToGram;
	}
	
	@Override
    public String getUnitName() {
        return this.name();
    }
}