package org.utilities.dataframe.symbol;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.datavalue.BooleanDataValue;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.datavalue.DoubleDataValue;
import org.utilities.dataframe.datavalue.FloatDataValue;
import org.utilities.dataframe.datavalue.IntDataValue;
import org.utilities.dataframe.datavalue.LongDataValue;
import org.utilities.dataframe.datavalue.StringDataValue;
import org.utilities.dataframe.mutate.Mutation;
import org.utilities.dataframe.mutate.MutationImpl;
import org.utilities.symbolicmath.symbol.Symbol;

public class SymbolMutation {

	public static Mutation toDataValue(String name, Symbol<DataEntry, DataValue> symbol) {
		return MutationImpl.create(name, symbol::apply);
	}

	public static Mutation toString(String name, Symbol<DataEntry, String> symbol) {
		return toDataValue(name, symbol.andThen(StringDataValue::new));
	}

	public static Mutation toInt(String name, Symbol<DataEntry, Integer> symbol) {
		return toDataValue(name, symbol.andThen(IntDataValue::new));
	}

	public static Mutation toLong(String name, Symbol<DataEntry, Long> symbol) {
		return toDataValue(name, symbol.andThen(LongDataValue::new));
	}

	public static Mutation toFloat(String name, Symbol<DataEntry, Float> symbol) {
		return toDataValue(name, symbol.andThen(FloatDataValue::new));
	}

	public static Mutation toDouble(String name, Symbol<DataEntry, Double> symbol) {
		return toDataValue(name, symbol.andThen(DoubleDataValue::new));
	}

	public static Mutation toBoolean(String name, Symbol<DataEntry, Boolean> symbol) {
		return toDataValue(name, symbol.andThen(BooleanDataValue::new));
	}

}
