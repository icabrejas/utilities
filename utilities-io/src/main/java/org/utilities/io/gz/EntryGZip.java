package org.utilities.io.gz;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.io.EntryIO;

public class EntryGZip<I> implements EntryIO<I> {

	private I info;
	private Supplier<InputStream> inputStream;

	public EntryGZip(I info, Supplier<InputStream> inputStream) {
		this.info = info;
		this.inputStream = inputStream;
	}

	@Override
	public I getInfo() {
		return info;
	}

	@Override
	public GZIPInputStream getContent() throws QuietException {
		try {
			InputStream inputStream = this.inputStream.get();
			return new GZIPInputStream(inputStream);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	@Override
	public String toString() {
		return "EntryGZip [info=" + info + "]";
	}

}
