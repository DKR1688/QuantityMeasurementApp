package com.quantity_measurement_app;

// UC10 --- 
// this interface defines a contract for all measurement unit categories
// remove duplication across LengthUnit and WeightUnit and enable generic programming through bounded type parameters.
public interface IMeasurable {
	double getConversionFactor(); // it is relative to base unit
	double convertToBaseUnit(double value);
	double convertFromBaseUnit(double baseValue);
	String getUnitName();
}