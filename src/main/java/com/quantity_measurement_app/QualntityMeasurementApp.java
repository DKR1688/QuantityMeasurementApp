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

	public static void main(String[] args) {

		demonstrateFeetEquality(1.0, 1.0);
		demonstrateFeetEquality(1.0, 2.0);

		demonstrateInchesEquality(1.0, 1.0);
		demonstrateInchesEquality(1.0, 2.0);

		demonstrateFeetInchesComparison(1.0, 12.0);

		demonstrateYardComparison(1.0, Length.LengthUnit.YARDS, 3.0, Length.LengthUnit.FEET);
		demonstrateYardComparison(1.0, Length.LengthUnit.YARDS, 36.0, Length.LengthUnit.INCHES);
		demonstrateYardComparison(1.0, Length.LengthUnit.CENTIMETERS, 0.393701, Length.LengthUnit.INCHES);
		demonstrateYardComparison(2.0, Length.LengthUnit.YARDS, 72.0, Length.LengthUnit.INCHES);
	}
}