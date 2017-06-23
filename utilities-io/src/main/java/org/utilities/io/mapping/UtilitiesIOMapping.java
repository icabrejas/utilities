package org.utilities.io.mapping;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.timeseries.Event;
import org.utilities.core.time.Unixtime;
import org.utilities.core.time.UtilitiesTime;
import org.utilities.io.csv.UtilitiesCSV;
import org.utilities.io.csv.bean.IterableCSVBean;
import org.utilities.io.csv.string.EntryCSVString;
import org.utilities.io.csv.string.IterablePipeCSVString;
import org.utilities.io.gz.EntryGZip;
import org.utilities.io.gz.UtilitiesGZip;
import org.utilities.io.json.UtilitiesJSON;
import org.utilities.io.s3.EntryS3;
import org.utilities.io.text.IterableLines;

import com.opencsv.bean.MappingStrategy;

public class UtilitiesIOMapping {

	private UtilitiesIOMapping() {
	}

	public static class GZip {

		private GZip() {
		}

		public static <I> IterableLines<I> parseLines(EntryGZip<I> gz) {
			return new IterableLines<>(gz.getInfo(), gz::getReader);
		}

		public static <I> IterablePipeCSVString<I> parseCSV(EntryGZip<I> gz) throws QuietException {
			return UtilitiesCSV.newIterableCSVString(gz.getInfo(), gz::getReader);
		}

		public static <I, T> IterableCSVBean<I, T> parseCSV(EntryGZip<I> gz, MappingStrategy<T> strategy)
				throws QuietException {
			return UtilitiesCSV.newIterableCSVBean(gz.getInfo(), gz::getReader, strategy);
		}

		public static <I, T> Function<EntryGZip<I>, IterableCSVBean<I, T>> parseCSVMapper(MappingStrategy<T> strategy) {
			return gz -> parseCSV(gz, strategy);
		}

		public static <I, T> IterableCSVBean<I, T> parseCSV(EntryGZip<I> gz, Class<T> type) throws QuietException {
			return parseCSV(gz, UtilitiesCSV.newHeaderColumnNameMappingStrategy(type));
		}

		public static <I, T> Function<EntryGZip<I>, IterableCSVBean<I, T>> parseCSVMapper(Class<T> type)
				throws QuietException {
			return gz -> parseCSV(gz, type);
		}

		public static <I, T> IterableCSVBean<I, T> parseCSV(EntryGZip<I> gz, Class<T> type, String... columns)
				throws QuietException {
			return parseCSV(gz, UtilitiesCSV.newColumnPositionMappingStrategy(type, columns));
		}

		public static <I, T> Function<EntryGZip<I>, IterableCSVBean<I, T>> parseCSVMapper(Class<T> type,
				String... columns) throws QuietException {
			return gz -> parseCSV(gz, type, columns);
		}

		public static <I, T> IterableCSVBean<I, T> parseCSV(EntryGZip<I> gz, Class<T> type,
				Map<String, String> columnMapping) throws QuietException {
			return parseCSV(gz, UtilitiesCSV.newHeaderColumnNameTranslateMappingStrategy(type, columnMapping));
		}

		public static <I, T> Function<EntryGZip<I>, IterableCSVBean<I, T>> parseCSVMapper(Class<T> type,
				Map<String, String> columnMapping) throws QuietException {
			return gz -> parseCSV(gz, type, columnMapping);
		}

		public static <I, T> T parseJSON(EntryGZip<I> gz, Class<T> valueType) throws QuietException {
			return UtilitiesJSON.jsonToClass(gz.getString(), valueType);
		}

		public static <I, T> Function<EntryGZip<I>, T> parseJSONMapper(Class<T> valueType) throws QuietException {
			return gz -> parseJSON(gz, valueType);
		}

	}

	public static class S3 {

		private S3() {
		}

		public static <I> IterableLines<String> parseLines(EntryS3 entryS3) {
			return new IterableLines<String>(entryS3.getInfo(), entryS3::getReader);
		}

		public static <I> IterablePipeCSVString<String> parseCSV(EntryS3 entryS3) throws QuietException {
			return UtilitiesCSV.newIterableCSVString(entryS3.getInfo(), entryS3::getReader);
		}

		public static <T> IterableCSVBean<String, T> parseCSV(EntryS3 entryS3, MappingStrategy<T> strategy)
				throws QuietException {
			return UtilitiesCSV.newIterableCSVBean(entryS3.getInfo(), entryS3::getReader, strategy);
		}

		public static <T> Function<EntryS3, IterableCSVBean<String, T>> parseCSVMapper(MappingStrategy<T> strategy) {
			return entryS3 -> parseCSV(entryS3, strategy);
		}

		public static <T> IterableCSVBean<String, T> parseCSV(EntryS3 entryS3, Class<T> type) throws QuietException {
			return parseCSV(entryS3, UtilitiesCSV.newHeaderColumnNameMappingStrategy(type));
		}

		public static <T> Function<EntryS3, IterableCSVBean<String, T>> parseCSVMapper(Class<T> type)
				throws QuietException {
			return entryS3 -> parseCSV(entryS3, type);
		}

		public static <T> IterableCSVBean<String, T> parseCSV(EntryS3 entryS3, Class<T> type, String... columns)
				throws QuietException {
			return parseCSV(entryS3, UtilitiesCSV.newColumnPositionMappingStrategy(type, columns));
		}

		public static <T> Function<EntryS3, IterableCSVBean<String, T>> parseCSVMapper(Class<T> type, String... columns)
				throws QuietException {
			return entryS3 -> parseCSV(entryS3, type, columns);
		}

		public static <T> IterableCSVBean<String, T> parseCSV(EntryS3 entryS3, Class<T> type,
				Map<String, String> columnMapping) throws QuietException {
			return parseCSV(entryS3, UtilitiesCSV.newHeaderColumnNameTranslateMappingStrategy(type, columnMapping));
		}

		public static <T> Function<EntryS3, IterableCSVBean<String, T>> parseCSVMapper(Class<T> type,
				Map<String, String> columnMapping) throws QuietException {
			return entryS3 -> parseCSV(entryS3, type, columnMapping);
		}

		public static <T> T parseJSON(EntryS3 entryS3, Class<T> valueType) throws QuietException {
			return UtilitiesJSON.jsonToClass(entryS3.getString(), valueType);
		}

		public static <T> Function<EntryS3, T> parseJSONMapper(Class<T> valueType) throws QuietException {
			return entryS3 -> parseJSON(entryS3, valueType);
		}

		public static EntryGZip<String> parseGZip(EntryS3 entryS3) {
			return UtilitiesGZip.newEntryGZip(entryS3.getInfo(), entryS3::getContent);
		}

	}

	public static class CSV {

		public static <I> Function<EntryCSVString<I>, Event<I, Double>> parseEventMapper(String datetimeLabel,
				String pattern) {
			return parseEventMapper(datetimeLabel, UtilitiesTime.unixParser(pattern));
		}

		public static <I> Function<EntryCSVString<I>, Event<I, Double>> parseEventMapper(String datetimeLabel) {
			return parseEventMapper(datetimeLabel, Long::parseLong);
		}

		public static <I> Function<EntryCSVString<I>, Event<I, Double>> parseEventMapper(String datetimeLabel,
				Function<String, Long> unixtime) {
			return entry -> parseEvent(entry, datetimeLabel, unixtime);
		}

		public static <I> Event<I, Double> parseEvent(EntryCSVString<I> entry, String datetimeLabel,
				Function<String, Long> unixtime) {
			I metainfo = entry.getMetainfo();
			String dateTime = entry.getString(datetimeLabel);
			Unixtime unixtime_ = Unixtime.newInstanceUnix(unixtime.apply(dateTime));
			Event<I, Double> evt = new Event<I, Double>(metainfo, unixtime_);
			for (String header : entry.getHeaders()) {
				if (!header.equals(datetimeLabel)) {
					String field = entry.getString(header);
					if (!field.isEmpty()) {
						double value = Double.parseDouble(field);
						if (!Double.isNaN(value)) {
							evt.put(header, value);
						}
					}
				}
			}
			return evt;
		}

		public static <I> Function<EntryCSVString<I>, Event<I, Double>> parseEventMapper(String dateLabel,
				String timeLabel, String pattern) {
			return parseEventMapper(dateLabel, timeLabel, " ", pattern);
		}

		public static <I> Function<EntryCSVString<I>, Event<I, Double>> parseEventMapper(String dateLabel,
				String timeLabel, String separator, String pattern) {
			Function<String, Long> parser = UtilitiesTime.unixParser(pattern);
			return parseEventMapper(dateLabel, timeLabel, (_date, _time) -> parser.apply(_date + separator + _time));
		}

		public static <I> Function<EntryCSVString<I>, Event<I, Double>> parseEventMapper(String dateLabel,
				String timeLabel, BiFunction<String, String, Long> unixtime) {
			return entry -> parseEvent(entry, dateLabel, timeLabel, unixtime);
		}

		public static <I> Event<I, Double> parseEvent(EntryCSVString<I> entry, String dateLabel, String timeLabel,
				BiFunction<String, String, Long> unixtime) {
			I metainfo = entry.getMetainfo();
			String date = entry.getString(dateLabel);
			String time = entry.getString(timeLabel);
			Unixtime unixtime_ = Unixtime.newInstanceUnix(unixtime.apply(date, time));
			Event<I, Double> evt = new Event<I, Double>(metainfo, unixtime_);
			for (String header : entry.getHeaders()) {
				if (!header.equals(dateLabel) && !header.equals(timeLabel)) {
					String field = entry.getString(header);
					if (field.isEmpty()) {
						evt.put(header, Double.parseDouble(field));
					}
				}
			}
			return evt;
		}

	}
}
