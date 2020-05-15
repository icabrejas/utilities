package org.utilities.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Supplier;

import org.utilities.core.lang.exception.QuietException;

public interface IOEntry extends Supplier<InputStream> {

	default Reader getReader() {
		return new InputStreamReader(get());
	}

	default String getString() throws QuietException {
		return UtilitiesIO.toString(get());
	}

}
