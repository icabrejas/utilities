package org.utilities.io.csv.bean;

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
import com.opencsv.bean.IterableCSVToBean;
import com.opencsv.bean.MappingStrategy;

public class IterableCSVBean<T> implements IterablePipe<T> {

	private CSVReaderBuilder reader;
	private MappingStrategy<T> strategy;

	public IterableCSVBean(Supplier<? extends Reader> reader, MappingStrategy<T> strategy) {
		this.reader = new CSVReaderBuilder(reader);
		this.strategy = strategy;
	}

	public IterableCSVBean<T> separator(char separator) {
		reader.separator(separator);
		return this;
	}

	public IterableCSVBean<T> quotechar(char quotechar) {
		reader.quotechar(quotechar);
		return this;
	}

	public IterableCSVBean<T> escape(char escape) {
		reader.escape(escape);
		return this;
	}

	public IterableCSVBean<T> line(int line) {
		reader.line(line);
		return this;
	}

	public IterableCSVBean<T> strictQuotes(boolean strictQuotes) {
		reader.strictQuotes(strictQuotes);
		return this;
	}

	public IterableCSVBean<T> ignoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
		reader.ignoreLeadingWhiteSpace(ignoreLeadingWhiteSpace);
		return this;
	}

	public IterableCSVBean<T> keepCR(boolean keepCR) {
		reader.keepCR(keepCR);
		return this;
	}

	@Override
	public IteratorCloseable<T> iterator() {
		try {
			IterableCSVBean.It<T> it = new IterableCSVBean.It<>(reader, strategy);
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

	private static class It<T> implements Iterator<T>, Closeable {

		private IteratorCloseable<T> iteratorCloseable;

		public It(Supplier<CSVReader> reader, MappingStrategy<T> strategy) throws FileNotFoundException {
			CSVReader reader_ = reader.get();
			IterableCSVToBean<T> it = new IterableCSVToBean<>(reader_, strategy, null);
			this.iteratorCloseable = new IteratorCloseable<>(it.iterator(), reader_);
		}

		@Override
		public boolean hasNext() {
			return iteratorCloseable.hasNext();
		}

		@Override
		public T next() {
			return iteratorCloseable.next();
		}

		@Override
		public void close() throws IOException {
			iteratorCloseable.close();
		}

	}

}
