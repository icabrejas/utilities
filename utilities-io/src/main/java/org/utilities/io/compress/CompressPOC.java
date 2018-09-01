package org.utilities.io.compress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.poi.util.IOUtils;
import org.utilities.core.time.TicToc;
import org.utilities.core.time.UtilitiesTime;

public class CompressPOC {

	public static void main(String[] args) throws IOException {

		String file = "C:/tmp/alex-lynn.com - 2018-03-21 - Gloria Sol - Magnetic.zip";
		// String file = "C:/tmp/alex-lynn.com - 2018-04-08 - Gloria Sol -
		// Happiness.zip";
		// String file = "C:/tmp/suicidegirls.com - 2018-06-25 - Coralinne -
		// Summer in Portugal.zip";
		File targetDir = new File("C:/tmp/coralinne");
		targetDir.mkdirs();

		TicToc tic = UtilitiesTime.tic();
		readCommonsCompressZip(file, targetDir);
		tic.toc();

	}

	private static void readBasicZip(String file, File targetDir) throws IOException {
		try (InputStream fi = Files.newInputStream(Paths.get(file));
				InputStream bi = new BufferedInputStream(fi);
				ZipInputStream i = new ZipInputStream(bi);) {
			ZipEntry entry = null;
			while ((entry = i.getNextEntry()) != null) {
				String name = fileName(targetDir, entry.getName());
				File f = new File(name);
				if (entry.isDirectory()) {
					if (!f.isDirectory() && !f.mkdirs()) {
						throw new IOException("failed to create directory " + f);
					}
				} else {
					File parent = f.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("failed to create directory " + parent);
					}
					try (OutputStream o = Files.newOutputStream(f.toPath())) {
						IOUtils.copy(i, o);
					}
				}
			}
		}
		//
		// IterablePipeZip<File> zip = UtilitiesZip.newInstance(new File(file));
		// for (EntryZip<File> entryZip : zip) {
		// ZipEntry entry = entryZip.getEntry();
		// if ("suicidegirls.com - 2018-06-25 - Coralinne - Summer in
		// Portugal/Coralinne - Summer In Portugal/51.jpg"
		// .equals(entry.getName())) {
		// String name = fileName(targetDir, entry.getName());
		// System.out.println(name);
		// File f = new File(name);
		// if (entry.isDirectory()) {
		// if (!f.isDirectory() && !f.mkdirs()) {
		// throw new IOException("failed to create directory " + f);
		// }
		// } else {
		// File parent = f.getParentFile();
		// if (!parent.isDirectory() && !parent.mkdirs()) {
		// throw new IOException("failed to create directory " + parent);
		// }
		// try (OutputStream o = Files.newOutputStream(f.toPath())) {
		// IOUtils.copy(entryZip.getContent(), o);
		// }
		// }
		// }
		// }
	}

	private static void readCommonsCompressZip(String file, File targetDir) throws IOException {
		try (InputStream fi = Files.newInputStream(Paths.get(file));
				InputStream bi = new BufferedInputStream(fi);
				ArchiveInputStream i = new ZipArchiveInputStream(bi);) {
			ArchiveEntry entry = null;
			while ((entry = i.getNextEntry()) != null) {
				if (!i.canReadEntryData(entry)) {
					// log something?
					continue;
				}
				String name = fileName(targetDir, entry.getName());
				File f = new File(name);
				if (entry.isDirectory()) {
					if (!f.isDirectory() && !f.mkdirs()) {
						throw new IOException("failed to create directory " + f);
					}
				} else {
					File parent = f.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("failed to create directory " + parent);
					}
					try (OutputStream o = Files.newOutputStream(f.toPath())) {
						IOUtils.copy(i, o);
					}
				}
			}
		}
	}

	private static String fileName(File targetDir, String name) {
		return targetDir + "/" + name;
	}
}
