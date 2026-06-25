package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityInformationDAO;
import model.FacilityInformation;

@WebServlet("/FacilityPageServlet")
public class FacilityPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String FACILITY_PAGE_JSP_PATH = "/WEB-INF/jsp/facilitypage.jsp";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_REGISTERED_KEY = "registered";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (isNotLoggedIn(session)) {
			redirectToLogin(response);
			return;
		}

		String facilityId = getFacilityIdFromSession(session);
		boolean registered = isFacilityRegistered(facilityId);

		setRegisteredAttribute(request, registered);
		forwardToFacilityPage(request, response);
	}

	private boolean isNotLoggedIn(HttpSession session) {
		return session == null || session.getAttribute(SESSION_FACILITY_ID_KEY) == null;
	}

	private void redirectToLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(LOGIN_REDIRECT_URL);
	}

	private String getFacilityIdFromSession(HttpSession session) {
		return (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
	}

	private boolean isFacilityRegistered(String facilityId) {
		FacilityInformationDAO dao = new FacilityInformationDAO();
		FacilityInformation facilityInfo = dao.findByFacilityId(facilityId);
		return facilityInfo != null;
	}

	private void setRegisteredAttribute(HttpServletRequest request, boolean registered) {
		request.setAttribute(REQUEST_REGISTERED_KEY, registered);
	}

	private void forwardToFacilityPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(FACILITY_PAGE_JSP_PATH);
		dispatcher.forward(request, response);
	}
}