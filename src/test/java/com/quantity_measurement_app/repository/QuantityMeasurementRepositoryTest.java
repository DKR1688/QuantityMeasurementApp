package com.quantity_measurement_app.repository;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.quantity_measurement_app.entity.QuantityMeasurementEntity;
class QuantityMeasurementRepositoryTest {
	private IQuantityMeasurementRepository repository;

	@BeforeEach
	void setUp() {
		repository = QuantityMeasurementCacheRepository.getInstance();
	}

	@Test
	void testSaveEntity() {
		int before = repository.getAllMeasurements().size();

		repository.save(new QuantityMeasurementEntity("ADD", "1m", "2m", "3m"));

		int after = repository.getAllMeasurements().size();

		assertEquals(before + 1, after);
	}

	@Test
	void testSaveMultipleEntities() {
		int before = repository.getAllMeasurements().size();

		repository.save(new QuantityMeasurementEntity("ADD", "1", "2", "3"));
		repository.save(new QuantityMeasurementEntity("SUBTRACT", "5", "2", "3"));

		int after = repository.getAllMeasurements().size();

		assertEquals(before + 2, after);
	}

	@Test
	void testGetAllMeasurementsNotNull() {
		List<?> list = repository.getAllMeasurements();
		assertNotNull(list);
	}

	@Test
	void testCacheRepositorySingleton() {
		IQuantityMeasurementRepository repo2 = QuantityMeasurementCacheRepository.getInstance();

		assertSame(repository, repo2);
	}

	@Test
	void testEntityStoredCorrectly() {

		repository.save(new QuantityMeasurementEntity("COMPARE", "1", "1", "true"));

		QuantityMeasurementEntity last = repository.getAllMeasurements()
				.get(repository.getAllMeasurements().size() - 1);

		assertEquals("COMPARE", last.getOperation());
	}

	@Test
	void testRepositorySizeAfterInsert() {

		int before = repository.getAllMeasurements().size();

		repository.save(new QuantityMeasurementEntity("ADD", "1", "2", "3"));
		repository.save(new QuantityMeasurementEntity("ADD", "3", "4", "7"));

		int after = repository.getAllMeasurements().size();

		assertTrue(after >= before + 2);
	}

	@Test
	void testRepositoryHandlesManyEntries() {

		int before = repository.getAllMeasurements().size();

		for (int i = 0; i < 50; i++) {
			repository.save(new QuantityMeasurementEntity("ADD", "1", "2", "3"));
		}

		int after = repository.getAllMeasurements().size();

		assertTrue(after >= before + 50);
	}

	@Test
	void testRepositoryListIsolation() {

		List<?> list1 = repository.getAllMeasurements();
		List<?> list2 = repository.getAllMeasurements();

		assertNotSame(list1, list2);
	}

	@Test
	void testRepositoryInsertDifferentOperations() {

		int before = repository.getAllMeasurements().size();

		repository.save(new QuantityMeasurementEntity("ADD", "1", "2", "3"));
		repository.save(new QuantityMeasurementEntity("SUBTRACT", "5", "2", "3"));

		int after = repository.getAllMeasurements().size();

		assertEquals(before + 2, after);
	}

	@Test
	void testRepositoryHandlesNullMessage() {

		int before = repository.getAllMeasurements().size();

		repository.save(new QuantityMeasurementEntity("ERROR", "msg", null, null));

		int after = repository.getAllMeasurements().size();

		assertEquals(before + 1, after);
	}

	@Test
	void testRepositoryDataPersistenceDuringRuntime() {

		repository.save(new QuantityMeasurementEntity("ADD", "1", "2", "3"));

		assertFalse(repository.getAllMeasurements().isEmpty());
	}

	@Test
	void testRepositoryReturnsEntityObjects() {

		repository.save(new QuantityMeasurementEntity("ADD", "1", "2", "3"));

		Object obj = repository.getAllMeasurements().get(repository.getAllMeasurements().size() - 1);

		assertTrue(obj instanceof QuantityMeasurementEntity);
	}
}