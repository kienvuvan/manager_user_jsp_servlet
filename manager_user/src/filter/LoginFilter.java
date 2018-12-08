package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.UserDatabase;
import bean.User;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);
		try {
			String loginURI = request.getContextPath() + "/home";
			boolean loggedIn = session != null && session.getAttribute("user") != null;
			boolean loginRequest = request.getRequestURI().equals(loginURI);
			String errorAccount = "";
			String userNameSave = "";
			if (loggedIn || loginRequest) {
				filterChain.doFilter(request, response);
				return;
			} else {
				String userName = request.getParameter("loginId");
				String passWord = request.getParameter("password");
				UserDatabase userDatabase = new UserDatabase();
				if (userDatabase.findAccount(userName, passWord)) {
					User user = new User();
					user.setLoginName(userName);
					user.setPassword(passWord);
					request.getSession().setAttribute("user", user);
					filterChain.doFilter(request, response);
					return;
				} else {
					if (userName != null && passWord != null) {
						errorAccount = "Tai khoan hoac mat khau khong chinh xac";
						userNameSave = userName;
					}
				}
			}
			request.getSession().setAttribute("errorAccount", errorAccount);
			request.getSession().setAttribute("userNameSave", userNameSave);
			response.sendRedirect(loginURI);
		} catch (Exception e) {
			 RequestDispatcher requestDispatcher = request.getServletContext()
			 .getRequestDispatcher("/views/System_Error.jsp");
			 requestDispatcher.forward(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
