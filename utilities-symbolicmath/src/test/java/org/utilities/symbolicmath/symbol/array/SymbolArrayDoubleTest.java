package org.utilities.symbolicmath.symbol.array;

import org.junit.Test;
import org.utilities.symbolicmath.store.SymbolStore;

import junit.framework.TestCase;

public class SymbolArrayDoubleTest extends TestCase {

	@Test
	public void test_toPrimitive() {
		assertEquals(3., SymbolArrayDouble.constant(new Double[] { 1., 3., 5. })
				.toPrimitive(true)
				.mean()
				.apply(SymbolStore.dummy()));

		assertEquals(2., SymbolArrayDouble.constant(new Double[] { 1., 3., null })
				.toPrimitive(true)
				.mean()
				.apply(SymbolStore.dummy()));

		assertEquals(null, SymbolArrayDouble.constant(new Double[] { 1., 3., null })
				.toPrimitive(false)
				.mean()
				.apply(SymbolStore.dummy()));
	}

}
