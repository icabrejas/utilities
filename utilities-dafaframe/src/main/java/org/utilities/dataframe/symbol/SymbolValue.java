package org.utilities.dataframe.symbol;

import java.time.Instant;

import org.utilities.dataframe.dataentry.DataEntry;
import org.utilities.dataframe.dataentry.DataEntryImpl;
import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.mutate.Mutation;
import org.utilities.symbolicmath.symbol.BooleanSymbol;
import org.utilities.symbolicmath.symbol.DoubleSymbol;
import org.utilities.symbolicmath.symbol.FloatSymbol;
import org.utilities.symbolicmath.symbol.IntSymbol;
import org.utilities.symbolicmath.symbol.LongSymbol;
import org.utilities.symbolicmath.symbol.StringSymbol;
import org.utilities.symbolicmath.symbol.Symbol;
import org.utilities.symbolicmath.symbol.InstantSymbol;

public interface SymbolValue extends Symbol<DataEntry, DataValue> {

	public static StringSymbol<DataEntry> getString(String name) {
		return entry -> entry.getString(name);
	}

	public static IntSymbol<DataEntry> getInt(String name) {
		return entry -> entry.getInt(name);
	}

	public static LongSymbol<DataEntry> getLong(String name) {
		return entry -> entry.getLong(name);
	}

	public static FloatSymbol<DataEntry> getFloat(String name) {
		return entry -> entry.getFloat(name);
	}

	public static DoubleSymbol<DataEntry> getDouble(String name) {
		return entry -> entry.getDouble(name);
	}

	public static BooleanSymbol<DataEntry> getBoolean(String name) {
		return entry -> entry.getBoolean(name);
	}

	public static InstantSymbol<DataEntry> getInstant(String name) {
		return entry -> Instant.ofEpochMilli(entry.getLong(name));
	}

	public static void main(String[] args) {

		DataEntryImpl entry = new DataEntryImpl();
		entry.put("VAR1", -10.2);
		entry.put("VAR2", 2);
		entry.put("VAR3", 5.1);

		DoubleSymbol<DataEntry> symbol = SymbolValue.getDouble("VAR1")
				.abs()
				.add(SymbolValue.getDouble("VAR2")
						.pow(2));
		Mutation mutation = SymbolMutation.toDouble("IND1", symbol);

		// TODO
		// Mutation<String> mutation = DoubleSymbol.dataValue("VAR1")
		// .abs()
		// .add(SymbolValue.dataValue("VAR2")
		// .pow(2))
		// .mutation("IND1");

		System.out.println(entry);
		System.out.println(entry.mutate(mutation));

	}

}
