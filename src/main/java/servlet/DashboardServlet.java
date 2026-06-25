package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.DashboardDAO;
import model.DashboardSummary;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DASHBOARD_JSP = "WEB-INF/jsp/dashboard.jsp";
	private static final String REDIRECT_WELCOME = "WelcomeServlet";

	private final DashboardDAO dashboardDAO = new DashboardDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String facilityId = getFacilityId(request, response);
		if (facilityId == null) {
			return;
		}

		DashboardSummary summary = dashboardDAO.getSummary(facilityId);

		request.setAttribute("countTodayReserve", summary.getTodayReserveCount());
		request.setAttribute("petCount", summary.getPetCount());
		request.setAttribute("reserve", summary.getNextReserve());
		request.setAttribute("latestPet", summary.getLatestPet());
		request.setAttribute("viewCount", summary.getViewCount());
		request.setAttribute("facilityUnreadCount", summary.getUnreadMessageCount());

		RequestDispatcher dispatcher = request.getRequestDispatcher(DASHBOARD_JSP);
		dispatcher.forward(request, response);
	}

	private String getFacilityId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getSession() == null || request.getSession().getAttribute("facilityId") == null) {
			response.sendRedirect(REDIRECT_WELCOME);
			return null;
		}
		return (String) request.getSession().getAttribute("facilityId");
	}
}