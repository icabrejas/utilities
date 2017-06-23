package org.utilities.io.text;

public class EntryLine<I> {

	private I meatinfo;
	private String line;

	public EntryLine(I meatinfo, String line) {
		super();
		this.meatinfo = meatinfo;
		this.line = line;
	}

	public I getMeatinfo() {
		return meatinfo;
	}

	public String getLine() {
		return line;
	}

	@Override
	public String toString() {
		return "EntryLine [meatinfo=" + meatinfo + ", line=" + line + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((line == null) ? 0 : line.hashCode());
		result = prime * result + ((meatinfo == null) ? 0 : meatinfo.hashCode());
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
		EntryLine<?> other = (EntryLine<?>) obj;
		if (line == null) {
			if (other.line != null)
				return false;
		} else if (!line.equals(other.line))
			return false;
		if (meatinfo == null) {
			if (other.meatinfo != null)
				return false;
		} else if (!meatinfo.equals(other.meatinfo))
			return false;
		return true;
	}

}
