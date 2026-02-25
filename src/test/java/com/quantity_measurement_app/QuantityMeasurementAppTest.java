package com.quantity_measurement_app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class QuantityMeasurementAppTest {

	@Test
	void testEquality_SameValue() {
		QualntityMeasurementApp.Feet feet1=new QualntityMeasurementApp.Feet(1.0);
		QualntityMeasurementApp.Feet feet2=new QualntityMeasurementApp.Feet(1.0);
		assertTrue(feet1.equals(feet2), "1.0 ft should equal 1.0 ft");
	}

	@Test
	void testEquality_DifferentValue() {
		QualntityMeasurementApp.Feet feet1=new QualntityMeasurementApp.Feet(1.0);
		QualntityMeasurementApp.Feet feet2=new QualntityMeasurementApp.Feet(2.0);
		assertFalse(feet1.equals(feet2), "1.0 ft should not equal 2.0 ft");
	}

	@Test
	void testEquality_NullComparison() {
		QualntityMeasurementApp.Feet feet1=new QualntityMeasurementApp.Feet(1.0);
		assertFalse(feet1.equals(null), "Feet object should not equal null");
	}

	@Test
	void testEquality_SameReference() {
		QualntityMeasurementApp.Feet feet1=new QualntityMeasurementApp.Feet(1.0);
		assertTrue(feet1.equals(feet1), "Feet object should equal itself");
	}

	@Test
	void testEquality_NonNumericInput() {
		QualntityMeasurementApp.Feet feet1 = new QualntityMeasurementApp.Feet(1.0);
		String nonNumeric = "Not a Feet object";
		assertFalse(feet1.equals(nonNumeric), "Feet object should not equal non-numeric input");
	}
}