package org.utilities.io.html;

import java.io.InputStream;

import org.utilities.io.EntryIO;

public class WebEntry implements EntryIO<String> {

	private String url;

	public WebEntry(String url) {
		this.url = url;
	}

	@Override
	public String getInfo() {
		return url;
	}

	@Override
	public InputStream getContent() {
		return UtilitiesScraping.getInputStream(url);
	}

}
