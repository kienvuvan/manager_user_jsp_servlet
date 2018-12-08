/**
 * Copyright (C) 2018 Luvina Academy
 * CommonDatabase.java 12/11/2018, VÅ© VÄƒn KiÃªn
 */
package common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import properties.ReadPropertiesDatabase;

/**
 * Class kết nối CSDL Mysql
 * 
 * @author kien vu
 */
public class CommonDatabase {

	private static CommonDatabase instance;
	private static Connection connection;
	private static String url;
	private static String database;
	private static String driver;
	public static String username;
	private static String password;

	/**
	 * Khởi tạo đối tương CommonDatabase để kết nối với Database trong CSDL
	 * 
	 * @throws SQLException
	 *             Kết nối với CSDL bị lỗi
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private CommonDatabase() throws SQLException, ClassNotFoundException {
		try {
			// Đọc các thông số kết nối database trong file properties
			url = ReadPropertiesDatabase.getData("url");
			database = ReadPropertiesDatabase.getData("database");
			driver = ReadPropertiesDatabase.getData("driver");
			username = ReadPropertiesDatabase.getData("username");
			password = ReadPropertiesDatabase.getData("password");
			Class.forName(driver);
			// Kết nối database
			connection = DriverManager.getConnection(url + database, username, password);
		} catch (ClassNotFoundException | SQLException ex) {
			System.out.println("Lỗi kết nối database"+ex);
			throw ex;
		}
	}

	/**
	 * Phương thức trả về 1 kết nối với CSDL Mysql
	 * 
	 * @return 1 kết nối tới CSDL Mysql
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Phương thức thực hiện đóng kết nối với database
	 * 
	 * @throws SQLException
	 *             Xảy ra lỗi khi không đóng được kết nối
	 */
	public static void closeConnection() throws Exception {
		try {
			if (connection != null || !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	/**
	 * Phương thức trả về 1 đối tượng duy nhất của Mysql.Nếu đối tượng đã khởi
	 * tạo thì trả về luôn, còn chưa tồn tại thì tạo mới
	 * 
	 * @return đối tượng Mysql duy nhất
	 * @throws SQLException
	 *             Kết nối với CSDL bị lỗi
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static CommonDatabase getInstance() throws SQLException, ClassNotFoundException {
		try {
			if (instance == null) {
				instance = new CommonDatabase();
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return instance;
	}
}
