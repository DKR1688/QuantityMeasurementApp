package com.quantity_measurement_app;

public class QuantityMeasurementApp {
	// UC10 ---
	// this replaces multiple category-specific demonstration methods from UC9 with
	// a single generic implementation.
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

	private static void demonstrateSubtraction() {
		System.out.println("Subtraction for implicit target unit -----");
		Quantity<LengthUnit> length1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> length2 = new Quantity<>(6.0, LengthUnit.INCHES);
	    System.out.println("Input: " + length1 + " - " + length2);
	    System.out.println("Result: " + length1.subtract(length2));
	    System.out.println();

		Quantity<WeightUnit> weight1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> weight2 = new Quantity<>(5000.0, WeightUnit.GRAM);
	    System.out.println("Input: " + weight1 + " - " + weight2);
	    System.out.println("Result: " + weight1.subtract(weight2));
	    System.out.println();

		System.out.println("Subtraction for explicit target unit) -----");    
		System.out.println("Input: " + length1 + " - " + length2 + " (INCHES)");
	    System.out.println("Result: " + length1.subtract(length2, LengthUnit.INCHES));
	    System.out.println();
	}

	private static void demonstrateDivision() {
		System.out.println("Division -----");
		Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(2.0, LengthUnit.FEET);    
		System.out.println("Input: " + l1 + " / " + l2);
	    System.out.println("Result: " + l1.divide(l2));
	    System.out.println();

		Quantity<LengthUnit> l3 = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> l4 = new Quantity<>(2.0, LengthUnit.FEET);    
		System.out.println("Input: " + l3 + " / " + l4);
	    System.out.println("Result: " + l3.divide(l4));
	    System.out.println();
	    
	    Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
	    Quantity<VolumeUnit> v2 = new Quantity<>(10.0, VolumeUnit.LITRE);
	    System.out.println("Result < 1: " + v1.divide(v2));
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

		// for volume
		// UC11 ---
		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

		demonstrateEquality(v1, v2);
		demonstrateConversion(v3, VolumeUnit.LITRE);
		demonstrateAddition(v1, v2, VolumeUnit.LITRE);
		
		// UC12 ---
	    demonstrateSubtraction();
	    demonstrateDivision();

	}
}