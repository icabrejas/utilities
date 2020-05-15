package org.utilities.io.csv;

import java.io.Reader;
import java.util.function.Supplier;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;

public class CSVReaderBuilder implements Supplier<CSVReader> {

	private Supplier<? extends Reader> reader;
	private char separator = CSVParser.DEFAULT_SEPARATOR;
	private char quotechar = CSVParser.DEFAULT_QUOTE_CHARACTER;
	private char escape = CSVParser.DEFAULT_ESCAPE_CHARACTER;
	private int line = 0; // TODO skip lines?
	private boolean strictQuotes = CSVParser.DEFAULT_STRICT_QUOTES;
	private boolean ignoreLeadingWhiteSpace = CSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE;
	private boolean keepCR = false;

	public CSVReaderBuilder(Supplier<? extends Reader> reader) {
		this.reader = reader;
	}

	public CSVReaderBuilder separator(char separator) {
		this.separator = separator;
		return this;
	}

	public CSVReaderBuilder quotechar(char quotechar) {
		this.quotechar = quotechar;
		return this;
	}

	public CSVReaderBuilder escape(char escape) {
		this.escape = escape;
		return this;
	}

	public CSVReaderBuilder line(int line) {
		this.line = line;
		return this;
	}

	public CSVReaderBuilder strictQuotes(boolean strictQuotes) {
		this.strictQuotes = strictQuotes;
		return this;
	}

	public CSVReaderBuilder ignoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
		this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
		return this;
	}

	public CSVReaderBuilder keepCR(boolean keepCR) {
		this.keepCR = keepCR;
		return this;
	}

	@Override
	public CSVReader get() {
		CSVParser parser = new CSVParserBuilder()//
				.withSeparator(separator)
				.withQuoteChar(quotechar)
				.withEscapeChar(escape)
				.withStrictQuotes(strictQuotes)
				.withIgnoreLeadingWhiteSpace(ignoreLeadingWhiteSpace)
				.build();
		return new com.opencsv.CSVReaderBuilder(reader.get())//
				.withCSVParser(parser)
				.build();
	}

	@Override
	public String toString() {
		return "CSVReaderBuilder [separator=" + separator + ", quotechar=" + quotechar + ", escape=" + escape
				+ ", line=" + line + ", strictQuotes=" + strictQuotes + ", ignoreLeadingWhiteSpace="
				+ ignoreLeadingWhiteSpace + ", keepCR=" + keepCR + "]";
	}

}
