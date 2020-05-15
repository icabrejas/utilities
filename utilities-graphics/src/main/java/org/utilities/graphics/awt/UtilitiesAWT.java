package org.utilities.graphics.awt;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.util.function.BiConsumerPlus;
import org.utilities.core.util.function.FunctionPlus;
import org.utilities.core.util.function.SupplierPlus;
import org.utilities.graphics.awt.component.listeners.ActionListenerImpl;
import org.utilities.graphics.awt.component.listeners.ComponentListenerImpl;
import org.utilities.graphics.awt.component.listeners.FocusListenerImpl;
import org.utilities.graphics.awt.component.listeners.HierarchyBoundsListenerImpl;
import org.utilities.graphics.awt.component.listeners.HierarchyListenerImpl;
import org.utilities.graphics.awt.component.listeners.InputMethodListenerImpl;
import org.utilities.graphics.awt.component.listeners.KeyListenerImpl;
import org.utilities.graphics.awt.component.listeners.ListSelectionListenerImpl;
import org.utilities.graphics.awt.component.listeners.MouseListenerImpl;
import org.utilities.graphics.awt.component.listeners.MouseMotionListenerImpl;
import org.utilities.graphics.awt.component.listeners.MouseWheelListenerImpl;
import org.utilities.graphics.awt.component.listeners.PropertyChangeListenerImpl;
import org.utilities.graphics.awt.scale.AWTScale2D;
import org.utilities.graphics.awt.scale.Orientation;
import org.utilities.graphics.awt.scale.Range;
import org.utilities.graphics.awt.scale.Scale;

public class UtilitiesAWT {

	public static Rectangle2D.Double setLocation(Rectangle2D frame, Point2D loc) {
		return setLocation(frame, loc.getX(), loc.getY());
	}

	public static Rectangle2D.Double setLocation(Rectangle2D frame, double x, double y) {
		return new Rectangle2D.Double(x, y, frame.getWidth(), frame.getHeight());
	}

	public static Rectangle setLocation(Rectangle frame, Point loc) {
		return setLocation(frame, loc.x, loc.y);
	}

	public static Rectangle setLocation(Rectangle frame, int x, int y) {
		return new Rectangle(x, y, frame.width, frame.height);
	}

	public static Scale<Double, Integer> getXScale(Supplier<Rectangle2D> viewbox, Supplier<Rectangle> viewport) {
		Function<Rectangle2D, Double> minIn = Rectangle2D::getMinX;
		Function<Rectangle2D, Double> maxIn = Rectangle2D::getMaxX;
		Range<Double> input = getViewboxRange(viewbox, minIn, maxIn);
		Function<Rectangle, Integer> minOut = FunctionPlus.compose(Double::intValue, Rectangle2D::getMinX);
		Function<Rectangle, Integer> maxOut = FunctionPlus.compose(Double::intValue, Rectangle2D::getMaxX);
		Range<Integer> output = getViewportRange(viewport, minOut, maxOut, Orientation.POSITIVE);
		return Scale.newInstance(input, output);
	}

	public static Scale<Double, Integer> getYScale(Supplier<Rectangle2D> viewbox, Supplier<Rectangle> viewport,
			Orientation orient) {
		Function<Rectangle2D, Double> minIn = Rectangle2D::getMinY;
		Function<Rectangle2D, Double> maxIn = Rectangle2D::getMaxY;
		Range<Double> input = getViewboxRange(viewbox, minIn, maxIn);
		Function<Rectangle, Integer> minOut = FunctionPlus.compose(Double::intValue, Rectangle2D::getMinY);
		Function<Rectangle, Integer> maxOut = FunctionPlus.compose(Double::intValue, Rectangle2D::getMaxY);
		Range<Integer> output = getViewportRange(viewport, minOut, maxOut, orient);
		return Scale.newInstance(input, output);
	}

	public static Range<Double> getViewboxRange(Supplier<Rectangle2D> viewbox, Function<Rectangle2D, Double> min,
			Function<Rectangle2D, Double> max) {
		Supplier<Double> min_ = SupplierPlus.andThen(viewbox, min);
		Supplier<Double> max_ = SupplierPlus.andThen(viewbox, max);
		return new Range.Double(min_, max_);
	}

	public static Range<Integer> getViewportRange(Supplier<Rectangle> viewport, Function<Rectangle, Integer> min,
			Function<Rectangle, Integer> max, Orientation orient) {
		Supplier<Integer> min_ = SupplierPlus.andThen(viewport, min);
		Supplier<Integer> max_ = SupplierPlus.andThen(viewport, max);
		switch (orient) {
		case POSITIVE:
			return new Range.Int(min_, max_);
		case NEGATIVE:
		default:
			return new Range.Int(max_, min_);
		}
	}

	public static GeneralPath toPath(List<Point2D> points, AWTScale2D scale) {
		GeneralPath path = new GeneralPath();
		if (!points.isEmpty()) {
			Point p = scale.transform(points.get(0));
			path.moveTo(p.getX(), p.getY());
			for (int i = 1; i < points.size(); i++) {
				p = scale.transform(points.get(i));
				path.lineTo(p.getX(), p.getY());
			}
		}
		return path;
	}

	public static ActionListenerImpl newActionListener(Consumer<ActionEvent> onActionPerformed) {
		return new ActionListenerImpl().onActionPerformed(onActionPerformed);
	}

	public static ComponentListenerImpl newComponentListener() {
		return new ComponentListenerImpl();
	}

	public static ListSelectionListenerImpl newListSelectionListener(Consumer<ListSelectionEvent> onValueChanged) {
		return new ListSelectionListenerImpl().onValueChanged(onValueChanged);
	}

	public static KeyListenerImpl newKeyListener() {
		return new KeyListenerImpl();
	}

	public static InputMethodListenerImpl newInputMethodListener() {
		return new InputMethodListenerImpl();
	}

	public static HierarchyListenerImpl newHierarchyListener(Consumer<HierarchyEvent> onHierarchyChanged) {
		return new HierarchyListenerImpl().onHierarchyChanged(onHierarchyChanged);
	}

	public static FocusListenerImpl newFocusListener() {
		return new FocusListenerImpl();
	}

	public static HierarchyBoundsListenerImpl newHierarchyBoundsListener() {
		return new HierarchyBoundsListenerImpl();
	}

	public static MouseListenerImpl newMouseListener() {
		return new MouseListenerImpl();
	}

	public static MouseMotionListenerImpl newMouseMotionListener() {
		return new MouseMotionListenerImpl();
	}

	public static MouseWheelListenerImpl newMouseWheelListener(Consumer<MouseWheelEvent> onMouseWheelMoved) {
		return new MouseWheelListenerImpl().onMouseWheelMoved(onMouseWheelMoved);
	}

	public static PropertyChangeListenerImpl newPropertyChangeListener(Consumer<PropertyChangeEvent> onPropertyChange) {
		return new PropertyChangeListenerImpl().onPropertyChange(onPropertyChange);
	}

	public static Rectangle2D getBounds(Graphics2D g2, Font font, String string) {
		return font.getStringBounds(string, g2.getFontRenderContext());
	}

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit()
				.getScreenSize();
	}

	public static Clipboard getSystemClipboard() {

		return Toolkit.getDefaultToolkit()
				.getSystemClipboard();
	}

	public static BufferedImage createScreenCapture() throws QuietException {
		Rectangle bounds = new Rectangle(getScreenSize());
		return createScreenCapture(bounds);
	}

	private static BufferedImage createScreenCapture(Rectangle bounds) {
		try {
			return new Robot().createScreenCapture(bounds);
		} catch (AWTException e) {
			throw new QuietException(e);
		}
	}

	public static void transferToClipBoard(Transferable contents) {
		getSystemClipboard().setContents(contents, BiConsumerPlus.dummy()::accept);
	}

	public static BufferedImage snapshoot(JComponent component) {
		BufferedImage bufferedImage = new BufferedImage(component.getWidth(), component.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		component.paint(bufferedImage.createGraphics());
		return bufferedImage;
	}

	public static BufferedImage snapshoot(JComponent component, BufferedImage backgound) {
		BufferedImage bufferedImage = new BufferedImage(component.getWidth(), component.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(backgound, 0, 0, component.getWidth(), component.getHeight(), component);
		component.paint(bufferedImage.createGraphics());
		return bufferedImage;
	}

	public static JFrame newJFrame() {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		return frame;
	}

	public static JPanel addBody(JFrame frame) {
		JPanel body = new JPanel();
		body.setLayout(new BorderLayout());
		body.setBackground(Color.BLACK);
		frame.add(body, BorderLayout.CENTER);
		return body;
	}

}
