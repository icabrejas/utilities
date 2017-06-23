package org.utilities.io.zip;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.io.EntryIO;
import org.utilities.io.UtilitiesIO;

public class EntryZip<I> implements EntryIO<I> {

	private I info;
	private ZipEntry entry;
	private byte[] bytes;

	public EntryZip(ZipEntry entry, I info, InputStream inputStream) throws QuietException {
		this.info = info;
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
	public I getInfo() {
		return info;
	}

	@Override
	public InputStream getContent() {
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public String toString() {
		return "EntryZip [info=" + info + ", entry=" + entry + ", bytes=" + bytes.length + "]";
	}

}
