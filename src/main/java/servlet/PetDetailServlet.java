package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FavoriteDAO;
import dao.PetListDAO;
import dao.ReserveDAO;
import model.PetDetail;

@WebServlet("/PetDetailServlet")
public class PetDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String PET_DETAIL_JSP_PATH = "WEB-INF/jsp/petDetail.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_DETAIL_FROM_KEY = "detailFrom";
	private static final String SESSION_DETAIL_FACILITY_ID_KEY = "detailFacilityId";
	private static final String SESSION_RESERVE_PET_ID_KEY = "reservePetID";
	private static final String SESSION_RESERVE_FACILITY_ID_KEY = "reserveFacilityID";
	private static final String SESSION_RESERVED_KEY = "reserved";
	private static final String SESSION_CATEGORY_NAME_KEY = "categoryName";
	private static final String SESSION_CATEGORY_ID_KEY = "categoryId";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REQUEST_FROM_KEY = "from";
	private static final String REQUEST_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_PET_DETAIL_KEY = "petDetail";
	private static final String REQUEST_RESERVED_KEY = "reserved";
	private static final String REQUEST_FAVORITE_KEY = "favorite";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMessage";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		Integer petId = parsePetId(request);
		if (petId == null) {
			forwardWithErrorMessage(request, response, "不正なペット情報です");
			return;
		}
		request.setAttribute(REQUEST_PET_ID_KEY, petId);

		String from = resolveFrom(request, session);
		request.setAttribute(REQUEST_FROM_KEY, from);

		updateDetailFacilityId(session, request);

		PetDetail petDetail = loadPetDetail(petId);
		request.setAttribute(REQUEST_PET_DETAIL_KEY, petDetail);

		boolean reserved = isReserved(petId);
		request.setAttribute(REQUEST_RESERVED_KEY, reserved);

		saveDetailSessionAttributes(session, petDetail, petId, reserved);

		String userId = (String) session.getAttribute(SESSION_USER_ID_KEY);
		if (userId != null) {
			boolean favorite = isFavorite(userId, petId);
			request.setAttribute(REQUEST_FAVORITE_KEY, favorite);
		}

		forwardToPetDetailPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private Integer parsePetId(HttpServletRequest request) {
		String petIdString = request.getParameter(REQUEST_PET_ID_KEY);
		if (petIdString == null || petIdString.isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(petIdString);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private String resolveFrom(HttpServletRequest request, HttpSession session) {
		String from = request.getParameter(REQUEST_FROM_KEY);
		if (from != null) {
			session.setAttribute(SESSION_DETAIL_FROM_KEY, from);
			return from;
		}
		return (String) session.getAttribute(SESSION_DETAIL_FROM_KEY);
	}

	private void updateDetailFacilityId(HttpSession session, HttpServletRequest request) {
		String facilityId = request.getParameter(REQUEST_FACILITY_ID_KEY);
		if (facilityId != null) {
			session.setAttribute(SESSION_DETAIL_FACILITY_ID_KEY, facilityId);
		}
	}

	private PetDetail loadPetDetail(Integer petId) {
		PetListDAO petListDAO = new PetListDAO();
		return petListDAO.showPetDetail(petId);
	}

	private boolean isReserved(Integer petId) {
		ReserveDAO reserveDAO = new ReserveDAO();
		return reserveDAO.existsReserveByPetID(petId);
	}

	private boolean isFavorite(String userId, Integer petId) {
		FavoriteDAO favoriteDAO = new FavoriteDAO();
		return favoriteDAO.isFavorite(userId, petId);
	}

	private void saveDetailSessionAttributes(HttpSession session, PetDetail petDetail, Integer petId,
			boolean reserved) {
		String facilityId = petDetail.getFacilityID();
		session.setAttribute(SESSION_RESERVE_PET_ID_KEY, petId);
		session.setAttribute(SESSION_RESERVE_FACILITY_ID_KEY, facilityId);
		session.setAttribute(SESSION_RESERVED_KEY, reserved);
		session.setAttribute(SESSION_CATEGORY_NAME_KEY, petDetail.getCategoryName());
		session.setAttribute(SESSION_CATEGORY_ID_KEY, petDetail.getCategoryId());
	}

	private void forwardWithErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws ServletException, IOException {
		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, errorMessage);
		forwardToPetDetailPage(request, response);
	}

	private void forwardToPetDetailPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_DETAIL_JSP_PATH);
		dispatcher.forward(request, response);
	}
}