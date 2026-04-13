package com.quantity_measurement_app.history.service;

import com.quantity_measurement_app.history.model.QuantityMeasurementEntity;
import com.quantity_measurement_app.history.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryServiceImpl implements IHistoryService {

    private final QuantityMeasurementRepository repository;

    @Autowired
    public HistoryServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements(String userEmail) {
        return repository.findByUserEmail(userEmail);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation, String userEmail) {
        return repository.findByOperationAndUserEmail(operation, userEmail);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType, String userEmail) {
        return repository.findByMeasurementTypeAndUserEmail(measurementType, userEmail);
    }

    @Override
    @Transactional
    public void deleteById(Long id, String userEmail) {
        QuantityMeasurementEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Measurement not found"));
        if (!userEmail.equals(entity.getUserEmail())) {
            throw new RuntimeException("Unauthorized");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByUserEmail(String userEmail) {
        repository.deleteAllByUserEmail(userEmail);
    }

    @Override
    @Transactional
    public void deleteFiltered(String userEmail, String operation, String measurementType) {
        if (operation != null && measurementType != null) {
            repository.deleteByUserEmailAndOperationAndMeasurementType(userEmail, operation, measurementType);
        } else if (operation != null) {
            repository.deleteByUserEmailAndOperation(userEmail, operation);
        } else if (measurementType != null) {
            repository.deleteByUserEmailAndMeasurementType(userEmail, measurementType);
        } else {
            deleteAllByUserEmail(userEmail);
        }
    }

    @Override
    public void saveMeasurement(QuantityMeasurementEntity entity) {
        repository.save(entity);
    }
}