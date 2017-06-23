package org.utilities.io.csv.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utilities.io.csv.UtilitiesCSV;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class MappingStrategyBuilder {

	private MappingStrategyBuilder() {
	}

	public static class ByPosition<T> {

		private Class<T> type;
		private List<String> columns = new ArrayList<>();

		public ByPosition(Class<T> type) {
			this.type = type;
		}

		public boolean add(String column) {
			return columns.add(column);
		}

		public ColumnPositionMappingStrategy<T> get() {
			return UtilitiesCSV.newColumnPositionMappingStrategy(type, columns.toArray(new String[columns.size()]));
		}

	}

	public static class ByHeaderName<T> {

		private Class<T> type;

		public ByHeaderName(Class<T> type) {
			this.type = type;
		}

		public HeaderColumnNameMappingStrategy<T> get() {
			return UtilitiesCSV.newHeaderColumnNameMappingStrategy(type);
		}

	}

	public static class ByHeaderNameTranslate<T> {

		private Class<T> type;
		private Map<String, String> columnMapping = new HashMap<>();

		public ByHeaderNameTranslate(Class<T> type) {
			this.type = type;
		}

		public String put(String src, String dest) {
			return columnMapping.put(src, dest);
		}

		public HeaderColumnNameTranslateMappingStrategy<T> get() {
			return UtilitiesCSV.newHeaderColumnNameTranslateMappingStrategy(type, columnMapping);
		}

	}
}
