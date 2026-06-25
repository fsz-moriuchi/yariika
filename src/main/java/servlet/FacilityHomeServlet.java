package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityHomeDAO;
import dao.FacilityViewDAO;
import model.FacilityHomeView;
import model.FacilityPetView;
import model.PopularPetView;

@WebServlet("/FacilityHomeServlet")
public class FacilityHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TARGET_JSP = "WEB-INF/jsp/facilityHome.jsp";
	private static final String LOGIN_USER_ID_KEY = "userId";
	private static final String VIEWED_FACILITY_IDS_KEY = "viewedFacilityIds";
	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		if (shouldRedirectToLogin(request, response)) {
			return;
		}

		String facilityId = request.getParameter("facilityId");
		FacilityHomeDAO homeDAO = new FacilityHomeDAO();
		FacilityViewDAO viewDAO = new FacilityViewDAO();

		int viewCount = getViewCountWithSessionGuard(request, viewDAO, facilityId);
		FacilityHomeData homeData = loadHomeData(homeDAO, facilityId);

		setHomeAttributes(request, viewCount, homeData);
		forwardToHomePage(request, response);
	}

	private boolean shouldRedirectToLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(LOGIN_USER_ID_KEY) == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return true;
		}
		return false;
	}

	private int getViewCountWithSessionGuard(HttpServletRequest request, FacilityViewDAO viewDAO, String facilityId) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return viewDAO.getViewCount(facilityId);
		}

		List<String> viewedFacilityIds = getViewedFacilityIds(session);

		if (!viewedFacilityIds.contains(facilityId)) {
			viewDAO.insertView(facilityId);
			viewedFacilityIds.add(facilityId);
		}

		return viewDAO.getViewCount(facilityId);
	}

	@SuppressWarnings("unchecked")
	private List<String> getViewedFacilityIds(HttpSession session) {
		List<String> viewedFacilityIds = (List<String>) session.getAttribute(VIEWED_FACILITY_IDS_KEY);
		if (viewedFacilityIds == null) {
			viewedFacilityIds = new ArrayList<>();
			session.setAttribute(VIEWED_FACILITY_IDS_KEY, viewedFacilityIds);
		}
		return viewedFacilityIds;
	}

	private FacilityHomeData loadHomeData(FacilityHomeDAO homeDAO, String facilityId) {
		return new FacilityHomeData(
				homeDAO.showFacilityInfo(facilityId),
				homeDAO.showPetList(facilityId),
				homeDAO.showPopularRanking(facilityId),
				homeDAO.showFavoritePet(facilityId),
				homeDAO.getClosedDays(facilityId));
	}

	private void setHomeAttributes(HttpServletRequest request, int viewCount, FacilityHomeData homeData) {
		request.setAttribute("viewCount", viewCount);
		request.setAttribute("facility", homeData.facility);
		request.setAttribute("petList", homeData.petList);
		request.setAttribute("rankingList", homeData.rankingList);
		request.setAttribute("favoritePet", homeData.favoritePet);
		request.setAttribute("closedDayList", homeData.closedDayList);
	}

	private void forwardToHomePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(TARGET_JSP);
		dispatcher.forward(request, response);
	}

	private static class FacilityHomeData {
		private final FacilityHomeView facility;
		private final List<FacilityPetView> petList;
		private final List<PopularPetView> rankingList;
		private final FacilityPetView favoritePet;
		private final List<String> closedDayList;

		private FacilityHomeData(FacilityHomeView facility, List<FacilityPetView> petList,
				List<PopularPetView> rankingList, FacilityPetView favoritePet, List<String> closedDayList) {
			this.facility = facility;
			this.petList = petList;
			this.rankingList = rankingList;
			this.favoritePet = favoritePet;
			this.closedDayList = closedDayList;
		}
	}
}