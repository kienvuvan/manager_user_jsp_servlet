/**
 * Copyright (C) 2018 Luvina Academy
 * SystemErrorController.java Dec 12, 2018, Vu Van Kien
 */
package manageuser.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kien vu
 *
 */
public class SystemErrorController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/views/System_Error.jsp");
		requestDispatcher.forward(request, response);
	}

}
