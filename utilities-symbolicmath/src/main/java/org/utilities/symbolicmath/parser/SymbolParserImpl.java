package org.utilities.symbolicmath.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.symbolicmath.store.SymbolStore;
import org.utilities.symbolicmath.store.rolling.RollingUtils;
import org.utilities.symbolicmath.store.rolling.SummarizerDouble;
import org.utilities.symbolicmath.store.symbol.Lag;
import org.utilities.symbolicmath.store.symbol.Value;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.SymbolDouble;
import org.utilities.symbolicmath.symbol.array.SymbolArrayDoublePrimitive;

public class SymbolParserImpl {

	public static final String LAG = "lag";
	public static final String ROLL_MEAN = "rollMean";

	public static final List<Character> ALGEBRAIC_ADD = Arrays.asList('+', '-');
	public static final List<Character> ALGEBRAIC_MULT = Arrays.asList('*', '/', ':');

	public static <T> Symbol<SymbolStore<T>, ?> parse(String formula) {
		return parse(formula, (String formula_) -> new Value<T>(formula_.trim()), x -> (Double) x);
	}

	@SuppressWarnings("unchecked")
	public static <T> SymbolDouble<SymbolStore<T>> parseAsDouble(String formula) {
		Symbol<SymbolStore<T>, ?> symbol = parse(formula);
		return ((Symbol<SymbolStore<T>, Double>) symbol).apply(SymbolDouble::as);
	}

	public static <T> Symbol<SymbolStore<T>, ?> parse(String formula,
			Function<String, Symbol<SymbolStore<T>, ?>> defaultParser, Function<T, Double> parseDouble) {
		int index;
		if (formula.startsWith("(") && formula.endsWith(")")) {
			return parse(formula.substring(1, formula.length() - 1));
		} else if (formula.startsWith("-")) {
			return compose(formula.substring(1)
					.trim(), SymbolDouble::minus);
		} else if (-1 < (index = findLastUnwrappedSymbol(formula, ALGEBRAIC_ADD, ALGEBRAIC_MULT))) {
			FormulaSplit formulaSplit = FormulaSplit.split(formula, index);
			return compose(formulaSplit.left, formulaSplit.right, algebraicOperator(formulaSplit.operator));
		} else {
			if (formula.startsWith(LAG)) {
				return lag(formula);
			} else if (formula.startsWith(ROLL_MEAN)) {
				return rollSummary(formula, parseDouble, SymbolArrayDoublePrimitive::mean);
			} else {
				return defaultParser.apply(formula);
			}
		}
	}

	private static <T> Symbol<SymbolStore<T>, ?> lag(String formula) {
		ContentSplit content = new ContentSplit(formula);
		String label = content.getString(0);
		int lag = content.length() == 1 ? 1 : content.getInt(1);
		return new Lag<>(label, lag);
	}

	private static <T> Symbol<SymbolStore<T>, Double> rollSummary(String formula, Function<T, Double> parseDouble,
			SummarizerDouble<T> summarizer) {
		ContentSplit content = new ContentSplit(formula);
		String label = content.getString(0);
		int window = content.getInt(1);
		boolean nullOmit = content.length() == 2 ? nullOmit = true : content.getBoolean(2);
		return RollingUtils.summarize(label, window, nullOmit, parseDouble, summarizer);
	}

	public static <T> BiFunction<SymbolDouble<SymbolStore<T>>, SymbolDouble<SymbolStore<T>>, SymbolDouble<SymbolStore<T>>> algebraicOperator(
			char symbol) {
		switch (symbol) {
		case '+':
			return SymbolDouble::add;
		case '-':
			return SymbolDouble::subtract;
		case '*':
			return SymbolDouble::multiply;
		case '/':
		case ':':
			return SymbolDouble::divide;
		default:
			return null;
		}
	}

	public static <T> SymbolDouble<SymbolStore<T>> compose(String formula,
			Function<SymbolDouble<SymbolStore<T>>, SymbolDouble<SymbolStore<T>>> func) {
		return func.apply(parseAsDouble(formula));
	}

	public static <T> SymbolDouble<SymbolStore<T>> compose(String left, String right,
			BiFunction<SymbolDouble<SymbolStore<T>>, SymbolDouble<SymbolStore<T>>, SymbolDouble<SymbolStore<T>>> func) {
		return func.apply(parseAsDouble(left), parseAsDouble(right));
	}

	public static int findLastUnwrappedSymbol(String formula, Collection<Character> first,
			Collection<Character> second) {
		int index;
		if (-1 < (index = findLastUnwrappedSymbol(formula, first))) {
			return index;
		} else if (-1 < (index = findLastUnwrappedSymbol(formula, second))) {
			return index;
		} else {
			return index;
		}
	}

	public static int findLastUnwrappedSymbol(String formula, Collection<Character> symbols) {
		int level = 0;
		char[] chars = formula.toCharArray();
		for (int i = chars.length - 1; 0 <= i; i--) {
			char symbol = chars[i];
			if (')' == symbol) {
				level++;
			} else if ('(' == symbol) {
				level--;
			} else if (level == 0 && symbols.contains(symbol)) {
				return i;
			}
		}
		return -1;
	}

}
