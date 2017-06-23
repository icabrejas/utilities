package org.utilities.graphics.awt.scale;

public class Scale2D<I, O> {

	public final Scale<I, O> x;
	public final Scale<I, O> y;

	public Scale2D(Scale<I, O> x, Scale<I, O> y) {
		this.x = x;
		this.y = y;
	}

}
