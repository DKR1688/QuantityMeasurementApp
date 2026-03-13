package com.quantity_measurement_app.repository;

import com.quantity_measurement_app.entity.QuantityMeasurementEntity;
import java.util.List;
public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);
    List<QuantityMeasurementEntity> getAllMeasurements();
}