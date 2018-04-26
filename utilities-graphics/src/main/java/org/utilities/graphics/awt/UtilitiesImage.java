package org.utilities.graphics.awt;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.graphics.swing.FitMode;
import org.utilities.io.UtilitiesIO;
import org.w3c.dom.Node;

public class UtilitiesImage {

	private static final RenderingHints HINTS;

	static {
		Map<Key, Object> init = new HashMap<>();
		init.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		init.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		init.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		init.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		init.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		init.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		init.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		init.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		init.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		HINTS = new RenderingHints(init);
	}

	private UtilitiesImage() {
	}

	public static void applyRenderingHints(Graphics2D g2) {
		g2.setRenderingHints(HINTS);
	}

	public static BufferedImage readQuietly(File input) throws QuietException {
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static BufferedImage readQuietly(InputStream input) throws QuietException {
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static BufferedImage readQuietly(byte[] bytes) throws QuietException {
		try (InputStream inputStream = new ByteArrayInputStream(bytes);) {
			return ImageIO.read(inputStream);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static byte[] toByteArrayQuietly(BufferedImage img, String formatName) throws QuietException {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
			ImageIO.write(img, formatName, outputStream);
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static void writeQuietly(BufferedImage image, File output) throws QuietException {
		try {
			ImageIO.write(image, UtilitiesIO.extension(output), output);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static void writeQuietly(BufferedImage image, String formatName, OutputStream output) throws QuietException {
		try {
			ImageIO.write(image, formatName, output);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static void compress(BufferedImage image, OutputStream outputStream, float quality) throws QuietException {
		try {
			ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
			ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg")
					.next();
			writer.setOutput(imageOutputStream);
			ImageWriteParam params = writer.getDefaultWriteParam();
			params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			params.setCompressionQuality(quality);
			writer.write(null, new IIOImage(image, null, null), params);
			writer.dispose();
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static void transferToClipBoard(BufferedImage img) {
		TransferableImage transferableImage = new TransferableImage(img);
		UtilitiesAWT.transferToClipBoard(transferableImage);
	}

	public static BufferedImage rotate(BufferedImage img, double angle) {
		int width = (int) (img.getWidth() * Math.cos(angle) + img.getHeight() * Math.sin(angle));
		int height = (int) (img.getWidth() * Math.sin(angle) + img.getHeight() * Math.cos(angle));
		BufferedImage rotated = new BufferedImage(width, height, img.getType());
		Graphics2D g = rotated.createGraphics();
		g.rotate(angle, width / 2, height / 2);
		g.drawImage(img, (img.getWidth() - width) / 2, (img.getHeight() - height) / 2, null);
		return rotated;
	}

	public static BufferedImage scale(BufferedImage img, double sx, double sy, boolean aspectRatio) {
		AffineTransform scale = null;
		int width, height;
		if (aspectRatio) {
			double s = Math.min(sx, sy);
			scale = AffineTransform.getScaleInstance(s, s);
			width = (int) Math.round(s * img.getWidth());
			height = (int) Math.round(s * img.getHeight());
		} else {
			scale = AffineTransform.getScaleInstance(sx, sy);
			width = (int) Math.round(sx * img.getWidth());
			height = (int) Math.round(sy * img.getHeight());
		}
		BufferedImage dest = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) dest.getGraphics();
		applyRenderingHints(g2);
		g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
		img = dest;
		dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		AffineTransformOp affineTransform = new AffineTransformOp(scale, HINTS);
		affineTransform.filter(img, dest);
		return dest;
	}

	public static BufferedImage scale(BufferedImage img, Dimension dim, boolean aspectRatio) {
		double sx = dim.getWidth() / img.getWidth();
		double sy = dim.getHeight() / img.getHeight();
		return scale(img, sx, sy, aspectRatio);
	}

	public static BufferedImage scale(BufferedImage img, double s) {
		return scale(img, s, s, true);
	}

	public static class Base64 {

		private static final Decoder DECODER = java.util.Base64.getDecoder();
		private static final Encoder ENCODER = java.util.Base64.getEncoder();

		public static BufferedImage decode(String base64) throws QuietException {
			byte[] bytes = DECODER.decode(base64);
			return readQuietly(bytes);

		}

		public static String encode(BufferedImage img, String formatName) throws QuietException {
			byte[] bytes = toByteArrayQuietly(img, formatName);
			return ENCODER.encodeToString(bytes);
		}

	}

	public static void drawImage(Component component, BufferedImage img, FitMode fitMode) {
		Graphics g = component.getGraphics();
		Dimension src_dim = new Dimension(img.getWidth(), img.getHeight());
		Rectangle bounds = fit(src_dim, component.getSize(), fitMode);
		img = scale(img, bounds.getSize(), true);
		g.drawImage(img, bounds.x, bounds.y, bounds.width, bounds.height, component);
	}

	public static Rectangle fit(Dimension src, Dimension container, FitMode fitMode) {
		switch (fitMode) {
		case LEFT:
			return fitLeft(src, container);
		case TOP:
			return fitTop(src, container);
		case RIGHT:
			return fitRight(src, container);
		case BOTTON:
			return fitBotton(src, container);
		case CENTER:
			return fitCenter(src, container);
		case EXPAND:
			return fitExpand(src, container);
		default:
			return null;
		}
	}

	public static Rectangle fitLeft(Dimension src, Dimension container) {
		int width = Math.round((float) (src.width * container.height) / src.height);
		return new Rectangle(0, 0, width, container.height);
	}

	public static Rectangle fitTop(Dimension src, Dimension container) {
		int height = Math.round((float) (src.height * container.width) / src.width);
		return new Rectangle(0, 0, container.width, height);
	}

	public static Rectangle fitRight(Dimension src, Dimension container) {
		int width = Math.round((float) (src.width * container.height) / src.height);
		return new Rectangle(container.width - width, 0, width, container.height);
	}

	public static Rectangle fitBotton(Dimension src, Dimension container) {
		int height = Math.round((float) (src.height * container.width) / src.width);
		return new Rectangle(0, container.height - height, container.width, height);
	}

	public static Rectangle fitCenter(Dimension src, Dimension container) {
		float factor = Math.min((float) container.width / src.width, (float) container.height / src.height);
		int width = Math.round(container.width - src.width * factor);
		int height = Math.round(container.height - src.height * factor);
		return new Rectangle(width / 2, height / 2, container.width - width, container.height - height);
	}

	public static Rectangle fitExpand(Dimension src, Dimension container) {
		return new Rectangle(0, 0, container.width, container.height);
	}

	public static BufferedImage translucent(BufferedImage image, float alpha) {
		BufferedImage translucent = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D g = translucent.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(image, null, 0, 0);
		g.dispose();
		return translucent;
	}

	public static BufferedImage translucent(BufferedImage image, Color color, float alpha) {
		BufferedImage translucent = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Color translucentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * alpha));
		for (int x = 0; x < translucent.getWidth(); x++) {
			for (int y = 0; y < translucent.getHeight(); y++) {
				if (translucent.getRGB(x, y) == color.getRGB()) {
					translucent.setRGB(x, y, 0x8F1C1C);
				} else {
					translucent.setRGB(x, y, translucentColor.getRGB());
				}
			}
		}
		return translucent;
	}

	public static byte[] toByteArray(BufferedImage image) throws QuietException {
		try {
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "jpeg", arrayOutputStream);
			return arrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static InputStream createInputStream(BufferedImage image) throws QuietException {
		return new ByteArrayInputStream(toByteArray(image));
	}

	public static List<Node> getMetadata(BufferedImage image) throws QuietException {
		try (InputStream iputStream = createInputStream(image);
				ImageInputStream imageIputStream = ImageIO.createImageInputStream(iputStream);) {
			return getMetadata(imageIputStream);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static List<Node> getMetadata(File file) throws QuietException {
		try (ImageInputStream inputStream = ImageIO.createImageInputStream(file);) {
			return getMetadata(inputStream);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	private static List<Node> getMetadata(ImageInputStream inputStream) throws QuietException {
		try {
			ImageReader reader = ImageIO.getImageReaders(inputStream)
					.next();
			reader.setInput(inputStream);
			IIOMetadata metadata = reader.getImageMetadata(0);
			if (metadata != null) {
				return IterablePipe.create(metadata.getMetadataFormatNames())
						.map(metadata::getAsTree)
						.toList();
			} else {
				return Collections.emptyList();
			}
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

}
