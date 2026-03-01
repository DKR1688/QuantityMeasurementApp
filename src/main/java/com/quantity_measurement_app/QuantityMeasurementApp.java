package com.quantity_measurement_app;

public class QuantityMeasurementApp {
	// UC10 ---
	// this replaces multiple category-specific demonstration methods from UC9 with a single generic implementation.
	// this eliminates duplicate demo logic
	public static <U extends IMeasurable> void demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " and " + q2);
		System.out.println("Equal: " + q1.equals(q2));
		System.out.println();
	}

	public static <U extends IMeasurable> void demonstrateConversion(Quantity<U> quantity, U targetUnit) {
		System.out.println("Input: " + quantity);
		System.out.println("Converted: " + quantity.convertTo(targetUnit));
		System.out.println();
	}

	public static <U extends IMeasurable> void demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
		System.out.println("Input: " + q1 + " + " + q2);
		System.out.println("Result: " + q1.add(q2, targetUnit));
		System.out.println();
	}

	public static void main(String[] args) {
		// for length
		Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

		demonstrateEquality(l1, l2);
		demonstrateConversion(l1, LengthUnit.INCHES);
		demonstrateAddition(l1, l2, LengthUnit.FEET);

		// for weight
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		demonstrateEquality(w1, w2);
		demonstrateConversion(w1, WeightUnit.GRAM);
		demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);
	}
}