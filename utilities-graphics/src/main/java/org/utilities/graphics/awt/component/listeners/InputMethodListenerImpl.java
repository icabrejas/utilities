package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class InputMethodListenerImpl implements InputMethodListener {

	private Consumer<InputMethodEvent> onInputMethodTextChanged = ConsumerPlus.dummy(InputMethodEvent.class);
	private Consumer<InputMethodEvent> onCaretPositionChanged = ConsumerPlus.dummy(InputMethodEvent.class);

	@Override
	public void inputMethodTextChanged(InputMethodEvent evt) {
		onInputMethodTextChanged.accept(evt);
	}

	@Override
	public void caretPositionChanged(InputMethodEvent evt) {
		onCaretPositionChanged.accept(evt);
	}

	public InputMethodListenerImpl onInputMethodTextChanged(Consumer<InputMethodEvent> onInputMethodTextChanged) {
		this.onInputMethodTextChanged = onInputMethodTextChanged;
		return this;
	}

	public InputMethodListenerImpl onCaretPositionChanged(Consumer<InputMethodEvent> onCaretPositionChanged) {
		this.onCaretPositionChanged = onCaretPositionChanged;
		return this;
	}

	public void add(Component component) {
		component.addInputMethodListener(this);
	}

}
