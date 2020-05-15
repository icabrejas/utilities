package org.utilities.graphics.awt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
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

	public static class IO {

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

		public static byte[] toByteArrayQuietly(BufferedImage image, String formatName) throws QuietException {
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
				ImageIO.write(image, formatName, outputStream);
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

		public static void writeQuietly(BufferedImage image, String formatName, OutputStream output)
				throws QuietException {
			try {
				ImageIO.write(image, formatName, output);
			} catch (IOException e) {
				throw new QuietException(e);
			}
		}

		public static void compress(BufferedImage image, OutputStream outputStream, String formatName, float quality)
				throws QuietException {
			try {
				ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
				ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName)
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

		public static void transferToClipBoard(BufferedImage image) {
			TransferableImage transferableImage = new TransferableImage(image);
			UtilitiesAWT.transferToClipBoard(transferableImage);
		}

		public static InputStream createInputStream(BufferedImage image, String formatName) throws QuietException {
			return new ByteArrayInputStream(toByteArrayQuietly(image, formatName));
		}

		public static List<Node> getMetadata(BufferedImage image, String formatName) throws QuietException {
			try (InputStream iputStream = createInputStream(image, formatName);
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
					return IterablePipe.create(Arrays.asList(metadata.getMetadataFormatNames()))
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

	public static class Base64 {

		private static final Decoder DECODER = java.util.Base64.getDecoder();
		private static final Encoder ENCODER = java.util.Base64.getEncoder();

		public static BufferedImage decode(String base64) throws QuietException {
			byte[] bytes = DECODER.decode(base64);
			return IO.readQuietly(bytes);

		}

		public static String encode(BufferedImage image, String formatName) throws QuietException {
			byte[] bytes = IO.toByteArrayQuietly(image, formatName);
			return ENCODER.encodeToString(bytes);
		}

	}

	public static class Tranform {

		public static BufferedImage rotate(BufferedImage image, double angle) {
			int width = (int) (image.getWidth() * Math.cos(angle) + image.getHeight() * Math.sin(angle));
			int height = (int) (image.getWidth() * Math.sin(angle) + image.getHeight() * Math.cos(angle));
			BufferedImage rotated = new BufferedImage(width, height, image.getType());
			Graphics2D g = rotated.createGraphics();
			g.rotate(angle, width / 2, height / 2);
			g.drawImage(image, (image.getWidth() - width) / 2, (image.getHeight() - height) / 2, null);
			return rotated;
		}

		public static BufferedImage scale(BufferedImage image, double sx, double sy, boolean aspectRatio) {
			AffineTransform scale = null;
			int width, height;
			if (aspectRatio) {
				double s = Math.min(sx, sy);
				scale = AffineTransform.getScaleInstance(s, s);
				width = (int) Math.round(s * image.getWidth());
				height = (int) Math.round(s * image.getHeight());
			} else {
				scale = AffineTransform.getScaleInstance(sx, sy);
				width = (int) Math.round(sx * image.getWidth());
				height = (int) Math.round(sy * image.getHeight());
			}
			BufferedImage dest = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics.get(dest)
					.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
			image = dest;
			dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			AffineTransformOp affineTransform = new AffineTransformOp(scale, HINTS);
			affineTransform.filter(image, dest);
			return dest;
		}

		public static BufferedImage scale(BufferedImage image, Dimension dim, boolean aspectRatio) {
			double sx = dim.getWidth() / image.getWidth();
			double sy = dim.getHeight() / image.getHeight();
			return scale(image, sx, sy, aspectRatio);
		}

		public static BufferedImage scale(BufferedImage image, double s) {
			return scale(image, s, s, true);
		}

		public static BufferedImage addAlphaBorder(BufferedImage image, int border) {
			int width = image.getWidth() + 2 * border;
			int height = image.getHeight() + 2 * border;
			BufferedImage expandedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics.get(expandedImg)
					.drawImage(image, border, border, null);
			return expandedImg;
		}

		public static BufferedImage translucent(BufferedImage image, float alpha) {
			BufferedImage translucent = new BufferedImage(image.getWidth(), image.getHeight(),
					BufferedImage.TRANSLUCENT);
			Graphics2D g = translucent.createGraphics();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g.drawImage(image, null, 0, 0);
			g.dispose();
			return translucent;
		}

		// FIXME
		public static BufferedImage translucent(BufferedImage image, Color color, float alpha) {
			BufferedImage translucent = new BufferedImage(image.getWidth(), image.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Color translucentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * alpha));
			for (int x = 0; x < translucent.getWidth(); x++) {
				for (int y = 0; y < translucent.getHeight(); y++) {
					if (image.getRGB(x, y) == color.getRGB()) {
						translucent.setRGB(x, y, 0x8F1C1C);
					} else {
						translucent.setRGB(x, y, translucentColor.getRGB());
					}
				}
			}
			return translucent;
		}

		public static BufferedImage shadow(BufferedImage image, Color color) {
			BufferedImage shadow = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			for (int i = 0; i < shadow.getWidth(); i++) {
				for (int j = 0; j < shadow.getHeight(); j++) {
					Color pixel = new Color(image.getRGB(i, j), true);
					pixel = new Color(color.getRed(), color.getGreen(), color.getBlue(), pixel.getAlpha());
					shadow.setRGB(i, j, pixel.getRGB());
				}
			}
			return shadow;
		}

		public static BufferedImageOp blurFilter(int blur) {
			float[] data = new float[blur * blur];
			for (int i = 0; i < data.length; i++) {
				data[i] = 1f / (blur * blur);
			}
			Kernel kernel = new Kernel(blur, blur, data);
			return new ConvolveOp(kernel);
		}

		public static BufferedImage blur(BufferedImage image, int blur) {
			return UtilitiesImage.Tranform.blurFilter(blur)
					.filter(image, null);
		}

	}

	public static class Graphics {

		public static Graphics2D get(BufferedImage image) {
			Graphics2D g2 = (Graphics2D) image.getGraphics();
			applyRenderingHints(g2);
			return g2;
		}

		public static void applyRenderingHints(Graphics2D g2) {
			g2.setRenderingHints(HINTS);
		}

		public static void drawImage(java.awt.Graphics g, BufferedImage image, FitMode fitMode, int alphaBorder) {
			Dimension src = Bounds.getSize(image);
			Rectangle container = g.getClipBounds();
			Rectangle clip = Bounds.fitSize(src, container, fitMode);
			drawImage(g, image, clip, alphaBorder);
		}

		public static void drawImage(java.awt.Graphics g, BufferedImage image, Rectangle clip, int alphaBorder) {
			if (clip.getWidth() != image.getWidth() || clip.getHeight() != image.getHeight()) {
				image = Tranform.scale(image, clip.getSize(), true);
			}
			if (0 < alphaBorder) {
				image = Tranform.addAlphaBorder(image, alphaBorder);
				g.drawImage(image, clip.x - alphaBorder, clip.y - alphaBorder, null);
			} else {
				g.drawImage(image, clip.x, clip.y, null);
			}
		}

	}

	public static class Bounds {

		public static Rectangle getBounds(BufferedImage image) {
			return new Rectangle(0, 0, image.getWidth(), image.getHeight());
		}

		public static Dimension getSize(BufferedImage image) {
			return new Dimension(image.getWidth(), image.getHeight());
		}

		public static Point fitLocation(Dimension src, Dimension dest, FitMode fitMode) {
			return fitLocation(new Rectangle(src), new Rectangle(dest), fitMode);
		}

		public static Point fitLocation(Rectangle src, Rectangle dest, FitMode fitMode) {
			switch (fitMode) {
			case LEFT:
				return fitLocationLeft(src, dest);
			case TOP:
				return fitLocationTop(src, dest);
			case RIGHT:
				return fitLocationRight(src, dest);
			case BOTTON:
				return fitLocationBotton(src, dest);
			case CENTER:
				return fitLocationCenter(src, dest);
			default:
				return null;
			}
		}

		private static Point fitLocationLeft(Rectangle src, Rectangle dest) {
			double tx = dest.getX() - src.getX();
			double ty = dest.getCenterY() - src.getCenterY();
			return new Point((int) tx, (int) ty);
		}

		private static Point fitLocationTop(Rectangle src, Rectangle dest) {
			double tx = dest.getCenterX() - src.getCenterX();
			double ty = dest.getY() - src.getY();
			return new Point((int) tx, (int) ty);
		}

		private static Point fitLocationRight(Rectangle src, Rectangle dest) {
			double tx = (dest.getCenterX() + dest.getWidth()) - (src.getCenterX() + src.getWidth());
			double ty = dest.getCenterY() - src.getCenterY();
			return new Point((int) tx, (int) ty);
		}

		private static Point fitLocationBotton(Rectangle src, Rectangle dest) {
			double tx = dest.getCenterX() - src.getCenterX();
			double ty = (dest.getCenterY() + dest.getHeight()) - (src.getCenterY() + src.getHeight());
			return new Point((int) tx, (int) ty);
		}

		private static Point fitLocationCenter(Rectangle src, Rectangle dest) {
			double tx = dest.getCenterX() - src.getCenterX();
			double ty = dest.getCenterY() - src.getCenterY();
			return new Point((int) tx, (int) ty);
		}

		public static Rectangle fitSize(Dimension src, Rectangle container, FitMode fitMode) {
			switch (fitMode) {
			case LEFT:
				return fitSizeLeft(src, container);
			case TOP:
				return fitSizeTop(src, container);
			case RIGHT:
				return fitSizeRight(src, container);
			case BOTTON:
				return fitSizeBotton(src, container);
			case CENTER:
				return fitSizeCenter(src, container);
			default:
				return null;
			}
		}

		public static Rectangle fitSizeLeft(Dimension src, Rectangle container) {
			int width = fitWidth(src, container);
			return new Rectangle(container.x, container.y, width, container.height);
		}

		public static Rectangle fitSizeTop(Dimension src, Rectangle container) {
			int height = fitHeight(src, container);
			return new Rectangle(container.x, container.y, container.width, height);
		}

		public static Rectangle fitSizeRight(Dimension src, Rectangle container) {
			int width = fitWidth(src, container);
			int x = container.x + container.width - width;
			return new Rectangle(x, container.y, width, container.height);
		}

		public static Rectangle fitSizeBotton(Dimension src, Rectangle container) {
			int height = fitHeight(src, container);
			int y = container.y + container.height - height;
			return new Rectangle(container.x, y, container.width, height);
		}

		public static Rectangle fitSizeCenter(Dimension src, Rectangle container) {
			float factor = fitFactor(src, container);
			int width = Math.round(src.width * factor);
			int height = Math.round(src.height * factor);
			int x = container.x + (container.width - width) / 2;
			int y = container.y + (container.height - height) / 2;
			return new Rectangle(x, y, width, height);
		}

		public static int fitWidth(Dimension src, Rectangle container) {
			return Math.round(src.width * fitHeightFactor(src, container));
		}

		public static int fitHeight(Dimension src, Rectangle container) {
			return Math.round(src.height * fitWidthFactor(src, container));
		}

		public static float fitFactor(Dimension src, Rectangle container) {
			return Math.min(fitWidthFactor(src, container), fitHeightFactor(src, container));
		}

		public static float fitHeightFactor(Dimension src, Rectangle container) {
			return (float) container.height / src.height;
		}

		public static float fitWidthFactor(Dimension src, Rectangle container) {
			return (float) container.width / src.width;
		}

		public static Dimension scale(Dimension src, float factor) {
			return new Dimension((int) (factor * src.width), (int) (factor * src.height));
		}

	}
}
