package servlet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.PetQuizDAO;
import model.PetQuiz;

@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String HOME_REDIRECT_URL = "HomeServlet";
	private static final String QUIZ_JSP_PATH = "WEB-INF/jsp/quiz.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_CATEGORY_ID_KEY = "categoryId";
	private static final String SESSION_QUIZ_LIST_KEY = "quizList";
	private static final String SESSION_QUIZ_SESSION_ID_KEY = "quizSessionId";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		Integer categoryId = resolveCategoryId(session);
		if (categoryId == null) {
			response.sendRedirect(HOME_REDIRECT_URL);
			return;
		}

		List<PetQuiz> quizList = loadQuizList(categoryId);
		storeQuizData(session, quizList, categoryId);

		forwardToQuizPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private Integer resolveCategoryId(HttpSession session) {
		return (Integer) session.getAttribute(SESSION_CATEGORY_ID_KEY);
	}

	private List<PetQuiz> loadQuizList(Integer categoryId) {
		PetQuizDAO quizDAO = new PetQuizDAO();
		return quizDAO.findByCategory(categoryId);
	}

	private void storeQuizData(HttpSession session, List<PetQuiz> quizList, Integer categoryId) {
		session.setAttribute(SESSION_QUIZ_LIST_KEY, quizList);
		session.setAttribute(SESSION_CATEGORY_ID_KEY, categoryId);
		session.setAttribute(SESSION_QUIZ_SESSION_ID_KEY, UUID.randomUUID().toString());
	}

	private void forwardToQuizPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(QUIZ_JSP_PATH);
		dispatcher.forward(request, response);
	}
}