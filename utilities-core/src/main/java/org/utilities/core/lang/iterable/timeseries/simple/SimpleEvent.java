package org.utilities.core.lang.iterable.timeseries.simple;

import org.utilities.core.time.Unixtime;
import org.utilities.core.time.UtilitiesTime;

public class SimpleEvent<I, V> {

	private I metainfo;
	private Unixtime unixtime;
	// FIXME remove
	private String label;
	private V value;

	public I getMetainfo() {
		return metainfo;
	}

	public void setMetainfo(I metainfo) {
		this.metainfo = metainfo;
	}

	public Unixtime getUnixtime() {
		return unixtime;
	}

	public void setUnixtime(Unixtime unixtime) {
		this.unixtime = unixtime;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder().append("metainfo: ")
				.append(metainfo)
				.append(", ")
				.append("unixtime: ")
				.append(UtilitiesTime.formatMillis(unixtime.getTimeInMillis(), "yyyy MMM dd HH:mm:ss"))
				.append(", ")
				.append(label)
				.append(": ")
				.append(value);
		return builder.toString();
	}

}
