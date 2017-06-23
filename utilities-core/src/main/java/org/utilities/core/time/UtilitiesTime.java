package org.utilities.core.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;

public class UtilitiesTime {

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
		return datetime -> parseQuietly(datetime, format).getTimeInUnix();
	}

	public static String formatUnix(long unix) {
		return formatUnix(unix, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatMillis(long millis) {
		return formatMillis(millis, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String formatUnix(long unix, String pattern) {
		return formatUnix(unix, pattern, Locale.ENGLISH);
	}

	public static String formatMillis(long millis, String pattern) {
		return formatMillis(millis, pattern, Locale.ENGLISH);
	}

	public static String formatDate(Date date, String pattern) {
		return formatDate(date, pattern, Locale.ENGLISH);
	}

	public static String formatUnix(long unix, String pattern, Locale locale) {
		return formatMillis(1000 * unix, pattern, locale);
	}

	public static String formatMillis(long millis, String pattern, Locale locale) {
		return formatDate(new Date(millis), pattern, locale);
	}

	public static String formatDate(Date date, String pattern, Locale locale) {
		return gmtFormatter(pattern, locale).format(date);
	}

	public static DateFormat gmtFormatter(String pattern, Locale locale) {
		DateFormat format = new SimpleDateFormat(pattern, locale);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format;
	}

	public static DateFormat gmtFormatter(String pattern) {
		return gmtFormatter(pattern, Locale.ENGLISH);
	}

	public static Unixtime parseQuietly(String date) throws QuietException {
		Unixtime unixtime = new Unixtime();
		unixtime = UnixtimeTransform.applyAll(unixtime, date);
		return unixtime;
	}

	public static Unixtime parseQuietly(String source, String pattern) throws QuietException {
		return parseQuietly(source, pattern, Locale.ENGLISH);
	}

	public static Unixtime parseQuietly(String source, String pattern, Locale locale) throws QuietException {
		return parseQuietly(source, gmtFormatter(pattern, locale));
	}

	public static Unixtime parseQuietly(String date, DateFormat format) throws QuietException {
		try {
			long millis = format.parse(date)
					.getTime();
			return new Unixtime(millis);
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

	public static Unixtime ymd(String date) throws QuietException {
		return parseQuietly(date, "yyyy-MM-dd");
	}

	public static Unixtime ymd_hms(String datetime) throws QuietException {
		return parseQuietly(datetime, "yyyy-MM-dd HH:mm:ss");
	}

	public static Unixtime hms(String time) throws QuietException {
		return parseQuietly(time, "HH:mm:ss");
	}

	public static boolean isIn(long time, long start, long end) {
		return start < time && time <= end;
	}

}
