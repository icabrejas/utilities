package org.utilities.symbolicmath.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LogicalUtilsTest {

	@Test
	public void test_primitive_all_1() {
		assertTrue(LogicalUtils.all(new boolean[] { true, true, true, true, true }));
	}

	@Test
	public void test_primitive_all_2() {
		assertFalse(LogicalUtils.all(new boolean[] { true, true, true, false, true }));
	}

	@Test
	public void test_primitive_all_3() {
		assertFalse(LogicalUtils.all(new boolean[] { false, false, false, false, false }));
	}

	@Test
	public void test_primitive_any_1() {
		assertTrue(LogicalUtils.any(new boolean[] { true, true, true, true, true }));
	}

	@Test
	public void test_primitive_any_2() {
		assertTrue(LogicalUtils.any(new boolean[] { true, true, true, false, true }));
	}

	@Test
	public void test_primitive_any_3() {
		assertFalse(LogicalUtils.any(new boolean[] { false, false, false, false, false }));
	}

	@Test
	public void test_all_1() {
		assertTrue(LogicalUtils.all(new Boolean[] { true, true, true, true, true }));
	}

	@Test
	public void test_all_2() {
		assertFalse(LogicalUtils.all(new Boolean[] { true, true, true, false, true }));
	}

	@Test
	public void test_all_3() {
		assertFalse(LogicalUtils.all(new Boolean[] { false, false, false, false, false }));
	}

	@Test
	public void test_all_4() {
		assertNull(LogicalUtils.all(new Boolean[] { true, true, true, null, true }));
	}

	@Test
	public void test_all_5() {
		assertFalse(LogicalUtils.all(new Boolean[] { true, null, true, false, true }));
	}

	@Test
	public void test_any_1() {
		assertTrue(LogicalUtils.any(new Boolean[] { true, true, true, true, true }));
	}

	@Test
	public void test_any_2() {
		assertTrue(LogicalUtils.any(new Boolean[] { true, true, true, false, true }));
	}

	@Test
	public void test_any_3() {
		assertFalse(LogicalUtils.any(new Boolean[] { false, false, false, false, false }));
	}

	@Test
	public void test_any_4() {
		assertTrue(LogicalUtils.any(new Boolean[] { null, false, true, false, true }));
	}

	@Test
	public void test_any_5() {
		assertNull(LogicalUtils.any(new Boolean[] { null, false, null, false, false }));
	}

}
