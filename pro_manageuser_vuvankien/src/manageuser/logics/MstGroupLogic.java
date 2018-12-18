/**
 * Copyright (C) 2018 Luvina Academy
 * MstGroupLogic.java Dec 13, 2018, Vu Van Kien
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.ArrayList;

import manageuser.entities.MstGroup;

/**
 * @author kien vu
 *
 */
public interface MstGroupLogic {
	public ArrayList<MstGroup> getAllGroups() throws ClassNotFoundException, SQLException, Exception;
}
