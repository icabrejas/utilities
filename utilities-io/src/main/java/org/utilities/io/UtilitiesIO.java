package org.utilities.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.io.csv.UtilitiesCSV;
import org.utilities.io.file.IterableFiles;
import org.utilities.io.gz.UtilitiesGZip;

public class UtilitiesIO {

	private UtilitiesIO() {
	}

	public static IterablePipe<File> dir(File src, boolean recursive) {
		if (!recursive) {
			return IterablePipe.newInstance(src.listFiles());
		} else {
			return IterableFiles.newInstance(src);
		}
	}

	public static String extension(File file) {
		String name = file.getName();
		return name.substring(name.lastIndexOf(".") + 1);
	}

	public static boolean is(File file, String extension) {
		String name = file.getName();
		return name.endsWith('.' + extension);
	}

	public static boolean isGZ(File file) {
		return is(file, UtilitiesGZip.EXTESION);
	}

	public static boolean isCSV(File file) {
		return is(file, UtilitiesCSV.EXTESION);
	}

	public static String toString(InputStream inputStream) throws QuietException {
		try {
			return IOUtils.toString(inputStream, Charset.defaultCharset());
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static String toStringQuietly(Reader reader) throws QuietException {
		try {
			return IOUtils.toString(reader);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static byte[] toByteArrayQuietly(InputStream inputStream) {
		try {
			return IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static <T extends Serializable> T readObjectQuietly(File file, Class<T> type) throws QuietException {
		try (InputStream input = new FileInputStream(file)) {
			return readObjectQuietly(input, type);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T readObjectQuietly(InputStream input, Class<T> type) throws QuietException {
		try (ObjectInputStream objectStream = new ObjectInputStream(input);) {
			return (T) objectStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new QuietException(e);
		}
	}

	public static void writeObjectQuietly(Serializable obj, File file) {
		try (OutputStream output = new FileOutputStream(file);) {
			writeObjectQuietly(obj, output);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static void writeObjectQuietly(Serializable obj, OutputStream output) {
		try (ObjectOutputStream objectStream = new ObjectOutputStream(output);) {
			objectStream.writeObject(obj);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static void copyQuietly(InputStream input, OutputStream output) throws QuietException {
		try {
			IOUtils.copy(input, output);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

}
