package org.utilities.io.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utilities.core.lang.exception.QuietException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class UtilitiesJSON {

	private UtilitiesJSON() {
	}

	public static String classToJson(Object value) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(value);
	}

	public static <T> T jsonToClass(String content, Class<T> valueType) throws QuietException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			content = format(content);
			return mapper.readValue(content, valueType);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static <T> T jsonToClass(String content, Class<T> valueType, String dateFormatPattern)
			throws QuietException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			content = format(content);
			DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			mapper.setDateFormat(dateFormat);
			return mapper.readValue(content, valueType);
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	private static String format(String content) {
		return content.replaceAll("\\r|\\n", "");
	}

	public static <T> List<T> jsonToList(String content, Class<T> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		List<?> generic = jsonToClass(content, List.class);
		return cast(generic, valueType);
	}

	public static <T> List<T> jsonToList(String content, Class<T> valueType, String dateFormatPattern)
			throws JsonParseException, JsonMappingException, IOException {
		List<?> generic = jsonToClass(content, List.class, dateFormatPattern);
		return cast(generic, valueType);
	}

	private static <T> List<T> cast(List<?> generic, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		List<T> casted = new ArrayList<T>();
		for (Object obj : generic) {
			casted.add(mapper.convertValue(obj, valueType));
		}
		return casted;
	}

	public static <V> Map<String, V> jsonToMap(String content, Class<V> valueType)
			throws JsonParseException, JsonMappingException, IOException {
		Map<?, ?> generic = jsonToClass(content, Map.class);
		return cast(generic, valueType);
	}

	public static <V> Map<String, V> jsonToMap(String content, Class<V> valueType, String dateFormatPattern)
			throws JsonParseException, JsonMappingException, IOException {
		Map<?, ?> generic = jsonToClass(content, Map.class, dateFormatPattern);
		return cast(generic, valueType);
	}

	private static <V> Map<String, V> cast(Map<?, ?> generic, Class<V> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, V> casted = new HashMap<String, V>();
		for (Object key : generic.keySet()) {
			String processedKey = mapper.convertValue(key, String.class);
			V processedValue = mapper.convertValue(generic.get(key), valueType);
			casted.put(processedKey, processedValue);
		}
		return casted;
	}

}