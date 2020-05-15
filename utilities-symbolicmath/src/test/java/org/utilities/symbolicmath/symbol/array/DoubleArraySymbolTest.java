package org.utilities.symbolicmath.symbol.array;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class DoubleArraySymbolTest extends TestCase {

	@Test
	public void test_abs() {
		SymbolArrayDouble<Object> v = SymbolArrayDouble.constant(new Double[] { 1., -2., 2.3 });
		SymbolArrayDouble<Object> abs = v.abs();
		Assert.assertArrayEquals(new Double[] { 1., 2., 2.3 }, abs.apply(null));
	}

	@Test
	public void test_abs_with_nulls() {
		SymbolArrayDouble<Object> v = SymbolArrayDouble.constant(new Double[] { 1., -2., null });
		SymbolArrayDouble<Object> abs = v.abs();
		Assert.assertArrayEquals(new Double[] { 1., 2., null }, abs.apply(null));
	}

	@Test
	public void test_add() {
		SymbolArrayDouble<Object> v = SymbolArrayDouble.constant(new Double[] { 1., -2., 2.3 });
		SymbolArrayDouble<Object> w = SymbolArrayDouble.constant(new Double[] { 3.2, 1.2, -5.3 });
		SymbolArrayDouble<Object> add = v.add(w);
		Assert.assertArrayEquals(new Double[] { 4.2, -0.8, -3.0 }, add.apply(null));
	}

	@Test
	public void test_add_with_nulls() {
		SymbolArrayDouble<Object> v = SymbolArrayDouble.constant(new Double[] { 1., null, 2.3 });
		SymbolArrayDouble<Object> w = SymbolArrayDouble.constant(new Double[] { 3.2, 1.2, null });
		SymbolArrayDouble<Object> add = v.add(w);
		Assert.assertArrayEquals(new Double[] { 4.2, null, null }, add.apply(null));
	}

}
