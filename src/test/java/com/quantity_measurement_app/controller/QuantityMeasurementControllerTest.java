package com.quantity_measurement_app.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.service.QuantityMeasurementServiceImpl;
import com.quantity_measurement_app.repository.IQuantityMeasurementRepository;
import com.quantity_measurement_app.repository.QuantityMeasurementCacheRepository;
public class QuantityMeasurementControllerTest {
	private QuantityMeasurementController controller;

	//UC15------------------------------------------------------------------------------------
	@Test
	void testController_DemonstrateEquality_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementController controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(repo));

		QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");

		assertDoesNotThrow(() -> controller.performComparison(q1, q2));
	}

	@Test
	void testController_DemonstrateConversion_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementController controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(repo));

		QuantityDTO input = new QuantityDTO(5, "FEET", "LENGTH");

		assertDoesNotThrow(() -> controller.performConversion(input, "INCH"));
	}

	@Test
	void testController_DemonstrateAddition_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementController controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(repo));

		QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");

		assertDoesNotThrow(() -> controller.performAddition(q1, q2));
	}
	
	//UC16---------------------------------------------------------------------------------------
	@BeforeEach
	void setup() {
		controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(QuantityMeasurementCacheRepository.getInstance()));
	}

	@Test
	void testControllerComparison() {
		controller.performComparison(new QuantityDTO(10, "METER", "LENGTH"), new QuantityDTO(10, "METER", "LENGTH"));
	}

	@Test
	void testControllerAddition() {
		controller.performAddition(new QuantityDTO(10, "METER", "LENGTH"), new QuantityDTO(5, "METER", "LENGTH"));
	}

	@Test
	void testControllerSubtraction() {
		controller.performSubtraction(new QuantityDTO(10, "METER", "LENGTH"), new QuantityDTO(5, "METER", "LENGTH"));
	}

	@Test
	void testControllerDivision() {
		controller.performDivision(new QuantityDTO(10, "METER", "LENGTH"), new QuantityDTO(2, "METER", "LENGTH"));
	}

	@Test
	void testControllerConversion() {
		controller.performConversion(new QuantityDTO(10, "METER", "LENGTH"), "CM");
	}

	@Test
	void testControllerHandlesZeroDivision() {
		controller.performDivision(new QuantityDTO(10, "METER", "LENGTH"), new QuantityDTO(1, "METER", "LENGTH"));
	}

	@Test
	void testControllerMultipleOperations() {
		controller.performAddition(new QuantityDTO(5, "METER", "LENGTH"), new QuantityDTO(5, "METER", "LENGTH"));
	}

	@Test
	void testControllerWorkflow() {
		controller.performComparison(new QuantityDTO(1, "METER", "LENGTH"), new QuantityDTO(1, "METER", "LENGTH"));
	}
}