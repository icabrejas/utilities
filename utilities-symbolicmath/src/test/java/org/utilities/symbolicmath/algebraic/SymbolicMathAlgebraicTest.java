package org.utilities.symbolicmath.algebraic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.utilities.symbolicmath.algebra.Absolute;
import org.utilities.symbolicmath.algebra.Addition;
import org.utilities.symbolicmath.algebra.Division;
import org.utilities.symbolicmath.algebra.Exponential;
import org.utilities.symbolicmath.algebra.Multiplication;
import org.utilities.symbolicmath.algebra.Subtraction;
import org.utilities.symbolicmath.conditional.If;
import org.utilities.symbolicmath.logical.Greater;
import org.utilities.symbolicmath.logical.Lower;
import org.utilities.symbolicmath.value.Constant;
import org.utilities.symbolicmath.value.Value;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class SymbolicMathAlgebraicTest extends TestCase {

	private Value<Map<String, Double>, Double> VAR1 = variable("VAR1");
	private Value<Map<String, Double>, Double> VAR2 = variable("VAR2");
	private Value<Map<String, Double>, Double> VAR3 = variable("VAR3");

	@Test
	public void testAdd23() {
		Map<String, Double> store = store(2, 3);
		Value<Map<String, Double>, Double> formula = new Addition<>(VAR1, VAR2);
		assertEquals(5., formula.apply(store));
	}

	@Test
	public void testAdd24() {
		Map<String, Double> store = store(2, 4);
		Value<Map<String, Double>, Double> formula = new Addition<>(VAR1, VAR2);
		assertEquals(6., formula.apply(store));
	}

	@Test
	public void testSubtract23() {
		Map<String, Double> store = store(2, 3);
		Value<Map<String, Double>, Double> formula = new Subtraction<>(VAR1, VAR2);
		assertEquals(-1., formula.apply(store));
	}

	@Test
	public void testSubtract24() {
		Map<String, Double> store = store(2, 4);
		Value<Map<String, Double>, Double> formula = new Subtraction<>(VAR1, VAR2);
		assertEquals(-2., formula.apply(store));
	}

	@Test
	public void testMultiply23() {
		Map<String, Double> store = store(2, 3);
		Value<Map<String, Double>, Double> formula = new Multiplication<>(VAR1, VAR2);
		assertEquals(6., formula.apply(store));
	}

	@Test
	public void testMultiply24() {
		Map<String, Double> store = store(2, 4);
		Value<Map<String, Double>, Double> formula = new Multiplication<>(VAR1, VAR2);
		assertEquals(8., formula.apply(store));
	}

	@Test
	public void testDivide23() {
		Map<String, Double> store = store(2, 3);
		Value<Map<String, Double>, Double> formula = new Division<>(VAR1, VAR2);
		assertEquals(0.66, formula.apply(store), 1E-2);
	}

	@Test
	public void testDivide24() {
		Map<String, Double> store = store(2, 4);
		Value<Map<String, Double>, Double> formula = new Division<>(VAR1, VAR2);
		assertEquals(0.5, formula.apply(store));
	}

	@Test
	public void testAbsolute2() {
		Map<String, Double> store = store(2);
		Value<Map<String, Double>, Double> formula = new Absolute<>(VAR1);
		assertEquals(2., formula.apply(store));
	}

	@Test
	public void testAbsoluteMinus3() {
		Map<String, Double> store = store(-3);
		Value<Map<String, Double>, Double> formula = new Absolute<>(VAR1);
		assertEquals(3., formula.apply(store));
	}

	@Test
	public void testAbsoluteSubtract43() {
		Map<String, Double> store = store(4, 3);
		Value<Map<String, Double>, Double> formula = new Absolute<>(new Subtraction<>(VAR1, VAR2));
		assertEquals(1., formula.apply(store));
	}

	@Test
	public void testExponetial0() {
		Map<String, Double> store = store(0);
		Value<Map<String, Double>, Double> formula = new Exponential<>(VAR1);
		assertEquals(1., formula.apply(store));
	}

	@Test
	public void testExponetial1() {
		Map<String, Double> store = store(1);
		Value<Map<String, Double>, Double> formula = new Exponential<>(VAR1);
		assertEquals(Math.E, formula.apply(store));
	}

	@Test
	public void testIf() {
		Map<String, Double> store = store(4, 5, 1);
		Value<Map<String, Double>, Double> formula1 = new If<>(new Greater<>(VAR2, VAR1, true), VAR3);
		assertEquals(1., formula1.apply(store));
		Value<Map<String, Double>, Double> formula2 = new If<>(new Lower<>(VAR2, VAR1, true), VAR3);
		assertNull(formula2.apply(store));
		Value<Map<String, Double>, Double> formula3 = new If<>(new Lower<>(VAR2, new Constant<>(10.), true), VAR1);
		assertEquals(4., formula3.apply(store));
	}

	private Value<Map<String, Double>, Double> variable(String variable) {
		return store -> store.get(variable);
	}

	private Map<String, Double> store(double... values) {
		Map<String, Double> store = new HashMap<>();
		for (int i = 0; i < values.length; i++) {
			store.put("VAR" + (i + 1), values[i]);
		}
		return store;
	}

}
