package com.quantity_measurement_app.history.service;

import com.quantity_measurement_app.history.model.QuantityMeasurementEntity;

import java.util.List;

public interface IHistoryService {

    List<QuantityMeasurementEntity> getAllMeasurements(String userEmail);

    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation, String userEmail);

    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType, String userEmail);

    void deleteById(Long id, String userEmail);

    void deleteAllByUserEmail(String userEmail);

    void deleteFiltered(String userEmail, String operation, String measurementType);

    void saveMeasurement(QuantityMeasurementEntity entity);
}