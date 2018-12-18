/**
 * Copyright (C) 2018 Luvina Academy
 * TblUserLogic.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.UserInfor;

/**
 * @author kien vu
 *
 */
public interface TblUserLogic {
	public boolean checkExitsAccount(String username, String password)
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException, Exception;

	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException, Exception;

	public ArrayList<UserInfor> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws ClassNotFoundException, SQLException, Exception;
}
