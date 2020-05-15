package org.utilities.symbolicmath.symbol.array;

import org.junit.Assert;
import org.junit.Test;
import org.utilities.symbolicmath.symbol.SymbolDouble;

import junit.framework.TestCase;

public class SymbolArrayDoublePrimitiveTest extends TestCase {

	@Test
	public void test_mean() {
		SymbolDouble<Object> mean = SymbolArrayDoublePrimitive.constant(new double[] { 1., 3., 4. })
				.mean();
		Assert.assertEquals(8. / 3., mean.apply(""), 0.);

		SymbolDouble<Object> emptyMean = SymbolArrayDoublePrimitive.constant(new double[] {})
				.mean();
		Assert.assertEquals(0., emptyMean.apply(""), 0.);
	}

}
