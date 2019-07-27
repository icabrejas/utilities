package org.utilities.dataframe.rename;

import java.util.HashMap;
import java.util.Map;

public class RenameImpl implements Rename {

	public Map<String, String> translations = new HashMap<>();

	public RenameImpl() {
	}

	public RenameImpl(String newName, String oldName) {
		rename(newName, oldName);
	}

	public RenameImpl rename(String newName, String oldName) {
		translations.put(newName, oldName);
		return this;
	}

	@Override
	public String translate(String key) {
		String translation = translations.get(key);
		if (translation != null) {
			key = translation;
		}
		return key;
	}

}
