package org.utilities.io.zip;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.io.UtilitiesIO;

public class ZipWriter implements Closeable {

	private FileOutputStream fileOutputStream;
	private ZipOutputStream output;

	public ZipWriter(File file) throws QuietException {
		if (file.exists()) {
			file.delete();
		}
		try {
			this.fileOutputStream = new FileOutputStream(file);
			this.output = new ZipOutputStream(fileOutputStream);
		} catch (FileNotFoundException e) {
			throw new QuietException(e);
		}
	}

	public void write(String name, byte[] buf) throws QuietException {
		try (InputStream input = new ByteArrayInputStream(buf)) {
			write(name, input);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public void write(String name, InputStream input) {
		try {
			ZipEntry entry = new ZipEntry(name);
			output.putNextEntry(entry);
			UtilitiesIO.copyQuietly(input, fileOutputStream);
			output.closeEntry();
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	@Override
	public void close() throws QuietException {
		try {
			output.close();
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

}
