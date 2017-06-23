package org.utilities.graphics.awt.scale;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.function.Supplier;

import org.utilities.core.util.function.SupplierPlus;
import org.utilities.graphics.awt.UtilitiesAWT;

public class AWTScale2D extends Scale2D<Double, Integer> {

	public final Scale2D<Double, Integer> units;

	public AWTScale2D(Supplier<Rectangle2D> viewbox, Supplier<Rectangle> viewport) {
		super(UtilitiesAWT.getXScale(viewbox, viewport),
				UtilitiesAWT.getYScale(viewbox, viewport, Orientation.NEGATIVE));
		viewbox = SupplierPlus.newInstance(viewbox)
				.map(UtilitiesAWT::setLocation, new Point2D.Double(0, 0));
		viewport = SupplierPlus.newInstance(viewport)
				.map(UtilitiesAWT::setLocation, new Point(0, 0));
		units = new Scale2D<>(UtilitiesAWT.getXScale(viewbox, viewport),
				UtilitiesAWT.getYScale(viewbox, viewport, Orientation.POSITIVE));
	}

	public Point transform(Point2D p) {
		int x = this.x.transform(p.getX());
		int y = this.y.transform(p.getY());
		return new Point(x, y);
	}

	public Point2D invert(Point p) {
		double x = this.x.invert(p.x);
		double y = this.y.invert(p.y);
		return new Point2D.Double(x, y);
	}

}
