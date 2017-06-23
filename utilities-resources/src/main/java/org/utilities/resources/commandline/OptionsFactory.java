package org.utilities.resources.commandline;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionsFactory {

	public static final String OPTION_HELP = "h";
	public static final String OPTION_CONFIG_FILE = "c";
	public static final String OPTION_CHECK_CONFIG_FILE = "C";
	public static final String OPTION_CONFIG_OVERRIDE = "X";

	public static Options basic() {
		final Options options = new Options();
		options.addOption(configFile());
		options.addOption(checkConfig());
		options.addOption(configOverride());
		options.addOption(help());
		return options;
	}

	private static Option configFile() {
		Option configFile = new Option(OPTION_CONFIG_FILE, true, "Use external config file");
		configFile.setArgName("path");
		return configFile;
	}

	private static Option checkConfig() {
		return new Option(OPTION_CHECK_CONFIG_FILE, false, "Check external config file");
	}

	private static Option configOverride() {
		Option configOverride = new Option(OPTION_CONFIG_OVERRIDE, true, "Override config value");
		configOverride.setArgName("path=value");
		return configOverride;
	}

	private static Option help() {
		return new Option(OPTION_HELP, false, "Show this help message");
	}

}
