package org.utilities.timeseries.filter;

import java.time.Instant;

import org.junit.Test;
import org.utilities.core.lang.iterable.filter.IPFilter;
import org.utilities.dataframe.cell.DFCellInstant;
import org.utilities.dataframe.row.DFRowImpl;
import org.utilities.timeseries.Event;

import junit.framework.TestCase;

public class EventTimeFilterTest extends TestCase {

	private static Event event(long seconds) {
		return new Event(new DFRowImpl("time", new DFCellInstant(Instant.ofEpochSecond(seconds))), "time");
	}

	@Test
	public void test_isEquals() {
		IPFilter<Event> filter = EventFilterUtils.isEquals(Instant.ofEpochSecond(1));
		assertFalse(filter.emit(-1, event(0)));
		assertTrue(filter.emit(-1, event(1)));
	}

	@Test
	public void test_isNotEquals() {
		IPFilter<Event> filter = EventFilterUtils.isNotEquals(Instant.ofEpochSecond(1));
		assertTrue(filter.emit(-1, event(0)));
		assertFalse(filter.emit(-1, event(1)));
	}

	@Test
	public void test_isBefore() {
		IPFilter<Event> filter = EventFilterUtils.isBefore(Instant.ofEpochSecond(1));
		assertTrue(filter.emit(-1, event(0)));
		assertFalse(filter.emit(-1, event(1)));
		assertFalse(filter.emit(-1, event(2)));
	}

	@Test
	public void test_isBeforeOrEquals() {
		IPFilter<Event> filter = EventFilterUtils.isBeforeOrEquals(Instant.ofEpochSecond(1));
		assertTrue(filter.emit(-1, event(0)));
		assertTrue(filter.emit(-1, event(1)));
		assertFalse(filter.emit(-1, event(2)));
	}

	@Test
	public void test_isAfter() {
		IPFilter<Event> filter = EventFilterUtils.isAfter(Instant.ofEpochSecond(1));
		assertFalse(filter.emit(-1, event(0)));
		assertFalse(filter.emit(-1, event(1)));
		assertTrue(filter.emit(-1, event(2)));
	}

	@Test
	public void test_isAfterOrEquals() {
		IPFilter<Event> filter = EventFilterUtils.isAfterOrEquals(Instant.ofEpochSecond(1));
		assertFalse(filter.emit(-1, event(0)));
		assertTrue(filter.emit(-1, event(1)));
		assertTrue(filter.emit(-1, event(2)));
	}

	@Test
	public void test_isBetween() {
		IPFilter<Event> filter = EventFilterUtils.isBetween(Instant.ofEpochSecond(1), Instant.ofEpochSecond(3));
		assertFalse(filter.emit(-1, event(0)));
		assertFalse(filter.emit(-1, event(1)));
		assertTrue(filter.emit(-1, event(2)));
		assertFalse(filter.emit(-1, event(3)));
		assertFalse(filter.emit(-1, event(4)));
	}

	@Test
	public void test_isBetweenOrEquals() {
		IPFilter<Event> filter = EventFilterUtils.isBetweenOrEquals(Instant.ofEpochSecond(1), Instant.ofEpochSecond(3));
		assertFalse(filter.emit(-1, event(0)));
		assertTrue(filter.emit(-1, event(1)));
		assertTrue(filter.emit(-1, event(2)));
		assertTrue(filter.emit(-1, event(3)));
		assertFalse(filter.emit(-1, event(4)));
	}

}
