package com.quantity_measurement_app.units;

// UC10 --- 
// this interface defines a contract for all measurement unit categories
// remove duplication across LengthUnit and WeightUnit and enable generic programming through bounded type parameters.
public interface IMeasurable {
	double getConversionFactor(); // it is relative to base unit
	double convertToBaseUnit(double value);
	double convertFromBaseUnit(double baseValue);
	String getUnitName();
	
	// UC14 ---
	// by default all units support arithmetic
    default boolean supportsArithmetic() {
        return true;
    }

    // validation hook
    default void validateOperationSupport(String operation) {
        if (!supportsArithmetic()) {
            throw new UnsupportedOperationException(
                getUnitName() + " does not support " + operation
            );
        }
    }
    
	// UC15 as helper method
	default String getMeasurementType() {
		return this.getClass().getSimpleName();
	}

	static IMeasurable getUnitFromName(String name, Class<? extends Enum<?>> enumClass) {
		for (Object constant : enumClass.getEnumConstants()) {
			IMeasurable unit = (IMeasurable) constant;
			if (unit.getUnitName().equalsIgnoreCase(name)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid unit: " + name);
	}
	
}