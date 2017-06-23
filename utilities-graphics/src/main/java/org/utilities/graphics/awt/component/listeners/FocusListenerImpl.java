package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class FocusListenerImpl implements FocusListener {

	private Consumer<FocusEvent> onFocusGained = ConsumerPlus.dummy(FocusEvent.class);
	private Consumer<FocusEvent> onFocusLost = ConsumerPlus.dummy(FocusEvent.class);

	@Override
	public void focusLost(FocusEvent evt) {
		onFocusLost.accept(evt);
	}

	@Override
	public void focusGained(FocusEvent evt) {
		onFocusGained.accept(evt);
	}

	public FocusListenerImpl onFocusGained(Consumer<FocusEvent> onFocusGained) {
		this.onFocusGained = onFocusGained;
		return this;
	}

	public FocusListenerImpl onFocusLost(Consumer<FocusEvent> onFocusLost) {
		this.onFocusLost = onFocusLost;
		return this;
	}

	public void add(Component component) {
		component.addFocusListener(this);
	}

}
