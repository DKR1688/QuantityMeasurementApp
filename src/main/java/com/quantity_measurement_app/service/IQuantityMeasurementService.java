package com.quantity_measurement_app.service;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.dto.QuantityMeasurementDTO;
import java.util.List;

public interface IQuantityMeasurementService {

    QuantityDTO convert(QuantityDTO input, String targetUnit);
    boolean compare(QuantityDTO q1, QuantityDTO q2);
    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);
    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);
    QuantityDTO multiply(QuantityDTO q1, QuantityDTO q2);
    double divide(QuantityDTO q1, QuantityDTO q2);

    List<QuantityMeasurementDTO> getMeasurementsByOperation(String operation);
    List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType);
    List<QuantityMeasurementDTO> getAllMeasurements();

    void deleteById(Long id);
    void deleteAllByUserEmail(String email);
    void deleteFiltered(String email, String operation, String measurementType);
}