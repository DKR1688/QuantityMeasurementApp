package com.quantity_measurement_app.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.repository.QuantityMeasurementRepository;

public class QuantityMeasurementServiceTest {
	private IQuantityMeasurementService service;

	@Mock
	private QuantityMeasurementRepository mockRepository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		service = new QuantityMeasurementServiceImpl(mockRepository);
	}

	@Test
	void testService_CompareEquality_SameUnit_Success() {
		QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		assertTrue(service.compare(q1, q2));
	}

	@Test
	void testService_CompareEquality_DifferentUnit_Success() {
		QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(10.0, "INCH", "LENGTHUNIT");
		assertTrue(service.compare(q1, q2));
	}

	@Test
	void testService_Convert_Success() {
		QuantityDTO input = new QuantityDTO(5.0, "FEET", "LENGTHUNIT");
		QuantityDTO result = service.convert(input, "INCH");
		assertEquals("INCH", result.getUnit());
	}

	@Test
	void testService_Add_Success() {
		QuantityDTO q1 = new QuantityDTO(5.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(5.0, "FEET", "LENGTHUNIT");
		QuantityDTO result = service.add(q1, q2);
		assertEquals(10.0, result.getValue());
	}

	@Test
	void testService_Subtract_Success() {
		QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(5.0, "FEET", "LENGTHUNIT");
		QuantityDTO result = service.subtract(q1, q2);
		assertEquals(5.0, result.getValue());
	}

	@Test
	void testService_Divide_Success() {
		QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "LENGTHUNIT");
		double result = service.divide(q1, q2);
		assertEquals(5.0, result);
	}

	@Test
	void testService_Divide_ByZero_Error() {
		QuantityDTO q1 = new QuantityDTO(10.0, "FEET", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(0.0, "FEET", "LENGTHUNIT");
		assertThrows(RuntimeException.class, () -> service.divide(q1, q2));
	}

	@Test
	void testCompareEqual() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		assertTrue(service.compare(q1, q2));
	}

	@Test
	void testCompareNotEqual() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(5.0, "METER", "LENGTHUNIT");
		assertFalse(service.compare(q1, q2));
	}

	@Test
	void testAddition() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(5.0, "METER", "LENGTHUNIT");
		assertEquals(15.0, service.add(q1, q2).getValue());
	}

	@Test
	void testSubtraction() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(5.0, "METER", "LENGTHUNIT");
		assertEquals(5.0, service.subtract(q1, q2).getValue());
	}

	@Test
	void testDivision() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(2.0, "METER", "LENGTHUNIT");
		assertEquals(5.0, service.divide(q1, q2));
	}

	@Test
	void testDivisionByZero() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(0.0, "METER", "LENGTHUNIT");
		assertThrows(RuntimeException.class, () -> service.divide(q1, q2));
	}

	@Test
	void testConversion() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		assertNotNull(service.convert(q1, "CM"));
	}

	@Test
	void testAdditionResultUnit() {
		QuantityDTO q1 = new QuantityDTO(10.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(5.0, "METER", "LENGTHUNIT");
		assertEquals("METER", service.add(q1, q2).getUnit());
	}

	@Test
	void testSubtractionPositive() {
		QuantityDTO q1 = new QuantityDTO(20.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(5.0, "METER", "LENGTHUNIT");
		assertTrue(service.subtract(q1, q2).getValue() > 0);
	}

	@Test
	void testServiceHandlesLargeNumbers() {
		QuantityDTO q1 = new QuantityDTO(1000.0, "METER", "LENGTHUNIT");
		QuantityDTO q2 = new QuantityDTO(2000.0, "METER", "LENGTHUNIT");
		assertEquals(3000.0, service.add(q1, q2).getValue());
	}
}
