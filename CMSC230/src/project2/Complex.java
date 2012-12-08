// UMUC CMSC230 - Project 2
// Written by Justin D. Smith
// 17 July 2009
// Complex.java
package project2;

import java.text.DecimalFormat;

public class Complex {
	// Class is immutable by design.
	private final double real;
	private final double imaginary;

	// Force truncate before display.
	DecimalFormat dString = new DecimalFormat("#.0#");

	// Constructor is private forcing client to use Builder Pattern class below.
	private Complex(Builder builder) {
		real 		= builder.real;
		imaginary 	= builder.imaginary;
	}

	public double getReal() { return real; }

	public double getImaginary() { return imaginary; }

	public Complex add(Complex j) {
		double rPart = this.getReal() + j.getReal();
		double iPart = this.getImaginary() + j.getImaginary();
		return new Complex.Builder().fromDouble(rPart, iPart).build();
	}

	public Complex subtract(Complex j) {
		double rPart = j.getReal() - this.getReal();
		double iPart = j.getImaginary() - this.getImaginary();
		return new Complex.Builder().fromDouble(rPart, iPart).build();
	}

	public Complex multiply(Complex j) {
		double 	a = this.getReal(),
				b = this.getImaginary(),
				c = j.getReal(),
				d = j.getImaginary();

		double 	rPart = (a*c) + (b*d) * -1;
		double 	iPart = (a*d) + (b*c);

		return new Complex.Builder().fromDouble(rPart, iPart).build();
	}

	public String toString() {
		StringBuilder results = new StringBuilder();
		if(this.getReal() != 0) {
			results = results.append(dString.format(this.getReal()));
			if(this.getImaginary() != 0)
				results = results.append(this.getImaginary() > 0 ? " + " + dString.format(this.getImaginary()) + "i"
																 : " - " + dString.format(Math.abs(this.getImaginary())) + "i");
		} else if(this.getImaginary() != 0)
			results = results.append(dString.format(this.getImaginary()) + "i");
		else // Real and Imaginary are 0.
			return "0";

		return results.toString();
	}

	// Attempting to challange myself using a simple Builder Pattern.
	// To institanite a new object using fromString:
	// Complex c = new Complex.Builder().fromString((a,b)).build();
	// To institanite a new object using fromDouble:
	// Complex c = new Complex.Builder().fromDouble(0.00, 0.00).build();
	static class Builder {
		private double real;
		private double imaginary;

		public Builder() {}

		// fromString() - Must be in (a,b) format with parens.
		public Builder fromString(String s) {
			String[] tokens = s.split(",");
			real			= Double.parseDouble(tokens[0].substring(1));
			imaginary 		= Double.parseDouble(tokens[1].substring(0, tokens[1].length() - 1));
			return this;
		}

		public Builder fromDouble(double rPart, double iPart) {
			this.real 		= rPart;
			this.imaginary 	= iPart;
			return this;
		}

		public Complex build() { return new Complex(this); }
	}
}
