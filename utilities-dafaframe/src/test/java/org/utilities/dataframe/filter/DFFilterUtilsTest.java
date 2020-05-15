package org.utilities.dataframe.filter;

import org.junit.Test;
import org.utilities.core.lang.iterable.filter.IPFilter;
import org.utilities.dataframe.cell.DFCellDouble;
import org.utilities.dataframe.row.DFRow;
import org.utilities.dataframe.row.DFRowImpl;

import junit.framework.TestCase;

public class DFFilterUtilsTest extends TestCase {

	public static DFRow row(double value) {
		return new DFRowImpl("VAR1", new DFCellDouble(value));
	}

	@Test
	public void test_isNull() {
		IPFilter<DFRow> filter = DFFilterUtils.isNull("VAR1");
		assertFalse(filter.emit(-1, row(3.)));
		assertTrue(filter.emit(-1, new DFRowImpl()));
	}

	@Test
	public void test_isNotNull() {
		IPFilter<DFRow> filter = DFFilterUtils.isNotNull("VAR1");
		assertTrue(filter.emit(-1, row(3.)));
		assertFalse(filter.emit(-1, new DFRowImpl()));
	}

	@Test
	public void test_isEquals() {
		IPFilter<DFRow> filter = DFFilterUtils.isEquals("VAR1", new DFCellDouble(3.));
		assertTrue(filter.emit(-1, row(3.)));
		assertFalse(filter.emit(-1, row(2.)));
		assertFalse(filter.emit(-1, new DFRowImpl()));
	}

	@Test
	public void test_isNotEquals() {
		IPFilter<DFRow> filter = DFFilterUtils.isNotEquals("VAR1", new DFCellDouble(3.));
		assertFalse(filter.emit(-1, row(3.)));
		assertTrue(filter.emit(-1, row(2.)));
		assertTrue(filter.emit(-1, new DFRowImpl()));
	}

	@Test
	public void test_isHigher() {
		IPFilter<DFRow> filter = DFFilterUtils.isHigher("VAR1", new DFCellDouble(3.));
		assertTrue(filter.emit(-1, row(4.)));
		assertFalse(filter.emit(-1, row(3.)));
		assertFalse(filter.emit(-1, row(2.)));
	}

	@Test
	public void test_isHigherOrEquals() {
		IPFilter<DFRow> filter = DFFilterUtils.isHigherOrEquals("VAR1", new DFCellDouble(3.));
		assertTrue(filter.emit(-1, row(4.)));
		assertTrue(filter.emit(-1, row(3.)));
		assertFalse(filter.emit(-1, row(2.)));
	}

	@Test
	public void test_Lower() {
		IPFilter<DFRow> filter = DFFilterUtils.isLower("VAR1", new DFCellDouble(3.));
		assertFalse(filter.emit(-1, row(4.)));
		assertFalse(filter.emit(-1, row(3.)));
		assertTrue(filter.emit(-1, row(2.)));
	}

	@Test
	public void test_isLowerOrEquals() {
		IPFilter<DFRow> filter = DFFilterUtils.isLowerOrEquals("VAR1", new DFCellDouble(3.));
		assertFalse(filter.emit(-1, row(4.)));
		assertTrue(filter.emit(-1, row(3.)));
		assertTrue(filter.emit(-1, row(2.)));
	}

	@Test
	public void test_isBetween() {
		IPFilter<DFRow> filter = DFFilterUtils.isBetween("VAR1", new DFCellDouble(2.), new DFCellDouble(4.));
		assertFalse(filter.emit(-1, row(5.)));
		assertFalse(filter.emit(-1, row(4.)));
		assertTrue(filter.emit(-1, row(3.)));
		assertFalse(filter.emit(-1, row(2.)));
		assertFalse(filter.emit(-1, row(1.)));
	}

	@Test
	public void test_isBetweenOrEquals() {
		IPFilter<DFRow> filter = DFFilterUtils.isBetweenOrEquals("VAR1", new DFCellDouble(2.), new DFCellDouble(4.));
		assertFalse(filter.emit(-1, row(5.)));
		assertTrue(filter.emit(-1, row(4.)));
		assertTrue(filter.emit(-1, row(3.)));
		assertTrue(filter.emit(-1, row(2.)));
		assertFalse(filter.emit(-1, row(1.)));
	}

}
