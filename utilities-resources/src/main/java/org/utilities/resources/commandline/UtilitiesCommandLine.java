package org.utilities.resources.commandline;

import java.util.ArrayList;
import java.util.List;

import org.utilities.io.json.UtilitiesJSON;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;

public class UtilitiesCommandLine {

	private static final ConfigRenderOptions RENDER_OPTIONS = ConfigRenderOptions.defaults()
			.setComments(false)
			.setOriginComments(false)
			.setFormatted(false);

	public static Config getConfig(String[] args, String... modules) {
		CommandLineParser commandLineParser = new CommandLineParser(OptionsFactory.basic(), args, modules);
		return commandLineParser.getConfig();
	}

	public static <T> T getObject(Config config, String key, Class<T> type) {
		return parse(config.getValue(key), type);
	}

	public static <T> List<T> getObjects(Config config, String key, Class<T> type) {
		List<T> values = new ArrayList<>();
		for (ConfigValue value : config.getList(key)) {
			values.add(parse(value, type));
		}
		return values;
	}

	public static <T> T parse(ConfigValue value, Class<T> type) {
		return UtilitiesJSON.jsonToClass(value.render(RENDER_OPTIONS), type);
	}

}
