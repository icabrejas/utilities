package org.utilities.dataframe.datavalue;

import java.util.Date;

public interface DataValue extends Comparable<DataValue> {

	String stringValue();

	Integer intValue();

	Long longValue();

	Float floatValue();

	Double doubleValue();

	Date dateValue();

	Boolean booleanValue();

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

	default BooleanDataValue parseBoolean() {
		return new BooleanDataValue(booleanValue());
	}

	default boolean isLowerThan(DataValue value) {
		return this.compareTo(value) < 0;
	}

	default boolean isHigherThan(DataValue value) {
		return this.compareTo(value) > 0;
	}

}
