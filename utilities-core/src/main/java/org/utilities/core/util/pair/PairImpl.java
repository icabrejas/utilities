package org.utilities.core.util.pair;

public class PairImpl<X, Y> implements Pair<X, Y> {

	private final X x;
	private final Y y;

	public PairImpl(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public X getX() {
		return x;
	}

	@Override
	public Y getY() {
		return y;
	}
	
}
