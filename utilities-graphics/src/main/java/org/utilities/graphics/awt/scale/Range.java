package org.utilities.graphics.awt.scale;

import java.util.function.Supplier;

public interface Range<T> {

	public double project(T t);

	public T get(double s);

	public class Double implements Range<java.lang.Double> {

		private Supplier<java.lang.Double> min;
		private Supplier<java.lang.Double> max;

		public Double(Supplier<java.lang.Double> min, Supplier<java.lang.Double> max) {
			this.min = min;
			this.max = max;
		}

		@Override
		public double project(java.lang.Double t) {
			return (t - min.get()) / size();
		}

		@Override
		public java.lang.Double get(double s) {
			return min.get() + s * size();
		}

		public double size() {
			return max.get() - min.get();
		}

	}

	public class Int implements Range<java.lang.Integer> {

		private Supplier<Integer> min;
		private Supplier<Integer> max;

		public Int(Supplier<Integer> min, Supplier<Integer> max) {
			this.min = min;
			this.max = max;
		}

		@Override
		public double project(java.lang.Integer t) {
			return (double) (t - min.get()) / size();
		}

		@Override
		public java.lang.Integer get(double s) {
			return (int) Math.round(min.get() + s * size());
		}

		public double size() {
			return max.get() - min.get();
		}

	}

	public class Color implements Range<java.awt.Color> {

		private java.awt.Color[] colors;

		public Color(java.awt.Color... colors) {
			this.colors = colors;
		}

		// FIXME
		@Override
		public double project(java.awt.Color t) {
			throw new RuntimeException("Not implemented yet");
		}

		@Override
		public java.awt.Color get(double s) {
			if (s < 0 || 1 < s) {
				throw new Error();
				// FIXME
				// throw new OutOfRangeException(s, 0, 1);
			}
			int n = colors.length - 1;
			java.awt.Color from = colors[(int) Math.floor(s * n)];
			java.awt.Color to = colors[(int) Math.ceil(s * n)];
			s = n * s - Math.floor(s * n);
			int r = (int) Math.round((1 - s) * from.getRed() + s * to.getRed());
			int g = (int) Math.round((1 - s) * from.getGreen() + s * to.getGreen());
			int b = (int) Math.round((1 - s) * from.getBlue() + s * to.getBlue());
			return new java.awt.Color(r, g, b);
		}

	}

}
