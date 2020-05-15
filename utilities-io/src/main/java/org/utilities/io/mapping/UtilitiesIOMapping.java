package org.utilities.io.mapping;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.UtilitiesTime;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.dataframe.row.DFRow;
import org.utilities.io.csv.UtilitiesCSV;
import org.utilities.io.csv.bean.IterableCSVBean;
import org.utilities.io.csv.string.IterablePipeCSV;
import org.utilities.io.gz.GZipIOEntry;
import org.utilities.io.gz.UtilitiesGZip;
import org.utilities.io.json.UtilitiesJSON;
import org.utilities.io.s3.S3IOEntry;
import org.utilities.io.text.IterableLines;
import org.utilities.timeseries.Event;

import com.opencsv.bean.MappingStrategy;

public class UtilitiesIOMapping {

	private UtilitiesIOMapping() {
	}

	public static class GZip {

		private GZip() {
		}

		public static IterableLines parseLines(GZipIOEntry gz) {
			return new IterableLines(gz::getReader);
		}

		public static IterablePipeCSV parseCSV(GZipIOEntry gz) throws QuietException {
			return UtilitiesCSV.newInstance(gz::getReader);
		}

		public static <T> IterableCSVBean<T> parseCSV(GZipIOEntry gz, MappingStrategy<T> strategy)
				throws QuietException {
			return UtilitiesCSV.newInstance(gz::getReader, strategy);
		}

		public static <T> Function<GZipIOEntry, IterableCSVBean<T>> parseCSVMapper(MappingStrategy<T> strategy) {
			return gz -> parseCSV(gz, strategy);
		}

		public static <T> IterableCSVBean<T> parseCSV(GZipIOEntry gz, Class<T> type) throws QuietException {
			return parseCSV(gz, UtilitiesCSV.newHeaderColumnNameMappingStrategy(type));
		}

		public static <T> Function<GZipIOEntry, IterableCSVBean<T>> parseCSVMapper(Class<T> type)
				throws QuietException {
			return gz -> parseCSV(gz, type);
		}

		public static <T> IterableCSVBean<T> parseCSV(GZipIOEntry gz, Class<T> type, String... columns)
				throws QuietException {
			return parseCSV(gz, UtilitiesCSV.newColumnPositionMappingStrategy(type, columns));
		}

		public static <T> Function<GZipIOEntry, IterableCSVBean<T>> parseCSVMapper(Class<T> type, String... columns)
				throws QuietException {
			return gz -> parseCSV(gz, type, columns);
		}

		public static <T> IterableCSVBean<T> parseCSV(GZipIOEntry gz, Class<T> type, Map<String, String> columnMapping)
				throws QuietException {
			return parseCSV(gz, UtilitiesCSV.newHeaderColumnNameTranslateMappingStrategy(type, columnMapping));
		}

		public static <T> Function<GZipIOEntry, IterableCSVBean<T>> parseCSVMapper(Class<T> type,
				Map<String, String> columnMapping) throws QuietException {
			return gz -> parseCSV(gz, type, columnMapping);
		}

		public static <T> T parseJSON(GZipIOEntry gz, Class<T> valueType) throws QuietException {
			return UtilitiesJSON.jsonToClass(gz.getString(), valueType);
		}

		public static <T> Function<GZipIOEntry, T> parseJSONMapper(Class<T> valueType) throws QuietException {
			return gz -> parseJSON(gz, valueType);
		}

	}

	public static class S3 {

		private S3() {
		}

		public static IterableLines parseLines(S3IOEntry entryS3) {
			return new IterableLines(entryS3::getReader);
		}

		public static IterablePipeCSV parseCSV(S3IOEntry entryS3) throws QuietException {
			return UtilitiesCSV.newInstance(entryS3::getReader);
		}

		public static <T> IterableCSVBean<T> parseCSV(S3IOEntry entryS3, MappingStrategy<T> strategy)
				throws QuietException {
			return UtilitiesCSV.newInstance(entryS3::getReader, strategy);
		}

		public static <T> Function<S3IOEntry, IterableCSVBean<T>> parseCSVMapper(MappingStrategy<T> strategy) {
			return entryS3 -> parseCSV(entryS3, strategy);
		}

		public static <T> IterableCSVBean<T> parseCSV(S3IOEntry entryS3, Class<T> type) throws QuietException {
			return parseCSV(entryS3, UtilitiesCSV.newHeaderColumnNameMappingStrategy(type));
		}

		public static <T> Function<S3IOEntry, IterableCSVBean<T>> parseCSVMapper(Class<T> type) throws QuietException {
			return entryS3 -> parseCSV(entryS3, type);
		}

		public static <T> IterableCSVBean<T> parseCSV(S3IOEntry entryS3, Class<T> type, String... columns)
				throws QuietException {
			return parseCSV(entryS3, UtilitiesCSV.newColumnPositionMappingStrategy(type, columns));
		}

		public static <T> Function<S3IOEntry, IterableCSVBean<T>> parseCSVMapper(Class<T> type, String... columns)
				throws QuietException {
			return entryS3 -> parseCSV(entryS3, type, columns);
		}

		public static <T> IterableCSVBean<T> parseCSV(S3IOEntry entryS3, Class<T> type,
				Map<String, String> columnMapping) throws QuietException {
			return parseCSV(entryS3, UtilitiesCSV.newHeaderColumnNameTranslateMappingStrategy(type, columnMapping));
		}

		public static <T> Function<S3IOEntry, IterableCSVBean<T>> parseCSVMapper(Class<T> type,
				Map<String, String> columnMapping) throws QuietException {
			return entryS3 -> parseCSV(entryS3, type, columnMapping);
		}

		public static <T> T parseJSON(S3IOEntry entryS3, Class<T> valueType) throws QuietException {
			return UtilitiesJSON.jsonToClass(entryS3.getString(), valueType);
		}

		public static <T> Function<S3IOEntry, T> parseJSONMapper(Class<T> valueType) throws QuietException {
			return entryS3 -> parseJSON(entryS3, valueType);
		}

		public static GZipIOEntry parseGZip(S3IOEntry entryS3) {
			return UtilitiesGZip.newEntryGZip(entryS3::get);
		}

	}

	public static class CSV {

		public static Function<DFRow, Event> parseEventMapper(String datetimeLabel, String pattern) {
			return parseEventMapper(datetimeLabel, UtilitiesTime.unixParser(pattern));
		}

		public static Function<DFRow, Event> parseEventMapper(String datetimeLabel) {
			return parseEventMapper(datetimeLabel, Long::parseLong);
		}

		public static Function<DFRow, Event> parseEventMapper(String datetimeLabel, Function<String, Long> unixtime) {
			return entry -> parseEvent(entry, datetimeLabel, unixtime);
		}

		// FIXME use unixtime parser
		public static Event parseEvent(DFRow entry, String datetimeLabel, Function<String, Long> unixtime) {
			return new Event(entry, datetimeLabel);
		}

		public static Function<DFRow, Event> parseEventMapper(String dateLabel, String timeLabel, String pattern) {
			return parseEventMapper(dateLabel, timeLabel, " ", pattern);
		}

		public static Function<DFRow, Event> parseEventMapper(String dateLabel, String timeLabel, String separator,
				String pattern) {
			Function<String, Long> parser = UtilitiesTime.unixParser(pattern);
			return parseEventMapper(dateLabel, timeLabel, (_date, _time) -> parser.apply(_date + separator + _time));
		}

		public static Function<DFRow, Event> parseEventMapper(String dateLabel, String timeLabel,
				BiFunction<String, String, Long> unixtime) {
			return entry -> parseEvent(entry, dateLabel, timeLabel, unixtime);
		}

		// FIXME use two columns
		public static Event parseEvent(DFRow entry, String dateLabel, String timeLabel,
				BiFunction<String, String, Long> unixtime) {
			// String date = entry.getString(dateLabel);
			// String time = entry.getString(timeLabel);
			// Instant unixtime_ = Instant.ofEpochMilli(unixtime.apply(date,
			// time));
			// Event evt = new Event(unixtime_);
			// entry = entry.remove(dateLabel, timeLabel);
			// evt.putAll(evt);
			// return evt;
			return null;
		}

	}

}
