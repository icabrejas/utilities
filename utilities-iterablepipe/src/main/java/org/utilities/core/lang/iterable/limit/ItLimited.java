package org.utilities.core.lang.iterable.limit;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.track.ItTracked;
import org.utilities.core.util.lambda.LambdaValue;

public class ItLimited<T> extends ItTracked<T> {

	private int serialNumber;
	private IPLimitedStopCriteria<T> stopCriteria;
	private LambdaValue<T> next = new LambdaValue<>();
	private boolean stop;

	public ItLimited(int serialNumber, Iterator<T> it, IPLimitedStopCriteria<T> stopCriteria) {
		super(serialNumber, it, stopCriteria);
		this.serialNumber = serialNumber;
		this.stopCriteria = stopCriteria;
	}

	@Override
	public boolean hasNext() {
		while (!next.isPresent() && super.hasNext()) {
			next.set(super.next());
			stop = stopCriteria.stop(serialNumber, next.get());
		}
		return next.isPresent() && !stop;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return next.remove();
	}

}