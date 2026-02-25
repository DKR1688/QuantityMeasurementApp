package com.quantity_measurement_app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

	// UC1 ---
	@Test
	void testEquality_SameValue() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		Length feet2 = new Length(1.0, Length.LengthUnit.FEET);
		assertTrue(feet1.equals(feet2), "1.0 ft should equal 1.0 ft");
	}

	@Test
	void testEquality_DifferentValue() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		Length feet2 = new Length(2.0, Length.LengthUnit.FEET);
		assertFalse(feet1.equals(feet2), "1.0 ft should not equal 2.0 ft");
	}

	@Test
	void testEquality_NullComparison() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		assertFalse(feet1.equals(null), "Feet object should not equal null");
	}

	@Test
	void testEquality_SameReference() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		assertTrue(feet1.equals(feet1), "Feet object should equal itself");
	}

	@Test
	void testEquality_NonNumericInput() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		String nonNumeric = "Not a Feet object";
		assertFalse(feet1.equals(nonNumeric), "Feet object should not equal non-numeric input");
	}

	// UC2 ---
	@Test
	void testInchesEquality_SameValue() {
		Length i1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length i2 = new Length(1.0, Length.LengthUnit.INCHES);
		assertTrue(i1.equals(i2), "1.0 inch should equal 1.0 inch");
	}

	@Test
	void testInchesEquality_DifferentValue() {
		Length i1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length i2 = new Length(2.0, Length.LengthUnit.INCHES);
		assertFalse(i1.equals(i2), "1.0 inch should not equal 2.0 inch");
	}

	@Test
	void testInchesEquality_NullComparison() {
		Length i1 = new Length(1.0, Length.LengthUnit.INCHES);
		assertFalse(i1.equals(null), "Inches object should not equal null");
	}

	@Test
	void testInchesEquality_DifferentClass() {
		Length i1 = new Length(1.0, Length.LengthUnit.INCHES);
		String nonNumeric = "Not an Inches object";
		assertFalse(i1.equals(nonNumeric), "Inches object should not equal non-numeric input");
	}

	@Test
	void testInchesEquality_SameReference() {
		Length i1 = new Length(1.0, Length.LengthUnit.INCHES);
		assertTrue(i1.equals(i1), "Inches object should equal itself");
	}

	// UC3 ---
	@Test
	public void testFeetInchesComparison() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(12.0, Length.LengthUnit.INCHES);
		assertEquals(feet, inches, "1.0 ft should equal 12.0 inches");
	}

	@Test
	public void testCrossUnitInequality() {
		Length feet = new Length(1.0, Length.LengthUnit.FEET);
		Length inches = new Length(10.0, Length.LengthUnit.INCHES);
		assertNotEquals(feet, inches, "1.0 ft should not equal 10.0 inches");
	}

	@Test
	public void testMultipleFeetComparison() {
		Length feet = new Length(2.0, Length.LengthUnit.FEET);
		Length inches = new Length(24.0, Length.LengthUnit.INCHES);
		assertEquals(feet, inches, "2.0 ft should equal 24.0 inches");
	}

}