package org.utilities.timeseries.subsample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.utilities.timeseries.subsample.wieght.WeightCalculator;

public class Subsampler<V> {

	private WeightCalculator<V> calculator;
	private int amount;
	private Node<V> last;
	private List<Node<V>> nodes = new ArrayList<>();

	public Subsampler(WeightCalculator<V> calculator, int amount) {
		this.calculator = calculator;
		this.amount = amount;
	}

	public void add(V value) {
		Node<V> node = new Node<>(calculator, value);
		if (last != null) {
			Node.connect(last, node);
		}
		last = node;
		nodes.add(node);
		if (amount < nodes.size()) {
			Collections.sort(nodes);
			Node<V> removed = nodes.remove(0);
			removed.disconnect();

		}
	}

}
