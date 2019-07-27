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

public class IterablePipeZip implements IterablePipe<ZipIOEntry> {

	private Supplier<InputStream> inputStream;

	public IterablePipeZip(Supplier<InputStream> inputStream) {
		this.inputStream = inputStream;
	}

	public ZipInputStream getInputStream() {
		return new ZipInputStream(inputStream.get());
	}

	@Override
	public Iterator<ZipIOEntry> iterator() {
		ZipInputStream zipInputStream = getInputStream();
		It it = new It(zipInputStream);
		return new IteratorCloseable<>(it, zipInputStream);
	}

	private static class It implements Iterator<ZipIOEntry> {

		private ZipEntry next;
		private ZipInputStream zipInputStream;

		public It(ZipInputStream zipInputStream) {
			this.zipInputStream = zipInputStream;
			next = getNextEntry(zipInputStream);
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public ZipIOEntry next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			ZipIOEntry current = new ZipIOEntry(next, zipInputStream);
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
