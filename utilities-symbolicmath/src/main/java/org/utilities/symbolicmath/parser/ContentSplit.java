package org.utilities.symbolicmath.parser;

public class ContentSplit {

	private String[] parts;

	public ContentSplit(String content) {
		this.parts = content.substring(content.indexOf('(') + 1, content.lastIndexOf(')'))
				.split(",");
	}

	public int length() {
		return parts.length;
	}

	public String getString(int i) {
		return parts[i].trim();
	}

	public int getInt(int i) {
		return Integer.parseInt(getString(i));
	}

	public boolean getBoolean(int i) {
		return Boolean.parseBoolean(getString(i));
	}

}
