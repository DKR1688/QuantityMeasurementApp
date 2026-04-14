package com.quantity_measurement_app.quantity.service;

import com.quantity_measurement_app.quantity.dto.QuantityDTO;
import com.quantity_measurement_app.quantity.units.LengthUnit;
import com.quantity_measurement_app.quantity.units.TemperatureUnit;
import com.quantity_measurement_app.quantity.units.VolumeUnit;
import com.quantity_measurement_app.quantity.units.WeightUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class QuantityServiceImpl implements IQuantityService {
	private static final double EPSILON = 1e-9;

	private final RestTemplate restTemplate;
	private final String historyServiceBaseUrl;

	@Autowired
	public QuantityServiceImpl(
			RestTemplate restTemplate,
			@Value("${app.history-service.url:http://localhost:8083}") String historyServiceBaseUrl) {
		this.restTemplate = restTemplate;
		this.historyServiceBaseUrl = historyServiceBaseUrl.replaceAll("/+$", "");
	}

	@Override
	public QuantityDTO convert(QuantityDTO input, String targetUnit, String userEmail) {
		try {
			double baseValue = toBaseUnit(input.getMeasurementType(), input.getUnit(), input.getValue());
			double convertedValue = fromBaseUnit(input.getMeasurementType(), targetUnit, baseValue);

			QuantityDTO result = new QuantityDTO(round(convertedValue), targetUnit, input.getMeasurementType());
			saveHistory("CONVERT", input.getMeasurementType(), formatQuantity(input), null, formatQuantity(result), userEmail, null);
			return result;
		} catch (Exception e) {
			saveHistory("CONVERT", input.getMeasurementType(), formatQuantity(input), null, null, userEmail, e.getMessage());
			throw new RuntimeException("Conversion failed", e);
		}
	}

	@Override
	public boolean compare(QuantityDTO q1, QuantityDTO q2, String userEmail) {
		try {
			ensureSameMeasurementType(q1, q2);
			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			boolean result = Math.abs(base1 - base2) < EPSILON;
			saveHistory("COMPARE", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), String.valueOf(result), userEmail, null);
			return result;
		} catch (Exception e) {
			saveHistory("COMPARE", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), null, userEmail, e.getMessage());
			throw new RuntimeException("Comparison failed", e);
		}
	}

	@Override
	public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String userEmail) {
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			double sumBase = base1 + base2;
			double resultValue = fromBaseUnit(q1.getMeasurementType(), q1.getUnit(), sumBase);

			QuantityDTO result = new QuantityDTO(round(resultValue), q1.getUnit(), q1.getMeasurementType());
			saveHistory("ADD", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), formatQuantity(result), userEmail, null);
			return result;
		} catch (Exception e) {
			saveHistory("ADD", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), null, userEmail, e.getMessage());
			throw new RuntimeException("Addition failed", e);
		}
	}

	@Override
	public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String userEmail) {
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			double diffBase = base1 - base2;
			double resultValue = fromBaseUnit(q1.getMeasurementType(), q1.getUnit(), diffBase);

			QuantityDTO result = new QuantityDTO(round(resultValue), q1.getUnit(), q1.getMeasurementType());
			saveHistory("SUBTRACT", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), formatQuantity(result), userEmail, null);
			return result;
		} catch (Exception e) {
			saveHistory("SUBTRACT", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), null, userEmail, e.getMessage());
			throw new RuntimeException("Subtraction failed", e);
		}
	}

	@Override
	public QuantityDTO multiply(QuantityDTO q1, QuantityDTO q2, String userEmail) {
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			double productBase = base1 * base2;
			double resultValue = fromBaseUnit(q1.getMeasurementType(), q1.getUnit(), productBase);

			QuantityDTO result = new QuantityDTO(round(resultValue), q1.getUnit(), q1.getMeasurementType());
			saveHistory("MULTIPLY", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), formatQuantity(result), userEmail, null);
			return result;
		} catch (Exception e) {
			saveHistory("MULTIPLY", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), null, userEmail, e.getMessage());
			throw new RuntimeException("Multiplication failed", e);
		}
	}

	@Override
	public double divide(QuantityDTO q1, QuantityDTO q2, String userEmail) {
		try {
			ensureSameMeasurementType(q1, q2);
			ensureArithmeticSupported(q1.getMeasurementType());

			double base1 = toBaseUnit(q1.getMeasurementType(), q1.getUnit(), q1.getValue());
			double base2 = toBaseUnit(q2.getMeasurementType(), q2.getUnit(), q2.getValue());
			if (Math.abs(base2) < EPSILON) {
				throw new IllegalArgumentException("Division by zero");
			}
			double result = round(base1 / base2);
			saveHistory("DIVIDE", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), String.valueOf(result), userEmail, null);
			return result;
		} catch (Exception e) {
			saveHistory("DIVIDE", q1.getMeasurementType(), formatQuantity(q1), formatQuantity(q2), null, userEmail, e.getMessage());
			throw new RuntimeException("Division failed", e);
		}
	}

	private void ensureSameMeasurementType(QuantityDTO q1, QuantityDTO q2) {
		if (!q1.getMeasurementType().equals(q2.getMeasurementType())) {
			throw new IllegalArgumentException("Cannot operate on different measurement types");
		}
	}

	private void ensureArithmeticSupported(String measurementType) {
		if (measurementType == null || measurementType.isBlank()) {
			throw new IllegalArgumentException("Measurement type is required");
		}
	}

	private double toBaseUnit(String measurementType, String unit, double value) {
		return switch (measurementType) {
			case "LENGTHUNIT" -> normalizeLengthUnit(unit).convertToBaseUnit(value);
			case "WEIGHTUNIT" -> normalizeWeightUnit(unit).convertToBaseUnit(value);
			case "VOLUMEUNIT" -> normalizeVolumeUnit(unit).convertToBaseUnit(value);
			case "TEMPERATUREUNIT" -> normalizeTemperatureUnit(unit).convertToBaseUnit(value);
			default -> throw new IllegalArgumentException("Unknown measurement type: " + measurementType);
		};
	}

	private double fromBaseUnit(String measurementType, String unit, double baseValue) {
		return switch (measurementType) {
			case "LENGTHUNIT" -> normalizeLengthUnit(unit).convertFromBaseUnit(baseValue);
			case "WEIGHTUNIT" -> normalizeWeightUnit(unit).convertFromBaseUnit(baseValue);
			case "VOLUMEUNIT" -> normalizeVolumeUnit(unit).convertFromBaseUnit(baseValue);
			case "TEMPERATUREUNIT" -> normalizeTemperatureUnit(unit).convertFromBaseUnit(baseValue);
			default -> throw new IllegalArgumentException("Unknown measurement type: " + measurementType);
		};
	}

	private LengthUnit normalizeLengthUnit(String rawUnit) {
		return switch (rawUnit.toUpperCase()) {
			case "INCH", "INCHES" -> LengthUnit.INCHES;
			case "FOOT", "FEET" -> LengthUnit.FEET;
			case "YARD", "YARDS" -> LengthUnit.YARDS;
			case "METER", "METERS", "METRE", "METRES", "M" -> LengthUnit.METER;
			case "CENTIMETER", "CENTIMETERS", "CENTIMETRE", "CENTIMETRES", "CM" -> LengthUnit.CENTIMETERS;
			case "MILLIMETER", "MILLIMETERS", "MILLIMETRE", "MILLIMETRES", "MM" -> LengthUnit.MILLIMETRE;
			default -> throw new IllegalArgumentException("Unknown length unit: " + rawUnit);
		};
	}

	private WeightUnit normalizeWeightUnit(String rawUnit) {
		return switch (rawUnit.toUpperCase()) {
			case "MILLIGRAM", "MILLIGRAMS", "MG" -> WeightUnit.MILLIGRAM;
			case "GRAM", "GRAMS", "G" -> WeightUnit.GRAM;
			case "KILOGRAM", "KILOGRAMS", "KG" -> WeightUnit.KILOGRAM;
			case "OUNCE", "OUNCES", "OZ" -> WeightUnit.OUNCE;
			case "POUND", "POUNDS", "LB", "LBS" -> WeightUnit.POUND;
			case "TONNE", "TONNES", "T" -> WeightUnit.TONNE;
			default -> throw new IllegalArgumentException("Unknown weight unit: " + rawUnit);
		};
	}

	private VolumeUnit normalizeVolumeUnit(String rawUnit) {
		return switch (rawUnit.toUpperCase()) {
			case "LITRE", "LITRES", "LITER", "LITERS", "L" -> VolumeUnit.LITRE;
			case "MILLILITRE", "MILLILITRES", "MILLILITER", "MILLILITERS", "ML" -> VolumeUnit.MILLILITRE;
			case "CUBIC_METRE", "CUBIC_METRES", "CUBIC_METER", "CUBIC_METERS", "M3" -> VolumeUnit.CUBIC_METRE;
			case "GALLON", "GALLONS", "GAL" -> VolumeUnit.GALLON;
			default -> throw new IllegalArgumentException("Unknown volume unit: " + rawUnit);
		};
	}

	private TemperatureUnit normalizeTemperatureUnit(String rawUnit) {
		return switch (rawUnit.toUpperCase()) {
			case "CELSIUS", "C" -> TemperatureUnit.CELSIUS;
			case "FAHRENHEIT", "F" -> TemperatureUnit.FAHRENHEIT;
			case "KELVIN", "K" -> TemperatureUnit.KELVIN;
			default -> throw new IllegalArgumentException("Unknown temperature unit: " + rawUnit);
		};
	}

	private double round(double value) {
		return Math.round(value * 1000.0) / 1000.0;
	}

	private String formatQuantity(QuantityDTO q) {
		return round(q.getValue()) + " " + q.getUnit();
	}

	private void saveHistory(String operation, String measurementType, String operand1, String operand2, String result, String userEmail, String error) {
		try {
			String url = historyServiceBaseUrl + "/api/v1/measurements/save";
			Map<String, Object> historyData = new java.util.HashMap<>();
			historyData.put("operation", operation);
			historyData.put("measurementType", measurementType);
			historyData.put("operand1", operand1);
			if (operand2 != null) {
				historyData.put("operand2", operand2);
			}
			if (result != null) {
				historyData.put("result", result);
			}
			historyData.put("userEmail", userEmail);
			if (error != null) {
				historyData.put("error", error);
			}
			System.out.println("Calling history service: " + url);
			System.out.println("History data: " + historyData);
			restTemplate.postForObject(url, historyData, String.class);
			System.out.println("History saved successfully");
		} catch (Exception e) {
			// Log error but don't fail the operation
			System.err.println("Failed to save history: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
