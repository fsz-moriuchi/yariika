package servlet;

import java.io.IOException;
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
import dao.PetListDAO;
import dao.ReserveDAO;
import model.FavoritePet;
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
		
		String facilityId = (String)session.getAttribute("facilityId");

		PetListDAO dao1 = new PetListDAO();
		List<PetInformationView> petList = dao1.showList();

		request.setAttribute("petList", petList);

		List<FavoritePet> favoritePetList = dao1.showFavoritePet();
		request.setAttribute("favoritePetList", favoritePetList);

		//matchRate
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
		}else if ("matchRateAsc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p1.getMatchRate() - p2.getMatchRate());
		}else if ("priceDesc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p2.getPrice() - p1.getPrice());
		}else if ("priceAsc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p1.getPrice() - p2.getPrice());
		}else if ("ageDesc".equals(sort)) {
			favoritePetList.sort((p1, p2) -> p2.getAge() - p1.getAge());
		}else if ("ageAsc".equals(sort)) {
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
		 
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);

	}
}