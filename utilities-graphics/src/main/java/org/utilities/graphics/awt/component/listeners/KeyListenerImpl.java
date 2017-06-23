package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class KeyListenerImpl implements KeyListener {

	private Consumer<KeyEvent> onKeyTyped = ConsumerPlus.dummy(KeyEvent.class);
	private Consumer<KeyEvent> onKeyReleased = ConsumerPlus.dummy(KeyEvent.class);
	private Consumer<KeyEvent> onKeyPressed = ConsumerPlus.dummy(KeyEvent.class);

	@Override
	public void keyTyped(KeyEvent evt) {
		onKeyTyped.accept(evt);
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		onKeyReleased.accept(evt);
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		onKeyPressed.accept(evt);
	}

	public KeyListenerImpl onKeyTyped(Consumer<KeyEvent> onKeyTyped) {
		this.onKeyTyped = onKeyTyped;
		return this;
	}

	public KeyListenerImpl onKeyReleased(Consumer<KeyEvent> onKeyReleased) {
		this.onKeyReleased = onKeyReleased;
		return this;
	}

	public KeyListenerImpl onKeyPressed(Consumer<KeyEvent> onKeyPressed) {
		this.onKeyPressed = onKeyPressed;
		return this;
	}

	public void add(Component component) {
		component.addKeyListener(this);
	}

}
