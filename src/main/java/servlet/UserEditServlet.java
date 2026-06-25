package servlet;

import java.io.IOException;
import java.sql.Date;

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

@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String USER_EDIT_JSP_PATH = "WEB-INF/jsp/userEdit.jsp";
	private static final String USER_INFO_REDIRECT_URL = "UserInfoServlet";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_USER_KEY = "user";
	private static final String REQUEST_USER_INFO_KEY = "userInfo";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String REQUEST_USER_INFO_ID_KEY = "userInfoId";
	private static final String REQUEST_USER_NAME_KEY = "userName";
	private static final String REQUEST_USER_GENDER_KEY = "userGender";
	private static final String REQUEST_USER_BIRTHDAY_KEY = "userBirthday";
	private static final String REQUEST_USER_TEL_KEY = "userTel";
	private static final String REQUEST_USER_MAIL_KEY = "userMail";
	private static final String REQUEST_USER_ADDRESS_KEY = "userAddress";
	private static final String ERROR_NO_USER_INFO_MESSAGE = "個人情報が未登録のため更新できません";
	private static final String ERROR_INVALID_ACCESS_MESSAGE = "不正なアクセスです";
	private static final String ERROR_UPDATE_FAILED_MESSAGE = "更新に失敗しました";

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
		forwardToUserEditPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

		String userId = user.getUserId();
		UserInfoDAO userInfoDAO = new UserInfoDAO();

		UserInfo existingInfo = loadUserInfo(userInfoDAO, userId);
		if (existingInfo == null) {
			showError(request, response, ERROR_NO_USER_INFO_MESSAGE);
			return;
		}

		String userInfoIdString = request.getParameter(REQUEST_USER_INFO_ID_KEY);
		if (isEmpty(userInfoIdString)) {
			showError(request, response, ERROR_INVALID_ACCESS_MESSAGE);
			return;
		}

		Integer userInfoId = parseUserInfoId(userInfoIdString);
		if (userInfoId == null) {
			showError(request, response, ERROR_INVALID_ACCESS_MESSAGE);
			return;
		}

		UserInfo updatedUserInfo = buildUserInfo(request, userInfoDAO, userId, userInfoId);
		boolean result = userInfoDAO.updateInfo(updatedUserInfo);

		if (result) {
			response.sendRedirect(USER_INFO_REDIRECT_URL);
		} else {
			showError(request, response, ERROR_UPDATE_FAILED_MESSAGE);
		}
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

	private UserInfo loadUserInfo(UserInfoDAO userInfoDAO, String userId) {
		return userInfoDAO.findByUserId(userId);
	}

	private Integer parseUserInfoId(String userInfoIdString) {
		try {
			return Integer.parseInt(userInfoIdString);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private UserInfo buildUserInfo(HttpServletRequest request, UserInfoDAO userInfoDAO, String userId,
			Integer userInfoId) {
		String userName = request.getParameter(REQUEST_USER_NAME_KEY);
		String userGender = request.getParameter(REQUEST_USER_GENDER_KEY);
		Date userBirthday = resolveUserBirthday(request, userInfoDAO, userId);
		String userTel = request.getParameter(REQUEST_USER_TEL_KEY);
		String userMail = request.getParameter(REQUEST_USER_MAIL_KEY);
		String userAddress = request.getParameter(REQUEST_USER_ADDRESS_KEY);

		return new UserInfo(userInfoId, userId, userName, userGender, userBirthday, userTel, userMail, userAddress);
	}

	private Date resolveUserBirthday(HttpServletRequest request, UserInfoDAO userInfoDAO, String userId) {
		String birthdayString = request.getParameter(REQUEST_USER_BIRTHDAY_KEY);
		if (birthdayString != null && !birthdayString.isEmpty()) {
			try {
				return Date.valueOf(birthdayString);
			} catch (IllegalArgumentException e) {
				UserInfo oldInfo = userInfoDAO.findByUserId(userId);
				return oldInfo.getUserBirthday();
			}
		}
		UserInfo oldInfo = userInfoDAO.findByUserId(userId);
		return oldInfo.getUserBirthday();
	}

	private void showError(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, message);
		forwardToUserEditPage(request, response);
	}

	private void forwardToUserEditPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(USER_EDIT_JSP_PATH);
		dispatcher.forward(request, response);
	}
}