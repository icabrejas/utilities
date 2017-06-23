package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class ComponentListenerImpl implements ComponentListener {

	private Consumer<ComponentEvent> onComponentResized = ConsumerPlus.dummy(ComponentEvent.class);
	private Consumer<ComponentEvent> onComponentMoved = ConsumerPlus.dummy(ComponentEvent.class);
	private Consumer<ComponentEvent> onComponentShown = ConsumerPlus.dummy(ComponentEvent.class);
	private Consumer<ComponentEvent> onComponentHidden = ConsumerPlus.dummy(ComponentEvent.class);

	@Override
	public void componentResized(ComponentEvent evt) {
		onComponentResized.accept(evt);
	}

	@Override
	public void componentMoved(ComponentEvent evt) {
		onComponentMoved.accept(evt);
	}

	@Override
	public void componentShown(ComponentEvent evt) {
		onComponentShown.accept(evt);
	}

	@Override
	public void componentHidden(ComponentEvent evt) {
		onComponentHidden.accept(evt);
	}

	public static ComponentListenerImpl newInstance() {
		return new ComponentListenerImpl();
	}

	public ComponentListenerImpl onComponentResized(Consumer<ComponentEvent> onComponentResized) {
		this.onComponentResized = onComponentResized;
		return this;
	}

	public ComponentListenerImpl onComponentMoved(Consumer<ComponentEvent> onComponentMoved) {
		this.onComponentMoved = onComponentMoved;
		return this;
	}

	public ComponentListenerImpl onComponentShown(Consumer<ComponentEvent> onComponentShown) {
		this.onComponentShown = onComponentShown;
		return this;
	}

	public ComponentListenerImpl onComponentHidden(Consumer<ComponentEvent> onComponentHidden) {
		this.onComponentHidden = onComponentHidden;
		return this;
	}

	public void add(Component component) {
		component.addComponentListener(this);
	}

}
