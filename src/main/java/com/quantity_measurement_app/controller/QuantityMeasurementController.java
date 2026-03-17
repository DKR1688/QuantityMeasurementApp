package com.quantity_measurement_app.controller;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.service.IQuantityMeasurementService;

public class QuantityMeasurementController {
	private final IQuantityMeasurementService service;

	public QuantityMeasurementController(IQuantityMeasurementService service) {
		this.service = service;
	}

	public void performConversion(QuantityDTO input, String targetUnit) {
		QuantityDTO result = service.convert(input, targetUnit);
		System.out.println("Conversion Result:");
		System.out.println(
				input.getValue() + " " + input.getUnit() + " -> " + result.getValue() + " " + result.getUnit());
		System.out.println();
	}

	public void performComparison(QuantityDTO q1, QuantityDTO q2) {
		boolean result = service.compare(q1, q2);
		System.out.println("Comparison Result:");
		System.out.println(
				q1.getValue() + " " + q1.getUnit() + " == " + q2.getValue() + " " + q2.getUnit() + " ? " + result);
		System.out.println();
	}

	public void performAddition(QuantityDTO q1, QuantityDTO q2) {
		QuantityDTO result = service.add(q1, q2);
		System.out.println("Addition Result:");
		System.out.println(q1.getValue() + " " + q1.getUnit() + " + " + q2.getValue() + " " + q2.getUnit() + " = "
				+ result.getValue() + " " + result.getUnit());
		System.out.println();
	}

	public void performSubtraction(QuantityDTO q1, QuantityDTO q2) {
		QuantityDTO result = service.subtract(q1, q2);
		System.out.println("Subtraction Result:");
		System.out.println(q1.getValue() + " " + q1.getUnit() + " - " + q2.getValue() + " " + q2.getUnit() + " = "
				+ result.getValue() + " " + result.getUnit());
		System.out.println();
	}

	public void performDivision(QuantityDTO q1, QuantityDTO q2) {
		double result = service.divide(q1, q2);
		System.out.println("Division Result:");
		System.out.println(
				q1.getValue() + " " + q1.getUnit() + " / " + q2.getValue() + " " + q2.getUnit() + " = " + result);
		System.out.println();
	}
}