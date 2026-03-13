package com.quantity_measurement_app.controller;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.service.IQuantityMeasurementService;
public class QuantityMeasurementController {
	private final IQuantityMeasurementService service;

	public QuantityMeasurementController(IQuantityMeasurementService service) {
		this.service = service;
	}

	public void performComparison(QuantityDTO q1, QuantityDTO q2) {
		boolean result = service.compare(q1, q2);
		System.out.println("Comparison Result: " + result);
	}

	public void performConversion(QuantityDTO input, String targetUnit) {
		QuantityDTO result = service.convert(input, targetUnit);
		System.out.println("Converted: " + result);
	}

	public void performAddition(QuantityDTO q1, QuantityDTO q2) {
		QuantityDTO result = service.add(q1, q2);
		System.out.println("Addition Result: " + result);
	}

	public void performSubtraction(QuantityDTO q1, QuantityDTO q2) {
		QuantityDTO result = service.subtract(q1, q2);
		System.out.println("Subtraction Result: " + result);
	}

	public void performDivision(QuantityDTO q1, QuantityDTO q2) {
		double result = service.divide(q1, q2);
		System.out.println("Division Result: " + result);
	}
}