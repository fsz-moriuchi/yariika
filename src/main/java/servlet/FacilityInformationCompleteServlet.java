package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/FacilityInformationCompleteServlet")
public class FacilityInformationCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String COMPLETE_JSP_PATH = "/WEB-INF/jsp/facilityInformationComplete.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (shouldRedirectToLogin(request)) {
			redirectToLogin(response);
			return;
		}

		forwardToCompletePage(request, response);
	}

	private boolean shouldRedirectToLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session == null || session.getAttribute(SESSION_FACILITY_ID_KEY) == null;
	}

	private void redirectToLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(LOGIN_REDIRECT_URL);
	}

	private void forwardToCompletePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(COMPLETE_JSP_PATH);
		dispatcher.forward(request, response);
	}
}