package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * The {@code PropertiesHolder} is an utility class which provides properties
 * 
 * @author Margarita Skokova
 */
public class PropertiesHolder {
	private static final Logger log = Logger.getLogger(PropertiesHolder.class);
	private static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static String rootPathWebDB = System.getProperty("user.home");

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

	public static String recieveConfig(Key key) {
		return connectToProperties(key, rootPath + "properties.properties");
	}

	public static String recieveConfigDB(Key key) {

		switch (recieveConfig(Key.DB_TYPE)) {
		case "local":
			return connectToProperties(key, rootPath + "properties.properties");
		case "webhosting":
			return connectToProperties(key, rootPathWebDB + "/mydb.cfg");
		}
		return rootPath;
	}

	private static String connectToProperties(Key key, String pathToConfig) {
		Properties properties = new Properties();
		try (FileInputStream file = new FileInputStream(pathToConfig)) {
			properties.load(file);
		} catch (IOException e) {
			log.error(e);
		}
		return properties.get(key.name).toString();
	}

}
