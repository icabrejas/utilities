package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class HierarchyListenerImpl implements HierarchyListener {

	private Consumer<HierarchyEvent> onHierarchyChanged = ConsumerPlus.dummy(HierarchyEvent.class);

	@Override
	public void hierarchyChanged(HierarchyEvent evt) {
		onHierarchyChanged.accept(evt);
	}

	public HierarchyListenerImpl onHierarchyChanged(Consumer<HierarchyEvent> onHierarchyChanged) {
		this.onHierarchyChanged = onHierarchyChanged;
		return this;
	}

	public void add(Component component) {
		component.addHierarchyListener(this);
	}

}
