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

public class DataValueMutation {

	public static MutationImpl toDataValue(String name, String x, Function<DataValue, DataValue> mutation) {
		return MutationImpl.create(name, entry -> mutation.apply(entry.get(x)));
	}

	public static MutationImpl toString(String name, String x, Function<DataValue, String> mutation) {
		return toDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl toInt(String name, String x, Function<DataValue, Integer> mutation) {
		return toDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl toLong(String name, String x, Function<DataValue, Long> mutation) {
		return toDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl toFloat(String name, String x, Function<DataValue, Float> mutation) {
		return toDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl toDouble(String name, String x, Function<DataValue, Double> mutation) {
		return toDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl toDate(String name, String x, Function<DataValue, Date> mutation) {
		return toDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl toDataValue(String name, String x, String y,
			BiFunction<DataValue, DataValue, DataValue> mutation) {
		return MutationImpl.create(name, entry -> mutation.apply(entry.get(x), entry.get(y)));
	}

	public static MutationImpl toString(String name, String x, String y,
			BiFunction<DataValue, DataValue, String> mutation) {
		return toDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl toInt(String name, String x, String y,
			BiFunction<DataValue, DataValue, Integer> mutation) {
		return toDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl toLong(String name, String x, String y,
			BiFunction<DataValue, DataValue, Long> mutation) {
		return toDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl toFloat(String name, String x, String y,
			BiFunction<DataValue, DataValue, Float> mutation) {
		return toDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl toDouble(String name, String x, String y,
			BiFunction<DataValue, DataValue, Double> mutation) {
		return toDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl toDate(String name, String x, String y,
			BiFunction<DataValue, DataValue, Date> mutation) {
		return toDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

}
