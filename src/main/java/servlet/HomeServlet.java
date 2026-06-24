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
import model.PetSortable;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SORT_MATCH_RATE_DESC = "matchRateDesc";
	private static final String SORT_MATCH_RATE_ASC = "matchRateAsc";
	private static final String SORT_PRICE_DESC = "priceDesc";
	private static final String SORT_PRICE_ASC = "priceAsc";
	private static final String SORT_AGE_DESC = "ageDesc";
	private static final String SORT_AGE_ASC = "ageAsc";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String userId = (String) session.getAttribute("userId");
		String sort = request.getParameter("sort");

		PetListDAO petListDAO = new PetListDAO();
		Map<Integer, Integer> matchRateMap = petListDAO.getAllMatchRate(userId);

		List<PetInformationView> petList = petListDAO.showList();
		request.setAttribute("petList", petList);

		List<FavoritePet> favoritePetList = petListDAO.showFavoritePet();
		applyMatchRate(favoritePetList, matchRateMap);
		sortPets(favoritePetList, sort);
		request.setAttribute("favoritePetList", favoritePetList);

		request.setAttribute("sort", sort);

		handleSearch(request, petListDAO, sort, matchRateMap);

		setUnreadMessageCount(request, userId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);
	}

	private void handleSearch(
			HttpServletRequest request,
			PetListDAO petListDAO,
			String sort,
			Map<Integer, Integer> matchRateMap) {

		String search = request.getParameter("clickSearch");
		if (!"true".equals(search)) {
			return;
		}

		SearchCondition condition = createSearchCondition(request);

		List<PetDetail> searchPetList = petListDAO.searchAllPet(
				condition.categoryId,
				condition.gender,
				condition.colorArray,
				condition.petSize,
				condition.ageRange,
				condition.priceRange);

		applyMatchRate(searchPetList, matchRateMap);
		sortPets(searchPetList, sort);

		request.setAttribute("sort", sort);
		request.setAttribute("clickSearch", true);
		request.setAttribute("searchPetList", searchPetList);
		request.setAttribute("searchResultCount", searchPetList.size());

		setSearchAttributes(request, condition);
	}

	private SearchCondition createSearchCondition(HttpServletRequest request) {
		SearchCondition condition = new SearchCondition();
		condition.categoryId = request.getParameter("categoryId");
		condition.gender = request.getParameter("gender");
		condition.colorArray = request.getParameterValues("color");
		condition.petSize = request.getParameter("pet_size");
		condition.ageRange = request.getParameter("ageRange");
		condition.priceRange = request.getParameter("priceRange");
		return condition;
	}

	private void setSearchAttributes(HttpServletRequest request, SearchCondition condition) {
		List<String> selectedColorList = new ArrayList<>();
		if (condition.colorArray != null) {
			selectedColorList = Arrays.asList(condition.colorArray);
		}

		request.setAttribute("selectedCategoryId", condition.categoryId);
		request.setAttribute("selectedGender", condition.gender);
		request.setAttribute("selectedColorList", selectedColorList);
		request.setAttribute("selectedPet_size", condition.petSize);
		request.setAttribute("selectedAgeRange", condition.ageRange);
		request.setAttribute("selectedPriceRange", condition.priceRange);
	}

	private <T extends PetSortable> void applyMatchRate(
			List<T> petList,
			Map<Integer, Integer> matchRateMap) {

		for (T pet : petList) {
			int matchRate = matchRateMap.getOrDefault(pet.getPetID(), 0);
			pet.setMatchRate(matchRate);
		}
	}

	private <T extends PetSortable> void sortPets(List<T> petList, String sort) {
		Comparator<T> comparator = createComparator(sort);
		if (comparator != null) {
			petList.sort(comparator);
		}
	}

	private <T extends PetSortable> Comparator<T> createComparator(String sort) {
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
		request.setAttribute("userUnreadCount", userUnreadCount);
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