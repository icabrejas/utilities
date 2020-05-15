package org.utilities.dataframe.map;

import java.time.Instant;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.utilities.core.lang.iterable.map.IPMapper;
import org.utilities.core.lang.iterable.map.IPMapperImpl;
import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.cell.DFCellBoolean;
import org.utilities.dataframe.cell.DFCellDouble;
import org.utilities.dataframe.cell.DFCellFloat;
import org.utilities.dataframe.cell.DFCellInstant;
import org.utilities.dataframe.cell.DFCellInt;
import org.utilities.dataframe.cell.DFCellLong;
import org.utilities.dataframe.cell.DFCellString;
import org.utilities.dataframe.row.DFRow;

public class DFMapUtils {

	public static class FromDFRow {

		public static IPMapper<DFRow, DFRow> toCell(String name, Function<DFRow, DFCell> mutation) {
			return new IPMapperImpl<DFRow, DFRow>(row -> row.mutate(name, mutation));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, Function<DFRow, String> mutation) {
			return toCell(name, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, Function<DFRow, Boolean> mutation) {
			return toCell(name, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, Function<DFRow, Integer> mutation) {
			return toCell(name, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, Function<DFRow, Long> mutation) {
			return toCell(name, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, Function<DFRow, Float> mutation) {
			return toCell(name, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, Function<DFRow, Double> mutation) {
			return toCell(name, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, Function<DFRow, Instant> mutation) {
			return toCell(name, mutation.andThen(DFCellInstant::new));
		}

	}

	public static class FromDFCell {

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, Function<DFCell, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.get(x)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, Function<DFCell, String> mutation) {
			return toCell(name, x, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, String x, Function<DFCell, Boolean> mutation) {
			return toCell(name, x, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, Function<DFCell, Integer> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, Function<DFCell, Long> mutation) {
			return toCell(name, x, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, Function<DFCell, Float> mutation) {
			return toCell(name, x, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, Function<DFCell, Double> mutation) {
			return toCell(name, x, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, Function<DFCell, Instant> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInstant::new));
		}

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, String y,
				BiFunction<DFCell, DFCell, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.get(x), row.get(y)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, String y,
				BiFunction<DFCell, DFCell, String> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, String y,
				BiFunction<DFCell, DFCell, Integer> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, String y,
				BiFunction<DFCell, DFCell, Long> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, String y,
				BiFunction<DFCell, DFCell, Float> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, String y,
				BiFunction<DFCell, DFCell, Double> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, String y,
				BiFunction<DFCell, DFCell, Instant> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInstant::new));
		}

	}

	public static class FromInstant {

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, Function<Instant, DFCell> mutation) {
			return FromDFCell.toCell(name, x, mutation.compose(DFCell::instantValue));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, Function<Instant, String> mutation) {
			return toCell(name, x, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, String x, Function<Instant, Boolean> mutation) {
			return toCell(name, x, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, Function<Instant, Integer> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, Function<Instant, Long> mutation) {
			return toCell(name, x, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, Function<Instant, Float> mutation) {
			return toCell(name, x, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, Function<Instant, Double> mutation) {
			return toCell(name, x, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, Function<Instant, Instant> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInstant::new));
		}

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, String y,
				BiFunction<Instant, Instant, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.getInstant(x), row.getInstant(y)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, String y,
				BiFunction<Instant, Instant, String> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, String y,
				BiFunction<Instant, Instant, Integer> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, String y,
				BiFunction<Instant, Instant, Long> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, String y,
				BiFunction<Instant, Instant, Float> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, String y,
				BiFunction<Instant, Instant, Double> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, String y,
				BiFunction<Instant, Instant, Instant> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInstant::new));
		}

	}

	public static class FromDouble {

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, Function<Double, DFCell> mutation) {
			return FromDFCell.toCell(name, x, mutation.compose(DFCell::doubleValue));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, Function<Double, String> mutation) {
			return toCell(name, x, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, String x, Function<Double, Boolean> mutation) {
			return toCell(name, x, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, Function<Double, Integer> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, Function<Double, Long> mutation) {
			return toCell(name, x, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, Function<Double, Float> mutation) {
			return toCell(name, x, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, Function<Double, Double> mutation) {
			return toCell(name, x, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, Function<Double, Instant> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInstant::new));
		}

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, String y,
				BiFunction<Double, Double, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.getDouble(x), row.getDouble(y)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, String y,
				BiFunction<Double, Double, String> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, String y,
				BiFunction<Double, Double, Integer> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, String y,
				BiFunction<Double, Double, Long> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, String y,
				BiFunction<Double, Double, Float> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, String y,
				BiFunction<Double, Double, Double> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, String y,
				BiFunction<Double, Double, Instant> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInstant::new));
		}

	}

	public static class FromFloat {

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, Function<Float, DFCell> mutation) {
			return FromDFCell.toCell(name, x, mutation.compose(DFCell::floatValue));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, Function<Float, String> mutation) {
			return toCell(name, x, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, String x, Function<Float, Boolean> mutation) {
			return toCell(name, x, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, Function<Float, Integer> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, Function<Float, Long> mutation) {
			return toCell(name, x, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, Function<Float, Float> mutation) {
			return toCell(name, x, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, Function<Float, Double> mutation) {
			return toCell(name, x, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, Function<Float, Instant> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInstant::new));
		}

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, String y,
				BiFunction<Float, Float, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.getFloat(x), row.getFloat(y)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, String y,
				BiFunction<Float, Float, String> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, String y,
				BiFunction<Float, Float, Integer> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, String y,
				BiFunction<Float, Float, Long> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, String y,
				BiFunction<Float, Float, Float> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, String y,
				BiFunction<Float, Float, Double> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, String y,
				BiFunction<Float, Float, Instant> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInstant::new));
		}

	}

	public static class FromInt {

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, Function<Integer, DFCell> mutation) {
			return FromDFCell.toCell(name, x, mutation.compose(DFCell::intValue));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, Function<Integer, String> mutation) {
			return toCell(name, x, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, String x, Function<Integer, Boolean> mutation) {
			return toCell(name, x, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, Function<Integer, Integer> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, Function<Integer, Long> mutation) {
			return toCell(name, x, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, Function<Integer, Float> mutation) {
			return toCell(name, x, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, Function<Integer, Double> mutation) {
			return toCell(name, x, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, Function<Integer, Instant> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInstant::new));
		}

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, String y,
				BiFunction<Integer, Integer, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.getInt(x), row.getInt(y)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, String y,
				BiFunction<Integer, Integer, String> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, String y,
				BiFunction<Integer, Integer, Integer> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, String y,
				BiFunction<Integer, Integer, Long> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, String y,
				BiFunction<Integer, Integer, Float> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, String y,
				BiFunction<Integer, Integer, Double> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, String y,
				BiFunction<Integer, Integer, Instant> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInstant::new));
		}
	}

	public static class FromLong {

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, Function<Long, DFCell> mutation) {
			return FromDFCell.toCell(name, x, mutation.compose(DFCell::longValue));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, Function<Long, String> mutation) {
			return toCell(name, x, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, String x, Function<Long, Boolean> mutation) {
			return toCell(name, x, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, Function<Long, Integer> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, Function<Long, Long> mutation) {
			return toCell(name, x, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, Function<Long, Float> mutation) {
			return toCell(name, x, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, Function<Long, Double> mutation) {
			return toCell(name, x, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, Function<Long, Instant> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInstant::new));
		}

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, String y,
				BiFunction<Long, Long, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.getLong(x), row.getLong(y)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, String y,
				BiFunction<Long, Long, String> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, String y,
				BiFunction<Long, Long, Integer> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, String y,
				BiFunction<Long, Long, Long> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, String y,
				BiFunction<Long, Long, Float> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, String y,
				BiFunction<Long, Long, Double> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, String y,
				BiFunction<Long, Long, Instant> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInstant::new));
		}

	}

	public static class FromString {

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, Function<String, DFCell> mutation) {
			return FromDFCell.toCell(name, x, mutation.compose(DFCell::stringValue));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, Function<String, String> mutation) {
			return toCell(name, x, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toBoolean(String name, String x, Function<String, Boolean> mutation) {
			return toCell(name, x, mutation.andThen(DFCellBoolean::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, Function<String, Integer> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, Function<String, Long> mutation) {
			return toCell(name, x, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, Function<String, Float> mutation) {
			return toCell(name, x, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, Function<String, Double> mutation) {
			return toCell(name, x, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, Function<String, Instant> mutation) {
			return toCell(name, x, mutation.andThen(DFCellInstant::new));
		}

		public static IPMapper<DFRow, DFRow> toCell(String name, String x, String y,
				BiFunction<String, String, DFCell> mutation) {
			return FromDFRow.toCell(name, row -> mutation.apply(row.getString(x), row.getString(y)));
		}

		public static IPMapper<DFRow, DFRow> toString(String name, String x, String y,
				BiFunction<String, String, String> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellString::new));
		}

		public static IPMapper<DFRow, DFRow> toInt(String name, String x, String y,
				BiFunction<String, String, Integer> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInt::new));
		}

		public static IPMapper<DFRow, DFRow> toLong(String name, String x, String y,
				BiFunction<String, String, Long> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellLong::new));
		}

		public static IPMapper<DFRow, DFRow> toFloat(String name, String x, String y,
				BiFunction<String, String, Float> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellFloat::new));
		}

		public static IPMapper<DFRow, DFRow> toDouble(String name, String x, String y,
				BiFunction<String, String, Double> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellDouble::new));
		}

		public static IPMapper<DFRow, DFRow> toInstant(String name, String x, String y,
				BiFunction<String, String, Instant> mutation) {
			return toCell(name, x, y, mutation.andThen(DFCellInstant::new));
		}

	}

}
