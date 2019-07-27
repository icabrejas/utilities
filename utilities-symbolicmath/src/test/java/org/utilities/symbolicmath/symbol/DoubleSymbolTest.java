package org.utilities.symbolicmath.symbol;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class DoubleSymbolTest extends TestCase {

	private DoubleSymbol<Store> VAR1 = variable("VAR1");
	private DoubleSymbol<Store> VAR2 = variable("VAR2");
	private DoubleSymbol<Store> VAR3 = variable("VAR3");

	@Test
	public void testAdd23() {
		Store store = store(2, 3);
		Symbol<Store, Double> formula = VAR1.add(VAR2);
		assertEquals(5., formula.apply(store));
	}

	@Test
	public void testAdd24() {
		Store store = store(2, 4);
		assertEquals(6., VAR1.add(VAR2)
				.apply(store));
	}

	@Test
	public void testSubtract23() {
		Store store = store(2, 3);
		assertEquals(-1., VAR1.subtract(VAR2)
				.apply(store));
	}

	@Test
	public void testSubtract24() {
		Store store = store(2, 4);
		assertEquals(-2., VAR1.subtract(VAR2)
				.apply(store));
	}

	@Test
	public void testMultiply23() {
		Store store = store(2, 3);
		assertEquals(6., VAR1.multiply(VAR2)
				.apply(store));
	}

	@Test
	public void testMultiply24() {
		Store store = store(2, 4);
		assertEquals(8., VAR1.multiply(VAR2)
				.apply(store));
	}

	@Test
	public void testDivide23() {
		Store store = store(2, 3);
		assertEquals(0.66, VAR1.divide(VAR2)
				.apply(store), 1E-2);
	}

	@Test
	public void testDivide24() {
		Store store = store(2, 4);
		assertEquals(0.5, VAR1.divide(VAR2)
				.apply(store));
	}

	@Test
	public void testAbsolute2() {
		Store store = store(2);
		assertEquals(2., VAR1.abs()
				.apply(store));
	}

	@Test
	public void testAbsoluteMinus3() {
		Store store = store(-3);
		assertEquals(3., VAR1.abs()
				.apply(store));
	}

	@Test
	public void testAbsoluteSubtract43() {
		Store store = store(4, 3);
		assertEquals(1., VAR1.subtract(VAR2)
				.abs()
				.apply(store));
	}

	@Test
	public void testExponetial0() {
		Store store = store(0);
		assertEquals(1., VAR1.exp()
				.apply(store));
	}

	@Test
	public void testExponetial1() {
		Store store = store(1);
		assertEquals(Math.E, VAR1.exp()
				.apply(store));
	}

	@Test
	public void testIf() {
		Store store = store(4, 5, 1);
		assertEquals(1., VAR2.greater(true, VAR1)
				.ifElse(VAR3)
				.apply(store));
		assertNull(VAR2.lower(true, VAR1)
				.ifElse(VAR3)
				.apply(store));
		assertEquals(4., VAR2.lower(true, DoubleSymbol.constant(10.))
				.ifElse(VAR1)
				.apply(store));
	}

	private DoubleSymbol<Store> variable(String variable) {
		return store -> store.get(variable);
	}

	private Store store(double... values) {
		Store store = new Store();
		for (int i = 0; i < values.length; i++) {
			store.put("VAR" + (i + 1), values[i]);
		}
		return store;
	}

	public static class Store {

		private HashMap<String, Double> store = new HashMap<>();

		public Double put(String key, Double value) {
			return store.put(key, value);
		}

		public Double get(String key) {
			return store.get(key);
		}

	}

}
