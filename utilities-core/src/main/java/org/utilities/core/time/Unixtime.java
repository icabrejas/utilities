package org.utilities.core.time;


import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Unixtime implements Comparable<Unixtime> {

	private long milliseconds;

	public Unixtime() {
		this(0);
	}

	public Unixtime(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public static Unixtime newInstance() {
		return new Unixtime();
	}

	public static Unixtime newInstanceMillis(long millis) {
		return new Unixtime(millis);
	}

	public static Unixtime newInstanceUnix(long unix) {
		return new Unixtime(1000 * unix);
	}

	public Unixtime field(int field, int value) {
		if (field == Calendar.MONTH) {
			value--;
		}
		Calendar calendar = getTimeInCalendar();
		calendar.set(field, value);
		return new Unixtime(calendar.getTimeInMillis());
	}

	public Unixtime year(int year) {
		return field(Calendar.YEAR, year);
	}

	public Unixtime month(int month) {
		return field(Calendar.MONTH, month);
	}

	public Unixtime day(int day) {
		return field(Calendar.DAY_OF_MONTH, day);
	}

	public Unixtime hour(int hour) {
		return field(Calendar.HOUR_OF_DAY, hour);
	}

	public Unixtime minute(int minute) {
		return field(Calendar.MINUTE, minute);
	}

	public Unixtime second(int second) {
		return field(Calendar.SECOND, second);
	}

	public Unixtime millisecond(int millis) {
		return field(Calendar.MILLISECOND, millis);
	}

	public int get(int field) {
		Calendar calendar = getTimeInCalendar();
		if (field == Calendar.MONTH) {
			return calendar.get(field) + 1;
		} else {
			return calendar.get(field);
		}
	}

	public Unixtime add(int field, int amount) {
		Calendar calendar = getTimeInCalendar();
		calendar.add(field, amount);
		return new Unixtime(calendar.getTimeInMillis());
	}

	public Unixtime addMilliseconds(long millis) {
		return new Unixtime(this.milliseconds + millis);
	}

	public Unixtime addUnix(long unix) {
		return addMilliseconds(1000 * unix);
	}

	public long getTimeInMillis() {
		return milliseconds;
	}

	public long getTimeInUnix() {
		return milliseconds / 1000;
	}

	public Date getTimeInDate() {
		return new Date(milliseconds);
	}

	public Calendar getTimeInCalendar() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.ENGLISH);
		cal.setTimeInMillis(milliseconds);
		return cal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (milliseconds ^ (milliseconds >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unixtime other = (Unixtime) obj;
		if (milliseconds != other.milliseconds)
			return false;
		return true;
	}

	@Override
	public int compareTo(Unixtime other) {
		return Long.compare(this.milliseconds, other.milliseconds);
	}

	public static long offsetInMillis(Unixtime from, Unixtime to) {
		return to.getTimeInMillis() - from.getTimeInMillis();
	}

	public static long offsetInUnix(Unixtime from, Unixtime to) {
		return to.getTimeInUnix() - from.getTimeInUnix();
	}

	@Override
	public String toString() {
		return UtilitiesTime.formatMillis(getTimeInMillis(), "yyyy-MM-dd HH:mm:ss.SSS");
	}

}
