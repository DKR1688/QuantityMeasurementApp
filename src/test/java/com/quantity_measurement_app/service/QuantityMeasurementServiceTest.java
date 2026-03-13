package com.quantity_measurement_app.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.repository.IQuantityMeasurementRepository;
import com.quantity_measurement_app.repository.QuantityMeasurementCacheRepository;
public class QuantityMeasurementServiceTest {
	private IQuantityMeasurementService service;
	
	//UC15-------------------------------------------------------------------------------------------
	@Test
	void testService_CompareEquality_SameUnit_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);

		QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(10, "FEET", "LENGTH");

		assertTrue(service.compare(q1, q2));
	}

	@Test
	void testService_CompareEquality_DifferentUnit_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);

		QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(10, "INCH", "LENGTH");

		assertTrue(service.compare(q1, q2));
	}

	@Test
	void testService_Convert_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);
		QuantityDTO input = new QuantityDTO(5, "FEET", "LENGTH");
		QuantityDTO result = service.convert(input, "INCH");

		assertEquals("INCH", result.getUnit());
	}

	@Test
	void testService_Add_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);

		QuantityDTO q1 = new QuantityDTO(5, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");
		QuantityDTO result = service.add(q1, q2);

		assertEquals(10, result.getValue());
	}

	@Test
	void testService_Subtract_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);

		QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "FEET", "LENGTH");
		QuantityDTO result = service.subtract(q1, q2);

		assertEquals(5, result.getValue());
	}

	@Test
	void testService_Divide_Success() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);

		QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(2, "FEET", "LENGTH");
		double result = service.divide(q1, q2);

		assertEquals(5, result);
	}

	@Test
	void testService_Divide_ByZero_Error() {
		IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(repo);

		QuantityDTO q1 = new QuantityDTO(10, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(0, "FEET", "LENGTH");

		assertThrows(RuntimeException.class, () -> service.divide(q1, q2));
	}
	
	//UC16----------------------------------------------------------------------------------------
	@BeforeEach
	void setup() {
		service = new QuantityMeasurementServiceImpl(QuantityMeasurementCacheRepository.getInstance());
	}

	@Test
	void testCompareEqual() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(10, "METER", "LENGTH");
		assertTrue(service.compare(q1, q2));
	}

	@Test
	void testCompareNotEqual() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "METER", "LENGTH");
		assertFalse(service.compare(q1, q2));
	}

	@Test
	void testAddition() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "METER", "LENGTH");
		assertEquals(15, service.add(q1, q2).getValue());
	}

	@Test
	void testSubtraction() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "METER", "LENGTH");
		assertEquals(5, service.subtract(q1, q2).getValue());
	}

	@Test
	void testDivision() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(2, "METER", "LENGTH");
		assertEquals(5, service.divide(q1, q2));
	}

	@Test
	void testDivisionByZero() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(0, "METER", "LENGTH");
		assertThrows(RuntimeException.class, () -> service.divide(q1, q2));
	}

	@Test
	void testConversion() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		assertNotNull(service.convert(q1, "CM"));
	}

	@Test
	void testAdditionResultUnit() {
		QuantityDTO q1 = new QuantityDTO(10, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "METER", "LENGTH");
		assertEquals("METER", service.add(q1, q2).getUnit());
	}

	@Test
	void testSubtractionPositive() {
		QuantityDTO q1 = new QuantityDTO(20, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(5, "METER", "LENGTH");
		assertTrue(service.subtract(q1, q2).getValue() > 0);
	}

	@Test
	void testServiceHandlesLargeNumbers() {
		QuantityDTO q1 = new QuantityDTO(1000, "METER", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(2000, "METER", "LENGTH");
		assertEquals(3000, service.add(q1, q2).getValue());
	}
}