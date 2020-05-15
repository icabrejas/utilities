package org.utilities.dataframe.group;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.dataframe.DataFrame;
import org.utilities.dataframe.row.DFRow;

// TODO usefull
public class DFGroup {

	private List<Map.Entry<DFRow, DataFrame>> dataframes;

	public DFGroup(DataFrame dataFrame, String... keys) {
		this.dataframes = dataFrame.stream(-1, false)
				.collect(Collectors.groupingBy(row -> row.select(keys)))
				.entrySet()
				.stream()
				.map(DFGroup::parseDF)
				.collect(Collectors.toList());
	}

	private static Map.Entry<DFRow, DataFrame> parseDF(Map.Entry<DFRow, List<DFRow>> entry) {
		return new AbstractMap.SimpleEntry<>(entry.getKey(), DataFrame.as(entry.getValue()));
	}

	public DataFrame ungroup() {
		return IterablePipe.create(dataframes)
				.flatMap(DFGroup::ungroup)
				.apply(DataFrame::as);
	}

	private static Iterable<DFRow> ungroup(Entry<DFRow, DataFrame> entry) {
		DataFrame dataFrame = entry.getValue();
		return dataFrame.map(row -> row.bind(null, entry.getKey(), null));
	}

}
