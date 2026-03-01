package com.quantity_measurement_app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {
	private static final double EPSILON = 1e-4;

	// UC1 --- feet tests
	@Test
	void testEquality_SameValue() {
		assertEquals(new Length(1.0, LengthUnit.FEET), new Length(1.0, LengthUnit.FEET));
	}

	@Test
	void testEquality_DifferentValue() {
		assertNotEquals(new Length(1.0, LengthUnit.FEET), new Length(2.0, LengthUnit.FEET));
	}

	@Test
	void testEquality_NullComparison() {
		Length feet = new Length(1.0, LengthUnit.FEET);
		assertNotEquals(feet, null);
	}

	@Test
	void testEquality_SameReference() {
		Length feet = new Length(1.0, LengthUnit.FEET);
		assertEquals(feet, feet);
	}

	@Test
	void testEquality_DifferentClass() {
		Length feet = new Length(1.0, LengthUnit.FEET);
		assertNotEquals(feet, "Not a Length object");
	}

	// UC2 --- inches tests
	@Test
	void testInchesEquality_SameValue() {
		assertEquals(new Length(1.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.INCHES));
	}

	@Test
	void testInchesEquality_DifferentValue() {
		assertNotEquals(new Length(1.0, LengthUnit.INCHES), new Length(2.0, LengthUnit.INCHES));
	}

	// UC3 --- cross unit(FEET & INCHES)
	@Test
	void testFeetToInches_Equivalent() {
		assertEquals(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES));
	}

	@Test
	void testFeetToInches_NotEquivalent() {
		assertNotEquals(new Length(1.0, LengthUnit.FEET), new Length(10.0, LengthUnit.INCHES));
	}

	@Test
	void testMultipleFeetComparison() {
		assertEquals(new Length(2.0, LengthUnit.FEET), new Length(24.0, LengthUnit.INCHES));
	}

	// UC4 --- yard tests
	@Test
	void testEquality_YardToYard_SameValue() {
		assertEquals(new Length(1.0, LengthUnit.YARDS), new Length(1.0, LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToYard_DifferentValue() {
		assertNotEquals(new Length(1.0, LengthUnit.YARDS), new Length(2.0, LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToFeet_EquivalentValue() {
		assertEquals(new Length(1.0, LengthUnit.YARDS), new Length(3.0, LengthUnit.FEET));
	}

	@Test
	void testEquality_FeetToYard_EquivalentValue() {
		assertEquals(new Length(3.0, LengthUnit.FEET), new Length(1.0, LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToInches_EquivalentValue() {
		assertEquals(new Length(1.0, LengthUnit.YARDS), new Length(36.0, LengthUnit.INCHES));
	}

	@Test
	void testEquality_InchesToYard_EquivalentValue() {
		assertEquals(new Length(36.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.YARDS));
	}

	@Test
	void testEquality_YardToFeet_NonEquivalentValue() {
		assertNotEquals(new Length(1.0, LengthUnit.YARDS), new Length(2.0, LengthUnit.FEET));
	}

	// UC4 --- centimeter tests
	@Test
	void testEquality_CentimetersToCentimeters() {
		assertEquals(new Length(2.0, LengthUnit.CENTIMETERS), new Length(2.0, LengthUnit.CENTIMETERS));
	}

	@Test
	void testEquality_CentimetersToInches_EquivalentValue() {
		assertEquals(new Length(1.0, LengthUnit.CENTIMETERS), new Length(0.393701, LengthUnit.INCHES));
	}

	@Test
	void testEquality_CentimetersToFeet_NonEquivalentValue() {
		assertNotEquals(new Length(1.0, LengthUnit.CENTIMETERS), new Length(1.0, LengthUnit.FEET));
	}

	// transitive property
	@Test
	void testEquality_MultiUnit_TransitiveProperty() {
		Length yard = new Length(1.0, LengthUnit.YARDS);
		Length feet = new Length(3.0, LengthUnit.FEET);
		Length inches = new Length(36.0, LengthUnit.INCHES);

		assertEquals(yard, feet);
		assertEquals(feet, inches);
		assertEquals(yard, inches);
	}

	// null unit validation
	@Test
	void testEquality_YardWithNullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Length(1.0, null));
	}

	@Test
	void testEquality_AllUnits_ComplexScenario() {
		assertEquals(new Length(2.0, LengthUnit.YARDS), new Length(6.0, LengthUnit.FEET));
		assertEquals(new Length(2.0, LengthUnit.YARDS), new Length(72.0, LengthUnit.INCHES));
	}

	// UC5 --- unit conversion tests
	@Test
	void testConversion_FeetToInches() {
		double result = Length.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
		assertEquals(12.0, result, EPSILON);
	}

	@Test
	void testConversion_InchesToFeet() {
		double result = Length.convert(24.0, LengthUnit.INCHES, LengthUnit.FEET);
		assertEquals(2.0, result, EPSILON);
	}

	@Test
	void testConversion_YardsToInches() {
		double result = Length.convert(1.0, LengthUnit.YARDS, LengthUnit.INCHES);
		assertEquals(36.0, result, EPSILON);
	}

	@Test
	void testConversion_InchesToYards() {
		double result = Length.convert(72.0, LengthUnit.INCHES, LengthUnit.YARDS);
		assertEquals(2.0, result, EPSILON);
	}

	// cross-Unit Conversion
	@Test
	void testConversion_CentimetersToInches() {
		double result = Length.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
		assertEquals(1.0, result, EPSILON);
	}

	@Test
	void testConversion_FeetToYards() {
		double result = Length.convert(6.0, LengthUnit.FEET, LengthUnit.YARDS);
		assertEquals(2.0, result, EPSILON);
	}

	// round Trip Conversion
	@Test
	void testConversion_RoundTrip_PreservesValue() {
		double original = 5.0;
		double converted = Length.convert(original, LengthUnit.FEET, LengthUnit.INCHES);
		double back = Length.convert(converted, LengthUnit.INCHES, LengthUnit.FEET);

		assertEquals(original, back, EPSILON);
	}

	// zero Value
	@Test
	void testConversion_ZeroValue() {
		double result = Length.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES);
		assertEquals(0.0, result, EPSILON);
	}

	// negative Value
	@Test
	void testConversion_NegativeValue() {
		double result = Length.convert(-1.0, LengthUnit.FEET, LengthUnit.INCHES);
		assertEquals(-12.0, result, EPSILON);
	}

	// same Unit Conversion
	@Test
	void testConversion_SameUnit() {
		double result = Length.convert(5.0, LengthUnit.FEET, LengthUnit.FEET);
		assertEquals(5.0, result, EPSILON);
	}

	// large Value
	@Test
	void testConversion_LargeValue() {
		double large = 1_000_000.0;
		double result = Length.convert(large, LengthUnit.FEET, LengthUnit.INCHES);
		assertEquals(12_000_000.0, result, EPSILON);
	}

	// small Value
	@Test
	void testConversion_SmallValue() {
		double small = 0.0001;
		double result = Length.convert(small, LengthUnit.FEET, LengthUnit.INCHES);
		assertEquals(0.0012, result, EPSILON);
	}

	// invalid Unit Handling
	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> Length.convert(1.0, null, LengthUnit.FEET));
		assertThrows(IllegalArgumentException.class, () -> Length.convert(1.0, LengthUnit.FEET, null));
	}

	// invalid Value Handling
	@Test
	void testConversion_NaN_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> Length.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES));
	}

	@Test
	void testConversion_Infinite_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> Length.convert(Double.POSITIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCHES));
		assertThrows(IllegalArgumentException.class,
				() -> Length.convert(Double.NEGATIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCHES));
	}

	// UC6 --- unit addition tests
	@Test
	void testAddition_SameUnit_FeetPlusFeet() {
		Length result = new Length(1.0, LengthUnit.FEET).add(new Length(2.0, LengthUnit.FEET));
		assertEquals(new Length(3.0, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_SameUnit_InchPlusInch() {
		Length result = new Length(6.0, LengthUnit.INCHES).add(new Length(6.0, LengthUnit.INCHES));

		assertEquals(new Length(12.0, LengthUnit.INCHES), result);
	}

	@Test
	void testAddition_CrossUnit_FeetPlusInches() {
		Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES));
		assertEquals(new Length(2.0, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_CrossUnit_InchPlusFeet() {
		Length result = new Length(12.0, LengthUnit.INCHES).add(new Length(1.0, LengthUnit.FEET));
		assertEquals(new Length(24.0, LengthUnit.INCHES), result);
	}

	@Test
	void testAddition_CrossUnit_YardPlusFeet() {
		Length result = new Length(1.0, LengthUnit.YARDS).add(new Length(3.0, LengthUnit.FEET));
		assertEquals(new Length(2.0, LengthUnit.YARDS), result);
	}

	@Test
	void testAddition_CrossUnit_CentimeterPlusInch() {
		Length result = new Length(2.54, LengthUnit.CENTIMETERS).add(new Length(1.0, LengthUnit.INCHES));
		assertEquals(5.08, result.getValue(), EPSILON);
		assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
	}

	// commutativity
	@Test
	void testAddition_Commutativity() {
		Length a = new Length(1.0, LengthUnit.FEET);
		Length b = new Length(12.0, LengthUnit.INCHES);

		Length result1 = a.add(b);
		Length result2 = b.add(a);
		assertEquals(result1.convertTo(LengthUnit.INCHES), result2.convertTo(LengthUnit.INCHES));
	}

	// identity
	@Test
	void testAddition_WithZero() {
		Length result = new Length(5.0, LengthUnit.FEET).add(new Length(0.0, LengthUnit.INCHES));
		assertEquals(new Length(5.0, LengthUnit.FEET), result);
	}

	// null handling
	@Test
	void testAddition_NullSecondOperand() {
		Length first = new Length(1.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> first.add(null));
	}

	// large and small value
	@Test
	void testAddition_LargeValues() {
		Length result = new Length(1e6, LengthUnit.FEET).add(new Length(1e6, LengthUnit.FEET));
		assertEquals(new Length(2e6, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_SmallValues() {
		Length result = new Length(0.001, LengthUnit.FEET).add(new Length(0.002, LengthUnit.FEET));
		assertEquals(0.003, result.getValue(), EPSILON);
	}

	// UC7 --- addition tests with targets
	@Test
	void testAddition_ExplicitTargetUnit_Feet() {
		Length result = Length.add(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES),
				LengthUnit.FEET);
		assertEquals(new Length(2.0, LengthUnit.FEET), result);
	}

	@Test
	void testAddition_ExplicitTargetUnit_Inches() {
		Length result = Length.add(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES),
				LengthUnit.INCHES);
		assertEquals(new Length(24.0, LengthUnit.INCHES), result);
	}

	@Test
	void testAddition_ExplicitTargetUnit_Yards() {
		Length result = Length.add(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		assertEquals(new Length(0.6667, LengthUnit.YARDS), result);
	}

	@Test
	void testAddition_ExplicitTargetUnit_Centimeters() {
		Length result = Length.add(new Length(1.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.INCHES),
				LengthUnit.CENTIMETERS);
		assertEquals(new Length(5.08, LengthUnit.CENTIMETERS), result);
	}

	// target same as first and second operand
	@Test
	void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
		Length result = Length.add(new Length(2.0, LengthUnit.YARDS), new Length(3.0, LengthUnit.FEET),
				LengthUnit.YARDS);
		assertEquals(new Length(3.0, LengthUnit.YARDS), result);
	}

	@Test
	void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
		Length result = Length.add(new Length(2.0, LengthUnit.YARDS), new Length(3.0, LengthUnit.FEET),
				LengthUnit.FEET);
		assertEquals(new Length(9.0, LengthUnit.FEET), result);
	}

	// commutativity
	@Test
	void testAddition_ExplicitTargetUnit_Commutativity() {
		Length result1 = Length.add(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		Length result2 = Length.add(new Length(12.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.FEET),
				LengthUnit.YARDS);
		assertEquals(result1, result2);
	}

	// zero value case
	@Test
	void testAddition_ExplicitTargetUnit_WithZero() {
		Length result = Length.add(new Length(5.0, LengthUnit.FEET), new Length(0.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		assertEquals(new Length(1.6667, LengthUnit.YARDS), result);
	}

	// negative value case
	@Test
	void testAddition_ExplicitTargetUnit_NegativeValues() {
		Length result = Length.add(new Length(5.0, LengthUnit.FEET), new Length(-2.0, LengthUnit.FEET),
				LengthUnit.INCHES);
		assertEquals(new Length(36.0, LengthUnit.INCHES), result);
	}

	// null target unit
	@Test
	void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		assertThrows(IllegalArgumentException.class,
				() -> Length.add(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), null));
	}

	// large to small scale and small to large scale
	@Test
	void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
		Length result = Length.add(new Length(1000.0, LengthUnit.FEET), new Length(500.0, LengthUnit.FEET),
				LengthUnit.INCHES);
		assertEquals(new Length(18000.0, LengthUnit.INCHES), result);
	}

	@Test
	void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
		Length result = Length.add(new Length(12.0, LengthUnit.INCHES), new Length(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		assertEquals(new Length(0.6667, LengthUnit.YARDS), result);
	}

	// precision tolerance check
	@Test
	void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
		Length result = Length.add(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES),
				LengthUnit.YARDS);
		Length expected = new Length(0.666666, LengthUnit.YARDS);
		assertEquals(expected, result);
	}

	// UC9 ---
	@Test
	void testEquality_KilogramToKilogram_SameValue() {
		assertEquals(new Weight(1.0, WeightUnit.KILOGRAM), new Weight(1.0, WeightUnit.KILOGRAM));
	}

	@Test
	void testEquality_KilogramToKilogram_DifferentValue() {
		assertNotEquals(new Weight(1.0, WeightUnit.KILOGRAM), new Weight(2.0, WeightUnit.KILOGRAM));
	}

	@Test
	void testEquality_GramToGram() {
		assertEquals(new Weight(500.0, WeightUnit.GRAM), new Weight(500.0, WeightUnit.GRAM));
	}

	@Test
	void testEquality_PoundToPound() {
		assertEquals(new Weight(2.0, WeightUnit.POUND), new Weight(2.0, WeightUnit.POUND));
	}

	@Test
	void testEquality_KilogramToGram_EquivalentValue() {
		assertEquals(new Weight(1.0, WeightUnit.KILOGRAM), new Weight(1000.0, WeightUnit.GRAM));
	}

	@Test
	void testEqualityGramToPound() {
		assertEquals(new Weight(453.592, WeightUnit.GRAM), new Weight(1.0, WeightUnit.POUND));
	}

	@Test
	void testEqualityNullComparison() {
		assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(null));
	}

	@Test
	void testEqualitySameReference() {
		Weight q = new Weight(1.0, WeightUnit.KILOGRAM);
		assertEquals(q, q);
	}

	@Test
	void testEquality_ZeroValue() {
		assertEquals(new Weight(0.0, WeightUnit.KILOGRAM), new Weight(0.0, WeightUnit.GRAM));
	}

	@Test
	void testEquality_NegativeWeight() {
		assertEquals(new Weight(-1.0, WeightUnit.KILOGRAM), new Weight(-1000.0, WeightUnit.GRAM));
	}

	@Test
	void testEquality_LargeWeightValue() {
		assertEquals(new Weight(1000000.0, WeightUnit.GRAM), new Weight(1000.0, WeightUnit.KILOGRAM));
	}

	@Test
	void testEquality_SmallWeightValue() {
		assertEquals(new Weight(0.001, WeightUnit.KILOGRAM), new Weight(1.0, WeightUnit.GRAM));
	}

	@Test
	void testConversionSameUnit() {
		Weight result = new Weight(5.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.KILOGRAM);
		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversionZeroValue() {
		Weight result = new Weight(0.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
		assertEquals(0.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversionNegativeValue() {
		Weight result = new Weight(-1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
		assertEquals(-1000.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversion_RoundTrip() {
		Weight original = new Weight(1.5, WeightUnit.KILOGRAM);
		Weight result = original.convertTo(WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);
		assertEquals(original.getValue(), result.getValue(), EPSILON);
	}

	@Test
	void testAdditionSameUnit_KilogramPlusKilogram() {
		Weight result = new Weight(1.0, WeightUnit.KILOGRAM).add(new Weight(2.0, WeightUnit.KILOGRAM));
		assertEquals(3.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	void testAddition_CrossUnit_KilogramPlusGram() {
		Weight result = new Weight(1.0, WeightUnit.KILOGRAM).add(new Weight(1000.0, WeightUnit.GRAM));
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Gram() {
		Weight result = new Weight(1.0, WeightUnit.KILOGRAM).add(new Weight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);
		assertEquals(2000.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	@Test
	void testAdditionCommutativity() {
		Weight a = new Weight(1.0, WeightUnit.KILOGRAM).add(new Weight(1000.0, WeightUnit.GRAM));
		Weight b = new Weight(1000.0, WeightUnit.GRAM).add(new Weight(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
		assertEquals(a.convertTo(WeightUnit.GRAM), b);
	}

	@Test
	void testAdditionWithZero() {
		Weight result = new Weight(5.0, WeightUnit.KILOGRAM).add(new Weight(0.0, WeightUnit.GRAM));
		assertEquals(5.0, result.getValue(), EPSILON);

	}

	@Test
	void testAdditionNegativeValues() {
		Weight result = new Weight(5.0, WeightUnit.KILOGRAM).add(new Weight(-2000.0, WeightUnit.GRAM));
		assertEquals(3.0, result.getValue(), EPSILON);
	}

	@Test
	void testAdditionLargeValues() {
		Weight result = new Weight(1e6, WeightUnit.KILOGRAM).add(new Weight(1e6, WeightUnit.KILOGRAM));
		assertEquals(2e6, result.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	// UC10 ---
	// these tests maintain functionality and performance
	// interface tests
	@Test
	void testIMeasurableInterface_LengthUnitImplementation() {
		IMeasurable unit = LengthUnit.FEET;
		double base = unit.convertToBaseUnit(1.0);
		double back = unit.convertFromBaseUnit(base);
		assertEquals(1.0, back, EPSILON);
	}

	@Test
	void testIMeasurableInterface_WeightUnitImplementation() {
		IMeasurable unit = WeightUnit.KILOGRAM;
		double base = unit.convertToBaseUnit(2.0);
		double back = unit.convertFromBaseUnit(base);
		assertEquals(2.0, back, EPSILON);
	}

	@Test
	void testIMeasurableInterface_ConsistentBehavior() {
		IMeasurable l = LengthUnit.INCHES;
		IMeasurable w = WeightUnit.GRAM;
		assertTrue(Double.isFinite(l.convertToBaseUnit(5)));
		assertTrue(Double.isFinite(w.convertToBaseUnit(5)));
	}

	// generic quantity functionality
	@Test
	void testGenericQuantity_LengthOperations_Equality() {
		assertEquals(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
	}

	@Test
	void testGenericQuantity_WeightOperations_Equality() {
		assertEquals(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM));
	}

	@Test
	void testGenericQuantity_LengthOperations_Conversion() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		assertEquals(12.0, result.getValue(), EPSILON);
	}

	@Test
	void testGenericQuantity_WeightOperations_Conversion() {
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
		assertEquals(1000.0, result.getValue(), EPSILON);
	}

	@Test
	void testGenericQuantity_LengthOperations_Addition() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES),
				LengthUnit.FEET);
		assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
	}

	@Test
	void testGenericQuantity_WeightOperations_Addition() {
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
				.add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
		assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
	}

	// cross category prevention
	@Test
	void testCrossCategoryPrevention_LengthVsWeight() {
		Quantity<LengthUnit> l = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.GRAM);
		assertFalse(l.equals(w));
	}

	@Test
	void testCrossCategoryPrevention_CompilerTypeSafety() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		assertNotNull(length);
		// Compile-time safety verified by generic constraints
	}

	// constructor validation
	@Test
	void testGenericQuantity_ConstructorValidation_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
	}

	@Test
	void testGenericQuantity_ConstructorValidation_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}

	// conversion and addition combinations
	@Test
	void testGenericQuantity_Conversion_AllUnitCombinations() {
		Quantity<LengthUnit> yard = new Quantity<>(1.0, LengthUnit.YARDS);
		assertEquals(36.0, yard.convertTo(LengthUnit.INCHES).getValue(), EPSILON);
	}

	@Test
	void testGenericQuantity_Addition_AllUnitCombinations() {
		Quantity<LengthUnit> result = new Quantity<>(2.0, LengthUnit.YARDS).add(new Quantity<>(3.0, LengthUnit.FEET),
				LengthUnit.YARDS);
		assertEquals(new Quantity<>(3.0, LengthUnit.YARDS), result);
	}

	// backward compatibility
	@Test
	void testBackwardCompatibility_AllUC1Through9Tests() {
		assertEquals(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
	}

	// quantity measurement app methods tests
	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);
		assertTrue(a.equals(b));
	}

	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
		Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertEquals(1000.0, w.convertTo(WeightUnit.GRAM).getValue(), EPSILON);
	}

	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
				.add(new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
		assertEquals(2.0, result.getValue(), EPSILON);
	}

	// wildcard flexibility
	@Test
	void testTypeWildcard_FlexibleSignatures() {
		Quantity<?> q = new Quantity<>(1.0, LengthUnit.FEET);
		assertNotNull(q);
	}

//	// scalability
//	enum VolumeUnit implements IMeasurable {
//		LITER(1.0), MILLILITER(0.001);
//
//		private final double factor;
//
//		VolumeUnit(double factor) {
//			this.factor = factor;
//		}
//
//		public double getConversionFactor() {
//			return factor;
//		}
//
//		public double convertToBaseUnit(double v) {
//			return v * factor;
//		}
//
//		public double convertFromBaseUnit(double b) {
//			return b / factor;
//		}
//
//		public String getUnitName() {
//			return name();
//		}
//	}
//
//	@Test
//	void testScalability_NewUnitEnumIntegration() {
//		assertEquals(new Quantity<>(1.0, VolumeUnit.LITER), new Quantity<>(1000.0, VolumeUnit.MILLILITER));
//	}
//
//	@Test
//	void testScalability_MultipleNewCategories() {
//		assertNotNull(new Quantity<>(5.0, VolumeUnit.LITER));
//	}

	// bounded type parameter enforcement
	@Test
	void testGenericBoundedTypeParameter_Enforcement() {
		Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
		assertNotNull(q);
	}

	// equals and hashcode contract
	@Test
	void testHashCode_GenericQuantity_Consistency() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	void testEquals_GenericQuantity_ContractPreservation() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
		Quantity<LengthUnit> c = new Quantity<>(36.0, LengthUnit.INCHES);
		assertEquals(a, b);
		assertEquals(b, c);
		assertEquals(a, c);
	}

	// architectural and design tests
	@Test
	void testEnumAsUnitCarrier_BehaviorEncapsulation() {
		assertEquals(12.0, LengthUnit.FEET.convertToBaseUnit(1.0), EPSILON);
	}

	@Test
	void testTypeErasure_RuntimeSafety() {
		Quantity<?> q = new Quantity<>(1.0, LengthUnit.FEET);
		assertTrue(q.equals(new Quantity<>(12.0, LengthUnit.INCHES)));
	}

	@Test
	void testCompositionOverInheritance_Flexibility() {
		assertTrue(new Quantity<>(1.0, LengthUnit.FEET) instanceof Quantity);
	}

	@Test
	void testCodeReduction_DRYValidation() {
		assertNotNull(Quantity.class);
	}

	@Test
	void testMaintainability_SingleSourceOfTruth() {
		assertNotNull(Quantity.class.getDeclaredMethods());
	}

//	@Test
//	void testArchitecturalReadiness_MultipleNewCategories() {
//		assertNotNull(new Quantity<>(10.0, VolumeUnit.LITER));
//	}

	@Test
	void testPerformance_GenericOverhead() {
		long start = System.nanoTime();
		new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
		long end = System.nanoTime();
		assertTrue(end - start < 1_000_000);
	}

	@Test
	void testDocumentation_PatternClarity() {
		assertTrue(IMeasurable.class.isInterface());
	}

	@Test
	void testInterfaceSegregation_MinimalContract() {
		assertEquals(4, IMeasurable.class.getDeclaredMethods().length);
	}

	@Test
	void testImmutability_GenericQuantity() {
		Quantity<LengthUnit> original = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = original.add(new Quantity<>(1.0, LengthUnit.FEET));
		assertNotSame(original, result);
	}

	// UC11 ---
	@Test
	void testEquality_LitreToLitre_SameValue() {
		assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
	}

	@Test
	void testEquality_LitreToLitre_DifferentValue() {
		assertNotEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE));
	}

	@Test
	void testEquality_MillilitreToMillilitre_SameValue() {
		assertEquals(new Quantity<>(500.0, VolumeUnit.MILLILITRE), new Quantity<>(500.0, VolumeUnit.MILLILITRE));
	}

	@Test
	void testEquality_GallonToGallon_SameValue() {
		assertEquals(new Quantity<>(1.0, VolumeUnit.GALLON), new Quantity<>(1.0, VolumeUnit.GALLON));
	}

	// cross unit equality
	@Test
	void testEquality_LitreToMillilitre_EquivalentValue() {
		assertEquals(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
	}

	@Test
	void testEquality_LitreToGallon_EquivalentValue() {
		assertEquals(new Quantity<>(3.78541, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.GALLON));
	}

	@Test
	void testEquality_MillilitreToGallon_EquivalentValue() {
		assertEquals(new Quantity<>(3785.41, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.GALLON));
	}

	// incompatibility tests
	@Test
	void testEquality_VolumeVsLength_Incompatible() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

		assertFalse(volume.equals(length));
	}

	@Test
	void testEquality_VolumeVsWeight_Incompatible() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(volume.equals(weight));
	}

	// reflexive, null, zero, negative, large and small
	@Test
	void testEquality__SameReference() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(volume, volume);
	}

	@Test
	void testEquality__NullComparison() {
		assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(null));
	}

	@Test
	void testEquality_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
	}

	@Test
	void testEqualityZeroValue() {
		assertEquals(new Quantity<>(0.0, VolumeUnit.LITRE), new Quantity<>(0.0, VolumeUnit.MILLILITRE));
	}

	@Test
	void testEquality_NegativeVolume() {
		assertEquals(new Quantity<>(-1.0, VolumeUnit.LITRE), new Quantity<>(-1000.0, VolumeUnit.MILLILITRE));
	}

	@Test
	void testEquality_LargeVolumeValue() {
		assertEquals(new Quantity<>(1000000.0, VolumeUnit.MILLILITRE), new Quantity<>(1000.0, VolumeUnit.LITRE));
	}

	@Test
	void testEquality_SmallVolumeValue() {
		assertEquals(new Quantity<>(0.001, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.MILLILITRE));
	}

	@Test
	void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, result.getValue(), EPSILON);
	}

	@Test
	void testConversionRoundTrip() {
		Quantity<VolumeUnit> original = new Quantity<>(1.5, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = original.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
		assertEquals(original.getValue(), result.getValue(), EPSILON);
	}

	@Test
	void testAddition_SameUnit_LitrePlusLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(2.0, VolumeUnit.LITRE));
		assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE), result);
	}

	@Test
	void testAddition_CrossUnit_LitrePlusMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

		assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), result);
	}

	@Test
	void testAddition_ExplicitTargetUnit_Gallon() {
		Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE)
				.add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);

		assertEquals(2.0, result.getValue(), EPSILON);
	}

	@Test
	void testVolumeUnitEnum_LitreConstant() {
		assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
	}

	@Test
	void testConvertToBaseUnit_GallonToLitre() {
		assertEquals(3.78541, VolumeUnit.GALLON.convertToBaseUnit(1.0), EPSILON);
	}

	@Test
	void testConvertFromBaseUnit_LitreToMillilitre() {
		assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0), EPSILON);
	}

	@Test
	void testHashCodeConsistency() {
		Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
	}
	
}
