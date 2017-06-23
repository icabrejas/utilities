package org.utilities.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.Entry;

public interface EntryIO<I> extends Entry<I, InputStream> {

	I getInfo();

	default Reader getReader() {
		return new InputStreamReader(getContent());
	}

	default String getString() throws QuietException {
		return UtilitiesIO.toString(getContent());
	}

}