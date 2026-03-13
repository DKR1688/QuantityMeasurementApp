package com.quantity_measurement_app.integration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.quantity_measurement_app.controller.QuantityMeasurementController;
import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.repository.IQuantityMeasurementRepository;
import com.quantity_measurement_app.repository.QuantityMeasurementCacheRepository;
import com.quantity_measurement_app.service.QuantityMeasurementServiceImpl;
public class QuantityMeasurementIntegrationTest {
	private QuantityMeasurementController controller;
	
	// UC15----------------------------------------------------------------------------------
	@Test
	void testIntegration_EndToEnd_LengthAddition() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementController controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(repo));

		QuantityDTO q1 = new QuantityDTO(2, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(3, "FEET", "LENGTH");

		assertDoesNotThrow(() -> controller.performAddition(q1, q2));
	}

	@Test
	void testLayerSeparation_ServiceIndependence() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);

		assertNotNull(service);
	}

	@Test
	void testLayerSeparation_ControllerIndependence() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementController controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(repo));

		assertNotNull(controller);
	}
	
	// UC16------------------------------------------------------------------------------------
	@BeforeEach
	void setup() {
		controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(QuantityMeasurementCacheRepository.getInstance()));
	}

	@Test
	void testEndToEndAddition() {
		controller.performAddition(new QuantityDTO(10, "METER", "LENGTH"), new QuantityDTO(20, "METER", "LENGTH"));
	}

	@Test
	void testEndToEndComparison() {
		controller.performComparison(new QuantityDTO(5, "METER", "LENGTH"), new QuantityDTO(5, "METER", "LENGTH"));
	}

	@Test
	void testEndToEndSubtraction() {
		controller.performSubtraction(new QuantityDTO(20, "METER", "LENGTH"), new QuantityDTO(10, "METER", "LENGTH"));
	}

	@Test
	void testEndToEndDivision() {
		controller.performDivision(new QuantityDTO(10, "METER", "LENGTH"), new QuantityDTO(2, "METER", "LENGTH"));
	}

	@Test
	void testEndToEndConversion() {
		controller.performConversion(new QuantityDTO(10, "METER", "LENGTH"), "CM");
	}
	
}
