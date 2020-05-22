package com.s4n.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.Thread.currentThread;

@Slf4j
@Data

/**
 * Utility class for loading the app initial configuration
 */
public class Configuration {

	private static Properties properties;

	static {
		properties = new Properties();
		String propertiesPath = currentThread().getContextClassLoader().getResource("app.properties").getPath();
		try {
			properties.load(new FileInputStream(propertiesPath));
		} catch (IOException e) {
			String error = "Error iniciando la aplicacion. No se puede cargar la configuracion";
			log.error(error);
			throw new RuntimeException(error);
		}
	}

	public static String getProperty(String key) {
		return (String) properties.get(key);
	}

	public static void addProperty(String key, String value) {
		properties.setProperty(key, value);
	}


}
