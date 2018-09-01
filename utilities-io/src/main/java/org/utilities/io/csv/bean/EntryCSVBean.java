package org.utilities.io.csv.bean;

public class EntryCSVBean<I, T> {

	private I metadata;
	private T bean;

	public EntryCSVBean(I metadata, T bean) {
		this.metadata = metadata;
		this.bean = bean;
	}

	public I getMetadata() {
		return metadata;
	}

	public T getBean() {
		return bean;
	}

	@Override
	public String toString() {
		return "EntryCSVBean [metadata=" + metadata + ", bean=" + bean + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bean == null) ? 0 : bean.hashCode());
		result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntryCSVBean<?, ?> other = (EntryCSVBean<?, ?>) obj;
		if (bean == null) {
			if (other.bean != null)
				return false;
		} else if (!bean.equals(other.bean))
			return false;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		return true;
	}

}
