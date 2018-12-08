/**
 * Copyright (C) 2018 Luvina Academy
 * ReadPropertiesDatabase.java 12/11/2018, VÅ© VÄƒn KiÃªn
 */
package properties;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

@SuppressWarnings("unchecked")
/**
 * Class dùng để đọc file config.properties để lấy các thuộc tính của CSDL
 * 
 * @author kien vu
 *
 */
public class ReadPropertiesDatabase {
	private static HashMap<String, String> hashMapDatabase = new HashMap<>();

	/**
	 * Đọc file properties và bỏ vào trong map
	 */
	static {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			hashMapDatabase.put(key, properties.getProperty(key));
		}
	}

	/**
	 * Hàm đọc từ trong map
	 * 
	 * @param key:
	 *            key cần đọc
	 * 
	 * @return giá trị của key
	 */
	public static String getData(String key) {
		String value = "";
		if (hashMapDatabase.containsKey(key)) {
			value = hashMapDatabase.get(key);
		}
		return value;
	}

}
