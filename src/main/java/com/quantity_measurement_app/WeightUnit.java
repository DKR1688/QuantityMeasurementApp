package com.quantity_measurement_app;

// enum representing weight units.
// base unit is GRAM
public enum WeightUnit {
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

	public double convertToBaseUnit(double value) {
		return round(value * conversionFactorToGram);
	}

	public double convertFromBaseUnit(double baseValue) {
		return round(baseValue / conversionFactorToGram);
	}

	// converting value from one unit to another
	public static double convert(double value, WeightUnit from, WeightUnit to) {
		double baseValue = from.convertToBaseUnit(value);
		return to.convertFromBaseUnit(baseValue);
	}

	// rounding to 2 decimal places
	private static double round(double value) {
		return Math.round(value * 100.0) / 100.0;
	}
}