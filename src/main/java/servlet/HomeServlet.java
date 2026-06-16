package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityViewDAO;
import dao.MessageDAO;
import dao.PetListDAO;
import dao.ReserveDAO;
import model.FavoritePet;
import model.PetDetail;
import model.PetInformationView;
import model.Reserve;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String sort = request.getParameter("sort");

		String facilityId = (String) session.getAttribute("facilityId");

		PetListDAO dao1 = new PetListDAO();
		List<PetInformationView> petList = dao1.showList();

		request.setAttribute("petList", petList);

		List<FavoritePet> favoritePetList = dao1.showFavoritePet();
		request.setAttribute("favoritePetList", favoritePetList);

		//matchRate おすすめの場合：favoritePetList
		if (userId != null) {
			Map<Integer, Integer> allMatchRateMap = dao1.getAllMatchRate(userId);

			for (FavoritePet fPet : favoritePetList) {
				int matchRate = allMatchRateMap.getOrDefault(fPet.getPetID(), 0);
				fPet.setMatchRate(matchRate);
			}
			request.setAttribute("allMatchRateMap", allMatchRateMap);

		}
		if ("matchRateDesc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p2.getMatchRate() - p1.getMatchRate());
		} else if ("matchRateAsc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p1.getMatchRate() - p2.getMatchRate());
		} else if ("priceDesc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p2.getPrice() - p1.getPrice());
		} else if ("priceAsc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p1.getPrice() - p2.getPrice());
		} else if ("ageDesc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p2.getAge() - p1.getAge());
		} else if ("ageAsc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p1.getAge() - p2.getAge());
		}

		request.setAttribute("sort", sort);

		//本日の予約件数のカウント(施設側)
		ReserveDAO dao2 = new ReserveDAO();
		int countTodayReserve = dao2.countTodayReserve(facilityId);

		request.setAttribute("countTodayReserve", countTodayReserve);

		//登録しているペット数のカウント(施設側)
		int petCount = dao1.countByfacilityID(facilityId);
		request.setAttribute("petCount", petCount);

		//直近の予約1件を表示
		Reserve reserve = dao2.findnextReserve(facilityId);
		request.setAttribute("reserve", reserve);

		//最後に追加したペットの表示
		FavoritePet latestPet = dao1.findLatestPetByFacilityID(facilityId);
		request.setAttribute("latestPet", latestPet);

		//ホームページアクセス数の表示
		FacilityViewDAO dao3 = new FacilityViewDAO();
		int viewCount = dao3.getViewCount(facilityId);
		request.setAttribute("viewCount", viewCount);

		//条件検索
		String search = request.getParameter("clickSearch");
		//検索ボタン押すとき
		if ("true".equals(search)) {
			String categoryId = request.getParameter("categoryId");
			String gender = request.getParameter("gender");
			String[] colorArray = request.getParameterValues("color");

			List<String> selectedColorList = new ArrayList<>();

			if (colorArray != null) {
				selectedColorList = Arrays.asList(colorArray);
			}
			String pet_size = request.getParameter("pet_size");
			String ageRange = request.getParameter("ageRange");
			String priceRange = request.getParameter("priceRange");

			List<PetDetail> searchPetList = dao1.searchAllPet(categoryId, gender, colorArray, pet_size, ageRange,
					priceRange);
			// 検索にもmatchRateを入れる、一覧の場合：searchPetList
			if (userId != null) {
				Map<Integer, Integer> allMatchRateMap = dao1.getAllMatchRate(userId);

				for (PetDetail pdPet : searchPetList) {
					int matchRate = allMatchRateMap.getOrDefault(pdPet.getPetID(), 0);
					pdPet.setMatchRate(matchRate);
				}
				request.setAttribute("allMatchRateMap", allMatchRateMap);

			}
			if ("matchRateDesc".equals(sort)) {
				searchPetList.sort((p1, p2) -> p2.getMatchRate() - p1.getMatchRate());
			} else if ("matchRateAsc".equals(sort)) {
				searchPetList.sort((p1, p2) -> p1.getMatchRate() - p2.getMatchRate());
			} else if ("priceDesc".equals(sort)) {
				searchPetList.sort((p1, p2) -> p2.getPrice() - p1.getPrice());
			} else if ("priceAsc".equals(sort)) {
				searchPetList.sort((p1, p2) -> p1.getPrice() - p2.getPrice());
			} else if ("ageDesc".equals(sort)) {
				searchPetList.sort((p1, p2) -> p2.getAge() - p1.getAge());
			} else if ("ageAsc".equals(sort)) {
				searchPetList.sort((p1, p2) -> p1.getAge() - p2.getAge());
			}
			request.setAttribute("sort", sort);

			request.setAttribute("clickSearch", true);
			request.setAttribute("searchPetList", searchPetList);
			//検索結果件数計算
			request.setAttribute("searchResultCount", searchPetList.size());
			//選択した条件をjspで残る
			request.setAttribute("selectedCategoryId", categoryId);
			request.setAttribute("selectedGender", gender);
			request.setAttribute("selectedColorList", selectedColorList);
			request.setAttribute("selectedPet_size", pet_size);
			request.setAttribute("selectedAgeRange", ageRange);
			request.setAttribute("selectedPriceRange", priceRange);
		}

		//メッセージ未読表示
		MessageDAO dao4 = new MessageDAO();
		// ユーザー側
		int userUnreadCount = 0;
		if (userId != null) {
			userUnreadCount = dao4.countUnreadByUserId(userId);
		}
		request.setAttribute("userUnreadCount", userUnreadCount);
		// 施設側
		int facilityUnreadCount = 0;
		if (facilityId != null) {
			facilityUnreadCount = dao4.countUnreadByFacilityId(facilityId);
		}
		request.setAttribute("facilityUnreadCount", facilityUnreadCount);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);

	}
}