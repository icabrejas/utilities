package org.utilities.core.lang.iterable.map;

import org.junit.Test;

import junit.framework.TestCase;

public class ParserTest extends TestCase {

	@Test
	public void test() {
		A a = new A();
		a.setFieldA("123");
		B b = Parser.newInstance(A.class, B.class)
				.parseInt("fieldB", A::getFieldA)
				.map(a);
		assertEquals(new Integer(123), b.getFieldB());
	}

	public static class A {

		private String fieldA;

		public String getFieldA() {
			return fieldA;
		}

		public void setFieldA(String fieldA) {
			this.fieldA = fieldA;
		}

	}

	public static class B {

		private Integer fieldB;

		public Integer getFieldB() {
			return fieldB;
		}

		public void setFieldB(Integer fieldB) {
			this.fieldB = fieldB;
		}

	}

}
