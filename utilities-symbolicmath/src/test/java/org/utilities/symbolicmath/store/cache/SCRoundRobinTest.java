package org.utilities.symbolicmath.store.cache;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class SCRoundRobinTest extends TestCase {

	@Test(expected = IndexOutOfBoundsException.class)
	public void from_equals_to() {
		SCRoundRobin<Double> cache = new SCRoundRobin<>(3, Double[]::new);
		int i = -1;
		cache.put(++i, 1.);
		cache.put(++i, 3.);
		cache.put(++i, 2.);
		cache.range(1, 1);
	}

	@Test
	public void test() {
		SCRoundRobin<Double> cache = new SCRoundRobin<>(3, Double[]::new);
		int i = -1;
		cache.put(++i, 1.);
		Assert.assertArrayEquals(new Double[] { null, null, 1. }, cache.range(i - 3, i));
		cache.put(++i, 3.);
		Assert.assertArrayEquals(new Double[] { null, 1., 3. }, cache.range(i - 3, i));
		cache.put(++i, 2.);
		Assert.assertArrayEquals(new Double[] { 1., 3., 2. }, cache.range(i - 3, i));
		cache.put(++i, 2.);
		Assert.assertArrayEquals(new Double[] { 3., 2., 2. }, cache.range(i - 3, i));
		cache.put(++i, 5.);
		Assert.assertArrayEquals(new Double[] { 2., 2., 5. }, cache.range(i - 3, i));
		cache.put(++i, null);
		Assert.assertArrayEquals(new Double[] { 2., 5., null }, cache.range(i - 3, i));
		cache.put(++i, 10.);
		Assert.assertArrayEquals(new Double[] { 5., null, 10. }, cache.range(i - 3, i));
		cache.put(++i, 8.);
		Assert.assertArrayEquals(new Double[] { null, 10., 8. }, cache.range(i - 3, i));
		cache.put(++i, 12.);
		Assert.assertArrayEquals(new Double[] { 10., 8., 12. }, cache.range(i - 3, i));
	}

}
