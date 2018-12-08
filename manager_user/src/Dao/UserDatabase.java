package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.CommonDatabase;

public class UserDatabase {

	private static Connection connection;
	private static final String LOGIN_ACCOUNT = "SELECT * FROM tbl_user WHERE login_name = ? AND pass = ?";
	
	public boolean findAccount(String userName, String passWord) throws ClassNotFoundException, SQLException {
		try{
			connection = CommonDatabase.getInstance().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_ACCOUNT);
			int index = 1;
			preparedStatement.setString(index++, userName);
			preparedStatement.setString(index++, passWord);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}
		}catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return false;
	}
	
}
