package org.utilities.dataframe.bind;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.IterablePipeSequence;
import org.utilities.dataframe.cell.DFCell;
import org.utilities.dataframe.row.DFRow;

public class DFRowBinded implements DFRow {

	private static final char SEPARATOR = '.';
	private List<DFRow> dataEntries;
	private List<String> prefixes;

	public DFRowBinded(List<String> prefixes, List<DFRow> dataEntries) {
		if (prefixes.size() != dataEntries.size()) {
			throw new Error(); // TODO spefific exception
		}
		this.dataEntries = dataEntries;
		this.prefixes = prefixes;
	}

	public DFRowBinded(String prefixA, DFRow entryA, String prefixB, DFRow entryB) {
		this(Arrays.asList(prefixA, prefixB), Arrays.asList(entryA, entryB));
	}

	@Override
	public Collection<String> keys() {
		return IterablePipeSequence.create(0, prefixes.size() - 1)
				.flatMap(i -> names(dataEntries.get(i), prefixes.get(i)))
				.toSet();
	}

	@Override
	public Iterator<Entry<String, DFCell>> iterator() {
		return IterablePipeSequence.create(0, prefixes.size() - 1)
				.flatMap(i -> cells(dataEntries.get(i), prefixes.get(i)))
				.iterator();
	}

	// TODO cases with prefix == null
	@Override
	public DFCell get(String name) {
		for (int i = 0; i < prefixes.size(); i++) {
			String prefix = prefixes.get(i);
			if (name.startsWith(prefix + '.')) {
				DFRow entry = dataEntries.get(i);
				return entry.get(name.substring(prefix.length() + 1));
			}
		}
		return null;
	}

	private Set<String> names(DFRow row, String prefix) {
		return IterablePipe.create(row.keys())
				.map(DFRowBinded::name, prefix)
				.toSet();
	}

	private static String name(String name, String prefix) {
		if (prefix != null) {
			return prefix + SEPARATOR + name;
		} else {
			return name;
		}
	}

	private Set<Map.Entry<String, DFCell>> cells(DFRow row, String prefix) {
		return IterablePipe.create(row)
				.map(cell -> new AbstractMap.SimpleEntry<>(name(cell.getKey(), prefix), cell.getValue()))
				.map(cell -> (Map.Entry<String, DFCell>) cell)
				.toSet();
	}

}
