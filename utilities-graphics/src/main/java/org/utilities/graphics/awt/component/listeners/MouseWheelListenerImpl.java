package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class MouseWheelListenerImpl implements MouseWheelListener {

	private Consumer<MouseWheelEvent> onMouseWheelMoved = ConsumerPlus.dummy(MouseWheelEvent.class);

	@Override
	public void mouseWheelMoved(MouseWheelEvent evt) {
		onMouseWheelMoved.accept(evt);
	}

	public MouseWheelListenerImpl onMouseWheelMoved(Consumer<MouseWheelEvent> onMouseWheelMoved) {
		this.onMouseWheelMoved = onMouseWheelMoved;
		return this;
	}

	public void add(Component component) {
		component.addMouseWheelListener(this);
	}

}
