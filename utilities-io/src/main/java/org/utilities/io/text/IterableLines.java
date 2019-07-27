package org.utilities.io.text;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.function.Supplier;

import org.apache.commons.io.LineIterator;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.util.function.SupplierPlus;
import org.utilities.io.IteratorCloseable;

public class IterableLines implements IterablePipe<String> {

	private Supplier<? extends Reader> reader;

	public IterableLines(Supplier<? extends Reader> reader) {
		this.reader = reader;
	}

	public static IterableLines newInstance(Supplier<? extends Reader> reader) {
		return new IterableLines(reader);
	}

	public static IterableLines newInstance(String pathname) {
		return IterableLines.newInstance(new File(pathname));
	}

	public static IterableLines newInstance(File file) {
		Supplier<? extends Reader> reader = SupplierPlus.parseQuiet(() -> new FileReader(file));
		return new IterableLines(reader);
	}

	@Override
	public IteratorCloseable<String> iterator() {
		It it = new It(new LineIterator(reader.get()));
		return new IteratorCloseable<>(it, it);
	}

	private static class It implements Iterator<String>, Closeable {

		private LineIterator it;

		public It(LineIterator it) {
			this.it = it;
		}

		@Override
		public void close() throws IOException {
			it.close();
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public String next() {
			return it.next();
		}

	}

}
