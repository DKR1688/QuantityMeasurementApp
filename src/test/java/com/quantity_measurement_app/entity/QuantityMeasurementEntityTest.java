package com.quantity_measurement_app.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
public class QuantityMeasurementEntityTest {
	//UC15-------------------------------------------------
	@Test
	void testQuantityEntity_SingleOperandConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("CONVERT", "5 FEET", null, "60 INCH");

		assertEquals("CONVERT", entity.getOperation());
		assertEquals("5 FEET", entity.getOperand1());
		assertNull(entity.getOperand2());
		assertEquals("60 INCH", entity.getResult());
	}

	@Test
	void testQuantityEntity_BinaryOperandConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "5 FEET", "5 FEET", "10 FEET");

		assertEquals("ADD", entity.getOperation());
		assertEquals("5 FEET", entity.getOperand1());
		assertEquals("5 FEET", entity.getOperand2());
		assertEquals("10 FEET", entity.getResult());
	}

	@Test
	void testQuantityEntity_ErrorConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "Unsupported operation");

		assertTrue(entity.hasError());
		assertEquals("Unsupported operation", entity.getError());
	}

	@Test
	void testQuantityEntity_ToString_Success() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "5 FEET", "5 FEET", "10 FEET");
		String output = entity.toString();

		assertTrue(output.contains("ADD"));
		assertTrue(output.contains("Result"));
	}

	@Test
	void testQuantityEntity_ToString_Error() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("ADD", "Invalid operation");
		String output = entity.toString();

		assertTrue(output.contains("ERROR"));
	}
}
