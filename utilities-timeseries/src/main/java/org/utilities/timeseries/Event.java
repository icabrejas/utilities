package org.utilities.timeseries;

import java.time.Instant;
import java.util.Comparator;

import org.utilities.dataframe.dataentry.DataEntryImpl;

public class Event extends DataEntryImpl {

	private Instant instant;

	public Event(Instant instant) {
		this.instant = instant;
	}

	public Instant getTime() {
		return instant;
	}

	public static Comparator<Event> timeComparator() {
		return Comparator.comparing(Event::getTime);
	}

	public boolean timeEquals(Event other) {
		if (instant == null) {
			return other.instant == null;
		} else {
			return instant.equals(other.instant);
		}
	}

}
