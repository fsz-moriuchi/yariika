package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.FacilityLogin;
import model.FacilityLoginLogic;
import util.PasswordUtil;

@WebServlet("/FacilityLoginServlet")
public class FacilityLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_JSP_PATH = "/WEB-INF/jsp/facilityLogin.jsp";
	private static final String DASHBOARD_URL = "DashboardServlet";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_USER_KEY = "user";
	private static final String ERROR_MESSAGE = "ログインに失敗しました";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forwardToLoginPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		FacilityLogin login = createFacilityLogin(request);
		boolean loginSucceeded = authenticate(login);

		if (loginSucceeded) {
			saveFacilitySession(request, login.getFacilityId());
			response.sendRedirect(DASHBOARD_URL);
			return;
		}

		showLoginError(request, response);
	}

	private void forwardToLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private FacilityLogin createFacilityLogin(HttpServletRequest request) {
		String facilityId = request.getParameter("facilityId");
		String password = request.getParameter("password");
		String hash = PasswordUtil.hashPassword(password);
		return new FacilityLogin(facilityId, hash);
	}

	private boolean authenticate(FacilityLogin login) {
		FacilityLoginLogic logic = new FacilityLoginLogic();
		return logic.execute(login);
	}

	private void saveFacilitySession(HttpServletRequest request, String facilityId) {
		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_USER_ID_KEY);
		session.removeAttribute(SESSION_USER_KEY);
		session.setAttribute(SESSION_FACILITY_ID_KEY, facilityId);
	}

	private void showLoginError(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorMsg", ERROR_MESSAGE);
		forwardToLoginPage(request, response);
	}
}