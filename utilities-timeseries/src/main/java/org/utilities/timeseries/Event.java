package org.utilities.timeseries;

import java.time.Instant;
import java.util.Comparator;

import org.utilities.dataframe.row.DFRow;
import org.utilities.dataframe.row.DFRowImpl;

public class Event extends DFRowImpl {

	private String time;

	public Event(String time) {
		this.time = time;
	}

	public Event(DFRow row, String time) {
		super(row);
		this.time = time;
	}

	public Instant getTime() {
		return getInstant(time);
	}

	public static Comparator<Event> timeComparator() {
		return Comparator.comparing(Event::getTime);
	}

	public int compareTimeTo(Event other) {
		return getTime().compareTo(other.getTime());
	}

	public boolean timeEquals(Event other) {
		return getTime().equals(other.getTime());
	}

}
