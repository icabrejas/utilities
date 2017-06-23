package org.utilities.io.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Supplier;

import org.utilities.core.util.function.SupplierPlus;

public class UtilitiesZip {

	public static IterablePipeZip<File> newInstance(File file) {
		Supplier<InputStream> inputStream = SupplierPlus.parseQuiet(() -> new FileInputStream(file));
		return new IterablePipeZip<>(file, inputStream);
	}
	
}
