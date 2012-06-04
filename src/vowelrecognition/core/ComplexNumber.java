package vowelrecognition.core;

import java.util.ArrayList;
import java.util.List;

public class ComplexNumber {
	private final double re;
	private double im;

	public ComplexNumber(double x) {
		re = x;
	}

	public ComplexNumber(double re, double im) {
		this(re);
		this.im = im;
	}

	public ComplexNumber(ComplexNumber c) {
		this(c.re, c.im);
	}

	public double real() {
		return this.re;
	}

	public double imaginary() {
		return this.im;
	}

	public ComplexNumber plus(ComplexNumber c) {
		return new ComplexNumber(this.re + c.re, this.im + c.im);
	}

	public ComplexNumber plus(double x) {
		return this.plus(new ComplexNumber(x));
	}

	public ComplexNumber minus(ComplexNumber c) {
		return this.plus(new ComplexNumber(-c.re, -c.im));
	}

	public ComplexNumber minus(double x) {
		return this.minus(new ComplexNumber(x));
	}

	public ComplexNumber times(ComplexNumber c) {
		return new ComplexNumber(this.re * c.re - this.im * c.im, this.re * c.im
				+ this.im * c.re);
	}

	public ComplexNumber times(double x) {
		return this.times(new ComplexNumber(x));
	}

	public ComplexNumber conjugate() {
		return new ComplexNumber(re, -im);
	}

	public double abs() {
		return Math.sqrt(re * re + im * im);
	}

	public static ComplexNumber[] getComplexArray(double[] v) {
		ComplexNumber[] tmp = new ComplexNumber[v.length];
		for (int i = 0; i < v.length; ++i) {
			tmp[i] = new ComplexNumber(v[i]);
		}
		return tmp;
	}

	public static List<ComplexNumber> getComplexArrayList(ComplexNumber[] v) {
		List<ComplexNumber> tmp = new ArrayList<ComplexNumber>();
		for (ComplexNumber c : v) {
			tmp.add(new ComplexNumber(c));
		}
		return tmp;
	}

	public static List<ComplexNumber> getComplexArrayList(double[] v) {
		return ComplexNumber.getComplexArrayList(ComplexNumber.getComplexArray(v));
	}
}
