package org.utilities.graphics.awt.component.listeners;

import java.awt.Component;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.util.function.Consumer;

import org.utilities.core.util.function.ConsumerPlus;

public class HierarchyBoundsListenerImpl implements HierarchyBoundsListener {

	private Consumer<HierarchyEvent> onAncestorResized = ConsumerPlus.dummy(HierarchyEvent.class);
	private Consumer<HierarchyEvent> onAncestorMoved = ConsumerPlus.dummy(HierarchyEvent.class);

	@Override
	public void ancestorResized(HierarchyEvent evt) {
		onAncestorResized.accept(evt);
	}

	@Override
	public void ancestorMoved(HierarchyEvent evt) {
		onAncestorMoved.accept(evt);
	}

	public HierarchyBoundsListenerImpl onAncestorResized(Consumer<HierarchyEvent> onAncestorResized) {
		this.onAncestorResized = onAncestorResized;
		return this;
	}

	public HierarchyBoundsListenerImpl onAncestorMoved(Consumer<HierarchyEvent> onAncestorMoved) {
		this.onAncestorMoved = onAncestorMoved;
		return this;
	}

	public void add(Component component) {
		component.addHierarchyBoundsListener(this);
	}

}
