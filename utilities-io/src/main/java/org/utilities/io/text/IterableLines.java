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

public class IterableLines<I> implements IterablePipe<EntryLine<I>> {

	private I metainfo;
	private Supplier<? extends Reader> reader;

	public IterableLines(I metainfo, Supplier<? extends Reader> reader) {
		this.metainfo = metainfo;
		this.reader = reader;
	}

	public static <I> IterableLines<I> newInstance(I metainfo, Supplier<? extends Reader> reader) {
		return new IterableLines<>(metainfo, reader);
	}

	public static IterableLines<File> newInstance(String pathname) {
		return IterableLines.newInstance(new File(pathname));
	}

	public static IterableLines<File> newInstance(File file) {
		Supplier<? extends Reader> reader = SupplierPlus.parseQuiet(() -> new FileReader(file));
		return new IterableLines<>(file, reader);
	}

	public I getMetainfo() {
		return metainfo;
	}

	@Override
	public IteratorCloseable<EntryLine<I>> iterator() {
		It<I> it = new It<>(metainfo, new LineIterator(reader.get()));
		return new IteratorCloseable<>(it, it);
	}

	private static class It<I> implements Iterator<EntryLine<I>>, Closeable {

		private I metainfo;
		private LineIterator it;

		public It(I metainfo, LineIterator it) {
			this.metainfo = metainfo;
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
		public EntryLine<I> next() {
			return new EntryLine<>(metainfo, it.next());
		}

	}

}
