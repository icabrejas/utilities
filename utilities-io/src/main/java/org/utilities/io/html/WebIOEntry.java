package org.utilities.io.html;

import java.io.InputStream;

import org.utilities.io.IOEntry;

public class WebIOEntry implements IOEntry {

	private String url;

	public WebIOEntry(String url) {
		this.url = url;
	}

	@Override
	public InputStream get() {
		return UtilitiesScraping.getInputStream(url);
	}

}
