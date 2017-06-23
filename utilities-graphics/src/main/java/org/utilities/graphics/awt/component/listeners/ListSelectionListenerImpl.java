package org.utilities.graphics.awt.component.listeners;

import java.util.function.Consumer;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.utilities.core.util.function.ConsumerPlus;

public class ListSelectionListenerImpl implements ListSelectionListener {

	private Consumer<ListSelectionEvent> onValueChanged = ConsumerPlus.dummy(ListSelectionEvent.class);

	@Override
	public void valueChanged(ListSelectionEvent evt) {
		onValueChanged.accept(evt);
	}

	public ListSelectionListenerImpl onValueChanged(Consumer<ListSelectionEvent> onValueChanged) {
		this.onValueChanged = onValueChanged;
		return this;
	}

	public void add(JList<?> list) {
		list.addListSelectionListener(this);
	}

}
