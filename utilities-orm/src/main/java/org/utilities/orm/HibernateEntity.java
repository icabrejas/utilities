package org.utilities.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.hibernate.cfg.NotYetImplementedException;

public interface HibernateEntity {

	default Collection<Value> values() {
		throw new NotYetImplementedException();
	};

	default Collection<Value> onDuplicatedKey() {
		return Collections.emptySet();
	};

	public static class Value {

		public String field;
		public Object value;

		public Value(String field, Object value) {
			this.field = field;
			this.value = value;
		}

	}

	public static class Values {
	
		public String field;

		public Values(String field) {
			this.field = field;
		}

		@Override
		public String toString() {
			return "VALUES(" + field + ")";
		}
	}

	public static class ValueList implements Collection<Value> {

		private Collection<Value> values = new ArrayList<>();

		public ValueList() {
		}

		public ValueList(String field, Object value) {
			append(field, value);
		}

		public ValueList append(String field, Object value) {
			values.add(new Value(field, value));
			return this;
		}

		@Override
		public void forEach(Consumer<? super Value> action) {
			values.forEach(action);
		}

		@Override
		public int size() {
			return values.size();
		}

		@Override
		public boolean isEmpty() {
			return values.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return values.contains(o);
		}

		@Override
		public Iterator<Value> iterator() {
			return values.iterator();
		}

		@Override
		public Object[] toArray() {
			return values.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return values.toArray(a);
		}

		@Override
		public boolean add(Value e) {
			return values.add(e);
		}

		@Override
		public boolean remove(Object o) {
			return values.remove(o);
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return values.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends Value> c) {
			return values.addAll(c);
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return values.removeAll(c);
		}

		@Override
		public boolean removeIf(Predicate<? super Value> filter) {
			return values.removeIf(filter);
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return values.retainAll(c);
		}

		@Override
		public void clear() {
			values.clear();
		}

		@Override
		public boolean equals(Object o) {
			return values.equals(o);
		}

		@Override
		public int hashCode() {
			return values.hashCode();
		}

		@Override
		public Spliterator<Value> spliterator() {
			return values.spliterator();
		}

		@Override
		public Stream<Value> stream() {
			return values.stream();
		}

		@Override
		public Stream<Value> parallelStream() {
			return values.parallelStream();
		}

	}
}
