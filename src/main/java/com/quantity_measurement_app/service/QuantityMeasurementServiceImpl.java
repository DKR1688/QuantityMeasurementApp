package com.quantity_measurement_app.service;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.dto.QuantityMeasurementDTO;
import com.quantity_measurement_app.model.QuantityMeasurementEntity;
import com.quantity_measurement_app.exception.QuantityMeasurementException;
import com.quantity_measurement_app.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
	private final QuantityMeasurementRepository repository;

	@Autowired
	public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	private String formatQuantity(QuantityDTO q) {
		return q.getValue() + " " + q.getUnit();
	}

	@Override
	public QuantityDTO convert(QuantityDTO input, String targetUnit) {
		try {
			// it replace with actual conversion logic
			QuantityDTO result = new QuantityDTO(input.getValue(), targetUnit, input.getMeasurementType());

			QuantityMeasurementEntity entity = new QuantityMeasurementEntity("CONVERT", formatQuantity(input), null,
					formatQuantity(result));
			repository.save(entity);

			return result;
		} catch (Exception e) {
			// it save error to database
			repository.save(new QuantityMeasurementEntity("CONVERT", e.getMessage()));
			throw new QuantityMeasurementException("Conversion failed", e);
		}
	}

	@Override
	public boolean compare(QuantityDTO q1, QuantityDTO q2) {
		try {
			boolean result = q1.getValue().equals(q2.getValue());

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

	public List<QuantityMeasurementDTO> getMeasurementsByOperation(String operation) {
		List<QuantityMeasurementEntity> entities = repository.findByOperation(operation);
		return QuantityMeasurementDTO.fromEntityList(entities);
	}

	public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
		List<QuantityMeasurementEntity> entities = repository.findByMeasurementType(measurementType);
		return QuantityMeasurementDTO.fromEntityList(entities);
	}

	public List<QuantityMeasurementDTO> getAllMeasurements() {
		List<QuantityMeasurementEntity> entities = repository.findAll();
		return QuantityMeasurementDTO.fromEntityList(entities);
	}
}
