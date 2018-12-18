/**
 * Copyright (C) 2018 Luvina Academy
 * ListUserController.java Dec 11, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.ConfigProperties;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * @author kien vu
 *
 */
public class ListUserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// Get session
		HttpSession session = request.getSession(true);
		try {
			// Nếu người dùng chưa login
			if (!Common.checkLogin(session)) {
				// Chuyển về trang đăng nhập
				response.sendRedirect(Constant.LOGIN_URL);
				// Nếu đã đăng nhập
			} else {
				MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
				// Lấy toàn bộ danh sách group
				ArrayList<MstGroup> listAllGroups = mstGroupLogicImpl.getAllGroups();
				// Set giá trị listAllGroups lên request để truyền cho màn hình
				// ADM002.jsp
				request.setAttribute("listAllGroups", listAllGroups);
				// Lấy giá trị hiển thị tối đa số bản trên 1 page
				int limit = ConfigProperties.getData("SIZE_RECORD");
				// Lấy giá trị hiển thị số lượng page
				int sizePaning = ConfigProperties.getData("SIZE_PANNING");
				ArrayList<Integer> listPaning = new ArrayList<>();
				ArrayList<UserInfor> listUserInfor = new ArrayList<>();
				int totalUser = 0;
				// Lấy giá trị kiểu sắp xếp theo tên được truyền từ bên view
				// ADM002.jsp
				String typeSortName = (String) request.getParameter("typeSortName");
				// Nếu không có giá trị thì gán mặc định là sắp xếp tăng
				if (typeSortName == "" || typeSortName == null) {
					typeSortName = Constant.SORTASC;
				}
				// Lấy giá trị kiểu sắp xếp theo trình độ tiếng nhật được truyền
				// từ bên view ADM002.jsp
				String typeSortCodeLevel = (String) request.getParameter("typeSortCodeLevel");
				// Nếu không có giá trị thì gán mặc định là sắp xếp giảm
				if (typeSortCodeLevel == "" || typeSortCodeLevel == null) {
					typeSortCodeLevel = Constant.SORTDESC;
				}
				// Lấy giá trị kiểu sắp xếp theo ngày hết hạn được truyền từ bên
				// view ADM002.jsp
				String typeSortEndDate = (String) request.getParameter("typeSortEndDate");
				// Nếu không có giá trị thì gán mặc định là sắp xếp tăng
				if (typeSortEndDate == "" || typeSortEndDate == null) {
					typeSortEndDate = Constant.SORTDESC;
				}
				// Lấy giá trị kiểu hiển thị màn hình ADM002.jsp
				String typeShow = (String) request.getParameter("typeShow");
				// Nếu không có giá trị mặc định là kiểu đăng nhập hoặc tìm kiếm
				if (typeShow == null) {
					typeShow = Constant.TYPE_LOGIN_OR_SEARCH;
				}
				int groupId = 0;
				TblUserLogic tblUserLogic = new TblUserLogicImpl();
				// Lấy giá trị groupId từ selectBox
				String groupIdString = request.getParameter("groupId");
				// Lấy giá trị name từ text tìm kiếm
				String fullname = request.getParameter("fullname");
				groupId = 0;
				// Chuyển giá trị groupId sang giá trị số
				if (groupIdString != null) {
					groupId = Integer.parseInt(groupIdString);
				}
				int paningCurrent = 1;
				String columnFirst = "";
				int paningFirst = 1;
				int offset = 0;
				// Nếu kiểu vào để hiển thị màn hình ADM002.jsp là login hoặc
				// search
				if (Constant.TYPE_LOGIN_OR_SEARCH.equals(typeShow)) {
					columnFirst = Constant.FULL_NAME_COLUMN;
					// Nếu là trường hợp sắp xếp
				} else if (typeShow.startsWith("sort")) {
					fullname = (String) session.getAttribute("fullname");
					groupId = (int) session.getAttribute("groupId");
					switch (typeShow) {
					case Constant.TYPE_SORT_FULL_NAME:
						typeSortName = Common.changeSort(typeSortName);
						columnFirst = Constant.FULL_NAME_COLUMN;
						break;
					case Constant.TYPE_SORT_CODE_LEVEL:
						typeSortCodeLevel = Common.changeSort(typeSortCodeLevel);
						columnFirst = Constant.CODE_LEVEL_COLUMN;
						break;
					case Constant.TYPE_SORT_END_DATE:
						typeSortEndDate = Common.changeSort(typeSortEndDate);
						columnFirst = Constant.END_DATE_COLUMN;
						break;
					}
				} else if (Constant.TYPE_PANING.equals(typeShow)) {
					String page = request.getParameter("page");
					fullname = (String) session.getAttribute("fullname");
					groupId = (int) session.getAttribute("groupId");
					typeSortName = (String) session.getAttribute("typeSortName");
					typeSortCodeLevel = (String) session.getAttribute("typeSortCodeLevel");
					typeSortEndDate = (String) session.getAttribute("typeSortEndDate");
					columnFirst = (String) session.getAttribute("columnFirst");
					int pageNext= Integer.parseInt(page);
					offset = (pageNext-1)*limit;
				}
				// Lấy giá trị tổng bản ghi tìm kiếm được
				totalUser = tblUserLogic.getTotalUsers(groupId, fullname);
				listPaning = Common.getListPaning(totalUser, limit, paningFirst);
				listUserInfor = tblUserLogic.getListUsers(offset, limit, groupId, fullname, columnFirst, typeSortName,
						typeSortCodeLevel, typeSortEndDate);
				if (listUserInfor.isEmpty()) {
					request.setAttribute("listEmpty", MessageProperties.getData("LIST_EMPTY"));
				}
				session.setAttribute("columnFirst", columnFirst);
				session.setAttribute("typeSortName", typeSortName);
				session.setAttribute("typeSortCodeLevel", typeSortCodeLevel);
				session.setAttribute("typeSortEndDate", typeSortEndDate);
				request.setAttribute("paningCurrent", paningCurrent);
				request.setAttribute("totalUser", totalUser);
				session.setAttribute("fullname", fullname);
				session.setAttribute("groupId", groupId);
				request.setAttribute("limit", limit);
				request.setAttribute("sizePaning", sizePaning);
				request.setAttribute("listPaning", listPaning);
				request.setAttribute("listUserInfor", listUserInfor);
				request.getRequestDispatcher(Constant.VIEW_ADM002).forward(request, response);
			}
		} catch (Exception e) {
			e.getMessage();
			response.sendRedirect(Constant.ERROR_URL);
		}
	}

}
