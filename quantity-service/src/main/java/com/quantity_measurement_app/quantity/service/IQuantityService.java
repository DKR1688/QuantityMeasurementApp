package com.quantity_measurement_app.quantity.service;

import com.quantity_measurement_app.quantity.dto.QuantityDTO;

public interface IQuantityService {

    QuantityDTO convert(QuantityDTO input, String targetUnit, String userEmail);
    boolean compare(QuantityDTO q1, QuantityDTO q2, String userEmail);
    QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String userEmail);
    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String userEmail);
    QuantityDTO multiply(QuantityDTO q1, QuantityDTO q2, String userEmail);
    double divide(QuantityDTO q1, QuantityDTO q2, String userEmail);
}