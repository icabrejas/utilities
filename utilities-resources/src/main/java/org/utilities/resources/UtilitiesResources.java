package org.utilities.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.imageio.ImageIO;

import org.utilities.core.lang.exception.QuietException;

public class UtilitiesResources {

	public static InputStream getInputStream(String name) {
		ClassLoader loader = UtilitiesResources.class.getClassLoader();
		return loader.getResourceAsStream(name);
	}

	public static Reader getReader(String name) {
		return new InputStreamReader(getInputStream(name));
	}

	public static BufferedImage getImage(String name) {
		try (InputStream input = getInputStream(name)) {
			return ImageIO.read(input);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

}
