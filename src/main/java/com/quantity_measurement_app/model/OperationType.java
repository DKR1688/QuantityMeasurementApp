package com.quantity_measurement_app.model;

//this enum representing the different types of operations that can be performed
public enum OperationType {
	ADD("Addition"), SUBTRACT("Subtraction"), MULTIPLY("Multiplication"), DIVIDE("Division"), COMPARE("Comparison"),
	CONVERT("Conversion");

	private final String displayName;

	OperationType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	// it convert string to OperationType enum
	public static OperationType fromString(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			return OperationType.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid operation type: " + value);
		}
	}

	public static String[] getValidOperations() {
		OperationType[] types = OperationType.values();
		String[] operations = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			operations[i] = types[i].name();
		}
		return operations;
	}
}
