package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReserveCancelServlet")
public class ReserveCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String HOME_SERVLET_PATH = "HomeServlet";
	private static final String SESSION_FACILITY_ID_KEY = "userId";
	private static final String SESSION_RESERVE_DATE_KEY = "reserveDate";
	private static final String SESSION_RESERVE_PET_ID_KEY = "reservePetID";
	private static final String SESSION_RESERVE_FACILITY_ID_KEY = "reserveFacilityID";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		clearReservationSession(session);
		forwardToHome(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_FACILITY_ID_KEY) != null;
	}

	private void clearReservationSession(HttpSession session) {
		session.removeAttribute(SESSION_RESERVE_DATE_KEY);
		session.removeAttribute(SESSION_RESERVE_PET_ID_KEY);
		session.removeAttribute(SESSION_RESERVE_FACILITY_ID_KEY);
	}

	private void forwardToHome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_SERVLET_PATH);
		dispatcher.forward(request, response);
	}
}