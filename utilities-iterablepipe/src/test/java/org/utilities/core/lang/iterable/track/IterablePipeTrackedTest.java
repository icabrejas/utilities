package org.utilities.core.lang.iterable.track;

import org.junit.Test;
import org.utilities.core.lang.iterable.IterablePipeSequence;
import org.utilities.core.util.lambda.LambdaBoolean;
import org.utilities.core.util.lambda.LambdaInt;

import junit.framework.TestCase;

public class IterablePipeTrackedTest extends TestCase {

	@Test
	public void test_start_end() {
		LambdaBoolean start = new LambdaBoolean(false);
		LambdaBoolean end = new LambdaBoolean(false);
		IPTracker<Integer> tracker = new IPTrackerImpl<Integer>()//
				.start(serialNumber -> start.set(true))
				.end(serialNumber -> end.set(true));
		Iterable<Integer> indices = IterablePipeSequence.create(1, 10)
				.track(tracker);
		assertFalse(start.get());
		assertFalse(end.get());
		for (int i : indices) {
			assertTrue(start.get());
			assertFalse(end.get());
		}
		assertTrue(start.get());
		assertTrue(end.get());
	}

	@Test
	public void test_ask_for_multiples_iterators_sequentially() {
		LambdaInt start = new LambdaInt(0);
		LambdaInt end = new LambdaInt(0);
		IPTracker<Integer> tracker = new IPTrackerImpl<Integer>()//
				.start(serialNumber -> start.increment())
				.end(serialNumber -> end.increment());
		Iterable<Integer> indices = IterablePipeSequence.create(1, 10)
				.track(tracker);

		assertEquals(new Integer(0), start.get());
		assertEquals(new Integer(0), end.get());

		for (int i : indices) {
			assertEquals(new Integer(1), start.get());
			assertEquals(new Integer(0), end.get());
		}
		assertEquals(new Integer(1), start.get());
		assertEquals(new Integer(1), end.get());

		for (int i : indices) {
			assertEquals(new Integer(2), start.get());
			assertEquals(new Integer(1), end.get());
		}
		assertEquals(new Integer(2), start.get());
		assertEquals(new Integer(2), end.get());
	}

}
