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

import dao.FacilityClosedDayDAO;
import dao.FacilityInformationDAO;
import model.FacilityInformation;

@WebServlet("/FacilityInformationConfirmServlet")
public class FacilityInformationConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TARGET_JSP = "/WEB-INF/jsp/facilityInformationConfirm.jsp";
	private static final String REDIRECT_LOGIN = "WelcomeServlet";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_FACILITY_INFO_KEY = "facilityInfo";
	private static final String REQUEST_CLOSED_DAY_LIST_KEY = "facilityClosedDayList";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (shouldRedirectToLogin(session)) {
			redirectToLogin(response);
			return;
		}

		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
		FacilityInformation facilityInfo = loadFacilityInformation(facilityId);
		List<String> facilityClosedDayList = loadClosedDayList(facilityId);

		setRequestAttributes(request, facilityInfo, facilityClosedDayList);
		forwardToConfirmPage(request, response);
	}

	private boolean shouldRedirectToLogin(HttpSession session) {
		return session == null || session.getAttribute(SESSION_FACILITY_ID_KEY) == null;
	}

	private void redirectToLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(REDIRECT_LOGIN);
	}

	private FacilityInformation loadFacilityInformation(String facilityId) {
		FacilityInformationDAO facilityInformationDAO = new FacilityInformationDAO();
		return facilityInformationDAO.findByFacilityId(facilityId);
	}

	private List<String> loadClosedDayList(String facilityId) {
		FacilityClosedDayDAO facilityClosedDayDAO = new FacilityClosedDayDAO();
		return facilityClosedDayDAO.findByFacilityID(facilityId);
	}

	private void setRequestAttributes(
			HttpServletRequest request,
			FacilityInformation facilityInfo,
			List<String> facilityClosedDayList) {

		request.setAttribute(REQUEST_FACILITY_INFO_KEY, facilityInfo);
		request.setAttribute(REQUEST_CLOSED_DAY_LIST_KEY, facilityClosedDayList);
	}

	private void forwardToConfirmPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(TARGET_JSP);
		dispatcher.forward(request, response);
	}
}