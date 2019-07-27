package org.utilities.io.csv.string;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.function.Supplier;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.dataframe.DataFrame;
import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.dataentry.DataEntryImpl;
import org.utilities.io.IteratorCloseable;
import org.utilities.io.csv.CSVReaderBuilder;
import org.utilities.io.csv.UtilitiesCSV;

import com.opencsv.CSVReader;

public class IterablePipeCSV implements DataFrame {

	private CSVReaderBuilder reader;
	private boolean headers = true;
	private boolean trim = false;

	public IterablePipeCSV(Supplier<? extends Reader> reader) {
		this.reader = new CSVReaderBuilder(reader);
	}

	public IterablePipeCSV separator(char separator) {
		reader.separator(separator);
		return this;
	}

	public IterablePipeCSV quotechar(char quotechar) {
		reader.quotechar(quotechar);
		return this;
	}

	public IterablePipeCSV escape(char escape) {
		reader.escape(escape);
		return this;
	}

	public IterablePipeCSV line(int line) {
		reader.line(line);
		return this;
	}

	public IterablePipeCSV strictQuotes(boolean strictQuotes) {
		reader.strictQuotes(strictQuotes);
		return this;
	}

	public IterablePipeCSV ignoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
		reader.ignoreLeadingWhiteSpace(ignoreLeadingWhiteSpace);
		return this;
	}

	public IterablePipeCSV keepCR(boolean keepCR) {
		reader.keepCR(keepCR);
		return this;
	}

	public IterablePipeCSV headers(boolean headers) {
		this.headers = headers;
		return this;
	}

	public IterablePipeCSV trim(boolean trim) {
		this.trim = trim;
		return this;
	}

	@Override
	public IteratorCloseable<DataEntry> iterator() {
		try {
			IterablePipeCSV.It it = new IterablePipeCSV.It(reader.get(), headers, trim);
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

	@Override
	public String toString() {
		return "IterableCSVString [headers=" + headers + ", trim=" + trim + "]";
	}

	private static class It implements Iterator<DataEntry>, Closeable {

		private IteratorCloseable<String[]> it;
		private String[] headers;
		private boolean trim;

		public It(CSVReader reader, boolean headers, boolean trim) throws FileNotFoundException {
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
		public DataEntry next() {
			DataEntryImpl entry = new DataEntryImpl();
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
