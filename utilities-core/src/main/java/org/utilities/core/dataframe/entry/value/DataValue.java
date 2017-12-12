package org.utilities.core.dataframe.entry.value;

import java.util.Date;

import org.utilities.core.time.UtilitiesTime;

public interface DataValue extends Comparable<DataValue> {

	String stringValue();

	Integer intValue();

	Long longValue();

	Float floatValue();

	Double doubleValue();

	Date dateValue();

	default StringDataValue parseString() {
		return new StringDataValue(stringValue());
	}

	default IntDataValue parseInt() {
		return new IntDataValue(intValue());
	}

	default LongDataValue parseLong() {
		return new LongDataValue(longValue());
	}

	default FloatDataValue parseFloat() {
		return new FloatDataValue(floatValue());
	}

	default DoubleDataValue parseDouble() {
		return new DoubleDataValue(doubleValue());
	}

	default DateDataValue parseDate() {
		return new DateDataValue(dateValue());
	}

	default IntDataValue add(int x) {
		return new IntDataValue(intValue() + x);
	}

	default FloatDataValue add(float x) {
		return new FloatDataValue(floatValue() + x);
	}

	default DoubleDataValue add(double x) {
		return new DoubleDataValue(doubleValue() + x);
	}

	default IntDataValue subtract(int x) {
		return new IntDataValue(intValue() + x);
	}

	default FloatDataValue subtract(float x) {
		return new FloatDataValue(floatValue() + x);
	}

	default DoubleDataValue subtract(double x) {
		return new DoubleDataValue(doubleValue() + x);
	}

	default IntDataValue multiply(int x) {
		return new IntDataValue(x * intValue());
	}

	default FloatDataValue multiply(float x) {
		return new FloatDataValue(x * floatValue());
	}

	default DoubleDataValue multiply(double x) {
		return new DoubleDataValue(x * doubleValue());
	}

	default IntDataValue divide(int x) {
		return new IntDataValue(intValue() / x);
	}

	default FloatDataValue divide(float x) {
		return new FloatDataValue(floatValue() / x);
	}

	default DoubleDataValue divide(double x) {
		return new DoubleDataValue(doubleValue() / x);
	}

	default DateDataValue add(int field, int amount) {
		long millis = UtilitiesTime.add(longValue(), field, amount);
		return new DateDataValue(new Date(millis));
	}

}
