package org.utilities.graphics.swing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.function.Function;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.util.function.FunctionPlus;
import org.utilities.graphics.awt.UtilitiesImage;

public class UtilitiesFileChooser {

	public static <T> T showOpenDialog(Function<File, T> mapper, String defaultPath, String... fileExtensions) {
		File file = UtilitiesFileChooser.showOpenDialog(defaultPath, fileExtensions);
		return FunctionPlus.applyIfNotNull(file, mapper);
	}

	public static File showOpenDialog(String defaultPath, String... fileExtensions) {
		File selectedFile = null;
		JFileChooser fileChooser = new JFileChooser();
		if (defaultPath != null) {
			fileChooser.setCurrentDirectory(new File(defaultPath));
		}
		if (fileExtensions != null) {
			if (fileExtensions.length == 1 && fileExtensions[0] != null && fileExtensions[0].isEmpty()) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			} else {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("selectedExtensions", fileExtensions);
				fileChooser.setFileFilter(filter);
			}
		}
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		}
		return selectedFile;
	}

	public static File[] showOpenMultipleDialog(String defaultPath, String... fileExtension) {
		File[] selectedFiles = new File[] {};
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		if (defaultPath != null) {
			fileChooser.setCurrentDirectory(new File(defaultPath));
		}
		if (fileExtension != null) {
			fileChooser.setFileFilter(new FileNameExtensionFilter(null, fileExtension));
		}
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedFiles = fileChooser.getSelectedFiles();
		}
		return selectedFiles;
	}

	public static <T> IterablePipe<T> showOpenMultipleDialog(Function<File, T> mapper, String defaultPath,
			String... fileExtension) {
		return IterablePipe.create(Arrays.asList(showOpenMultipleDialog(defaultPath, fileExtension)))
				.map(mapper);
	}

	public static class Img {

		private static final String[] EXTENSIONS = new String[] { "jpg", "jpeg", "png", "gif" };

		public static BufferedImage load() throws QuietException {
			return showOpenDialog(UtilitiesImage.IO::readQuietly, null, EXTENSIONS);
		}

		public static IterablePipe<BufferedImage> loadAll() throws QuietException {
			return showOpenMultipleDialog(UtilitiesImage.IO::readQuietly, null, EXTENSIONS);
		}

		public static void save(BufferedImage image) {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File output = chooser.getSelectedFile();
				UtilitiesImage.IO.writeQuietly(image, output);
			}

		}

	}

}
