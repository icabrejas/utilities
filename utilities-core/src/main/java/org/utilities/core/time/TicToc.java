package org.utilities.core.time;

public class TicToc {

	public static final String DEFAULT_PATTERN = "HH:mm:ss.SSS";

	private String pattern;
	private long tic;

	public TicToc(String pattern, long tic) {
		this.pattern = pattern;
		this.tic = tic;
	}

	public TicToc tic() {
		this.tic = System.currentTimeMillis();
		return this;
	}

	public TicToc toc() {
		System.out.println(UtilitiesTime.formatMillis(System.currentTimeMillis() - tic, pattern));
		return tic();
	}

}
