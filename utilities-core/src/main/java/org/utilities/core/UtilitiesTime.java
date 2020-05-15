package org.utilities.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.time.InstantTransform;
import org.utilities.core.time.TicToc;

public class UtilitiesTime {

	public static final String DATE_IN_SECONDS_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_IN_SECONDS_PATTERN = "HH:mm:ss";
	public static final int DAY_IN_SECONDS = 24 * 60 * 60;
	public static final String DATE_IN_MILLIS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String TIME_IN_MILLIS_PATTERN = "HH:mm:ss.SSS";
	public static final long DAY_IN_MILLIS = DAY_IN_SECONDS * 1000L;
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	public static final String DEFAULT_TIMEZONE = "GMT";

	public static String currentTimeString() {
		return currentTimeString(DATE_IN_MILLIS_PATTERN);
	}

	public static String currentTimeString(String pattern) {
		return formatMillis(System.currentTimeMillis());
	}

	public static TicToc tic() {
		return UtilitiesTime.tic(TicToc.DEFAULT_PATTERN);
	}

	public static TicToc tic(String pattern) {
		return UtilitiesTime.tic(pattern, System.currentTimeMillis());
	}

	public static TicToc tic(String pattern, long tic) {
		return new TicToc(pattern, tic);
	}

	public static Function<String, Long> unixParser(String pattern) {
		DateFormat format = gmtFormatter(pattern);
		return datetime -> parseQuietly(datetime, format).toEpochMilli();
	}

	public static String formatUnix(long unix) {
		if (unix < DAY_IN_SECONDS) {
			return formatUnix(unix, TIME_IN_SECONDS_PATTERN);
		} else {
			return formatUnix(unix, DATE_IN_SECONDS_PATTERN);
		}
	}

	public static String formatUnix(long unix, String pattern) {
		return formatUnix(unix, pattern, DEFAULT_LOCALE);
	}

	public static String formatUnix(long unix, String pattern, Locale locale) {
		return formatMillis(1000 * unix, pattern, locale);
	}

	public static String formatMillis(long millis) {
		if (millis < DAY_IN_MILLIS) {
			return formatMillis(millis, TIME_IN_MILLIS_PATTERN);
		} else {
			return formatMillis(millis, DATE_IN_MILLIS_PATTERN);
		}
	}

	public static String formatMillis(long millis, String pattern) {
		return formatMillis(millis, pattern, DEFAULT_LOCALE);
	}

	public static String formatMillis(long millis, String pattern, Locale locale) {
		return formatDate(new Date(millis), pattern, locale);
	}

	public static String formatDate(Date date) {
		return formatDate(date, DATE_IN_MILLIS_PATTERN);
	}

	public static String formatDate(Date date, String pattern) {
		return formatDate(date, pattern, DEFAULT_LOCALE);
	}

	public static String formatDate(Date date, String pattern, Locale locale) {
		return gmtFormatter(pattern, locale).format(date);
	}

	public static DateFormat gmtFormatter(String pattern) {
		return gmtFormatter(pattern, DEFAULT_LOCALE);
	}

	public static DateFormat gmtFormatter(String pattern, Locale locale) {
		return getFormatter(pattern, locale, DEFAULT_TIMEZONE);
	}

	public static DateFormat getFormatter(String pattern, Locale locale, String timeZone) {
		DateFormat format = new SimpleDateFormat(pattern, locale);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		return format;
	}

	public static Calendar parseCalendar(Date date) {
		return parseCalendar(date, DEFAULT_TIMEZONE);
	}

	public static Calendar parseCalendar(Date date, String timeZoneID) {
		return parseCalendar(date, timeZoneID, DEFAULT_LOCALE);
	}

	public static Calendar parseCalendar(Date date, String timeZoneID, Locale locale) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID), locale);
		calendar.setTime(date);
		return calendar;
	}

	public static Calendar parseCalendar(long millis) {
		return parseCalendar(millis, DEFAULT_TIMEZONE);
	}

	public static Calendar parseCalendar(long millis, String timeZoneID) {
		return parseCalendar(millis, timeZoneID, DEFAULT_LOCALE);
	}

	public static Calendar parseCalendar(long millis, String timeZoneID, Locale locale) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID), locale);
		calendar.setTimeInMillis(millis);
		return calendar;
	}

	public static int get(Date date, int field) {
		return get(date, field, DEFAULT_TIMEZONE);
	}

	public static int get(Date date, int field, String timeZoneID) {
		return get(date, field, timeZoneID, DEFAULT_LOCALE);
	}

	public static int get(Date date, int field, String timeZoneID, Locale locale) {
		return parseCalendar(date, timeZoneID, locale).get(field);
	}

	public static Date add(Date date, int field, int amount) {
		return add(date, field, amount, DEFAULT_TIMEZONE);
	}

	public static Date add(Date date, int field, int amount, String timeZoneID) {
		return add(date, field, amount, timeZoneID, DEFAULT_LOCALE);
	}

	public static Date add(Date date, int field, int amount, String timeZoneID, Locale locale) {
		Calendar calendar = parseCalendar(date, timeZoneID, locale);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	public static long add(long millis, int field, int amount) {
		return add(millis, field, amount, DEFAULT_TIMEZONE);
	}

	public static long add(long millis, int field, int amount, String timeZoneID) {
		return add(millis, field, amount, timeZoneID, DEFAULT_LOCALE);
	}

	public static long add(long millis, int field, int amount, String timeZoneID, Locale locale) {
		Calendar calendar = parseCalendar(millis, timeZoneID, locale);
		calendar.add(field, amount);
		return calendar.getTimeInMillis();
	}

	public static Instant parseQuietly(String date) throws QuietException {
		Instant instant = Instant.now();
		instant = InstantTransform.applyAll(instant, date);
		return instant;
	}

	public static Instant parseQuietly(String source, String pattern) throws QuietException {
		return parseQuietly(source, pattern, DEFAULT_LOCALE);
	}

	public static Instant parseQuietly(String source, String pattern, Locale locale) throws QuietException {
		return parseQuietly(source, gmtFormatter(pattern, locale));
	}

	public static Instant parseQuietly(String date, DateFormat format) throws QuietException {
		try {
			long millis = format.parse(date)
					.getTime();
			return Instant.ofEpochMilli(millis);
		} catch (ParseException e) {
			throw new QuietException(e);
		}
	}

	public static String ymd(long millis) throws QuietException {
		return formatMillis(millis, "yyyy-MM-dd");
	}

	public static String ymd_hms(long millis) throws QuietException {
		return formatMillis(millis, "yyyy-MM-dd HH:mm:ss");
	}

	public static String hms(long millis) throws QuietException {
		return formatMillis(millis, "HH:mm:ss");
	}

	public static Instant ymd(String date) throws QuietException {
		return parseQuietly(date, "yyyy-MM-dd");
	}

	public static Instant ymd_hms(String datetime) throws QuietException {
		return parseQuietly(datetime, "yyyy-MM-dd HH:mm:ss");
	}

	public static Instant hms(String time) throws QuietException {
		return parseQuietly(time, "HH:mm:ss");
	}

	public static boolean isBetween(long time, long start, long end) {
		return start < time && time <= end;
	}

}
