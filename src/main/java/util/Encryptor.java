package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

public class Encryptor {
	private static final Logger log = Logger.getLogger(Encryptor.class);
	public static final String key = System.getenv("KEY");

	public String encryptStringValue(String value) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key);

		return encryptor.encrypt(value);
	}

	public Properties decryptProperties(String pathToConfig) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(key);

		Properties properties = new EncryptableProperties(encryptor);
		try {
			properties.load(new FileInputStream(pathToConfig));
		} catch (IOException e) {
			log.error(e);
		}
		return properties;
	}

}
