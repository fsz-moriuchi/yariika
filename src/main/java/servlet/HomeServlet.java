package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.MessageDAO;
import dao.PetListDAO;
import model.FavoritePet;
import model.PetDetail;
import model.PetInformationView;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String HOME_JSP_PATH = "WEB-INF/jsp/home.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";

	private static final String REQUEST_PET_LIST_KEY = "petList";
	private static final String REQUEST_FAVORITE_PET_LIST_KEY = "favoritePetList";
	private static final String REQUEST_SORT_KEY = "sort";
	private static final String REQUEST_CLICK_SEARCH_KEY = "clickSearch";
	private static final String REQUEST_SEARCH_PET_LIST_KEY = "searchPetList";
	private static final String REQUEST_SEARCH_RESULT_COUNT_KEY = "searchResultCount";
	private static final String REQUEST_SELECTED_CATEGORY_ID_KEY = "selectedCategoryId";
	private static final String REQUEST_SELECTED_GENDER_KEY = "selectedGender";
	private static final String REQUEST_SELECTED_COLOR_LIST_KEY = "selectedColorList";
	private static final String REQUEST_SELECTED_PET_SIZE_KEY = "selectedPet_size";
	private static final String REQUEST_SELECTED_AGE_RANGE_KEY = "selectedAgeRange";
	private static final String REQUEST_SELECTED_PRICE_RANGE_KEY = "selectedPriceRange";
	private static final String REQUEST_USER_UNREAD_COUNT_KEY = "userUnreadCount";

	private static final String SORT_MATCH_RATE_DESC = "matchRateDesc";
	private static final String SORT_MATCH_RATE_ASC = "matchRateAsc";
	private static final String SORT_PRICE_DESC = "priceDesc";
	private static final String SORT_PRICE_ASC = "priceAsc";
	private static final String SORT_AGE_DESC = "ageDesc";
	private static final String SORT_AGE_ASC = "ageAsc";
	private static final String CLICK_SEARCH_TRUE = "true";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String userId = (String) session.getAttribute(SESSION_USER_ID_KEY);
		String sort = request.getParameter(REQUEST_SORT_KEY);

		PetListDAO petListDAO = new PetListDAO();
		Map<Integer, Integer> matchRateMap = petListDAO.getAllMatchRate(userId);

		List<PetInformationView> petList = petListDAO.showList();
		List<FavoritePet> favoritePetList = petListDAO.showFavoritePet();

		sortPetInformationViewList(petList, sort);
		applyMatchRateToFavoritePets(favoritePetList, matchRateMap);
		sortFavoritePets(favoritePetList, sort);

		request.setAttribute(REQUEST_PET_LIST_KEY, petList);
		request.setAttribute(REQUEST_FAVORITE_PET_LIST_KEY, favoritePetList);
		request.setAttribute(REQUEST_SORT_KEY, sort);

		handleSearch(request, petListDAO, sort, matchRateMap);
		setUnreadMessageCount(request, userId);

		RequestDispatcher dispatcher = request.getRequestDispatcher(HOME_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private void handleSearch(
			HttpServletRequest request,
			PetListDAO petListDAO,
			String sort,
			Map<Integer, Integer> matchRateMap) {

		if (!CLICK_SEARCH_TRUE.equals(request.getParameter(REQUEST_CLICK_SEARCH_KEY))) {
			return;
		}

		SearchCondition searchCondition = createSearchCondition(request);

		List<PetDetail> searchPetList = petListDAO.searchAllPet(
				searchCondition.categoryId,
				searchCondition.gender,
				searchCondition.colorArray,
				searchCondition.petSize,
				searchCondition.ageRange,
				searchCondition.priceRange);

		applyMatchRateToSearchPets(searchPetList, matchRateMap);
		sortSearchPets(searchPetList, sort);

		request.setAttribute(REQUEST_SORT_KEY, sort);
		request.setAttribute(REQUEST_CLICK_SEARCH_KEY, true);
		request.setAttribute(REQUEST_SEARCH_PET_LIST_KEY, searchPetList);
		request.setAttribute(REQUEST_SEARCH_RESULT_COUNT_KEY, searchPetList.size());

		setSearchAttributes(request, searchCondition);
	}

	private SearchCondition createSearchCondition(HttpServletRequest request) {
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.categoryId = request.getParameter("categoryId");
		searchCondition.gender = request.getParameter("gender");
		searchCondition.colorArray = request.getParameterValues("color");
		searchCondition.petSize = request.getParameter("pet_size");
		searchCondition.ageRange = request.getParameter("ageRange");
		searchCondition.priceRange = request.getParameter("priceRange");
		return searchCondition;
	}

	private void setSearchAttributes(HttpServletRequest request, SearchCondition searchCondition) {
		List<String> selectedColorList = new ArrayList<>();
		if (searchCondition.colorArray != null) {
			selectedColorList = Arrays.asList(searchCondition.colorArray);
		}

		request.setAttribute(REQUEST_SELECTED_CATEGORY_ID_KEY, searchCondition.categoryId);
		request.setAttribute(REQUEST_SELECTED_GENDER_KEY, searchCondition.gender);
		request.setAttribute(REQUEST_SELECTED_COLOR_LIST_KEY, selectedColorList);
		request.setAttribute(REQUEST_SELECTED_PET_SIZE_KEY, searchCondition.petSize);
		request.setAttribute(REQUEST_SELECTED_AGE_RANGE_KEY, searchCondition.ageRange);
		request.setAttribute(REQUEST_SELECTED_PRICE_RANGE_KEY, searchCondition.priceRange);
	}

	private void applyMatchRateToFavoritePets(List<FavoritePet> favoritePetList, Map<Integer, Integer> matchRateMap) {
		for (FavoritePet pet : favoritePetList) {
			pet.setMatchRate(matchRateMap.getOrDefault(pet.getPetID(), 0));
		}
	}

	private void applyMatchRateToSearchPets(List<PetDetail> searchPetList, Map<Integer, Integer> matchRateMap) {
		for (PetDetail pet : searchPetList) {
			pet.setMatchRate(matchRateMap.getOrDefault(pet.getPetID(), 0));
		}
	}

	private void sortPetInformationViewList(List<PetInformationView> petList, String sort) {
		Comparator<PetInformationView> comparator = createPetInformationViewComparator(sort);
		if (comparator != null) {
			petList.sort(comparator);
		}
	}

	private void sortFavoritePets(List<FavoritePet> favoritePetList, String sort) {
		Comparator<FavoritePet> comparator = createFavoritePetComparator(sort);
		if (comparator != null) {
			favoritePetList.sort(comparator);
		}
	}

	private void sortSearchPets(List<PetDetail> searchPetList, String sort) {
		Comparator<PetDetail> comparator = createPetDetailComparator(sort);
		if (comparator != null) {
			searchPetList.sort(comparator);
		}
	}

	private Comparator<PetInformationView> createPetInformationViewComparator(String sort) {
		if (sort == null) {
			return null;
		}

		switch (sort) {
		case SORT_PRICE_DESC:
			return (p1, p2) -> Integer.compare(p2.getPrice(), p1.getPrice());
		case SORT_PRICE_ASC:
			return (p1, p2) -> Integer.compare(p1.getPrice(), p2.getPrice());
		case SORT_AGE_DESC:
			return (p1, p2) -> Integer.compare(p2.getAge(), p1.getAge());
		case SORT_AGE_ASC:
			return (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
		default:
			return null;
		}
	}

	private Comparator<FavoritePet> createFavoritePetComparator(String sort) {
		if (sort == null) {
			return null;
		}

		switch (sort) {
		case SORT_MATCH_RATE_DESC:
			return (p1, p2) -> Integer.compare(p2.getMatchRate(), p1.getMatchRate());
		case SORT_MATCH_RATE_ASC:
			return (p1, p2) -> Integer.compare(p1.getMatchRate(), p2.getMatchRate());
		case SORT_PRICE_DESC:
			return (p1, p2) -> Integer.compare(p2.getPrice(), p1.getPrice());
		case SORT_PRICE_ASC:
			return (p1, p2) -> Integer.compare(p1.getPrice(), p2.getPrice());
		case SORT_AGE_DESC:
			return (p1, p2) -> Integer.compare(p2.getAge(), p1.getAge());
		case SORT_AGE_ASC:
			return (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
		default:
			return null;
		}
	}

	private Comparator<PetDetail> createPetDetailComparator(String sort) {
		if (sort == null) {
			return null;
		}

		switch (sort) {
		case SORT_MATCH_RATE_DESC:
			return (p1, p2) -> Integer.compare(p2.getMatchRate(), p1.getMatchRate());
		case SORT_MATCH_RATE_ASC:
			return (p1, p2) -> Integer.compare(p1.getMatchRate(), p2.getMatchRate());
		case SORT_PRICE_DESC:
			return (p1, p2) -> Integer.compare(p2.getPrice(), p1.getPrice());
		case SORT_PRICE_ASC:
			return (p1, p2) -> Integer.compare(p1.getPrice(), p2.getPrice());
		case SORT_AGE_DESC:
			return (p1, p2) -> Integer.compare(p2.getAge(), p1.getAge());
		case SORT_AGE_ASC:
			return (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge());
		default:
			return null;
		}
	}

	private void setUnreadMessageCount(HttpServletRequest request, String userId) {
		MessageDAO messageDAO = new MessageDAO();
		int userUnreadCount = messageDAO.countUnreadByUserId(userId);
		request.setAttribute(REQUEST_USER_UNREAD_COUNT_KEY, userUnreadCount);
	}

	private static class SearchCondition {
		private String categoryId;
		private String gender;
		private String[] colorArray;
		private String petSize;
		private String ageRange;
		private String priceRange;
	}
}