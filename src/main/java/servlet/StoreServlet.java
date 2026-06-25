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

import dao.FacilityInformationDAO;
import dao.FavoriteDAO;
import dao.PetListDAO;
import model.PetInformationView;

@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String PET_MANAGEMENT_JSP_PATH = "WEB-INF/jsp/petManagement.jsp";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_FACILITY_LIST_KEY = "facilityList";
	private static final String REQUEST_FAVORITE_PET_ID_KEY = "favoritePetId";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
		List<PetInformationView> facilityList = loadFacilityList(facilityId);
		applyFavoriteCounts(facilityList);
		Integer favoritePetId = loadFavoritePetId(facilityId);

		request.setAttribute(REQUEST_FACILITY_LIST_KEY, facilityList);
		request.setAttribute(REQUEST_FAVORITE_PET_ID_KEY, favoritePetId);

		forwardToPetManagementPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_FACILITY_ID_KEY) != null;
	}

	private List<PetInformationView> loadFacilityList(String facilityId) {
		PetListDAO petListDAO = new PetListDAO();
		return petListDAO.showListByFacility(facilityId);
	}

	private void applyFavoriteCounts(List<PetInformationView> facilityList) {
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		for (PetInformationView pet : facilityList) {
			int favoriteCount = favoriteDAO.countFavorite(pet.getPetID());
			pet.setFavoriteCount(favoriteCount);
		}
	}

	private Integer loadFavoritePetId(String facilityId) {
		FacilityInformationDAO infoDAO = new FacilityInformationDAO();
		return infoDAO.findFavoritePetId(facilityId);
	}

	private void forwardToPetManagementPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_MANAGEMENT_JSP_PATH);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}