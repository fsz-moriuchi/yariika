package servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.QuizAnswerDAO;
import dao.QuizResultDAO;
import model.PetQuiz;
import model.QuizAnswer;
import model.QuizResult;
import model.User;

@WebServlet("/QuizAnswerServlet")
public class QuizAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "UserLoginServlet";
	private static final String HOME_REDIRECT_URL = "HomeServlet";
	private static final String QUIZ_RESULT_JSP_PATH = "WEB-INF/jsp/quizResult.jsp";
	private static final String SESSION_USER_KEY = "user";
	private static final String SESSION_CATEGORY_ID_KEY = "categoryId";
	private static final String SESSION_QUIZ_LIST_KEY = "quizList";
	private static final String SESSION_QUIZ_SESSION_ID_KEY = "quizSessionId";
	private static final String SESSION_RESERVED_KEY = "reserved";
	private static final String REQUEST_TOTAL_COUNT_KEY = "totalCount";
	private static final String REQUEST_COUNT_KEY = "count";
	private static final String REQUEST_PERCENT_KEY = "percent";
	private static final String REQUEST_RESULT_LIST_KEY = "resultList";
	private static final String REQUEST_RESERVED_KEY = "reserved";
	private static final String QUIZ_PARAM_PREFIX = "q";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		User login = (User) session.getAttribute(SESSION_USER_KEY);
		if (login == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}
		String userId = login.getUserId();

		Integer categoryId = (Integer) session.getAttribute(SESSION_CATEGORY_ID_KEY);
		if (categoryId == null) {
			response.sendRedirect(HOME_REDIRECT_URL);
			return;
		}

		List<PetQuiz> petQuizList = getQuizList(session);
		String quizSessionId = (String) session.getAttribute(SESSION_QUIZ_SESSION_ID_KEY);

		int correctCount = saveAnswersAndCountCorrect(request, userId, quizSessionId, petQuizList);
		int totalCount = petQuizList.size();
		int percent = calculatePercent(correctCount, totalCount);

		request.setAttribute(REQUEST_TOTAL_COUNT_KEY, totalCount);
		request.setAttribute(REQUEST_COUNT_KEY, correctCount);
		request.setAttribute(REQUEST_PERCENT_KEY, percent);

		List<QuizResult> resultList = loadReversedResultList(userId, quizSessionId);
		request.setAttribute(REQUEST_RESULT_LIST_KEY, resultList);

		Boolean reserved = (Boolean) session.getAttribute(SESSION_RESERVED_KEY);
		request.setAttribute(REQUEST_RESERVED_KEY, reserved);

		forwardToQuizResultPage(request, response);
	}

	@SuppressWarnings("unchecked")
	private List<PetQuiz> getQuizList(HttpSession session) {
		return (List<PetQuiz>) session.getAttribute(SESSION_QUIZ_LIST_KEY);
	}

	private int saveAnswersAndCountCorrect(
			HttpServletRequest request,
			String userId,
			String quizSessionId,
			List<PetQuiz> petQuizList) {

		QuizAnswerDAO answerDao = new QuizAnswerDAO();
		int correctCount = 0;

		for (PetQuiz quiz : petQuizList) {
			Integer userAnswer = readUserAnswer(request, quiz.getQuizId());
			if (userAnswer == null) {
				continue;
			}

			QuizAnswer answer = new QuizAnswer(userId, quiz.getQuizId(), userAnswer, quizSessionId);
			answerDao.insert(answer);

			if (userAnswer == quiz.getAnswer()) {
				correctCount++;
			}
		}

		return correctCount;
	}

	private Integer readUserAnswer(HttpServletRequest request, int quizId) {
		String value = request.getParameter(QUIZ_PARAM_PREFIX + quizId);
		if (value == null) {
			return null;
		}
		return Integer.parseInt(value);
	}

	private int calculatePercent(int correctCount, int totalCount) {
		return correctCount * 100 / totalCount;
	}

	private List<QuizResult> loadReversedResultList(String userId, String quizSessionId) {
		QuizResultDAO resultDao = new QuizResultDAO();
		List<QuizResult> resultList = resultDao.findByUserId(userId, quizSessionId);
		Collections.reverse(resultList);
		return resultList;
	}

	private void forwardToQuizResultPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(QUIZ_RESULT_JSP_PATH);
		dispatcher.forward(request, response);
	}
}