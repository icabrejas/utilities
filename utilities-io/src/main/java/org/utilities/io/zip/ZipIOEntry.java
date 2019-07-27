package org.utilities.io.zip;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.io.IOEntry;
import org.utilities.io.UtilitiesIO;

public class ZipIOEntry implements IOEntry {

	private ZipEntry entry;
	private byte[] bytes;

	public ZipIOEntry(ZipEntry entry, InputStream inputStream) throws QuietException {
		this.entry = entry;
		this.bytes = UtilitiesIO.toByteArrayQuietly(inputStream);
	}

	public ZipEntry getEntry() {
		return entry;
	}

	public byte[] getBytes() {
		return bytes;
	}

	@Override
	public InputStream getContent() {
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public String toString() {
		return "EntryZip [entry=" + entry + ", bytes=" + bytes.length + "]";
	}

}
