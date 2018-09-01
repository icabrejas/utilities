package org.utilities.io.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.function.Supplier;

import org.utilities.core.util.function.SupplierPlus;
import org.utilities.io.IteratorCloseable;
import org.utilities.io.csv.bean.IterableCSVBean;
import org.utilities.io.csv.string.IterablePipeCSVString;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.MappingStrategy;

public class UtilitiesCSV {

	public static final String EXTESION = "csv";

	private UtilitiesCSV() {
	}

	public static String[] readHeaders(Supplier<CSVReader> reader) throws FileNotFoundException {
		CSVReader reader_ = reader.get();
		try (IteratorCloseable<String[]> it = new IteratorCloseable<>(reader_.iterator(), reader_)) {
			return it.hasNext() ? it.next() : null;
		}
	}

	public static <I, T> IterableCSVBean<I, T> newIterableCSVBean(I metadata, Supplier<? extends Reader> reader,
			MappingStrategy<T> strategy) {
		return new IterableCSVBean<>(metadata, reader, strategy);
	}

	public static <T> IterableCSVBean<File, T> newIterableCSVBean(File file, MappingStrategy<T> strategy) {
		Supplier<? extends Reader> reader = SupplierPlus.parseQuiet(() -> new FileReader(file));
		return newIterableCSVBean(file, reader, strategy);
	}

	public static <I> IterablePipeCSVString<I> newIterableCSVString(I metadata, Supplier<? extends Reader> reader) {
		return new IterablePipeCSVString<>(metadata, reader);
	}

	public static IterablePipeCSVString<File> newIterableCSVString(File file) {
		Supplier<? extends Reader> reader = SupplierPlus.parseQuiet(() -> new FileReader(file));
		return newIterableCSVString(file, reader);
	}

	public static <T> ColumnPositionMappingStrategy<T> newColumnPositionMappingStrategy(Class<T> type,
			String... columns) {
		ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
		strategy.setType(type);
		strategy.setColumnMapping(columns);
		return strategy;
	}

	public static <T> HeaderColumnNameMappingStrategy<T> newHeaderColumnNameMappingStrategy(Class<T> type) {
		HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
		strategy.setType(type);
		return strategy;
	}

	public static <T> HeaderColumnNameTranslateMappingStrategy<T> newHeaderColumnNameTranslateMappingStrategy(
			Class<T> type, Map<String, String> columnMapping) {
		HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
		strategy.setType(type);
		strategy.setColumnMapping(columnMapping);
		return strategy;
	}

}
