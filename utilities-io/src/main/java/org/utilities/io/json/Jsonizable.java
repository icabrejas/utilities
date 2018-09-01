package org.utilities.io.json;

import org.utilities.core.lang.exception.QuietException;

public interface Jsonizable {

	default String toJSON() throws QuietException {
		return UtilitiesJSON.classToJson(this);
	}

}
