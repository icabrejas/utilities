package org.utilities.io.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.utilities.core.util.function.SupplierPlus;
import org.utilities.io.UtilitiesIO;

public class UtilitiesZip {

	public static IterablePipeZip newInstance(File file) {
		Supplier<InputStream> inputStream = SupplierPlus.parseQuiet(() -> new FileInputStream(file));
		return new IterablePipeZip(inputStream);
	}

	public static List<ArchiveEntry> list(String file) throws IOException {
		List<ArchiveEntry> entries = new ArrayList<>();
		try (InputStream fileInputSteam = Files.newInputStream(Paths.get(file));
				InputStream bufferedInputStream = new BufferedInputStream(fileInputSteam);
				ArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(bufferedInputStream);) {
			ArchiveEntry entry = null;
			while ((entry = zipArchiveInputStream.getNextEntry()) != null) {
				if (zipArchiveInputStream.canReadEntryData(entry) && !entry.isDirectory()) {
					entries.add(entry);
				}
			}
		}
		return entries;
	}

	public static byte[] bytes(String file, String name) throws IOException {
		byte[] bytes = null;
		try (InputStream fileInputSteam = Files.newInputStream(Paths.get(file));
				InputStream bufferedInputStream = new BufferedInputStream(fileInputSteam);
				ArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(bufferedInputStream);) {
			ArchiveEntry entry = null;
			while (bytes == null && (entry = zipArchiveInputStream.getNextEntry()) != null) {
				if (zipArchiveInputStream.canReadEntryData(entry) && !entry.isDirectory()
						&& name.equals(entry.getName())) {
					bytes = UtilitiesIO.toByteArrayQuietly(zipArchiveInputStream);
				}
			}
		}
		return bytes;
	}

}
