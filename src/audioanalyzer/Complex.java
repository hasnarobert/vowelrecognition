package audioanalyzer;

import java.util.ArrayList;
import java.util.List;

public class Complex {
	private final double re;
	private double im;

	public Complex(double x) {
		re = x;
	}

	public Complex(double re, double im) {
		this(re);
		this.im = im;
	}

	public Complex(Complex c) {
		this(c.re, c.im);
	}

	public double real() {
		return this.re;
	}

	public double imaginary() {
		return this.im;
	}

	public Complex plus(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	public Complex plus(double x) {
		return this.plus(new Complex(x));
	}

	public Complex minus(Complex c) {
		return this.plus(new Complex(-c.re, -c.im));
	}

	public Complex minus(double x) {
		return this.minus(new Complex(x));
	}

	public Complex times(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im
				+ this.im * c.re);
	}

	public Complex times(double x) {
		return this.times(new Complex(x));
	}

	public Complex conjugate() {
		return new Complex(re, -im);
	}

	public double abs() {
		return Math.sqrt(re * re + im * im);
	}

	public static Complex[] getComplexArray(double[] v) {
		Complex[] tmp = new Complex[v.length];
		for (int i = 0; i < v.length; ++i) {
			tmp[i] = new Complex(v[i]);
		}
		return tmp;
	}

	public static List<Complex> getComplexArrayList(Complex[] v) {
		List<Complex> tmp = new ArrayList<Complex>();
		for (Complex c : v) {
			tmp.add(new Complex(c));
		}
		return tmp;
	}

	public static List<Complex> getComplexArrayList(double[] v) {
		return Complex.getComplexArrayList(Complex.getComplexArray(v));
	}
}
