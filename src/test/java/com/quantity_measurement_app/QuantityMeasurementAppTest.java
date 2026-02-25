package com.quantity_measurement_app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class QuantityMeasurementAppTest {

	//UC1 --- feet measurement
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
		QualntityMeasurementApp.Feet feet1=new QualntityMeasurementApp.Feet(1.0);
		String nonNumeric = "Not a Feet object";
		assertFalse(feet1.equals(nonNumeric), "Feet object should not equal non-numeric input");
	}
	
	//UC2 --- Inch measurement
	@Test
    void testInchesEquality_SameValue() {
		QualntityMeasurementApp.Inches i1=new QualntityMeasurementApp.Inches(1.0);
		QualntityMeasurementApp.Inches i2=new QualntityMeasurementApp.Inches(1.0);
        assertTrue(i1.equals(i2), "1.0 inch should equal 1.0 inch");
    }

    @Test
    void testInchesEquality_DifferentValue() {
    	QualntityMeasurementApp.Inches i1=new QualntityMeasurementApp.Inches(1.0);
    	QualntityMeasurementApp.Inches i2=new QualntityMeasurementApp.Inches(2.0);
        assertFalse(i1.equals(i2), "1.0 inch should not equal 2.0 inch");
    }

    @Test
    void testInchesEquality_NullComparison() {
    	QualntityMeasurementApp.Inches i1=new QualntityMeasurementApp.Inches(1.0);
        assertFalse(i1.equals(null), "Inches object should not equal null");
    }

    @Test
    void testInchesEquality_DifferentClass() {
    	QualntityMeasurementApp.Inches i1=new QualntityMeasurementApp.Inches(1.0);
        String nonNumeric = "Not an Inches object";
        assertFalse(i1.equals(nonNumeric), "Inches object should not equal non-numeric input");
    }

    @Test
    void testInchesEquality_SameReference() {
    	QualntityMeasurementApp.Inches i1=new QualntityMeasurementApp.Inches(1.0);
        assertTrue(i1.equals(i1), "Inches object should equal itself");
    }

    
}