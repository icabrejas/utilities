package org.utilities.io.gz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.util.function.SupplierPlus;
import org.utilities.io.UtilitiesIO;

public class UtilitiesGZip {

	public static final String EXTESION = "gz";

	private UtilitiesGZip() {
	}

	public static void compress(File file, boolean delete) throws QuietException {
		String name = file.getAbsolutePath() + '.' + EXTESION;
		File gzip = new File(name);
		UtilitiesGZip.compress(file, gzip);
		if (delete) {
			file.delete();
		}
	}

	public static void compress(File inputFile, File outputFile) throws QuietException {
		try (InputStream inputStream = new FileInputStream(inputFile);
				OutputStream outputStream = new FileOutputStream(outputFile);
				OutputStream gzip = new GZIPOutputStream(outputStream);) {
			IOUtils.copy(inputStream, gzip);
		} catch (Exception e) {
			throw new QuietException(e);
		}
	}

	public static String decompress(File file) throws QuietException {
		try (InputStream inputStream = new FileInputStream(file);
				GZIPInputStream gz = new GZIPInputStream(inputStream);) {
			return UtilitiesIO.toString(gz);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static GZipIOEntry newEntryGZip(File file) {
		return new GZipIOEntry(SupplierPlus.parseQuiet(() -> new FileInputStream(file)));
	}

	public static GZipIOEntry newEntryGZip(Supplier<InputStream> inputStream) {
		return new GZipIOEntry(inputStream);
	}

	public static GZIPInputStream getInputStream(File file) {
		return UtilitiesGZip.newEntryGZip(file)
				.get();
	}

}