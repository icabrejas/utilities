package org.utilities.timeseries;

import java.util.Comparator;
import java.util.Date;

import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.time.Unixtime;

public class SimpleEvent<I> implements DataValue {

	private I metainfo;
	private Unixtime unixtime;
	private DataValue value;

	public SimpleEvent(I metainfo, Unixtime unixtime) {
		this.metainfo = metainfo;
		this.unixtime = unixtime;
	}

	public I getMetainfo() {
		return metainfo;
	}

	public Unixtime getUnixtime() {
		return unixtime;
	}

	public DataValue getValue() {
		return value;
	}

	public String getStringValue() {
		return value.stringValue();
	}

	public Integer getIntValue() {
		return value.intValue();
	}

	public Long getLongValue() {
		return value.longValue();
	}

	public Float getFloatValue() {
		return value.floatValue();
	}

	public Double getDoubleValue() {
		return value.doubleValue();
	}

	public Date getDateValue() {
		return value.dateValue();
	}

	public long getTimeInMillis() {
		return unixtime.getTimeInMillis();
	}

	public long getTimeInUnix() {
		return unixtime.getTimeInUnix();
	}

	public SimpleEvent<I> value(DataValue value) {
		this.value = value;
		return this;
	}

	@Override
	public String stringValue() {
		return value.stringValue();
	}

	@Override
	public Integer intValue() {
		return value.intValue();
	}

	@Override
	public Long longValue() {
		return value.longValue();
	}

	@Override
	public Float floatValue() {
		return value.floatValue();
	}

	@Override
	public Double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public Date dateValue() {
		return value.dateValue();
	}

	public static <I extends Comparable<I>> Comparator<SimpleEvent<I>> keyComparator() {
		Comparator<SimpleEvent<I>> comparator = SimpleEvent.infoComparator();
		return comparator.thenComparing(SimpleEvent.timeComparator());
	}

	public static <I extends Comparable<I>> Comparator<SimpleEvent<I>> keyComparator(Class<I> info) {
		Comparator<SimpleEvent<I>> comparator = SimpleEvent.infoComparator(info);
		return comparator.thenComparing(SimpleEvent.timeComparator(info));
	}

	public static <I> Comparator<SimpleEvent<I>> timeComparator() {
		return Comparator.comparingLong(SimpleEvent::getTimeInMillis);
	}

	public static <I> Comparator<SimpleEvent<I>> timeComparator(Class<I> info) {
		return Comparator.comparingLong(SimpleEvent::getTimeInMillis);
	}

	public static <I extends Comparable<I>> Comparator<SimpleEvent<I>> infoComparator() {
		return Comparator.comparing(SimpleEvent::getMetainfo);
	}

	public static <I extends Comparable<I>> Comparator<SimpleEvent<I>> infoComparator(Class<I> info) {
		return Comparator.comparing(SimpleEvent::getMetainfo);
	}

	public boolean keyEquals(SimpleEvent<I> evt) {
		return infoEquals(evt) && timeEquals(evt);
	}

	public boolean infoEquals(SimpleEvent<I> other) {
		if (this.metainfo == null) {
			return other.metainfo == null;
		} else {
			return this.metainfo.equals(other.metainfo);
		}
	}

	public boolean timeEquals(SimpleEvent<I> other) {
		if (this.unixtime == null) {
			return other.unixtime == null;
		} else {
			return this.unixtime.equals(other.unixtime);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metainfo == null) ? 0 : metainfo.hashCode());
		result = prime * result + ((unixtime == null) ? 0 : unixtime.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		SimpleEvent<?> other = (SimpleEvent<?>) obj;
		if (metainfo == null) {
			if (other.metainfo != null)
				return false;
		} else if (!metainfo.equals(other.metainfo))
			return false;
		if (unixtime == null) {
			if (other.unixtime != null)
				return false;
		} else if (!unixtime.equals(other.unixtime))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleEvent [metainfo=" + metainfo + ", unixtime=" + unixtime + ", value=" + value + "]";
	}

	@Override
	public int compareTo(DataValue other) {
		return this.value.compareTo(other);
	}

}
