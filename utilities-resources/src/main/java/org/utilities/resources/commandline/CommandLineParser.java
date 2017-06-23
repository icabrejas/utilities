package org.utilities.resources.commandline;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;

public class CommandLineParser {

	private static Logger LOGGER = LoggerFactory.getLogger(CommandLineParser.class);

	private CommandLine commandLine;
	private Config config;

	public CommandLineParser(Options options, String[] args, String... modules) {
		this.commandLine = getCommandLine(options, args);
		if (commandLine.hasOption(OptionsFactory.OPTION_HELP)) {
			showHelpAndExit(options, ExitStatus.OK);
		}
		if (commandLine.hasOption(OptionsFactory.OPTION_CHECK_CONFIG_FILE)) {
			checkConfigFileAndExit(modules);
		}
		this.config = CommandLineParser.getOverridingConfig(commandLine)
				.withFallback(getExternalConfig())
				.withFallback(getBaseConfig(modules))
				.withFallback(ConfigFactory.load());
	}

	private CommandLine getCommandLine(Options options, String[] args) {
		try {
			DefaultParser parser = new DefaultParser();
			return parser.parse(options, args);
		} catch (UnrecognizedOptionException e) {
			LOGGER.error("Invalid option", e);
			showHelpAndExit(options, ExitStatus.ERROR);
		} catch (MissingArgumentException e) {
			LOGGER.error("Missing argument", e);
			showHelpAndExit(options, ExitStatus.ERROR);
		} catch (MissingOptionException e) {
			LOGGER.error("Missing option", e);
			showHelpAndExit(options, ExitStatus.ERROR);
		} catch (ParseException e) {
			LOGGER.error("Command line options parsing error", e);
			ExitStatus.ERROR.exit();
		}
		return null;
	}

	public void showHelpAndExit(Options options, ExitStatus exitStatus) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar <jar file> <options>", "Usage: ", options, "");
		ExitStatus.ERROR.exit();
	}

	private void checkConfigFileAndExit(String[] modules) {
		Config baseConfig = getBaseConfig(modules);
		Config externalConfig = getExternalConfig();
		List<String> missingPaths = new ArrayList<>();
		for (Map.Entry<String, ConfigValue> entry : baseConfig.entrySet()) {
			if (!externalConfig.hasPath(entry.getKey())) {
				missingPaths.add(entry.getKey());
			}
		}
		if (missingPaths.size() > 0) {
			for (String path : missingPaths) {
				LOGGER.error(path);
			}
			ExitStatus.ERROR.exit();
		}
		ExitStatus.OK.exit();
	}

	private Config getBaseConfig(String[] modules) {
		Config baseConfig = ConfigFactory.empty();
		for (String module : modules) {
			baseConfig = baseConfig.withFallback(ConfigFactory.parseResources(module + ".conf"));
			// FIXME baseConfig =
			// baseConfig.withFallback(loadConfigFile(module));
		}
		baseConfig.withFallback(ConfigFactory.defaultReference());
		return baseConfig;
	}

	private Config getExternalConfig() {
		Config externalConfig = ConfigFactory.empty();
		if (commandLine.hasOption(OptionsFactory.OPTION_CONFIG_FILE)) {
			File configFile = new File(commandLine.getOptionValue(OptionsFactory.OPTION_CONFIG_FILE));
			externalConfig = externalConfig.withFallback(ConfigFactory.parseFile(configFile));
		}
		return externalConfig;
	}

	private static Config getOverridingConfig(CommandLine commandLine) {
		Config overridingConfig = ConfigFactory.empty();
		if (commandLine.hasOption(OptionsFactory.OPTION_CONFIG_OVERRIDE)) {
			for (String setting : commandLine.getOptionValues(OptionsFactory.OPTION_CONFIG_OVERRIDE)) {
				String[] pair = setting.split("=", 2);
				overridingConfig = overridingConfig.withValue(pair[0], ConfigValueFactory.fromAnyRef(pair[1]));
			}
		}
		return overridingConfig;
	}

	public CommandLine getCli() {
		return commandLine;
	}

	public Config getConfig() {
		return config;
	}

	// FIXME
	public Config loadConfigFile(String moduleName) {
		InputStream stream = CommandLineParser.class.getResourceAsStream(String.format("%s.conf", moduleName));
		if (stream == null) {
			stream = CommandLineParser.class.getResourceAsStream(String.format("/%s.conf", moduleName));
		}
		return ConfigFactory.parseReader(new InputStreamReader(stream));
	}

}
