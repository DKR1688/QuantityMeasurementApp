package com.quantity_measurement_app;

public class QualntityMeasurementApp {
//	//UC1 - feet measurement equality
//	//inner class representing Feet measurement
//	public static class Feet {
//		private final double value;
//
//		//constructor
//		public Feet(double value) {
//			this.value=value;
//		}
//
//		//equals method
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj)
//				return true; //it is reflexive
//			if (obj == null)
//				return false; //null check
//			if (this.getClass() != obj.getClass())
//				return false; //type check
//
//			Feet other = (Feet) obj;
//			//here we are comparing value
//			return Double.compare(this.value, other.value) == 0;
//		}
//
//		@Override
//		public int hashCode() {
//			return Objects.hash(value);
//		}
//	}

	public static void demonstrateFeetEquality(double v1, double v2) {
		Length l1 = new Length(v1, LengthUnit.FEET);
		Length l2 = new Length(v2, LengthUnit.FEET);

		System.out.println("Input: " + v1 + " ft and " + v2 + " ft");
		System.out.println("Output: Equal (" + l1.equals(l2) + ")");
		System.out.println();
	}

//	//UC2 - inch measurement equality
//	//inner class representing Inches measurement
//    public static class Inches {
//        private final double value;
//
//        public Inches(double value) {
//            this.value=value;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) {
//            	return true;
//            }
//            if (obj == null) {
//            	return false;
//            }
//            if (this.getClass() != obj.getClass()) {
//            	return false;
//            }
//
//            Inches other = (Inches) obj;
//            return Double.compare(this.value, other.value) == 0;
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(value);
//        }
//    }

	public static void demonstrateInchesEquality(double v1, double v2) {
		Length l1 = new Length(v1, LengthUnit.INCHES);
		Length l2 = new Length(v2, LengthUnit.INCHES);

		System.out.println("Input: " + v1 + " inch and " + v2 + " inch");
		System.out.println("Output: Equal (" + l1.equals(l2) + ")");
		System.out.println();
	}

	// UC3 ---
	public static void demonstrateFeetInchesComparison(double v1, double v2) {
		Length f = new Length(v1, LengthUnit.FEET);
		Length l = new Length(v2, LengthUnit.INCHES);

		System.out.println("Input: " + v1 + " feet and " + v2 + " inch");
		System.out.println("Output: Equal (" + f.equals(l) + ")");
		System.out.println();
	}

	// UC4 - method to yard comparison
	public static void demonstrateYardComparison(double v1, LengthUnit u1, double v2, LengthUnit u2) {
		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);

		System.out.println("Input: " + l1 + " and " + l2);
		System.out.println("Output: Equal (" + l1.equals(l2) + ")");
		System.out.println();
	}

	// UC5 ---
	// method to raw values
	public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
		double result = Length.convert(value, from, to);

		System.out.println("Input: convert(" + value + ", " + from + ", " + to + ")");
		System.out.println("Output: " + result);
		System.out.println();
	}

	// method to existing Length object
	public static void demonstrateLengthConversion(Length length, LengthUnit to) {
		Length converted = length.convertTo(to);

		System.out.println("Input: " + length);
		System.out.println("Converted to " + to + ": " + converted.getValue());
		System.out.println();
	}

	// UC6 ---
	public static void demonstrateAddition(Length l1, Length l2) {
		Length result = l1.add(l2);

		System.out.println("Input: add(" + l1 + ", " + l2 + ")");
		System.out.println("Output: " + result);
		System.out.println();
	}

	// UC7 ---
	public static void demonstrateAdditionWithTarget(Length l1, Length l2, LengthUnit targetUnit) {

		Length result = Length.add(l1, l2, targetUnit);

		System.out.println("Input: add(" + l1 + ", " + l2 + ", " + targetUnit + ")");
		System.out.println("Output: " + result);
		System.out.println();
	}
	
	
	//UC9 ---
		// weight demonstration methods
		public static boolean demonstrateWeightEquality(Weight weight1, Weight weight2) {
			return weight1.equals(weight2);
		}

		public static boolean demonstrateWeightComparison(double value1, WeightUnit unit1, double value2,
				WeightUnit unit2) {
			Weight w1 = new Weight(value1, unit1);
			Weight w2 = new Weight(value2, unit2);
			return w1.equals(w2);
		}

		public static Weight demonstrateWeightConversion(double value, WeightUnit fromUnit, WeightUnit toUnit) {
			Weight weight = new Weight(value, fromUnit);
			return weight.convertTo(toUnit);
		}

		// method overloading
		public static Weight demonstrateWeightConversion(Weight weight, WeightUnit toUnit) {
			return weight.convertTo(toUnit);
		}

		public static Weight demonstrateWeightAddition(Weight weight1, Weight weight2) {
			return weight1.add(weight2);
		}

		// overloaded version with target unit
		public static Weight demonstrateWeightAddition(Weight weight1, Weight weight2, WeightUnit targetUnit) {
			return weight1.add(weight2, targetUnit);
		}
		

	public static void main(String[] args) {
		// examples
		demonstrateFeetEquality(1.0, 1.0);
		demonstrateFeetEquality(1.0, 2.0);

		demonstrateInchesEquality(1.0, 1.0);
		demonstrateInchesEquality(1.0, 2.0);

		demonstrateFeetInchesComparison(1.0, 12.0);

		demonstrateYardComparison(1.0, LengthUnit.YARDS, 3.0, LengthUnit.FEET);
		demonstrateYardComparison(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES);
		demonstrateYardComparison(1.0, LengthUnit.CENTIMETERS, 0.393701, LengthUnit.INCHES);
		demonstrateYardComparison(2.0, LengthUnit.YARDS, 72.0, LengthUnit.INCHES);

		// UC5 ---
		demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
		demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
		demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
		demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
		demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);

		Length yard = new Length(1.0, LengthUnit.YARDS);
		demonstrateLengthConversion(yard, LengthUnit.INCHES);

		// UC6 ---
		demonstrateAddition(new Length(1.0, LengthUnit.FEET), new Length(2.0, LengthUnit.FEET));
		demonstrateAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES));
		demonstrateAddition(new Length(12.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.FEET));
		demonstrateAddition(new Length(36.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.YARDS));
		demonstrateAddition(new Length(2.54, LengthUnit.CENTIMETERS), new Length(1.0, LengthUnit.INCHES));
		demonstrateAddition(new Length(5.0, LengthUnit.FEET), new Length(-2.0, LengthUnit.FEET));

		// UC7 ---
		demonstrateAdditionWithTarget(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.FEET);
		demonstrateAdditionWithTarget(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
		demonstrateAdditionWithTarget(new Length(36.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.YARDS), LengthUnit.FEET);
		demonstrateAdditionWithTarget(new Length(2.54, LengthUnit.CENTIMETERS), new Length(1.0, LengthUnit.INCHES),	LengthUnit.CENTIMETERS);
		demonstrateAdditionWithTarget(new Length(5.0, LengthUnit.FEET), new Length(-2.0, LengthUnit.FEET), LengthUnit.INCHES);

		
		// UC9 ---
		Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
		Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

		System.out.println("Equality:");
		System.out.println(demonstrateWeightEquality(w1, w2));

		System.out.println("\nConversion:");
		System.out.println(demonstrateWeightConversion(w1, WeightUnit.GRAM));

		System.out.println("\nAddition (implicit):");
		System.out.println(demonstrateWeightAddition(w1, w2));

		System.out.println("\nAddition (explicit - in GRAM):");
		System.out.println(demonstrateWeightAddition(w1, w2, WeightUnit.GRAM));
	}
}