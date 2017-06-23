package org.utilities.io.csv.bean;

public class EntryCSVBean<I, T> {

	private I metainfo;
	private T bean;

	public EntryCSVBean(I metainfo, T bean) {
		this.metainfo = metainfo;
		this.bean = bean;
	}

	public I getMetainfo() {
		return metainfo;
	}

	public T getBean() {
		return bean;
	}

	@Override
	public String toString() {
		return "EntryCSVBean [metainfo=" + metainfo + ", bean=" + bean + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bean == null) ? 0 : bean.hashCode());
		result = prime * result + ((metainfo == null) ? 0 : metainfo.hashCode());
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
		if (metainfo == null) {
			if (other.metainfo != null)
				return false;
		} else if (!metainfo.equals(other.metainfo))
			return false;
		return true;
	}

}
