package org.utilities.timeseries.subsample;

import org.utilities.timeseries.subsample.wieght.WeightCalculator;

public class Node<V> implements Comparable<Node<V>> {

	private WeightCalculator<V> calculator;
	private V value;
	private Node<V> prev;
	private Node<V> next;
	private Double weight;

	public Node(WeightCalculator<V> calculator, V value) {
		this.calculator = calculator;
		this.value = value;
	}

	public static <V> void connect(Node<V> prev, Node<V> next) {
		prev.next = next;
		prev.weight = null;
		next.prev = prev;
		next.weight = null;
	}

	public void disconnect() {
		if (this.prev != null) {
			prev.next = this.next;
		}
		if (this.next != null) {
			next.prev = this.prev;
		}
		this.next = null;
		this.prev = null;
	}

	@Override
	public int compareTo(Node<V> other) {
		return Double.compare(this.weight(), other.weight());
	}

	private double weight() {
		if (weight == null && prev != null && next != null) {
			weight = calculator.calculate(prev.value, value, next.value);
		}
		return weight != null ? weight : Double.MAX_VALUE;
	}

}