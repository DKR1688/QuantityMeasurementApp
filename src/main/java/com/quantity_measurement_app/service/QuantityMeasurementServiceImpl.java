package com.quantity_measurement_app.service;

import com.quantity_measurement_app.dto.QuantityDTO;
import com.quantity_measurement_app.dto.QuantityMeasurementDTO;
import com.quantity_measurement_app.exception.QuantityMeasurementException;
import com.quantity_measurement_app.model.QuantityMeasurementEntity;
import com.quantity_measurement_app.repository.QuantityMeasurementRepository;
import com.quantity_measurement_app.units.LengthUnit;
import com.quantity_measurement_app.units.TemperatureUnit;
import com.quantity_measurement_app.units.VolumeUnit;
import com.quantity_measurement_app.units.WeightUnit;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
	private static final double EPSILON = 1e-9;

	private final QuantityMeasurementRepository repository;

	@Autowired
	public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	private String formatQuantity(QuantityDTO q) {
		return round(q.getValue()) + " " + q.getUnit();
	}

	private double round(double value) {
		return Math.round(value * 1000.0) / 1000.0;
	}

	@Override
	public QuantityDTO convert(QuantityDTO input, String targetUnit) {
		String operation = "CONVERT";
		try {
			double baseValue = toBaseUnit(input.getMeasurementType(), input.getUnit(), input.getValue());
			double convertedValue = fromBaseUnit(input.getMeasurementType(), targetUnit, baseValue);

			QuantityDTO result = new QuantityDTO(round(convertedValue), targetUnit, input.getMeasurementType());
			saveSuccess(operation, input.getMeasurementType(), formatQuantity(input), null, formatQuantity(result));
			return result;
		} catch (Exception e) {
			saveError(operation, input.getMeasurementType(), e);
			throw new QuantityMeasurementException("Conversion failed", e);
		}
	}

	@Override
	public boolean compare(QuantityDTO q1, QuantityDTO q2) {
		String operation = "COMPARE";
		try {
			ensureSameMeasurementType(q1, q2);
			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			boolean result = Math.abs(base1 - base2) < EPSILON;

			saveSuccess(operation, q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), String.valueOf(result));
			return result;
		} catch (Exception e) {
			saveError(operation, q1 != null ? q1.getMeasurementType() : null, e);
			throw new QuantityMeasurementException("Comparison failed", e);
		}
	}

	@Override
	public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
		String operation = "ADD";
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			double sumBase = base1 + base2;
			double resultValue = fromBaseUnit(q1.getMeasurementType(), q1.getUnit(), sumBase);

			QuantityDTO result = new QuantityDTO(round(resultValue), q1.getUnit(), q1.getMeasurementType());
			saveSuccess(operation, q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), formatQuantity(result));
			return result;
		} catch (Exception e) {
			saveError(operation, q1 != null ? q1.getMeasurementType() : null, e);
			throw new QuantityMeasurementException("Addition failed", e);
		}
	}

	@Override
	public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
		String operation = "SUBTRACT";
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			double diffBase = base1 - base2;
			double resultValue = fromBaseUnit(q1.getMeasurementType(), q1.getUnit(), diffBase);

			QuantityDTO result = new QuantityDTO(round(resultValue), q1.getUnit(), q1.getMeasurementType());
			saveSuccess(operation, q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), formatQuantity(result));
			return result;
		} catch (Exception e) {
			saveError(operation, q1 != null ? q1.getMeasurementType() : null, e);
			throw new QuantityMeasurementException("Subtraction failed", e);
		}
	}

	@Override
	public double divide(QuantityDTO q1, QuantityDTO q2) {
		String operation = "DIVIDE";
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			if (Math.abs(base2) < EPSILON) {
				throw new QuantityMeasurementException("Division by zero");
			}

			double result = round(base1 / base2);
			saveSuccess(operation, q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), String.valueOf(result));
			return result;
		} catch (Exception e) {
			saveError(operation, q1 != null ? q1.getMeasurementType() : null, e);
			throw new QuantityMeasurementException("Division failed", e);
		}
	}

	@Override
	public QuantityDTO multiply(QuantityDTO q1, QuantityDTO q2) {
		String operation = "MULTIPLY";
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			double q2InQ1Unit = fromBaseUnit(q1.getMeasurementType(), q1.getUnit(), base2);
			double resultValue = q1.getValue() * q2InQ1Unit;

			QuantityDTO result = new QuantityDTO(round(resultValue), q1.getUnit(), q1.getMeasurementType());
			saveSuccess(operation, q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), formatQuantity(result));
			return result;
		} catch (Exception e) {
			saveError(operation, q1 != null ? q1.getMeasurementType() : null, e);
			throw new QuantityMeasurementException("Multiplication failed", e);
		}
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementsByOperation(String operation) {
		String userEmail = resolveCurrentUserEmail();
		List<QuantityMeasurementEntity> entities = repository.findByOperationAndUserEmail(operation, userEmail);
		return QuantityMeasurementDTO.fromEntityList(entities);
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
		String userEmail = resolveCurrentUserEmail();
		List<QuantityMeasurementEntity> entities = repository.findByMeasurementTypeAndUserEmail(measurementType, userEmail);
		return QuantityMeasurementDTO.fromEntityList(entities);
	}

	@Override
	public List<QuantityMeasurementDTO> getAllMeasurements() {
		String userEmail = resolveCurrentUserEmail();
		List<QuantityMeasurementEntity> entities = repository.findByUserEmail(userEmail);
		return QuantityMeasurementDTO.fromEntityList(entities);
	}

	private void saveSuccess(String operation, String measurementType, String operand1, String operand2, String result) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(operation, operand1, operand2, result);
		entity.setMeasurementType(measurementType);
		entity.setUserEmail(resolveCurrentUserEmail());
		repository.save(entity);
	}

	private void saveError(String operation, String measurementType, Exception e) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(operation, e.getMessage());
		entity.setMeasurementType(measurementType);
		entity.setUserEmail(resolveCurrentUserEmail());
		repository.save(entity);
	}

	private String resolveCurrentUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return "SYSTEM";
		}

		String name = authentication.getName();
		return (name == null || name.isBlank()) ? "SYSTEM" : name;
	}

	private void ensureSameMeasurementType(QuantityDTO q1, QuantityDTO q2) {
		if (!q1.getMeasurementType().equalsIgnoreCase(q2.getMeasurementType())) {
			throw new IllegalArgumentException("Measurement types must match");
		}
	}

	private void ensureArithmeticSupported(String measurementType) {
		if ("TEMPERATUREUNIT".equalsIgnoreCase(measurementType)) {
			throw new IllegalArgumentException("Arithmetic operations are not supported for temperature");
		}
	}

	private double toBaseUnit(String measurementType, String unit, double value) {
		return switch (measurementType.toUpperCase()) {
			case "LENGTHUNIT" -> normalizeLengthUnit(unit).convertToBaseUnit(value);
			case "WEIGHTUNIT" -> normalizeWeightUnit(unit).convertToBaseUnit(value);
			case "VOLUMEUNIT" -> normalizeVolumeUnit(unit).convertToBaseUnit(value);
			case "TEMPERATUREUNIT" -> normalizeTemperatureUnit(unit).convertToBaseUnit(value);
			default -> throw new IllegalArgumentException("Unsupported measurement type: " + measurementType);
		};
	}

	private double fromBaseUnit(String measurementType, String unit, double baseValue) {
		return switch (measurementType.toUpperCase()) {
			case "LENGTHUNIT" -> normalizeLengthUnit(unit).convertFromBaseUnit(baseValue);
			case "WEIGHTUNIT" -> normalizeWeightUnit(unit).convertFromBaseUnit(baseValue);
			case "VOLUMEUNIT" -> normalizeVolumeUnit(unit).convertFromBaseUnit(baseValue);
			case "TEMPERATUREUNIT" -> normalizeTemperatureUnit(unit).convertFromBaseUnit(baseValue);
			default -> throw new IllegalArgumentException("Unsupported measurement type: " + measurementType);
		};
	}

	private LengthUnit normalizeLengthUnit(String rawUnit) {
		String unit = sanitize(rawUnit);
		return switch (unit) {
			case "INCH", "INCHES" -> LengthUnit.INCHES;
			case "FOOT", "FEET" -> LengthUnit.FEET;
			case "YARD", "YARDS" -> LengthUnit.YARDS;
			case "METER", "METERS", "METRE", "METRES", "M" -> LengthUnit.METER;
			case "CENTIMETER", "CENTIMETERS", "CENTIMETRE", "CENTIMETRES", "CM" -> LengthUnit.CENTIMETERS;
			case "MILLIMETER", "MILLIMETERS", "MILLIMETRE", "MILLIMETRES", "MM" -> LengthUnit.MILLIMETRE;
			default -> throw new IllegalArgumentException("Unsupported length unit: " + rawUnit);
		};
	}

	private WeightUnit normalizeWeightUnit(String rawUnit) {
		String unit = sanitize(rawUnit);
		return switch (unit) {
			case "MILLIGRAM", "MILLIGRAMS", "MG" -> WeightUnit.MILLIGRAM;
			case "GRAM", "GRAMS", "G" -> WeightUnit.GRAM;
			case "KILOGRAM", "KILOGRAMS", "KG" -> WeightUnit.KILOGRAM;
			case "OUNCE", "OUNCES", "OZ" -> WeightUnit.OUNCE;
			case "POUND", "POUNDS", "LB", "LBS" -> WeightUnit.POUND;
			case "TONNE", "TONNES" -> WeightUnit.TONNE;
			default -> throw new IllegalArgumentException("Unsupported weight unit: " + rawUnit);
		};
	}

	private VolumeUnit normalizeVolumeUnit(String rawUnit) {
		String unit = sanitize(rawUnit);
		return switch (unit) {
			case "LITER", "LITERS", "LITRE", "LITRES", "L" -> VolumeUnit.LITRE;
			case "MILLILITER", "MILLILITERS", "MILLILITRE", "MILLILITRES", "ML" -> VolumeUnit.MILLILITRE;
			case "GALLON", "GALLONS" -> VolumeUnit.GALLON;
			case "CUBICMETER", "CUBICMETERS", "CUBICMETRE", "CUBICMETRES", "M3", "CUBIC_METRE" -> VolumeUnit.CUBIC_METRE;
			default -> throw new IllegalArgumentException("Unsupported volume unit: " + rawUnit);
		};
	}

	private TemperatureUnit normalizeTemperatureUnit(String rawUnit) {
		String unit = sanitize(rawUnit);
		return switch (unit) {
			case "CELSIUS", "C" -> TemperatureUnit.CELSIUS;
			case "FAHRENHEIT", "F" -> TemperatureUnit.FAHRENHEIT;
			case "KELVIN", "K" -> TemperatureUnit.KELVIN;
			default -> throw new IllegalArgumentException("Unsupported temperature unit: " + rawUnit);
		};
	}

	private String sanitize(String unit) {
		return unit == null ? "" : unit.trim().toUpperCase().replace('-', '_').replace(' ', '_');
	}
	
	@Override
	public void deleteById(Long id) {
	    String email = resolveCurrentUserEmail();

	    QuantityMeasurementEntity entity = repository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Record not found"));

	    // SECURITY: user can delete only their own record
	    if (!entity.getUserEmail().equals(email)) {
	        throw new RuntimeException("Unauthorized delete attempt");
	    }

	    repository.delete(entity);
	}

	@Override
	@Transactional
	public void deleteAllByUserEmail(String email) {
	    repository.deleteAllByUserEmail(email);
	}
	
	@Override
	@Transactional
	public void deleteFiltered(String email, String operation, String measurementType) {

	    //both filters
	    if (operation != null && !operation.isBlank() &&
	        measurementType != null && !measurementType.isBlank()) {

	        repository.deleteByUserEmailAndOperationAndMeasurementType(email, operation, measurementType);
	        return;
	    }

	    //only operation
	    if (operation != null && !operation.isBlank()) {
	        repository.deleteByUserEmailAndOperation(email, operation);
	        return;
	    }

	    //onty type
	    if (measurementType != null && !measurementType.isBlank()) {
	        repository.deleteByUserEmailAndMeasurementType(email, measurementType);
	        return;
	    }

	    //when no filter delete all
	    repository.deleteAllByUserEmail(email);
	}
}
