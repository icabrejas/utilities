package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class MouseMotionListenerImpl implements MouseMotionListener {

	public Consumer<MouseEvent> onMouseMoved = ConsumerPlus.dummy(MouseEvent.class);
	public Consumer<MouseEvent> onMouseDragged = ConsumerPlus.dummy(MouseEvent.class);

	@Override
	public void mouseMoved(MouseEvent evt) {
		onMouseMoved.accept(evt);
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		onMouseDragged.accept(evt);
	}

	public MouseMotionListenerImpl onMouseMoved(Consumer<MouseEvent> onMouseMoved) {
		this.onMouseMoved = onMouseMoved;
		return this;
	}

	public MouseMotionListenerImpl onMouseDragged(Consumer<MouseEvent> onMouseDragged) {
		this.onMouseDragged = onMouseDragged;
		return this;
	}

	public void add(Component component) {
		component.addMouseMotionListener(this);
	}

}
