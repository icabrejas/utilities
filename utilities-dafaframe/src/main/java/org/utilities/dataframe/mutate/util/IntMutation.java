package org.utilities.dataframe.mutate.util;

import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.dataframe.datavalue.DataValue;
import org.utilities.dataframe.datavalue.DateDataValue;
import org.utilities.dataframe.datavalue.DoubleDataValue;
import org.utilities.dataframe.datavalue.FloatDataValue;
import org.utilities.dataframe.datavalue.IntDataValue;
import org.utilities.dataframe.datavalue.LongDataValue;
import org.utilities.dataframe.datavalue.StringDataValue;
import org.utilities.dataframe.mutate.MutationImpl;

public class IntMutation {

	public static MutationImpl toDataValue(String name, String x, Function<Integer, DataValue> mutation) {
		return DataValueMutation.toDataValue(name, x, mutation.compose(DataValue::intValue));
	}

	public static MutationImpl toString(String name, String x, Function<Integer, String> mutation) {
		return toDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl toInt(String name, String x, Function<Integer, Integer> mutation) {
		return toDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl toLong(String name, String x, Function<Integer, Long> mutation) {
		return toDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl toFloat(String name, String x, Function<Integer, Float> mutation) {
		return toDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl toDouble(String name, String x, Function<Integer, Double> mutation) {
		return toDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl toDate(String name, String x, Function<Integer, Date> mutation) {
		return toDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl toDataValue(String name, String x, String y,
			BiFunction<Integer, Integer, DataValue> mutation) {
		return MutationImpl.create(name, entry -> mutation.apply(entry.getInt(x), entry.getInt(y)));
	}

	public static MutationImpl toString(String name, String x, String y,
			BiFunction<Integer, Integer, String> mutation) {
		return toDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl toInt(String name, String x, String y, BiFunction<Integer, Integer, Integer> mutation) {
		return toDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl toLong(String name, String x, String y, BiFunction<Integer, Integer, Long> mutation) {
		return toDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl toFloat(String name, String x, String y, BiFunction<Integer, Integer, Float> mutation) {
		return toDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl toDouble(String name, String x, String y,
			BiFunction<Integer, Integer, Double> mutation) {
		return toDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl toDate(String name, String x, String y, BiFunction<Integer, Integer, Date> mutation) {
		return toDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}
}
