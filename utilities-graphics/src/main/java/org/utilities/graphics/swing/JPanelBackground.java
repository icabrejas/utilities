package org.utilities.graphics.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.utilities.graphics.awt.UtilitiesImage;

public class JPanelBackground extends JPanel {

	private static final long serialVersionUID = 3354757047373487023L;

	private BufferedImage img;
	private Color background = Color.BLACK;
	private boolean opaque = true;
	private FitMode fitMode = FitMode.CENTER;

	public JPanelBackground() {
		setOpaque(false);
	}

	public void setImage(BufferedImage image) {
		this.img = image;
		repaint();
	}

	public void setFitMode(FitMode fitMode) {
		this.fitMode = fitMode;
	}

	@Override
	public void paint(Graphics g) {
		printBackGround(g);
		printImage(g);
		super.paint(g);
	}

	private void printBackGround(Graphics g) {
		if (opaque) {
			g.setColor(background);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	private void printImage(Graphics g) {
		if (img != null) {
			UtilitiesImage.drawImage(this, img, fitMode);
		}
	}

}
