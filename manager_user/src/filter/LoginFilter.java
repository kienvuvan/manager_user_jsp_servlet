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

import dao.UserDatabase;
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
			String loginURI = request.getContextPath() + "/login";
			String ADM002_URI = request.getContextPath() + "/ADM002";
			boolean loggedIn = session != null && session.getAttribute("user") != null;
			boolean loginRequest = request.getRequestURI().equals(loginURI);
			boolean ADM002_Request = request.getRequestURI().equals(ADM002_URI);
			UserDatabase userDatabase = new UserDatabase();
			StringBuilder error = new StringBuilder("");
			String username = "";
			// Nếu link là link trang đăng nhập
			if (loginRequest) {
				error.delete(0, error.length());
				username = "";
				filterChain.doFilter(request, response);
				return;
				// Nếu link là link vào trang ADM002.jsp
			} else if (ADM002_Request) {
				username = request.getParameter("loginId");
				String password = request.getParameter("password");
				if ("".equals(username)) {
					error.append("アカウント名を入力してください<br>");
				}
				if ("".equals(password)) {
					error.append("パスワードを入力してください");
				}
				if ("".equals(error.toString())) {
					if (userDatabase.findAccount(username, password)) {
						User user = new User();
						user.setLoginName(username);
						user.setPassword(password);
						session.setAttribute("user", user);
						filterChain.doFilter(request, response);
						return;
					} else {
						if (username != null && password != null) {
							error.append("アカウント名または パスワードは不正です。");
						}
					}
				}
			} else {
				if (loggedIn) {
					User userLoggedIn = (User) session.getAttribute("user");
					if (userDatabase.findAccount(userLoggedIn.getLoginName(), userLoggedIn.getPassword())) {
						RequestDispatcher requestDispatcher = request.getServletContext()
								.getRequestDispatcher("/views/ADM002.jsp");
						requestDispatcher.forward(request, response);
						return;
					}
				}
			}
			request.getSession().setAttribute("errorAccount", error.toString());
			request.getSession().setAttribute("usernameSave", username);
			response.sendRedirect("login");
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