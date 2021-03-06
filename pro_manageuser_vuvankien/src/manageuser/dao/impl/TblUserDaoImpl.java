/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserDaoImpl.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.utils.Constant;

/**
 * @author kien vu
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public TblUserDaoImpl() throws ClassNotFoundException, SQLException {
		super();
	}

	private Connection connection;
	private static final String GET_USER_BY_ID = "SELECT pass, salt FROM tbl_user WHERE rule = 0 AND login_name = ?";
	private static final String GET_TOTAL_USERS = "SELECT COUNT(*) as number " + "FROM tbl_user "
			+ "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan " + "INNER JOIN mst_japan "
			+ "ON mst_japan.code_level = tbl_detail_user_japan.code_level ) "
			+ "ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE 1=1 ";
	private static final String GET_LIST_USER = "SELECT tbl_user.user_id,tbl_user.full_name,tbl_user.birthday,"
			+ "mst_group.group_name, tbl_user.email,tbl_user.tel, mst_japan.name_level, "
			+ "tbl_detail_user_japan.end_date, tbl_detail_user_japan.total " + "FROM tbl_user "
			+ "INNER JOIN mst_group " + "ON mst_group.group_id = tbl_user.group_id "
			+ "LEFT JOIN (tbl_detail_user_japan "
			+ "INNER JOIN mst_japan  ON mst_japan.code_level = tbl_detail_user_japan.code_level )"
			+ " ON tbl_user.user_id = tbl_detail_user_japan.user_id WHERE 1=1 ";

	/**
	 * Phương thức kiểm tra tài khoản có tồn tại không?
	 * 
	 * @param username
	 *            Tên tài khoản người dùng nhập
	 * @param password
	 *            Mật khẩu người dùng nhập
	 * @return true Nếu tài khoản đúng và ngược lại
	 * @exception NoSuchAlgorithmException
	 * @exception ClassNotFoundException
	 * @exception SQLException
	 */
	@Override
	public TblUser getUserByLogIn(String username) throws ClassNotFoundException, SQLException {
		TblUser tblUser = new TblUser();
		try {
			// Tạo kết nối với database
			connection = connectDatabase();
			// Nếu thành công
			if (connection != null) {
				// Tạo lệnh truy vấn lấy ra tài khoản có tên tài khoản tồn tại
				// trong CSDL và có quyền admin
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(GET_USER_BY_ID);
				preparedStatement.setString(1, username);
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				// Nếu tài khoản tồn tại
				if (resultSet.next()) {
					int index = 1;
					// Lấy thông tin pass
					tblUser.setPassword(resultSet.getString(index++));
					// Lấy thông tin phần salt để tạo ra pass
					tblUser.setSalt(resultSet.getString(index++));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		// Tài khoản hoặc mật khẩu sai
		return tblUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getTotalUsers(int, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException {
		try {
			connection = connectDatabase();
			if (connection != null) {
				StringBuilder stringBuilderQueryUser = new StringBuilder(GET_TOTAL_USERS);
				if (groupId > 0) {
					stringBuilderQueryUser.append(" AND mst_group.group_id = ?");
				}
				if (!"".equals(fullName)) {
					stringBuilderQueryUser.append(" AND tbl_user.full_name LIKE ?");
				}
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(stringBuilderQueryUser.toString());
				int index = 1;
				if (groupId > 0) {
					preparedStatement.setInt(index++, groupId);
				}
				if (!"".equals(fullName)) {
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDao#getListUsers(int, int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException {
		ArrayList<UserInfor> listUserInfor = new ArrayList<>();
		try {
			connection = connectDatabase();
			if (connection != null) {
				StringBuilder stringBuilderQueryUser = new StringBuilder(GET_LIST_USER);
				if (groupId > 0) {
					stringBuilderQueryUser.append(" AND mst_group.group_id = ?");
				}
				if (!"".equals(fullName)) {
					stringBuilderQueryUser.append(" AND tbl_user.full_name LIKE ?");
				}
				stringBuilderQueryUser
						.append(" ORDER BY " + getOrderBy(sortType, sortByFullName, sortByCodeLevel, sortByEndDate));
				stringBuilderQueryUser.append(" LIMIT " + offset + " , " + limit);
				PreparedStatement preparedStatement = (PreparedStatement) connection
						.prepareStatement(stringBuilderQueryUser.toString());
				int index = 1;
				if (groupId > 0) {
					preparedStatement.setInt(index++, groupId);
				}
				if (!"".equals(fullName)) {
					preparedStatement.setString(index++, "%" + fullName + "%");
				}
				// Trả về bản truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					index = 1;
					int userId = resultSet.getInt(index++);
					String name = resultSet.getString(index++);
					Date birthday = resultSet.getDate(index++);
					String groupName = resultSet.getString(index++);
					String email = resultSet.getString(index++);
					String tel = resultSet.getString(index++);
					String nameLevel = resultSet.getString(index++);
					Date endDate = resultSet.getDate(index++);
					int total = resultSet.getInt(index++);
					UserInfor userInfor = new UserInfor();
					userInfor.setUserId(userId);
					userInfor.setFullName(name);
					userInfor.setBirthday(birthday);
					userInfor.setGroupName(groupName);
					userInfor.setEmail(email);
					userInfor.setTel(tel);
					userInfor.setNameLevel(nameLevel);
					userInfor.setEndDate(endDate);
					userInfor.setTotalScore(total);
					listUserInfor.add(userInfor);
				}

			}
		} catch (ClassNotFoundException | SQLException e) {
			e.getMessage();
			throw e;
		}
		return listUserInfor;
	}

	private String getOrderBy(String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) {
		String orderBy = "";
		switch (sortType) {
		case Constant.FULL_NAME_COLUMN:
			orderBy = " " + getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName + " , "
					+ getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
					+ getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate;
			break;
		case Constant.CODE_LEVEL_COLUMN:
			orderBy = " " + getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
					+ getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName + " , "
					+ getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate;
			break;
		case Constant.END_DATE_COLUMN:
			orderBy = " " + getData(Constant.END_DATE_COLUMN) + " " + sortByEndDate + " , "
					+ getData(Constant.CODE_LEVEL_COLUMN) + " " + sortByCodeLevel + " , "
					+ getData(Constant.FULL_NAME_COLUMN) + " " + sortByFullName;
			break;
		}
		return orderBy;
	}

}
