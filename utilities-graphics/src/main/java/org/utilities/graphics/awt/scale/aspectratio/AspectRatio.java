package org.utilities.graphics.awt.scale.aspectratio;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.function.Function;
import java.util.function.Supplier;

public class AspectRatio {

	public static Function<Rectangle2D, Rectangle2D> viewportRatio(Supplier<Rectangle> viewport) {
		return (Rectangle2D viewbox) -> {
			Rectangle viewport_ = viewport.get();
			return viewportRatio(viewbox, viewport_);
		};
	}

	public static Rectangle2D viewportRatio(Rectangle2D viewbox, Rectangle viewport) {
		double x, y, width, height;
		if (calculate(viewport) < calculate(viewbox)) {
			x = viewbox.getX();
			width = viewbox.getWidth();
			height = viewport.getHeight() * viewbox.getWidth() / viewport.getWidth();
			y = viewbox.getCenterY() - height / 2;
		} else {
			y = viewbox.getY();
			height = viewbox.getHeight();
			width = viewport.getWidth() * viewbox.getHeight() / viewport.getHeight();
			x = viewbox.getCenterX() - width / 2;
		}
		viewbox = new Rectangle2D.Double(x, y, width, height);
		return viewbox;
	}

	public static Rectangle2D squareRatio(Rectangle2D viewbox) {
		return viewportRatio(viewbox, new Rectangle(0, 0, 1, 1));
	}

	public static Function<Rectangle, Rectangle> viewboxRatio(Supplier<Rectangle2D> viewbox) {
		return (Rectangle viewport) -> {
			Rectangle2D viewbox_ = viewbox.get();
			return viewboxRatio(viewbox_, viewport);
		};
	}

	public static Rectangle viewboxRatio(Rectangle2D viewbox, Rectangle viewport) {
		int x, y, width, height;
		if (calculate(viewbox) < calculate(viewport)) {
			y = (int) viewport.getY();
			height = (int) viewport.getHeight();
			width = (int) (viewbox.getWidth() * viewport.getHeight() / viewbox.getHeight());
			x = (int) (viewport.getCenterX() - width / 2);
		} else {
			x = (int) viewport.getX();
			width = (int) viewport.getWidth();
			height = (int) (viewbox.getHeight() * viewport.getWidth() / viewbox.getWidth());
			y = (int) (viewport.getCenterY() - height / 2);
		}
		viewport = new Rectangle(x, y, width, height);
		return viewport;
	}

	public static Rectangle squareRatio(Rectangle viewport) {
		return viewboxRatio(new Rectangle2D.Double(0, 0, 1, 1), viewport);
	}
	
	public static double calculate(Rectangle2D rect) {
		return rect.getWidth() / rect.getHeight();
	}

}
