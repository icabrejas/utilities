package org.utilities.core.util.lambda;

public class LambdaValue<T> {

	private T value;

	public LambdaValue(T value) {
		this.value = value;
	}

	public LambdaValue() {
	}

	public T get() {
		return value;
	}

	public boolean isPresent() {
		return value != null;
	}

	public T set(T value) {
		return this.value = value;
	}
	
	public T remove(){
		T value = this.value;
		this.value = null;
		return value;
	}

	@Override
	public String toString() {
		return "StreamableValue [value=" + value + "]";
	}

}
