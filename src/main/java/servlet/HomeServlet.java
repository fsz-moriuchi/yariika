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

import dao.PetListDAO;
import model.FavoritePet;
import model.PetInformationView;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String sort = request.getParameter("sort");

		PetListDAO dao = new PetListDAO();
		List<PetInformationView> petList = dao.showList();

		request.setAttribute("petList", petList);

		List<FavoritePet> favoritePetList = dao.showFavoritePet();
		request.setAttribute("favoritePetList", favoritePetList);

		//matchRate
		if (userId != null) {
			Map<Integer, Integer> allMatchRateMap = dao.getAllMatchRate(userId);

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

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);

	}
}