package org.utilities.dataframe.cell;

import java.time.Instant;

//TODO work with not null boolean, int, long, float, double
public interface DFCell extends Comparable<DFCell> {

	String stringValue();

	Integer intValue();

	Long longValue();

	Float floatValue();

	Double doubleValue();

	Instant instantValue();

	Boolean booleanValue();

	default DFCellString parseString() {
		return new DFCellString(stringValue());
	}

	default DFCellInt parseInt() {
		return new DFCellInt(intValue());
	}

	default DFCellLong parseLong() {
		return new DFCellLong(longValue());
	}

	default DFCellFloat parseFloat() {
		return new DFCellFloat(floatValue());
	}

	default DFCellDouble parseDouble() {
		return new DFCellDouble(doubleValue());
	}

	default DFCellInstant parseDate() {
		return new DFCellInstant(instantValue());
	}

	default DFCellBoolean parseBoolean() {
		return new DFCellBoolean(booleanValue());
	}

	default boolean isLowerThan(DFCell value) {
		return this.compareTo(value) < 0;
	}

	default boolean isHigherThan(DFCell value) {
		return this.compareTo(value) > 0;
	}

}
