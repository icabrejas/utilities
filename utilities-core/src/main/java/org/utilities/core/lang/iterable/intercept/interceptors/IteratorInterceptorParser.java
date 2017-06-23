package org.utilities.core.lang.iterable.intercept.interceptors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.Entry;

// TODO
public class IteratorInterceptorParser<T, R> implements Function<T, R> {

	private Class<R> type;
	private List<Entry<Method, Function<T, ?>>> methods = new ArrayList<>();

	public IteratorInterceptorParser(Class<R> type) {
		this.type = type;
	}

	public static <T, R> IteratorInterceptorParser<T, R> newInstance(Class<T> input, Class<R> output) {
		return new IteratorInterceptorParser<>(output);
	}

	public <U> IteratorInterceptorParser<T, R> field(String field, Class<U> type, Function<T, U> value) throws QuietException {
		try {
			Method method = this.type.getMethod(setter(field), type);
			return field(method, value);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new QuietException(e);
		}
	}

	public IteratorInterceptorParser<T, R> field(Method method, Function<T, ?> value) {
		Entry<Method, Function<T, ?>> pair = Entry.newInstance(method, value);
		return field(pair);
	}

	public IteratorInterceptorParser<T, R> field(Entry<Method, Function<T, ?>> pair) {
		methods.add(pair);
		return this;
	}

	@Override
	public R apply(T t) {
		try {
			R r = type.newInstance();
			for (Entry<Method, Function<T, ?>> pair : methods) {
				Method method = pair.getInfo();
				Function<T, ?> value = pair.getContent();
				method.invoke(r, value.apply(t));
			}
			return r;
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			throw new QuietException(e);
		}
	}

	private String setter(String field) {
		return "set" + new String(new char[] { field.charAt(0) }).toUpperCase() + field.substring(1);
	}

}
