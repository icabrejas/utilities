package org.utilities.dataframe.row;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.dataframe.bind.DFRowBinded;
import org.utilities.dataframe.cell.DFCell;

public interface DFRow extends IterablePipe<Map.Entry<String, DFCell>> {

	Collection<String> keys();

	DFCell get(String key);

	default Collection<DFCell> values() {
		return get(keys());
	}

	default String getString(String name) {
		DFCell cell = get(name);
		return cell == null ? null : cell.stringValue();
	}

	default Integer getInt(String name) {
		DFCell cell = get(name);
		return cell == null ? null : cell.intValue();
	}

	default Long getLong(String name) {
		DFCell cell = get(name);
		return cell == null ? null : cell.longValue();
	}

	default Float getFloat(String name) {
		DFCell cell = get(name);
		return cell == null ? null : cell.floatValue();
	}

	default Double getDouble(String name) {
		DFCell cell = get(name);
		return cell == null ? null : cell.doubleValue();
	}

	default Instant getInstant(String name) {
		DFCell cell = get(name);
		return cell == null ? null : cell.instantValue();
	}

	default Boolean getBoolean(String name) {
		DFCell cell = get(name);
		return cell == null ? null : cell.booleanValue();
	}

	default List<DFCell> get(Collection<String> keys) {
		List<DFCell> values = new ArrayList<>();
		for (String key : keys) {
			values.add(get(key));
		}
		return values;
	}

	default boolean contains(String name) {
		return keys().contains(name);
	}

	default boolean containsKeys(Collection<String> keys) {
		return keys().containsAll(keys);
	}

	default DFRow bind(String thisPrefix, DFRow other, String otherPrefix) {
		return new DFRowBinded(thisPrefix, this, otherPrefix, other);
	}

	// public static DFLine bindColumns(List<DFLine> dataEntries, List<String>
	// prefixes) {
	// return new BindedDataEntry(dataEntries, prefixes);
	// }
	//
	// public static DFLine bindColumns(DFLine entryA, String prefixA, DFLine
	// entryB, String prefixB) {
	// return new BindedDataEntry(entryA, prefixA, entryB, prefixB);
	// }

	public static DFRowImpl gather(String key, String value, Map.Entry<String, DFCell> cell) {
		DFRowImpl row = new DFRowImpl();
		row.put(key, cell.getKey());
		row.put(value, cell.getValue());
		return row;
	}

	default List<DFRow> gather(String key, String value, Predicate<String> selection) {
		DFRowImpl common = new DFRowImpl();
		List<DFRowImpl> gathered = new ArrayList<>();
		for (Map.Entry<String, DFCell> cell : this) {
			if (selection.test(cell.getKey())) {
				gathered.add(gather(key, value, cell));
			} else {
				common.put(cell);
			}
		}
		return gathered.stream()
				.map(row -> row.putAll(common))
				.collect(Collectors.toList());
	}

	default List<DFRow> gather(String key, String value, Collection<String> names) {
		return gather(key, value, names::contains);
	}

	default List<DFRow> gather(String key, String value, String... names) {
		return gather(key, value, Arrays.asList(names));
	}

	default DFRow gather(String key, String value, String name) {
		DFRowImpl gathered = new DFRowImpl(this);
		gathered.put(key, name);
		gathered.put(value, this.get(key));
		gathered.remove(name);
		return gathered;
	}

	default DFRow mutate(String name, Function<DFRow, DFCell> mutation) {
		DFRowImpl mutated = new DFRowImpl(this);
		mutated.put(name, mutation.apply(mutated));
		return mutated;
	}

	default DFRow remove(Predicate<String> selection) {
		return select(selection.negate());
	}

	default DFRow remove(Collection<String> names) {
		return remove(names::contains);
	}

	default DFRow remove(String... names) {
		return remove(Arrays.asList(names));
	}

	default DFRow rename(Function<String, String> translator) {
		DFRowImpl renamed = new DFRowImpl();
		for (Map.Entry<String, DFCell> entry : this) {
			renamed.put(translator.apply(entry.getKey()), entry.getValue());
		}
		return renamed;
	}

	default DFRow rename(Map<String, String> translations) {
		return rename(name -> translations.containsKey(name) ? translations.get(name) : name);
	}

	default DFRow rename(String oldName, String newName) {
		return rename(name -> oldName.equals(name) ? newName : name);
	}

	default DFRow select(Predicate<String> selection) {
		DFRowImpl selected = new DFRowImpl();
		for (Map.Entry<String, DFCell> entry : this) {
			if (selection.test(entry.getKey())) {
				selected.put(entry);
			}
		}
		return selected;
	}

	default DFRow select(Collection<String> names) {
		return select(names::contains);
	}

	default DFRow select(String... names) {
		return select(Arrays.asList(names));
	}

	// TODO
	// default DFRow separate(String key, String separator, List<String> names)
	// {
	// DFRow entry = this;
	// for (int i = 0; i < names.size(); i++) {
	// entry = entry.mutate(DFMapper.split(names.get(i), key, separator, i));
	// }
	// return entry;
	// }

	// TODO tidyr::spread
	// TODO tidyr::separate_rows
	// TODO tidyr::unite
	// TODO tidyr::extract

	// TODO dplyr::transmute
	// TODO dplyr::mutate_all
	// TODO dplyr::mutate_if
	// TODO dplyr::mutate_at

}
