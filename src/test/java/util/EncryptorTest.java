package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import util.Config.Key;

class EncryptorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testEncrypt() {
		System.out.println(Config.getConfig(Key.GEOCODER_KEY));
		System.out.println(Config.getConfig(Key.GEOCODER_VALUE));

		System.out.println(new Encryptor().encryptStringValue("aff42e4f-aeb9-4eb0-b1cb-b3cca4c7971b"));
		System.out.println(Config.getConfig(Key.WEATHER_VALUE));
	}

}
