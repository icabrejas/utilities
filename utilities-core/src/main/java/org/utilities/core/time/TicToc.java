package org.utilities.core.time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utilities.core.UtilitiesTime;

public class TicToc {

	public static final String DEFAULT_PATTERN = "HH:mm:ss.SSS";

	private static Logger LOGGER = LoggerFactory.getLogger(TicToc.class);

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
		LOGGER.info(UtilitiesTime.formatMillis(System.currentTimeMillis() - tic, pattern));
		return tic();
	}

	public TicToc toc(String message) {
		LOGGER.info(message + UtilitiesTime.formatMillis(System.currentTimeMillis() - tic, pattern));
		return tic();
	}

}
