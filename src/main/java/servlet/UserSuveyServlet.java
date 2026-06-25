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

import dao.QuestionSurveyDAO;
import dao.SurveyChoiceDAO;
import dao.UsersDAO;
import model.Choice;
import model.Question;
import model.UserSurvey;

@WebServlet("/UserSuveyServlet")
public class UserSuveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String USER_SURVEY_JSP_PATH = "WEB-INF/jsp/userSurvey.jsp";
	private static final String USER_SURVEY_SUCCESS_JSP_PATH = "WEB-INF/jsp/userSurveySuccess.jsp";
	private static final String USER_UPDATE_SUCCESS_JSP_PATH = "WEB-INF/jsp/userUpdateSuccess.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String REQUEST_USER_SURVEY_LIST_KEY = "userSurveyList";
	private static final String REQUEST_QUESTION_LIST_KEY = "questionList";
	private static final String REQUEST_ALL_CHOICE_LIST_KEY = "allChoiceList";
	private static final String REQUEST_ACTION_KEY = "action";
	private static final String ACTION_REGISTER = "登録";
	private static final String ACTION_UPDATE = "更新";
	private static final String ERROR_REGISTER_FAILED_MESSAGE = "登録失敗";
	private static final String ERROR_UPDATE_FAILED_MESSAGE = "更新失敗";
	private static final String ERROR_INVALID_ACCESS_MESSAGE = "不正なアクセスです";
	private static final int QUESTION_COUNT = 14;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String userId = (String) session.getAttribute(SESSION_USER_ID_KEY);
		List<UserSurvey> userSurveyList = loadUserSurveyList(userId);
		List<Question> questionList = loadQuestionList();
		List<Choice> allChoiceList = loadAllChoiceList();

		request.setAttribute(REQUEST_USER_SURVEY_LIST_KEY, userSurveyList);
		request.setAttribute(REQUEST_QUESTION_LIST_KEY, questionList);
		request.setAttribute(REQUEST_ALL_CHOICE_LIST_KEY, allChoiceList);

		forwardToUserSurveyPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String action = request.getParameter(REQUEST_ACTION_KEY);
		String userId = (String) session.getAttribute(SESSION_USER_ID_KEY);

		if (isEmpty(action)) {
			showError(response, ERROR_INVALID_ACCESS_MESSAGE);
			return;
		}

		UsersDAO usersDAO = new UsersDAO();

		if (ACTION_REGISTER.equals(action)) {
			boolean result = saveNewUserSurvey(request, usersDAO, userId);
			if (result) {
				System.out.println("action = " + action);
				System.out.println("userId = " + userId);
				forwardToUserSurveySuccessPage(request, response);
			} else {
				showError(response, ERROR_REGISTER_FAILED_MESSAGE);
			}
		} else if (ACTION_UPDATE.equals(action)) {
			boolean result = updateExistingUserSurvey(request, usersDAO, userId);
			if (result) {
				forwardToUserUpdateSuccessPage(request, response);
			} else {
				showError(response, ERROR_UPDATE_FAILED_MESSAGE);
			}
		} else {
			showError(response, ERROR_INVALID_ACCESS_MESSAGE);
		}
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private List<UserSurvey> loadUserSurveyList(String userId) {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.showUserSurvey(userId);
	}

	private List<Question> loadQuestionList() {
		QuestionSurveyDAO questionSurveyDAO = new QuestionSurveyDAO();
		return questionSurveyDAO.findAllQuestion();
	}

	private List<Choice> loadAllChoiceList() {
		SurveyChoiceDAO surveyChoiceDAO = new SurveyChoiceDAO();
		return surveyChoiceDAO.findAllChoices();
	}

	private boolean saveNewUserSurvey(HttpServletRequest request, UsersDAO usersDAO, String userId) {
		return saveUserSurveyAnswers(request, usersDAO, userId, true);
	}

	private boolean updateExistingUserSurvey(HttpServletRequest request, UsersDAO usersDAO, String userId) {
		return saveUserSurveyAnswers(request, usersDAO, userId, false);
	}

	private boolean saveUserSurveyAnswers(HttpServletRequest request, UsersDAO usersDAO, String userId,
			boolean isRegister) {
		for (int questionId = 1; questionId <= QUESTION_COUNT; questionId++) {
			String choiceParam = request.getParameter("q" + questionId);
			Integer surveyChoiceId = parseSurveyChoiceId(choiceParam);
			if (surveyChoiceId == null) {
				return false;
			}

			UserSurvey userSurvey = new UserSurvey(userId, questionId, surveyChoiceId);
			boolean success = isRegister
					? usersDAO.createUserSurvey(userSurvey)
					: usersDAO.updateUserSurvey(userId, questionId, surveyChoiceId);

			if (!success) {
				return false;
			}
		}
		return true;
	}

	private Integer parseSurveyChoiceId(String value) {
		if (isEmpty(value)) {
			return null;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void forwardToUserSurveyPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(USER_SURVEY_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToUserSurveySuccessPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(USER_SURVEY_SUCCESS_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToUserUpdateSuccessPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(USER_UPDATE_SUCCESS_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void showError(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println(message);
	}
}