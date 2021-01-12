package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * The {@code Config} is an utility class which provides properties
 * 
 * @author Margarita Skokova
 */
public class Config {
	private static final Logger log = Logger.getLogger(Config.class);
	private static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static String rootPathWebDB = System.getProperty("user.home");

	private static Properties properties;

	public enum Key {
		JDBC_DRIVER("driver"), DB_URL("host"), DB_USER("username"), DB_PASSWORD("password"), DB_TYPE("db.type"),
		GEOCODER_URL("geocoder.url"), GEOCODER_KEY("geocoder.key"), GEOCODER_VALUE("geocoder.value"),
		GEOCODER_OUTPUT_FORMAT("geocoder.format"), GEOCODER_LANG("geocoder.language"), WEATHER_URL("weather.url"),
		WEATHER_KEY("weather.key"), WEATHER_VALUE("weather.value"), DB_LANG("db.lang"),;

		String name;

		Key(String name) {
			this.name = name;
		}
	}

	public static Optional<String> getConfig(Key key) {
		return connectToProperties(key, rootPath + "config.properties");
	}

	public static Optional<String> getConfigDB(Key key) {

		switch (getConfig(Key.DB_TYPE).get()) {
		case "local":
			return connectToProperties(key, rootPath + "config.properties");
		case "webhosting":
			return connectToProperties(key, rootPath + "/mydb.cfg");
		default:
			return Optional.empty();
		}
	}

	private static Optional<String> connectToProperties(Key key, String pathToConfig) {
		// Properties properties = new Encryptor().decryptProperties(pathToConfig);
		try (FileInputStream file = new FileInputStream(pathToConfig)) {
			properties = new Properties();
			properties.load(file);
		} catch (Exception e) {
			log.error(e);
		}

		return Optional.ofNullable(properties.get(key.name).toString());
	}

}
