package org.utilities.graphics.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.utilities.graphics.awt.UtilitiesImage;

public class JPanelBar extends JPanel {

	private static final long serialVersionUID = 6684112135150156485L;

	private BufferedImage left;
	private BufferedImage right;
	private BufferedImage background;

	public JPanelBar() {
		setOpaque(false);
	}

	public void setLeft(BufferedImage left) {
		this.left = left;
	}

	public void setRight(BufferedImage right) {
		this.right = right;
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
	}

	@Override
	public void paint(Graphics g) {
		try {
			printBackGround(g);
			printRightImage(g);
			printLeftImage(g);
		} catch (Exception ex) {
		}
		super.paint(g);
	}

	private void printBackGround(Graphics g) {
		if (background != null) {
			UtilitiesImage.drawImage(this, background, FitMode.CENTER);
		}
	}

	private void printLeftImage(Graphics g) {
		if (left != null) {
			UtilitiesImage.drawImage(this, left, FitMode.LEFT);
		}
	}

	private void printRightImage(Graphics g) {
		if (right != null) {
			UtilitiesImage.drawImage(this, right, FitMode.RIGHT);
		}
	}
}
