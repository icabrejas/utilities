package org.utilities.io.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.io.IteratorCloseable;

public class IterablePipeZip<I> implements IterablePipe<EntryZip<I>> {

	private I info;
	private Supplier<InputStream> inputStream;

	public IterablePipeZip(I info, Supplier<InputStream> inputStream) {
		this.info = info;
		this.inputStream = inputStream;
	}

	public I getInfo() {
		return info;
	}

	public ZipInputStream getInputStream() {
		return new ZipInputStream(inputStream.get());
	}

	@Override
	public Iterator<EntryZip<I>> iterator() {
		ZipInputStream zipInputStream = getInputStream();
		It<I> it = new It<>(this);
		return new IteratorCloseable<>(it, zipInputStream);
	}

	private static class It<I> implements Iterator<EntryZip<I>> {

		private ZipEntry next;
		private ZipInputStream zipInputStream;
		private IterablePipeZip<I> it;

		public It(IterablePipeZip<I> it) {
			this.it = it;
			this.zipInputStream = it.getInputStream();
			next = getNextEntry(zipInputStream);
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public EntryZip<I> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			EntryZip<I> current = new EntryZip<>(next, it.getInfo(), zipInputStream);
			next = getNextEntry(zipInputStream);
			return current;
		}

		private ZipEntry getNextEntry(ZipInputStream zipInputStream) throws QuietException {
			try {
				return zipInputStream.getNextEntry();
			} catch (IOException e) {
				throw new QuietException(e);
			}
		}

	}

}
