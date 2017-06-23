package org.utilities.graphics.awt.component.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JComboBox;

import org.utilities.core.util.function.ConsumerPlus;

public class ActionListenerImpl implements ActionListener {

	private Consumer<ActionEvent> onActionPerformed = ConsumerPlus.dummy(ActionEvent.class);

	@Override
	public void actionPerformed(ActionEvent evt) {
		onActionPerformed.accept(evt);
	}

	public ActionListenerImpl onActionPerformed(Consumer<ActionEvent> onActionPerformed) {
		this.onActionPerformed = onActionPerformed;
		return this;
	}

	public void add(JComboBox<?> comboBox) {
		comboBox.addActionListener(this);
	}

}
