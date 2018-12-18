/**
 * Copyright (C) 2018 Luvina Academy
 * MsrGroupDaoImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroup;
import manageuser.utils.Constant;

/**
 * @author kien vu
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {
	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MstGroupDaoImpl() throws ClassNotFoundException, SQLException {
		super();
	}

	private Connection connection;
	private static final String GET_ALL_GROUP = "SELECT * FROM mst_group";

	/**
	 * @throws ClassNotFoundException,
	 *             SQLException
	 * 
	 */
	@Override
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException {
		ArrayList<MstGroup> listAllGroup = new ArrayList<>();
		MstGroup mstGroupAll = new MstGroup();
		mstGroupAll.setGroupId(0);
		mstGroupAll.setGroupName(Constant.ALL);
		listAllGroup.add(mstGroupAll);
		// Tạo kết nối với database
		try {
			connection = connectDatabase();
			// Nếu thành công
			if (connection != null) {
				Statement statement = (Statement) connection.createStatement();
				ResultSet resultSet = statement.executeQuery(GET_ALL_GROUP);
				while (resultSet.next()) {
					int index = 1;
					MstGroup mstGroup = new MstGroup();
					mstGroup.setGroupId(resultSet.getInt(index++));
					mstGroup.setGroupName(resultSet.getString(index++));
					listAllGroup.add(mstGroup);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return listAllGroup;
	}

}
