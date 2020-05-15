package org.utilities.dataframe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.utilities.dataframe.cell.DFCellDouble;
import org.utilities.dataframe.row.DFRow;
import org.utilities.dataframe.row.DFRowImpl;
import org.utilities.dataframe.symbol.DFSymbolUtils;
import org.utilities.symbolicmath.symbol.array.SymbolArrayDoublePrimitive;

import junit.framework.TestCase;

public class DataFrameTest extends TestCase {

	@Test
	public void test() {
		List<DFRow> rows = new ArrayList<>();
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(1.0)));
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(3.0)));
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(2.0)));
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(2.0)));
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(5.0)));
		rows.add(new DFRowImpl());
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(10.0)));
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(8.0)));
		rows.add(new DFRowImpl("VAR1", new DFCellDouble(12.0)));

		DataFrame dataFrame = DataFrame.as(rows)
//				.mutate("IND1", DFSymbolUtils.mean("VAR1", 3, true));
				.mutate("IND1", DFSymbolUtils.summarize("VAR1", 3, true, SymbolArrayDoublePrimitive::mean));
		

		List<Double> values = dataFrame.map(DFRow::getDouble, "IND1")
				.toList();

		assertEquals(Arrays.asList(1., (1. + 3.) / 2, (1. + 3. + 2.) / 3, (3. + 2. + 2.) / 3, (2. + 2. + 5.) / 3,
				(2. + 5.) / 2, (5. + 10.) / 2, (10. + 8.) / 2, (10. + 8. + 12.) / 3), values);
	}

}
