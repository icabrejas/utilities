package org.utilities.io.gz;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.io.IOEntry;

public class GZipIOEntry implements IOEntry {

	private Supplier<InputStream> inputStream;

	public GZipIOEntry(Supplier<InputStream> inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public GZIPInputStream get() throws QuietException {
		try {
			return new GZIPInputStream(inputStream.get());
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

}
