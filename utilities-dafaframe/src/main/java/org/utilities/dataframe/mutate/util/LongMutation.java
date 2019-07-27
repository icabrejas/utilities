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

public class LongMutation {

	public static MutationImpl toDataValue(String name, String x, Function<Long, DataValue> mutation) {
		return DataValueMutation.toDataValue(name, x, mutation.compose(DataValue::longValue));
	}

	public static MutationImpl toString(String name, String x, Function<Long, String> mutation) {
		return toDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl toInt(String name, String x, Function<Long, Integer> mutation) {
		return toDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl toLong(String name, String x, Function<Long, Long> mutation) {
		return toDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl toFloat(String name, String x, Function<Long, Float> mutation) {
		return toDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl toDouble(String name, String x, Function<Long, Double> mutation) {
		return toDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl toDate(String name, String x, Function<Long, Date> mutation) {
		return toDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl toDataValue(String name, String x, String y,
			BiFunction<Long, Long, DataValue> mutation) {
		return MutationImpl.create(name, entry -> mutation.apply(entry.getLong(x), entry.getLong(y)));
	}

	public static MutationImpl toString(String name, String x, String y, BiFunction<Long, Long, String> mutation) {
		return toDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl toInt(String name, String x, String y, BiFunction<Long, Long, Integer> mutation) {
		return toDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl toLong(String name, String x, String y, BiFunction<Long, Long, Long> mutation) {
		return toDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl toFloat(String name, String x, String y, BiFunction<Long, Long, Float> mutation) {
		return toDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl toDouble(String name, String x, String y, BiFunction<Long, Long, Double> mutation) {
		return toDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl toDate(String name, String x, String y, BiFunction<Long, Long, Date> mutation) {
		return toDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

}
