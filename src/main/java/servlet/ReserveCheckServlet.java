package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ReserveDAO;
import model.Reserve;

@WebServlet("/ReserveCheckServlet")
public class ReserveCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String RESERVE_CHECK_JSP_PATH = "WEB-INF/jsp/reserveCheck.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String REQUEST_RESERVE_KEY = "reserve";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String userId = (String) session.getAttribute(SESSION_USER_ID_KEY);
		if (isEmpty(userId)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		Reserve reserve = loadReserve(userId);
		request.setAttribute(REQUEST_RESERVE_KEY, reserve);

		forwardToReserveCheckPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private Reserve loadReserve(String userId) {
		ReserveDAO reserveDAO = new ReserveDAO();
		return reserveDAO.reserveCheck(userId);
	}

	private void forwardToReserveCheckPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVE_CHECK_JSP_PATH);
		dispatcher.forward(request, response);
	}
}