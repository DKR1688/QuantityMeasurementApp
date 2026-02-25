package com.quantity_measurement_app;

import java.util.Objects;
//UC1 - feet measurement equality
public class QualntityMeasurementApp {

	//inner class representing Feet measurement
	public static class Feet {
		private final double value;

		//constructor
		public Feet(double value) {
			this.value = value;
		}

		//equals method
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true; //it is reflexive
			if (obj == null)
				return false; //null check
			if (this.getClass() != obj.getClass())
				return false; //type check

			Feet other = (Feet) obj;
			//here we are comparing value
			return Double.compare(this.value, other.value) == 0;
		}

		@Override
		public int hashCode() {
			return Objects.hash(value);
		}
	}

	public static void main(String[] args) {
		Feet feet1=new Feet(1.0);
		Feet feet2=new Feet(1.0);
		Feet feet3=new Feet(2.0);

		System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal ("+feet1.equals(feet2)+")");
        System.out.println();

        System.out.println("Input: 1.0 ft and 2.0 ft");
        System.out.println("Output: Equal ("+feet1.equals(feet3)+")");
        System.out.println();

        System.out.println("Input: 1.0 ft and null");
        System.out.println("Output: Equal ("+feet1.equals(null)+")");
        System.out.println();

        System.out.println("Input: 1.0 ft and same reference");
        System.out.println("Output: Equal ("+feet1.equals(feet1)+")");
        System.out.println();
	}
}