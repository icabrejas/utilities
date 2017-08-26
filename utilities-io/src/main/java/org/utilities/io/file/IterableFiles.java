package org.utilities.io.file;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.utilities.core.lang.iterable.IterablePipeFlat;

public class IterableFiles extends IterablePipeFlat<File> {

	private IterableFiles(File... src) {
		super(() -> new It(src));
	}

	public static IterableFiles newInstance(File src) {
		return new IterableFiles(src);
	}

	private static class It implements Iterator<Iterable<File>> {

		private Iterator<File> src;

		public It(File... src) {
			this.src = Arrays.asList(src)
					.iterator();
		}

		@Override
		public boolean hasNext() {
			return src.hasNext();
		}

		@Override
		public Iterable<File> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return iterable(src.next());
		}

		private Iterable<File> iterable(File file) {
			return file.isDirectory() ? new IterableFiles(file.listFiles()) : Arrays.asList(file);
		}

	}

}
