package com.quantity_measurement_app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

	// UC1 --- feet tests
	@Test
	void testEquality_SameValue() {
		assertEquals(new Length(1.0, Length.LengthUnit.FEET), new Length(1.0, Length.LengthUnit.FEET));
	}

	@Test
	void testEquality_DifferentValue() {
		assertNotEquals(new Length(1.0, Length.LengthUnit.FEET), new Length(2.0, Length.LengthUnit.FEET));
	}

	@Test
	void testEquality_NullComparison() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		assertNotEquals(feet, null);
	}

	@Test
	void testEquality_SameReference() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		assertEquals(feet, feet);
	}

	@Test
	void testEquality_DifferentClass() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		assertNotEquals(feet, "Not a Length object");
	}

	// UC2 --- inches tests
	@Test
	void testInchesEquality_SameValue() {
		assertEquals(new Length(1.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.INCHES));
	}

	@Test
	void testInchesEquality_DifferentValue() {
		assertNotEquals(new Length(1.0, Length.LengthUnit.INCHES), new Length(2.0, Length.LengthUnit.INCHES));
	}

	// UC3 --- cross unit(FEET & INCHES)
	@Test
	void testFeetToInches_Equivalent() {
		assertEquals(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES));
	}

	@Test
	void testFeetToInches_NotEquivalent() {
		assertNotEquals(new Length(1.0, Length.LengthUnit.FEET), new Length(10.0, Length.LengthUnit.INCHES));
	}

	@Test
	void testMultipleFeetComparison() {
		assertEquals(new Length(2.0, Length.LengthUnit.FEET), new Length(24.0, Length.LengthUnit.INCHES));
	}

	// UC4 --- yard tests
	@Test
	void testEquality_YardToYard_SameValue() {
		assertEquals(new Length(1.0, Length.LengthUnit.YARDS), new Length(1.0, Length.LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToYard_DifferentValue() {
		assertNotEquals(new Length(1.0, Length.LengthUnit.YARDS), new Length(2.0, Length.LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToFeet_EquivalentValue() {
		assertEquals(new Length(1.0, Length.LengthUnit.YARDS), new Length(3.0, Length.LengthUnit.FEET));
	}

	@Test
	void testEquality_FeetToYard_EquivalentValue() {
		assertEquals(new Length(3.0, Length.LengthUnit.FEET), new Length(1.0, Length.LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToInches_EquivalentValue() {
		assertEquals(new Length(1.0, Length.LengthUnit.YARDS), new Length(36.0, Length.LengthUnit.INCHES));
	}

	@Test
	void testEquality_InchesToYard_EquivalentValue() {
		assertEquals(new Length(36.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToFeet_NonEquivalentValue() {
		assertNotEquals(new Length(1.0, Length.LengthUnit.YARDS), new Length(2.0, Length.LengthUnit.FEET));
	}

	// UC4 --- centimeter tests
	@Test
	void testEquality_CentimetersToCentimeters() {
		assertEquals(new Length(2.0, Length.LengthUnit.CENTIMETERS), new Length(2.0, Length.LengthUnit.CENTIMETERS));
	}

	@Test
	void testEquality_CentimetersToInches_EquivalentValue() {
		assertEquals(new Length(1.0, Length.LengthUnit.CENTIMETERS), new Length(0.393701, Length.LengthUnit.INCHES));
	}

	@Test
	void testEquality_CentimetersToFeet_NonEquivalentValue() {
		assertNotEquals(new Length(1.0, Length.LengthUnit.CENTIMETERS), new Length(1.0, Length.LengthUnit.FEET));
	}

	// transitive property
	@Test
	void testEquality_MultiUnit_TransitiveProperty() {
		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		Length feet = new Length(3.0, Length.LengthUnit.FEET);
		Length inches = new Length(36.0, Length.LengthUnit.INCHES);

		assertEquals(yard, feet);
		assertEquals(feet, inches);
		assertEquals(yard, inches);
	}

	// null unit validation
	@Test
	void testEquality_YardWithNullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Length(1.0, null));
	}

	@Test
	void testEquality_AllUnits_ComplexScenario() {
		assertEquals(new Length(2.0, Length.LengthUnit.YARDS), new Length(6.0, Length.LengthUnit.FEET));
		assertEquals(new Length(2.0, Length.LengthUnit.YARDS), new Length(72.0, Length.LengthUnit.INCHES));
	}

	// UC5 --- unit conversion tests
	private static final double EPSILON = 1e-6;

	@Test
	void testConversion_FeetToInches() {
		double result = Length.convert(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		assertEquals(12.0, result, EPSILON);
	}

	@Test
	void testConversion_InchesToFeet() {
		double result = Length.convert(24.0, Length.LengthUnit.INCHES, Length.LengthUnit.FEET);
		assertEquals(2.0, result, EPSILON);
	}

	@Test
	void testConversion_YardsToInches() {
		double result = Length.convert(1.0, Length.LengthUnit.YARDS, Length.LengthUnit.INCHES);
		assertEquals(36.0, result, EPSILON);
	}

	@Test
	void testConversion_InchesToYards() {
		double result = Length.convert(72.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS);
		assertEquals(2.0, result, EPSILON);
	}

	// cross-Unit Conversion
	@Test
	void testConversion_CentimetersToInches() {
		double result = Length.convert(2.54, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES);
		assertEquals(1.0, result, EPSILON);
	}

	@Test
	void testConversion_FeetToYards() {
		double result = Length.convert(6.0, Length.LengthUnit.FEET, Length.LengthUnit.YARDS);
		assertEquals(2.0, result, EPSILON);
	}

	// round Trip Conversion
	@Test
	void testConversion_RoundTrip_PreservesValue() {
		double original = 5.0;
		double converted = Length.convert(original, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		double back = Length.convert(converted, Length.LengthUnit.INCHES, Length.LengthUnit.FEET);

		assertEquals(original, back, EPSILON);
	}

	// zero Value
	@Test
	void testConversion_ZeroValue() {
		double result = Length.convert(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		assertEquals(0.0, result, EPSILON);
	}

	// negative Value
	@Test
	void testConversion_NegativeValue() {
		double result = Length.convert(-1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		assertEquals(-12.0, result, EPSILON);
	}

	// same Unit Conversion
	@Test
	void testConversion_SameUnit() {
		double result = Length.convert(5.0, Length.LengthUnit.FEET, Length.LengthUnit.FEET);
		assertEquals(5.0, result, EPSILON);
	}

	// large Value
	@Test
	void testConversion_LargeValue() {
		double large = 1_000_000.0;
		double result = Length.convert(large, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		assertEquals(12_000_000.0, result, EPSILON);
	}

	// small Value
	@Test
	void testConversion_SmallValue() {
		double small = 0.0001;
		double result = Length.convert(small, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		assertEquals(0.0012, result, EPSILON);
	}

	// invalid Unit Handling
	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> Length.convert(1.0, null, Length.LengthUnit.FEET));
		assertThrows(IllegalArgumentException.class, () -> Length.convert(1.0, Length.LengthUnit.FEET, null));
	}

	// invalid Value Handling
	@Test
	void testConversion_NaN_Throws() {
		assertThrows(IllegalArgumentException.class, () -> Length.convert(Double.NaN, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
	}

	@Test
	void testConversion_Infinite_Throws() {
		assertThrows(IllegalArgumentException.class, () -> Length.convert(Double.POSITIVE_INFINITY, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
		assertThrows(IllegalArgumentException.class, () -> Length.convert(Double.NEGATIVE_INFINITY, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
	}
}