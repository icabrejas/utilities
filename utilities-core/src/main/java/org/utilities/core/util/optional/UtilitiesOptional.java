package org.utilities.core.util.optional;

import java.util.Optional;

public class UtilitiesOptional {

	public static <T> T get(Optional<T> optional) {
		return optional.isPresent() ? optional.get() : null;
	}
}
