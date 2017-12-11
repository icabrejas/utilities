package org.utilities.core.dataframe.entry.mutate;

import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.value.DataValue;
import org.utilities.core.dataframe.entry.value.DateDataValue;
import org.utilities.core.dataframe.entry.value.DoubleDataValue;
import org.utilities.core.dataframe.entry.value.FloatDataValue;
import org.utilities.core.dataframe.entry.value.IntDataValue;
import org.utilities.core.dataframe.entry.value.LongDataValue;
import org.utilities.core.dataframe.entry.value.StringDataValue;
import org.utilities.core.lang.UtilitiesNumber;

public interface Mutation extends Function<DataEntry, DataValue> {

	String getName();

	public static MutationImpl dataEntryToDataValue(String name, Function<DataEntry, DataValue> mutation) {
		return new MutationImpl(name, mutation);
	}

	public static MutationImpl dataValueToDataValue(String name, String x, Function<DataValue, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.get(x)));
	}

	public static MutationImpl dataValueToString(String name, String x, Function<DataValue, String> mutation) {
		return dataValueToDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl dataValueToInt(String name, String x, Function<DataValue, Integer> mutation) {
		return dataValueToDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl dataValueToLong(String name, String x, Function<DataValue, Long> mutation) {
		return dataValueToDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl dataValueToFloat(String name, String x, Function<DataValue, Float> mutation) {
		return dataValueToDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl dataValueToDouble(String name, String x, Function<DataValue, Double> mutation) {
		return dataValueToDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl dataValueToDate(String name, String x, Function<DataValue, Date> mutation) {
		return dataValueToDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl stringToDataValue(String name, String x, Function<String, DataValue> mutation) {
		return dataValueToDataValue(name, x, mutation.compose(DataValue::stringValue));
	}

	public static MutationImpl stringToString(String name, String x, Function<String, String> mutation) {
		return stringToDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl stringToInt(String name, String x, Function<String, Integer> mutation) {
		return stringToDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl stringToLong(String name, String x, Function<String, Long> mutation) {
		return stringToDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl stringToFloat(String name, String x, Function<String, Float> mutation) {
		return stringToDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl stringToDouble(String name, String x, Function<String, Double> mutation) {
		return stringToDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl stringToDate(String name, String x, Function<String, Date> mutation) {
		return stringToDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl intToDataValue(String name, String x, Function<Integer, DataValue> mutation) {
		return dataValueToDataValue(name, x, mutation.compose(DataValue::intValue));
	}

	public static MutationImpl intToString(String name, String x, Function<Integer, String> mutation) {
		return intToDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl intToInt(String name, String x, Function<Integer, Integer> mutation) {
		return intToDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl intToLong(String name, String x, Function<Integer, Long> mutation) {
		return intToDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl intToFloat(String name, String x, Function<Integer, Float> mutation) {
		return intToDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl intToDouble(String name, String x, Function<Integer, Double> mutation) {
		return intToDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl intToDate(String name, String x, Function<Integer, Date> mutation) {
		return intToDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl longToDataValue(String name, String x, Function<Long, DataValue> mutation) {
		return dataValueToDataValue(name, x, mutation.compose(DataValue::longValue));
	}

	public static MutationImpl longToString(String name, String x, Function<Long, String> mutation) {
		return longToDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl longToInt(String name, String x, Function<Long, Integer> mutation) {
		return longToDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl longToLong(String name, String x, Function<Long, Long> mutation) {
		return longToDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl longToFloat(String name, String x, Function<Long, Float> mutation) {
		return longToDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl longToDouble(String name, String x, Function<Long, Double> mutation) {
		return longToDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl longToDate(String name, String x, Function<Long, Date> mutation) {
		return longToDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl floatToDataValue(String name, String x, Function<Float, DataValue> mutation) {
		return dataValueToDataValue(name, x, mutation.compose(DataValue::floatValue));
	}

	public static MutationImpl floatToString(String name, String x, Function<Float, String> mutation) {
		return floatToDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl floatToInt(String name, String x, Function<Float, Integer> mutation) {
		return floatToDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl floatToLong(String name, String x, Function<Float, Long> mutation) {
		return floatToDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl floatToFloat(String name, String x, Function<Float, Float> mutation) {
		return floatToDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl floatToDouble(String name, String x, Function<Float, Double> mutation) {
		return floatToDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl floatToDate(String name, String x, Function<Float, Date> mutation) {
		return floatToDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl doubleToDataValue(String name, String x, Function<Double, DataValue> mutation) {
		return dataValueToDataValue(name, x, mutation.compose(DataValue::doubleValue));
	}

	public static MutationImpl doubleToString(String name, String x, Function<Double, String> mutation) {
		return doubleToDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl doubleToInt(String name, String x, Function<Double, Integer> mutation) {
		return doubleToDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl doubleToLong(String name, String x, Function<Double, Long> mutation) {
		return doubleToDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl doubleToFloat(String name, String x, Function<Double, Float> mutation) {
		return doubleToDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl doubleToDouble(String name, String x, Function<Double, Double> mutation) {
		return doubleToDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl doubleToDate(String name, String x, Function<Double, Date> mutation) {
		return doubleToDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl dateToDataValue(String name, String x, Function<Date, DataValue> mutation) {
		return dataValueToDataValue(name, x, mutation.compose(DataValue::dateValue));
	}

	public static MutationImpl dateToString(String name, String x, Function<Date, String> mutation) {
		return dateToDataValue(name, x, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl dateToInt(String name, String x, Function<Date, Integer> mutation) {
		return dateToDataValue(name, x, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl dateToLong(String name, String x, Function<Date, Long> mutation) {
		return dateToDataValue(name, x, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl dateToFloat(String name, String x, Function<Date, Float> mutation) {
		return dateToDataValue(name, x, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl dateToDouble(String name, String x, Function<Date, Double> mutation) {
		return dateToDataValue(name, x, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl dateToDate(String name, String x, Function<Date, Date> mutation) {
		return dateToDataValue(name, x, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl dataValueToDataValue(String name, String x, String y,
			BiFunction<DataValue, DataValue, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.get(x), entry.get(y)));
	}

	public static MutationImpl dataValueToString(String name, String x, String y,
			BiFunction<DataValue, DataValue, String> mutation) {
		return dataValueToDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl dataValueToInt(String name, String x, String y,
			BiFunction<DataValue, DataValue, Integer> mutation) {
		return dataValueToDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl dataValueToLong(String name, String x, String y,
			BiFunction<DataValue, DataValue, Long> mutation) {
		return dataValueToDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl dataValueToFloat(String name, String x, String y,
			BiFunction<DataValue, DataValue, Float> mutation) {
		return dataValueToDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl dataValueToDouble(String name, String x, String y,
			BiFunction<DataValue, DataValue, Double> mutation) {
		return dataValueToDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl dataValueToDate(String name, String x, String y,
			BiFunction<DataValue, DataValue, Date> mutation) {
		return dataValueToDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl stringToDataValue(String name, String x, String y,
			BiFunction<String, String, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.getString(x), entry.getString(y)));
	}

	public static MutationImpl stringToString(String name, String x, String y,
			BiFunction<String, String, String> mutation) {
		return stringToDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl stringToInt(String name, String x, String y,
			BiFunction<String, String, Integer> mutation) {
		return stringToDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl stringToLong(String name, String x, String y,
			BiFunction<String, String, Long> mutation) {
		return stringToDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl stringToFloat(String name, String x, String y,
			BiFunction<String, String, Float> mutation) {
		return stringToDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl stringToDouble(String name, String x, String y,
			BiFunction<String, String, Double> mutation) {
		return stringToDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl stringToDate(String name, String x, String y,
			BiFunction<String, String, Date> mutation) {
		return stringToDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl intToDataValue(String name, String x, String y,
			BiFunction<Integer, Integer, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.getInt(x), entry.getInt(y)));
	}

	public static MutationImpl intToString(String name, String x, String y,
			BiFunction<Integer, Integer, String> mutation) {
		return intToDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl intToInt(String name, String x, String y,
			BiFunction<Integer, Integer, Integer> mutation) {
		return intToDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl intToLong(String name, String x, String y, BiFunction<Integer, Integer, Long> mutation) {
		return intToDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl intToFloat(String name, String x, String y,
			BiFunction<Integer, Integer, Float> mutation) {
		return intToDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl intToDouble(String name, String x, String y,
			BiFunction<Integer, Integer, Double> mutation) {
		return intToDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl intToDate(String name, String x, String y, BiFunction<Integer, Integer, Date> mutation) {
		return intToDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl longToDataValue(String name, String x, String y,
			BiFunction<Long, Long, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.getLong(x), entry.getLong(y)));
	}

	public static MutationImpl longToString(String name, String x, String y, BiFunction<Long, Long, String> mutation) {
		return longToDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl longToInt(String name, String x, String y, BiFunction<Long, Long, Integer> mutation) {
		return longToDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl longToLong(String name, String x, String y, BiFunction<Long, Long, Long> mutation) {
		return longToDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl longToFloat(String name, String x, String y, BiFunction<Long, Long, Float> mutation) {
		return longToDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl longToDouble(String name, String x, String y, BiFunction<Long, Long, Double> mutation) {
		return longToDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl longToDate(String name, String x, String y, BiFunction<Long, Long, Date> mutation) {
		return longToDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl floatToDataValue(String name, String x, String y,
			BiFunction<Float, Float, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.getFloat(x), entry.getFloat(y)));
	}

	public static MutationImpl floatToString(String name, String x, String y,
			BiFunction<Float, Float, String> mutation) {
		return floatToDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl floatToInt(String name, String x, String y, BiFunction<Float, Float, Integer> mutation) {
		return floatToDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl floatToLong(String name, String x, String y, BiFunction<Float, Float, Long> mutation) {
		return floatToDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl floatToFloat(String name, String x, String y, BiFunction<Float, Float, Float> mutation) {
		return floatToDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl floatToDouble(String name, String x, String y,
			BiFunction<Float, Float, Double> mutation) {
		return floatToDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl floatToDate(String name, String x, String y, BiFunction<Float, Float, Date> mutation) {
		return floatToDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl doubleToDataValue(String name, String x, String y,
			BiFunction<Double, Double, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.getDouble(x), entry.getDouble(y)));
	}

	public static MutationImpl doubleToString(String name, String x, String y,
			BiFunction<Double, Double, String> mutation) {
		return doubleToDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl doubleToInt(String name, String x, String y,
			BiFunction<Double, Double, Integer> mutation) {
		return doubleToDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl doubleToLong(String name, String x, String y,
			BiFunction<Double, Double, Long> mutation) {
		return doubleToDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl doubleToFloat(String name, String x, String y,
			BiFunction<Double, Double, Float> mutation) {
		return doubleToDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl doubleToDouble(String name, String x, String y,
			BiFunction<Double, Double, Double> mutation) {
		return doubleToDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl doubleToDate(String name, String x, String y,
			BiFunction<Double, Double, Date> mutation) {
		return doubleToDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl dateToDataValue(String name, String x, String y,
			BiFunction<Date, Date, DataValue> mutation) {
		return dataEntryToDataValue(name, entry -> mutation.apply(entry.getDate(x), entry.getDate(y)));
	}

	public static MutationImpl dateToString(String name, String x, String y, BiFunction<Date, Date, String> mutation) {
		return dateToDataValue(name, x, y, mutation.andThen(StringDataValue::new));
	}

	public static MutationImpl dateToInt(String name, String x, String y, BiFunction<Date, Date, Integer> mutation) {
		return dateToDataValue(name, x, y, mutation.andThen(IntDataValue::new));
	}

	public static MutationImpl dateToLong(String name, String x, String y, BiFunction<Date, Date, Long> mutation) {
		return dateToDataValue(name, x, y, mutation.andThen(LongDataValue::new));
	}

	public static MutationImpl dateToFloat(String name, String x, String y, BiFunction<Date, Date, Float> mutation) {
		return dateToDataValue(name, x, y, mutation.andThen(FloatDataValue::new));
	}

	public static MutationImpl dateToDouble(String name, String x, String y, BiFunction<Date, Date, Double> mutation) {
		return dateToDataValue(name, x, y, mutation.andThen(DoubleDataValue::new));
	}

	public static MutationImpl dateToDate(String name, String x, String y, BiFunction<Date, Date, Date> mutation) {
		return dateToDataValue(name, x, y, mutation.andThen(DateDataValue::new));
	}

	public static MutationImpl sumInt(String name, String x, String y) {
		return intToInt(name, x, y, UtilitiesNumber::sum);
	}

	public static MutationImpl sumLong(String name, String x, String y) {
		return longToLong(name, x, y, UtilitiesNumber::sum);
	}

	public static MutationImpl sumFloat(String name, String x, String y) {
		return floatToFloat(name, x, y, UtilitiesNumber::sum);
	}

	public static MutationImpl sumDouble(String name, String x, String y) {
		return doubleToDouble(name, x, y, UtilitiesNumber::sum);
	}

	public static MutationImpl multInt(String name, String x, String y) {
		return intToInt(name, x, y, UtilitiesNumber::mult);
	}

	public static MutationImpl multLong(String name, String x, String y) {
		return longToLong(name, x, y, UtilitiesNumber::mult);
	}

	public static MutationImpl multFloat(String name, String x, String y) {
		return floatToFloat(name, x, y, UtilitiesNumber::mult);
	}

	public static MutationImpl multDouble(String name, String x, String y) {
		return doubleToDouble(name, x, y, UtilitiesNumber::mult);
	}

	public static MutationImpl minInt(String name, String x, String y) {
		return intToInt(name, x, y, UtilitiesNumber::min);
	}

	public static MutationImpl minLong(String name, String x, String y) {
		return longToLong(name, x, y, UtilitiesNumber::min);
	}

	public static MutationImpl minFloat(String name, String x, String y) {
		return floatToFloat(name, x, y, UtilitiesNumber::min);
	}

	public static MutationImpl minDouble(String name, String x, String y) {
		return doubleToDouble(name, x, y, UtilitiesNumber::min);
	}

	public static MutationImpl maxInt(String name, String x, String y) {
		return intToInt(name, x, y, UtilitiesNumber::max);
	}

	public static MutationImpl maxLong(String name, String x, String y) {
		return longToLong(name, x, y, UtilitiesNumber::max);
	}

	public static MutationImpl maxFloat(String name, String x, String y) {
		return floatToFloat(name, x, y, UtilitiesNumber::max);
	}

	public static MutationImpl maxDouble(String name, String x, String y) {
		return doubleToDouble(name, x, y, UtilitiesNumber::max);
	}

}