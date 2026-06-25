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

@WebServlet("/SurveyConfirmServlet")
public class SurveyConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String SURVEY_CONFIRM_JSP_PATH = "WEB-INF/jsp/surveyConfirm.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String REQUEST_USER_SURVEY_LIST_KEY = "userSurveyList";
	private static final String REQUEST_QUESTION_LIST_KEY = "questionList";
	private static final String REQUEST_ALL_CHOICE_LIST_KEY = "allChoiceList";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

		forwardToSurveyConfirmPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
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

	private void forwardToSurveyConfirmPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(SURVEY_CONFIRM_JSP_PATH);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}