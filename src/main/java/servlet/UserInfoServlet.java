package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserInfoDAO;
import model.User;
import model.UserInfo;

@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String USER_INFO_JSP_PATH = "WEB-INF/jsp/userInfo.jsp";
	private static final String SESSION_USER_KEY = "user";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String REQUEST_USER_INFO_KEY = "userInfo";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		User user = getSessionUser(session);
		if (user == null || isEmpty(user.getUserId())) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		UserInfo userInfo = loadUserInfo(user.getUserId());
		request.setAttribute(REQUEST_USER_INFO_KEY, userInfo);

		forwardToUserInfoPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private User getSessionUser(HttpSession session) {
		return session == null ? null : (User) session.getAttribute(SESSION_USER_KEY);
	}

	private UserInfo loadUserInfo(String userId) {
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		return userInfoDAO.findByUserId(userId);
	}

	private void forwardToUserInfoPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(USER_INFO_JSP_PATH);
		dispatcher.forward(request, response);
	}
}