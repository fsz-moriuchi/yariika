package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/QuizWarningServlet")
public class QuizWarningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String QUIZ_WARNING_JSP_PATH = "WEB-INF/jsp/quizWarning.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMessage";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isLoggedIn(request.getSession(false))) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		Integer petId = parsePetId(request);
		if (petId == null) {
			request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, "不正なペットIDです");
			forwardToQuizWarningPage(request, response);
			return;
		}

		request.setAttribute(REQUEST_PET_ID_KEY, petId);
		forwardToQuizWarningPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private Integer parsePetId(HttpServletRequest request) {
		String petIdText = request.getParameter(REQUEST_PET_ID_KEY);
		if (petIdText == null || petIdText.isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(petIdText);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void forwardToQuizWarningPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(QUIZ_WARNING_JSP_PATH);
		dispatcher.forward(request, response);
	}
}