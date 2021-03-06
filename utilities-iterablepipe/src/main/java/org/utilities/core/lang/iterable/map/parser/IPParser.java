package org.utilities.core.lang.iterable.map.parser;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.map.IPMapper;
import org.utilities.core.lang.iterable.track.IPTrackerImpl;

// TODO
public class IPParser<T, R> extends IPTrackerImpl<T> implements IPMapper<T, R> {

	private Class<R> type;
	private Map<Method, Function<T, ?>> methods = new HashMap<>();

	public IPParser(Class<R> type) {
		this.type = type;
	}

	public static <T, R> IPParser<T, R> from(Class<T> input, Class<R> output) {
		return new IPParser<>(output);
	}

	public <U> IPParser<T, R> parse(String field, Function<T, U> getter) throws QuietException {
		try {
			Method setter = findSetter(field);
			methods.put(setter, getter);
			return this;
		} catch (SecurityException e) {
			throw new QuietException(e);
		}
	}

	private Method findSetter(String field) {
		String name = "set" + new String(new char[] { field.charAt(0) }).toUpperCase() + field.substring(1);
		Method setter = null;
		Method[] methods = this.type.getMethods();
		for (int i = 0; i < methods.length && setter == null; i++) {
			Method method = methods[i];
			if (name.equals(method.getName())) {
				setter = method;
			}
		}
		return setter;
	}

	public IPParser<T, R> parseInt(String field, Function<T, String> getter) throws QuietException {
		return parse(field, IPParser.parseInt(getter));
	}

	public static <T> Function<T, Integer> parseInt(Function<T, String> getter) {
		return t -> {
			String value = getter.apply(t);
			return value != null ? Integer.parseInt(value) : null;
		};
	}

	public IPParser<T, R> parseLong(String field, Function<T, String> getter) throws QuietException {
		return parse(field, IPParser.parseLong(getter));
	}

	public static <T> Function<T, Long> parseLong(Function<T, String> getter) {
		return t -> {
			String value = getter.apply(t);
			return value != null ? Long.parseLong(value) : null;
		};
	}

	public IPParser<T, R> parseDouble(String field, Function<T, String> getter) throws QuietException {
		return parse(field, IPParser.parseDouble(getter));
	}

	public static <T> Function<T, Double> parseDouble(Function<T, String> getter) {
		return t -> {
			String value = getter.apply(t);
			return value != null ? Double.parseDouble(value) : null;
		};
	}

	public <U> IPParser<T, R> parseString(String field, Function<T, U> getter) throws QuietException {
		return parse(field, Object::toString);
	}

	@Override
	public R map(int serialNumber, T t) {
		try {
			R r = type.newInstance();
			for (Map.Entry<Method, Function<T, ?>> pair : methods.entrySet()) {
				Method method = pair.getKey();
				Function<T, ?> value = pair.getValue();
				method.invoke(r, value.apply(t));
			}
			return r;
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new QuietException(e);
		}
	}

}
