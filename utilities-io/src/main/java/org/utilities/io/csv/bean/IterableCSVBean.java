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

public class IterableCSVBean<I, T> implements IterablePipe<EntryCSVBean<I, T>> {

	private I metainfo;
	private CSVReaderBuilder reader;
	private MappingStrategy<T> strategy;

	public IterableCSVBean(I metainfo, Supplier<? extends Reader> reader, MappingStrategy<T> strategy) {
		this.metainfo = metainfo;
		this.reader = new CSVReaderBuilder(reader);
		this.strategy = strategy;
	}

	public IterableCSVBean<I, T> metainfo(I metainfo) {
		this.metainfo = metainfo;
		return this;
	}

	public IterableCSVBean<I, T> separator(char separator) {
		reader.separator(separator);
		return this;
	}

	public IterableCSVBean<I, T> quotechar(char quotechar) {
		reader.quotechar(quotechar);
		return this;
	}

	public IterableCSVBean<I, T> escape(char escape) {
		reader.escape(escape);
		return this;
	}

	public IterableCSVBean<I, T> line(int line) {
		reader.line(line);
		return this;
	}

	public IterableCSVBean<I, T> strictQuotes(boolean strictQuotes) {
		reader.strictQuotes(strictQuotes);
		return this;
	}

	public IterableCSVBean<I, T> ignoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
		reader.ignoreLeadingWhiteSpace(ignoreLeadingWhiteSpace);
		return this;
	}

	public IterableCSVBean<I, T> keepCR(boolean keepCR) {
		reader.keepCR(keepCR);
		return this;
	}

	@Override
	public IteratorCloseable<EntryCSVBean<I, T>> iterator() {
		try {
			IterableCSVBean.It<I, T> it = new IterableCSVBean.It<>(metainfo, reader, strategy);
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

	private static class It<I, T> implements Iterator<EntryCSVBean<I, T>>, Closeable {

		private I metainfo;
		private IteratorCloseable<T> iteratorCloseable;

		public It(I metainfo, Supplier<CSVReader> reader, MappingStrategy<T> strategy) throws FileNotFoundException {
			CSVReader reader_ = reader.get();
			IterableCSVToBean<T> it = new IterableCSVToBean<>(reader_, strategy, null);
			this.metainfo = metainfo;
			this.iteratorCloseable = new IteratorCloseable<>(it.iterator(), reader_);
		}

		@Override
		public boolean hasNext() {
			return iteratorCloseable.hasNext();
		}

		@Override
		public EntryCSVBean<I, T> next() {
			return new EntryCSVBean<>(metainfo, iteratorCloseable.next());
		}

		@Override
		public void close() throws IOException {
			iteratorCloseable.close();
		}

	}

}
