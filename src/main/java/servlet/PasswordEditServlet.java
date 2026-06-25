package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.PasswordEdit;
import model.PasswordEditLogic;

@WebServlet("/PasswordEditServlet")
public class PasswordEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String PASSWORD_EDIT_JSP_PATH = "WEB-INF/jsp/passwordEdit.jsp";
	private static final String PASSWORD_EDIT_SUCCESS_JSP_PATH = "WEB-INF/jsp/passwordEditSuccess.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_ERROR_MSG_KEY = "errorMsg";
	private static final String REQUEST_OLD_PASSWORD_KEY = "oldPassword";
	private static final String REQUEST_NEW_PASSWORD_KEY = "newPassword";
	private static final String REQUEST_NEW_PASSWORD_CONFIRM_KEY = "newPasswordConfirm";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isLoggedIn(request.getSession(false))) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		forwardToPasswordEditPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute(SESSION_USER_ID_KEY);
		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);

		if (!isLoggedIn(userId, facilityId)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		PasswordEdit passwordEditResult = executePasswordEdit(request, userId, facilityId);
		System.out.println(passwordEditResult);

		if (passwordEditResult.isSuccess()) {
			forwardToPasswordEditSuccessPage(request, response);
			return;
		}

		request.setAttribute(REQUEST_ERROR_MSG_KEY, passwordEditResult.getErrorMsg());
		forwardToPasswordEditPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null
				&& (session.getAttribute(SESSION_USER_ID_KEY) != null
						|| session.getAttribute(SESSION_FACILITY_ID_KEY) != null);
	}

	private boolean isLoggedIn(String userId, String facilityId) {
		return userId != null || facilityId != null;
	}

	private PasswordEdit executePasswordEdit(
			HttpServletRequest request,
			String userId,
			String facilityId) {

		String oldPassword = request.getParameter(REQUEST_OLD_PASSWORD_KEY);
		String newPassword = request.getParameter(REQUEST_NEW_PASSWORD_KEY);
		String newPasswordConfirm = request.getParameter(REQUEST_NEW_PASSWORD_CONFIRM_KEY);

		PasswordEditLogic passwordEditLogic = new PasswordEditLogic();
		return passwordEditLogic.execute(userId, facilityId, oldPassword, newPassword, newPasswordConfirm);
	}

	private void forwardToPasswordEditPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PASSWORD_EDIT_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToPasswordEditSuccessPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PASSWORD_EDIT_SUCCESS_JSP_PATH);
		dispatcher.forward(request, response);
	}
}