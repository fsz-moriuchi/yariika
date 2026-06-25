package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.UserLogin;
import model.UserLoginLogic;
import util.PasswordUtil;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String USER_LOGIN_JSP_PATH = "WEB-INF/jsp/userLogin.jsp";
	private static final String HOME_REDIRECT_URL = "HomeServlet";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_USER_KEY = "user";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_USER_ID_KEY = "userId";
	private static final String REQUEST_PASSWORD_KEY = "password";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String ERROR_LOGIN_FAILED_MESSAGE = "ログインに失敗しました";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forwardToLoginPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String userId = request.getParameter(REQUEST_USER_ID_KEY);
		String password = request.getParameter(REQUEST_PASSWORD_KEY);

		if (isEmpty(userId) || isEmpty(password)) {
			showLoginFailure(request, response);
			return;
		}

		HttpSession session = request.getSession();
		UserLoginLogic loginLogic = new UserLoginLogic();
		UserLogin login = new UserLogin(userId, PasswordUtil.hashPassword(password));

		boolean authenticated = loginLogic.execute(login);
		if (!authenticated) {
			showLoginFailure(request, response);
			return;
		}

		User user = loginLogic.getUser(login);
		if (user == null) {
			showLoginFailure(request, response);
			return;
		}

		session.removeAttribute(SESSION_FACILITY_ID_KEY);
		session.setAttribute(SESSION_USER_ID_KEY, userId);
		session.setAttribute(SESSION_USER_KEY, user);
		response.sendRedirect(HOME_REDIRECT_URL);
	}

	private boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private void showLoginFailure(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, ERROR_LOGIN_FAILED_MESSAGE);
		forwardToLoginPage(request, response);
	}

	private void forwardToLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(USER_LOGIN_JSP_PATH);
		dispatcher.forward(request, response);
	}
}