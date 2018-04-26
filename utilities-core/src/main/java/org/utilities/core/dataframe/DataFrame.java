package org.utilities.core.dataframe;

import java.util.Collection;

import org.utilities.core.dataframe.entry.DataEntry;
import org.utilities.core.dataframe.entry.mutate.Mutation;
import org.utilities.core.dataframe.entry.select.Selection;
import org.utilities.core.lang.iterable.IterablePipe;

public interface DataFrame extends IterablePipe<DataEntry> {

	public static DataFrame from(Iterable<DataEntry> entries) {
		return entries::iterator;
	}

	default DataFrame select(Selection selection) {
		return from(map(entry -> entry.select(selection)));
	}

	default DataFrame remove(Selection selection) {
		return from(map(entry -> entry.remove(selection)));
	}

	default DataFrame mutate(Mutation mutation) {
		return from(map(entry -> entry.mutate(mutation)));
	}

	// TODO add other version with Selection
	default DataFrame gather(String key, String value, String... names) {
		return from(flatMap(entry -> entry.gather(key, value, names)));
	}

	default DataFrame gather(String key, String value, Collection<String> names) {
		return from(flatMap(entry -> entry.gather(key, value, names)));
	}

}
