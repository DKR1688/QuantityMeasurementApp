package com.quantity_measurement_app.repository;

import com.quantity_measurement_app.entity.QuantityMeasurementEntity;
import java.util.ArrayList;
import java.util.List;
public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {
	private static QuantityMeasurementCacheRepository instance;
	private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

	private QuantityMeasurementCacheRepository() {
	}

	public static synchronized QuantityMeasurementCacheRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementCacheRepository();
		}
		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		cache.add(entity);
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		return new ArrayList<>(cache);
	}
}