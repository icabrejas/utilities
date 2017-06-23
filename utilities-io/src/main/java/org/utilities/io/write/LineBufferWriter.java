package org.utilities.io.write;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.utilities.core.lang.exception.QuietException;

public class LineBufferWriter<T> implements Closeable {

	private File file;
	private Function<T, String> toString;
	private int bufferSize;
	private List<String> lines = new ArrayList<>();

	public LineBufferWriter(File file, Function<T, String> toString, int bufferSize) {
		this.file = file;
		if (file.exists()) {
			file.delete();
		}
		this.toString = toString;
		this.bufferSize = bufferSize;
	}

	public static <T> LineBufferWriter<T> newInstance(File file) {
		return LineBufferWriter.newInstance(file, 100);
	}

	public static <T> LineBufferWriter<T> newInstance(File file, int bufferSize) {
		return LineBufferWriter.newInstance(file, Object::toString, bufferSize);
	}

	public static <T> LineBufferWriter<T> newInstance(File file, Function<T, String> toString, int bufferSize) {
		return new LineBufferWriter<>(file, toString, bufferSize);
	}

	public void write(T t) throws QuietException {
		lines.add(toString.apply(t));
		if (bufferSize < lines.size()) {
			write();
		}
	}

	private void write() throws QuietException {
		if (!lines.isEmpty()) {
			try {
				FileUtils.writeLines(file, lines, true);
				lines.clear();
			} catch (IOException e) {
				throw new QuietException (e);
			}
		}
	}

	@Override
	public void close() {
		write();
	}
}
