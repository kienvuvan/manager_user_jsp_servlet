/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserDao.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;

/**
 * @author kien vu
 *
 */
public interface TblUserDao extends BaseDao {

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
	 * @throws Exception 
	 */
	public TblUser getUserByLogIn(String username) throws ClassNotFoundException, SQLException;

	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException;

	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException;
}
