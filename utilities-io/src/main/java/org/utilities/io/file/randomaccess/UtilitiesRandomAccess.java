package org.utilities.io.file.randomaccess;

import java.io.IOException;
import java.io.RandomAccessFile;

public class UtilitiesRandomAccess {

	private static final String READ = "r";
	private static final String READ_WRITE = "rw";

	public static byte[] read(String filePath, int seek, int n) throws IOException {
		try (RandomAccessFile file = new RandomAccessFile(filePath, READ)) {
			file.seek(seek);
			byte[] bytes = new byte[n];
			file.read(bytes);
			return bytes;
		}

	}

	public static void write(String filePath, int seek, byte[] bytes) throws IOException {
		try (RandomAccessFile file = new RandomAccessFile(filePath, READ_WRITE)) {
			file.seek(seek);
			file.write(bytes);
		}
	}

	public static void append(String filePath, String data) throws IOException {
		try (RandomAccessFile file = new RandomAccessFile(filePath, READ_WRITE)) {
			file.seek(file.length());
			file.write(data.getBytes());
		}
	}

}
