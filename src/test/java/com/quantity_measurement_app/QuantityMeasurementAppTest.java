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
}