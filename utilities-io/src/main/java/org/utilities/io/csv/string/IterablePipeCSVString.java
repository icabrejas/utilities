package org.utilities.io.csv.string;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.function.Supplier;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.io.IteratorCloseable;
import org.utilities.io.csv.CSVReaderBuilder;
import org.utilities.io.csv.UtilitiesCSV;

import com.opencsv.CSVReader;

public class IterablePipeCSVString<I> implements IterablePipe<EntryCSVString<I>> {

	private I metainfo;
	private CSVReaderBuilder reader;
	private boolean headers = true;
	private boolean trim = false;

	public IterablePipeCSVString(I metainfo, Supplier<? extends Reader> reader) {
		this.metainfo = metainfo;
		this.reader = new CSVReaderBuilder(reader);
	}

	public IterablePipeCSVString<I> metainfo(I metainfo) {
		this.metainfo = metainfo;
		return this;
	}

	public IterablePipeCSVString<I> separator(char separator) {
		reader.separator(separator);
		return this;
	}

	public IterablePipeCSVString<I> quotechar(char quotechar) {
		reader.quotechar(quotechar);
		return this;
	}

	public IterablePipeCSVString<I> escape(char escape) {
		reader.escape(escape);
		return this;
	}

	public IterablePipeCSVString<I> line(int line) {
		reader.line(line);
		return this;
	}

	public IterablePipeCSVString<I> strictQuotes(boolean strictQuotes) {
		reader.strictQuotes(strictQuotes);
		return this;
	}

	public IterablePipeCSVString<I> ignoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
		reader.ignoreLeadingWhiteSpace(ignoreLeadingWhiteSpace);
		return this;
	}

	public IterablePipeCSVString<I> keepCR(boolean keepCR) {
		reader.keepCR(keepCR);
		return this;
	}

	public IterablePipeCSVString<I> headers(boolean headers) {
		this.headers = headers;
		return this;
	}

	public IterablePipeCSVString<I> trim(boolean trim) {
		this.trim = trim;
		return this;
	}

	@Override
	public String toString() {
		return "IterableCSVString [headers=" + headers + ", trim=" + trim + "]";
	}

	@Override
	public IteratorCloseable<EntryCSVString<I>> iterator() {
		try {
			IterablePipeCSVString.It<I> it = new IterablePipeCSVString.It<>(metainfo, reader.get(), headers, trim);
			return new IteratorCloseable<>(it, it);
		} catch (FileNotFoundException e) {
			throw new QuietException(e);
		}
	}

	public String[] readHeaders() {
		try {
			return UtilitiesCSV.readHeaders(reader);
		} catch (FileNotFoundException e) {
			throw new QuietException(e);
		}
	}

	private static class It<I> implements Iterator<EntryCSVString<I>>, Closeable {

		private I metainfo;
		private IteratorCloseable<String[]> it;
		private String[] headers;
		private boolean trim;

		public It(I metainfo, CSVReader reader, boolean headers, boolean trim) throws FileNotFoundException {
			this.metainfo = metainfo;
			this.it = new IteratorCloseable<>(reader.iterator(), reader);
			if (headers && it.hasNext()) {
				this.headers = it.next();
			}
			this.trim = trim;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public EntryCSVString<I> next() {
			EntryCSVString<I> entry = new EntryCSVString<>(metainfo);
			String[] fields = it.next();
			for (int i = 0; i < headers.length; i++) {
				if (trim) {
					entry.put(headers[i].trim(), fields[i].trim());
				} else {
					entry.put(headers[i].trim(), fields[i].trim());
				}
			}
			return entry;
		}

		@Override
		public void close() throws IOException {
			it.close();
		}

	}

}
