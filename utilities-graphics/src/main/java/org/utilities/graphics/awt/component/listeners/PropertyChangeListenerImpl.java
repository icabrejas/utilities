package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class PropertyChangeListenerImpl implements PropertyChangeListener {

	private Consumer<PropertyChangeEvent> onPropertyChange = ConsumerPlus.dummy(PropertyChangeEvent.class);

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		onPropertyChange.accept(evt);
	}

	public PropertyChangeListenerImpl onPropertyChange(Consumer<PropertyChangeEvent> onPropertyChange) {
		this.onPropertyChange = onPropertyChange;
		return this;
	}

	public void add(Component component) {
		component.addPropertyChangeListener(this);
	}

	public void add(String propertyName, Component component) {
		component.addPropertyChangeListener(propertyName, this);
	}

}
