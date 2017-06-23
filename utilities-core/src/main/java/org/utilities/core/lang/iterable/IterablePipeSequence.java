package org.utilities.core.lang.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IterablePipeSequence implements IterablePipe<Double> {

	private double from;
	private double to;
	private double by;

	public IterablePipeSequence(double from, double to, double by) {
		this.from = from;
		this.to = to;
		this.by = by;
	}

	public static IterablePipeSequence newInstance(double from, double to, double by) {
		return new IterablePipeSequence(from, to, by);
	}

	@Override
	public Iterator<Double> iterator() {
		return new It(from, to, by);
	}

	public double getFrom() {
		return from;
	}

	public double getTo() {
		return to;
	}

	public double getBy() {
		return by;
	}

	public int size() {
		return (int) ((to + by - from) / by);
	}

	@Override
	public String toString() {
		return "IterableSequence [from=" + from + ", to=" + to + ", by=" + by + "]";
	}

	private static class It implements Iterator<Double> {

		private double current;
		private double to;
		private double step;

		public It(double from, double to, double step) {
			this.current = from - step;
			this.to = to;
			this.step = step;
		}

		@Override
		public boolean hasNext() {
			return current + step <= to;
		}

		@Override
		public Double next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return current += step;
		}

	}

}