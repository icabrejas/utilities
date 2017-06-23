package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class MouseListenerImpl implements MouseListener {

	private Consumer<MouseEvent> onMouseReleased = ConsumerPlus.dummy(MouseEvent.class);
	private Consumer<MouseEvent> onMousePressed = ConsumerPlus.dummy(MouseEvent.class);
	private Consumer<MouseEvent> onMouseExited = ConsumerPlus.dummy(MouseEvent.class);
	private Consumer<MouseEvent> onMouseEntered = ConsumerPlus.dummy(MouseEvent.class);
	private Consumer<MouseEvent> onMouseClicked = ConsumerPlus.dummy(MouseEvent.class);

	@Override
	public void mouseReleased(MouseEvent evt) {
		onMouseReleased.accept(evt);
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		onMousePressed.accept(evt);
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		onMouseExited.accept(evt);
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		onMouseEntered.accept(evt);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		onMouseClicked.accept(evt);
	}

	public MouseListenerImpl onMouseReleased(Consumer<MouseEvent> onMouseReleased) {
		this.onMouseReleased = onMouseReleased;
		return this;
	}

	public MouseListenerImpl onMousePressed(Consumer<MouseEvent> onMousePressed) {
		this.onMousePressed = onMousePressed;
		return this;
	}

	public MouseListenerImpl onMouseExited(Consumer<MouseEvent> onMouseExited) {
		this.onMouseExited = onMouseExited;
		return this;
	}

	public MouseListenerImpl onMouseEntered(Consumer<MouseEvent> onMouseEntered) {
		this.onMouseEntered = onMouseEntered;
		return this;
	}

	public MouseListenerImpl onMouseClicked(Consumer<MouseEvent> onMouseClicked) {
		this.onMouseClicked = onMouseClicked;
		return this;
	}

	public void add(Component component) {
		component.addMouseListener(this);
	}

}
