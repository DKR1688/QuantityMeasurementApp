package com.quantity_measurement_app.service;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.entity.QuantityMeasurementEntity;
import com.quantity_measurement_app.exception.QuantityMeasurementException;
import com.quantity_measurement_app.repository.IQuantityMeasurementRepository;
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
	private final IQuantityMeasurementRepository repository;

	//dependency injection in constructor
	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	private String formatQuantity(QuantityDTO q) {
		return q.getValue() + " " + q.getUnit();
	}

	@Override
	public QuantityDTO convert(QuantityDTO input, String targetUnit) {
		try {
			// placeholder conversion replace with UC1–UC14 conversion logic
			QuantityDTO result = new QuantityDTO(input.getValue(), targetUnit, input.getMeasurementType());
			repository.save(
					new QuantityMeasurementEntity("CONVERT", formatQuantity(input), null, formatQuantity(result)));
			return result;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity("CONVERT", e.getMessage()));
			throw new QuantityMeasurementException("Conversion failed", e);
		}
	}

	@Override
	public boolean compare(QuantityDTO q1, QuantityDTO q2) {
		try {
			boolean result = q1.getValue() == q2.getValue();
			repository.save(new QuantityMeasurementEntity("COMPARE", formatQuantity(q1), formatQuantity(q2),
					String.valueOf(result)));
			return result;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity("COMPARE", e.getMessage()));
			throw new QuantityMeasurementException("Comparison failed", e);
		}
	}

	@Override
	public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
		try {
			double value = q1.getValue() + q2.getValue();
			QuantityDTO result = new QuantityDTO(value, q1.getUnit(), q1.getMeasurementType());
			repository.save(new QuantityMeasurementEntity("ADD", formatQuantity(q1), formatQuantity(q2),
					formatQuantity(result)));
			return result;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity("ADD", e.getMessage()));
			throw new QuantityMeasurementException("Addition failed", e);
		}
	}

	@Override
	public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
		try {
			double value = q1.getValue() - q2.getValue();
			QuantityDTO result = new QuantityDTO(value, q1.getUnit(), q1.getMeasurementType());
			repository.save(new QuantityMeasurementEntity("SUBTRACT", formatQuantity(q1), formatQuantity(q2),
					formatQuantity(result)));
			return result;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity("SUBTRACT", e.getMessage()));
			throw new QuantityMeasurementException("Subtraction failed", e);
		}
	}

	@Override
	public double divide(QuantityDTO q1, QuantityDTO q2) {
		try {
			if (q2.getValue() == 0) {
				throw new QuantityMeasurementException("Division by zero");
			}
			double result = q1.getValue() / q2.getValue();
			repository.save(new QuantityMeasurementEntity("DIVIDE", formatQuantity(q1), formatQuantity(q2),
					String.valueOf(result)));
			return result;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity("DIVIDE", e.getMessage()));
			throw new QuantityMeasurementException("Division failed", e);
		}
	}
}
