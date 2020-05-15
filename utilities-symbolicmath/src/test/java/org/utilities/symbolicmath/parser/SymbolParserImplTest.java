package org.utilities.symbolicmath.parser;

import org.junit.Test;
import org.utilities.symbolicmath.store.SSLocal;
import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.symbol.Symbol;

import junit.framework.TestCase;

public class SymbolParserImplTest extends TestCase {

	@Test
	public void test_x_variable() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("x");
		assertEquals(3., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x");
		assertEquals(3., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x ");
		assertEquals(3., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x ");
		assertEquals(3., symbol.apply(store));
	}

	@Test
	public void test_y_variable() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		store.put(1, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol = SymbolParserImpl.parseAsDouble("y");
		assertEquals(4., symbol.apply(store));
	}

	@Test
	public void test_x_add_y() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		store.put(1, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("x + y");
		assertEquals(7., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x+ y");
		assertEquals(7., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x +y");
		assertEquals(7., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x+y");
		assertEquals(7., symbol.apply(store));
	}

	@Test
	public void test_x_subtract_y() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		store.put(1, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("x - y");
		assertEquals(-1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x- y");
		assertEquals(-1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x -y");
		assertEquals(-1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x-y");
		assertEquals(-1., symbol.apply(store));
	}

	@Test
	public void test_x_mult_y() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		store.put(1, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("x * y");
		assertEquals(12., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x* y");
		assertEquals(12., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x *y");
		assertEquals(12., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x*y");
		assertEquals(12., symbol.apply(store));
	}

	@Test
	public void test_x_divide_y() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		store.put(1, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("x / y");
		assertEquals(0.75, symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x/ y");
		assertEquals(0.75, symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x /y");
		assertEquals(0.75, symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x/y");
		assertEquals(0.75, symbol.apply(store));
	}

	@Test
	public void test_x_divide_y_double_point() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		store.put(1, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("x : y");
		assertEquals(0.75, symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x: y");
		assertEquals(0.75, symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x :y");
		assertEquals(0.75, symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("x:y");
		assertEquals(0.75, symbol.apply(store));
	}

	@Test
	public void test_minus_x() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 3.);
		store.put(1, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("-x");
		assertEquals(-3., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("- x");
		assertEquals(-3., symbol.apply(store));
	}

	@Test
	public void test_priority_between_add_and_mult_v1() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 0.);
		store.put(1, "y", 4.);
		store.put(1, "z", 2.);
		Symbol<SymbolStore<Double>, Double> symbol = SymbolParserImpl.parseAsDouble("x*y+z");
		assertEquals(2., symbol.apply(store));
	}

	@Test
	public void test_priority_between_add_and_mult_v2() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 0.);
		store.put(1, "y", 4.);
		store.put(1, "z", 2.);
		Symbol<SymbolStore<Double>, Double> symbol = SymbolParserImpl.parseAsDouble("z+x*y");
		assertEquals(2., symbol.apply(store));
	}

	@Test
	public void test_priority_between_add_and_mult_with_parenthesis() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.put(1, "x", 0.);
		store.put(1, "y", 4.);
		store.put(1, "z", 2.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("x*(y+z)");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x*(y+z)");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x *(y+z)");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x * (y+z)");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x * ( y+z)");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x * ( y +z)");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x * ( y + z)");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x * ( y + z )");
		assertEquals(0., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble(" x * ( y + z ) ");
		assertEquals(0., symbol.apply(store));
	}

	@Test
	public void test_lag() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.subscribe("x", 2);
		store.put(1, "x", 3.);
		store.put(2, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("y-lag(x)");
		assertEquals(1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("y-lag( x)");
		assertEquals(1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("y-lag( x )");
		assertEquals(1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("y-lag(x )");
		assertEquals(1., symbol.apply(store));
	}

	@Test
	public void test_lag_null() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.subscribe("x", 2);
		store.put(1, "x", 3.);
		store.put(3, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol = SymbolParserImpl.parseAsDouble("y-lag(x)");
		assertNull(symbol.apply(store));
	}

	@Test
	public void test_lag_two_steps() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.subscribe("x", 3);
		store.put(1, "x", 3.);
		store.put(3, "y", 4.);
		Symbol<SymbolStore<Double>, Double> symbol;
		symbol = SymbolParserImpl.parseAsDouble("y-lag(x,2)");
		assertEquals(1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("y-lag( x,2)");
		assertEquals(1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("y-lag(x ,2)");
		assertEquals(1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("y-lag(x, 2)");
		assertEquals(1., symbol.apply(store));
		symbol = SymbolParserImpl.parseAsDouble("y-lag(x,2 )");
		assertEquals(1., symbol.apply(store));
	}

	@Test
	public void test_roll_mean() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.subscribe("x", 6);

		Symbol<SymbolStore<Double>, Double> symbol = SymbolParserImpl.parseAsDouble("x-rollMean(x,4)");

		store.put(1, "x", 3.);
		assertEquals(0., symbol.apply(store));
		store.put(3, "x", 7.);
		assertEquals(2., symbol.apply(store));
		store.put(4, "x", 5.);
		assertEquals(0., symbol.apply(store));
		store.put(6, "x", 3.);
		assertEquals(-2., symbol.apply(store));
	}

	@Test
	public void test_roll_mean_nullOmit_false() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.subscribe("x", 6);

		Symbol<SymbolStore<Double>, Double> symbol = SymbolParserImpl.parseAsDouble("x-rollMean(x,3,false)");
		
		store.put(1, "x", 3.);
		assertNull(symbol.apply(store));
		store.put(2, "x", 6.);
		assertNull(symbol.apply(store));
		store.put(3, "x", 6.);
		assertEquals(1., symbol.apply(store));
		store.put(4, "x", 6.);
		assertEquals(0., symbol.apply(store));
		store.put(6, "x", 2.);
		assertNull(symbol.apply(store));
	}

	@Test
	public void test_roll_mean_nullOmit_false_format() {
		SSLocal<Double> store = new SSLocal<>(Double[]::new);
		store.subscribe("x", 6);
		
		Symbol<SymbolStore<Double>, Double> symbol = SymbolParserImpl.parseAsDouble("x - rollMean(x, 3, false)");
		
		store.put(1, "x", 3.);
		assertNull(symbol.apply(store));
		store.put(2, "x", 6.);
		assertNull(symbol.apply(store));
		store.put(3, "x", 6.);
		assertEquals(1., symbol.apply(store));
		store.put(4, "x", 6.);
		assertEquals(0., symbol.apply(store));
		store.put(6, "x", 2.);
		assertNull(symbol.apply(store));
	}

}
