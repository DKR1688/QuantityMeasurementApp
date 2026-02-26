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
//        Feet f1=new Feet(v1);
//        Feet f2=new Feet(v2);

		Length l1 = new Length(v1, Length.LengthUnit.FEET);
		Length l2 = new Length(v2, Length.LengthUnit.FEET);
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
//        Inches i1=new Inches(v1);
//        Inches i2=new Inches(v2);

		Length l1 = new Length(v1, Length.LengthUnit.INCHES);
		Length l2 = new Length(v2, Length.LengthUnit.INCHES);
		System.out.println("Input: " + v1 + " inch and " + v2 + " inch");
		System.out.println("Output: Equal (" + l1.equals(l2) + ")");
		System.out.println();
	}

	// UC3 ---
	public static void demonstrateFeetInchesComparison(double v1, double v2) {
		Length f = new Length(v1, Length.LengthUnit.FEET);
		Length l = new Length(v2, Length.LengthUnit.INCHES);
		System.out.println("Input: " + v1 + " feet and " + v2 + " inch");
		System.out.println("Output: Equal (" + f.equals(l) + ")");
		System.out.println();
	}

	// UC4 - method to yard comparison
	public static void demonstrateYardComparison(double v1, Length.LengthUnit u1, double v2, Length.LengthUnit u2) {
		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);

		System.out.println("Input: " + l1 + " and " + l2);
		System.out.println("Output: Equal (" + l1.equals(l2) + ")");
		System.out.println();
	}

	// UC5 ---
	// method to raw values
	public static void demonstrateLengthConversion(double value, Length.LengthUnit from, Length.LengthUnit to) {

		double result = Length.convert(value, from, to);

		System.out.println("Input: convert(" + value + ", " + from + ", " + to + ")");
		System.out.println("Output: " + result);
		System.out.println();
	}

	// method to existing Length object
	public static void demonstrateLengthConversion(Length length, Length.LengthUnit to) {

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

	public static void main(String[] args) {
		// examples
		demonstrateFeetEquality(1.0, 1.0);
		demonstrateFeetEquality(1.0, 2.0);

		demonstrateInchesEquality(1.0, 1.0);
		demonstrateInchesEquality(1.0, 2.0);

		demonstrateFeetInchesComparison(1.0, 12.0);

		demonstrateYardComparison(1.0, Length.LengthUnit.YARDS, 3.0, Length.LengthUnit.FEET);
		demonstrateYardComparison(1.0, Length.LengthUnit.YARDS, 36.0, Length.LengthUnit.INCHES);
		demonstrateYardComparison(1.0, Length.LengthUnit.CENTIMETERS, 0.393701, Length.LengthUnit.INCHES);
		demonstrateYardComparison(2.0, Length.LengthUnit.YARDS, 72.0, Length.LengthUnit.INCHES);

		// UC5 ---
		demonstrateLengthConversion(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		demonstrateLengthConversion(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET);
		demonstrateLengthConversion(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS);
		demonstrateLengthConversion(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES);
		demonstrateLengthConversion(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);

		Length yard = new Length(1.0, Length.LengthUnit.YARDS);
		demonstrateLengthConversion(yard, Length.LengthUnit.INCHES);

		// UC6 ---
		demonstrateAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(2.0, Length.LengthUnit.FEET));
		demonstrateAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES));
		demonstrateAddition(new Length(12.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.FEET));
		demonstrateAddition(new Length(36.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.YARDS));
		demonstrateAddition(new Length(2.54, Length.LengthUnit.CENTIMETERS), new Length(1.0, Length.LengthUnit.INCHES));
		demonstrateAddition(new Length(5.0, Length.LengthUnit.FEET), new Length(-2.0, Length.LengthUnit.FEET));
	}
}