package servlet;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserInfoDAO;
import dao.UsersDAO;
import model.User;
import model.UserInfo;
import util.PasswordUtil;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String USER_REGISTER_JSP_PATH = "WEB-INF/jsp/userRegister.jsp";
	private static final String USER_LOGIN_REDIRECT_URL = "UserLoginServlet";
	private static final String REQUEST_USER_ID_KEY = "userId";
	private static final String REQUEST_PASSWORD_KEY = "password";
	private static final String REQUEST_USER_NAME_KEY = "userName";
	private static final String REQUEST_USER_GENDER_KEY = "userGender";
	private static final String REQUEST_USER_BIRTHDAY_KEY = "userBirthday";
	private static final String REQUEST_USER_TEL_KEY = "userTel";
	private static final String REQUEST_USER_MAIL_KEY = "userMail";
	private static final String REQUEST_USER_ADDRESS_KEY = "userAddress";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String ERROR_DUPLICATE_USER_ID_MESSAGE = "そのユーザーIDは既に使用されています";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forwardToRegisterPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		UserInfo userInfo = buildUserInfo(request);
		User user = buildUser(request);

		UsersDAO usersDAO = new UsersDAO();
		if (usersDAO.registerUser(user)) {
			saveUserInfo(userInfo);
			response.sendRedirect(USER_LOGIN_REDIRECT_URL);
		} else {
			showErrorAndReturn(request, response, ERROR_DUPLICATE_USER_ID_MESSAGE);
		}
	}

	private UserInfo buildUserInfo(HttpServletRequest request) {
		String userId = request.getParameter(REQUEST_USER_ID_KEY);
		String userName = request.getParameter(REQUEST_USER_NAME_KEY);
		String userGender = request.getParameter(REQUEST_USER_GENDER_KEY);
		Date userBirthday = Date.valueOf(request.getParameter(REQUEST_USER_BIRTHDAY_KEY));
		String userTel = request.getParameter(REQUEST_USER_TEL_KEY);
		String userMail = request.getParameter(REQUEST_USER_MAIL_KEY);
		String userAddress = request.getParameter(REQUEST_USER_ADDRESS_KEY);

		return new UserInfo(0, userId, userName, userGender, userBirthday, userTel, userMail, userAddress);
	}

	private User buildUser(HttpServletRequest request) {
		String userId = request.getParameter(REQUEST_USER_ID_KEY);
		String password = request.getParameter(REQUEST_PASSWORD_KEY);
		return new User(userId, PasswordUtil.hashPassword(password));
	}

	private void saveUserInfo(UserInfo userInfo) {
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		userInfoDAO.insert(userInfo);
	}

	private void showErrorAndReturn(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, message);
		forwardToRegisterPage(request, response);
	}

	private void forwardToRegisterPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(USER_REGISTER_JSP_PATH);
		dispatcher.forward(request, response);
	}
}