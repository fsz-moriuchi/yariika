package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserInfoDAO;
import dao.UsersDAO;
import model.User;
import model.UserInfo;
import model.UserSurvey;

@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String USER_LOGIN_REDIRECT_URL = "UserLoginServlet";
	private static final String MY_PAGE_JSP_PATH = "WEB-INF/jsp/mypage.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_USER_KEY = "user";
	private static final String REQUEST_USER_INFO_KEY = "userInfo";
	private static final String REQUEST_USER_SURVEY_LIST_KEY = "userSurveyList";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (isNotLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		User loginUser = (User) session.getAttribute(SESSION_USER_KEY);
		String userId = loginUser.getUserId();

		if (userId == null) {
			response.sendRedirect(USER_LOGIN_REDIRECT_URL);
			return;
		}

		UserInfo userInfo = fetchUserInfo(userId);
		List<UserSurvey> userSurveyList = fetchUserSurveyList(userId);

		request.setAttribute(REQUEST_USER_INFO_KEY, userInfo);
		request.setAttribute(REQUEST_USER_SURVEY_LIST_KEY, userSurveyList);

		forwardToMyPage(request, response);
	}

	private boolean isNotLoggedIn(HttpSession session) {
		return session == null || session.getAttribute(SESSION_USER_ID_KEY) == null;
	}

	private UserInfo fetchUserInfo(String userId) {
		UserInfoDAO userInfoDAO = new UserInfoDAO();
		return userInfoDAO.findByUserId(userId);
	}

	private List<UserSurvey> fetchUserSurveyList(String userId) {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.showUserSurvey(userId);
	}

	private void forwardToMyPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(MY_PAGE_JSP_PATH);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}