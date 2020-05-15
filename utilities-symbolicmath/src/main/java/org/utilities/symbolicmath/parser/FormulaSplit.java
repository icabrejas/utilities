package org.utilities.symbolicmath.parser;

public class FormulaSplit {

	public static FormulaSplit split(String formula, int index) {
		String left = formula.substring(0, index)
				.trim();
		char operator = formula.charAt(index);
		String right = formula.substring(index + 1)
				.trim();
		return new FormulaSplit(left, operator, right);
	}

	public final String left;
	public final char operator;
	public final String right;

	public FormulaSplit(String left, char operator, String right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

}
