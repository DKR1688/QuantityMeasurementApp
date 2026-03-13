package com.quantity_measurement_app;

import com.quantity_measurement_app.controller.QuantityMeasurementController;
import com.quantity_measurement_app.repository.QuantityMeasurementCacheRepository;
import com.quantity_measurement_app.service.IQuantityMeasurementService;
import com.quantity_measurement_app.service.QuantityMeasurementServiceImpl;
import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.units.*;
public class QuantityMeasurementApp {
	private static void demonstrateLength() {
		Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

		demonstrateEquality(l1, l2);
		demonstrateConversion(l1, LengthUnit.INCHES);
		demonstrateAddition(l1, l2, LengthUnit.FEET);
	}

	private static void demonstrateWeight() {
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		demonstrateEquality(w1, w2);
		demonstrateConversion(w1, WeightUnit.GRAM);
		demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);
	}

	private static void demonstrateVolume() {
		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

		demonstrateEquality(v1, v2);
		demonstrateConversion(v3, VolumeUnit.LITRE);
		demonstrateAddition(v1, v2, VolumeUnit.LITRE);
	}

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

	public static <U extends IMeasurable> void demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
		System.out.println("Subtraction for implicit target unit-----");
		System.out.println("Input: " + q1 + " - " + q2);
		System.out.println("Result: " + q1.subtract(q2));
		System.out.println();

		if (targetUnit != null) {
			System.out.println("Subtraction for explicit target unit-----");
			System.out.println("Input: " + q1 + " - " + q2 + " (" + targetUnit.getUnitName() + ")");
			System.out.println("Result: " + q1.subtract(q2, targetUnit));
			System.out.println();
		}
	}

	public static <U extends IMeasurable> void demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Division -----");
		System.out.println("Input: " + q1 + " / " + q2);
		System.out.println("Result: " + q1.divide(q2));
		System.out.println();
	}
	
	private static void demonstrateTemperature() {
		Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
		Quantity<TemperatureUnit> t3 = new Quantity<>(273.15, TemperatureUnit.KELVIN);

		// equality
		demonstrateEquality(t1, t2);

		// conversion
		demonstrateConversion(t1, TemperatureUnit.FAHRENHEIT);
		demonstrateConversion(t2, TemperatureUnit.CELSIUS);
		demonstrateConversion(t3, TemperatureUnit.CELSIUS);

		// arithmetic (should fail)
		try {
			demonstrateAddition(t1, t2, TemperatureUnit.CELSIUS);
		} catch (UnsupportedOperationException e) {
			System.out.println("Expected Error: " + e.getMessage());
			System.out.println();
		}
	}

	public static void main(String[] args) {
	    // UC15 architecture initialization
		var repository = QuantityMeasurementCacheRepository.getInstance();
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
		QuantityMeasurementController controller = new QuantityMeasurementController(service);
		
	    demonstrateLength();
	    demonstrateWeight();
	    demonstrateVolume();

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
	    Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
	    Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
	    Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

	    demonstrateEquality(v1, v2);
	    demonstrateConversion(v3, VolumeUnit.LITRE);
	    demonstrateAddition(v1, v2, VolumeUnit.LITRE);

	    // UC12 ---
	    Quantity<LengthUnit> l11 = new Quantity<>(10.0, LengthUnit.FEET);
	    Quantity<LengthUnit> l22 = new Quantity<>(6.0, LengthUnit.INCHES);
	    demonstrateSubtraction(l11, l22, LengthUnit.INCHES);
	    demonstrateDivision(l11, new Quantity<>(2.0, LengthUnit.FEET));

	    Quantity<WeightUnit> w11 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
	    Quantity<WeightUnit> w22 = new Quantity<>(5000.0, WeightUnit.GRAM);
	    demonstrateSubtraction(w11, w22, WeightUnit.GRAM);

	    Quantity<VolumeUnit> v11 = new Quantity<>(5.0, VolumeUnit.LITRE);
	    Quantity<VolumeUnit> v22 = new Quantity<>(10.0, VolumeUnit.LITRE);
	    demonstrateDivision(v11, v22);

	    // UC14 ---
	    demonstrateTemperature();
	    
		// UC15---
		QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(60, "INCH", "LENGTH");
		controller.performComparison(q1, q2);
		
	}
}