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

import dao.FacilityHomeDAO;
import dao.FacilityViewDAO;
import model.FacilityHomeView;
import model.FacilityPetView;
import model.PopularPetView;

@WebServlet("/FacilityHomeServlet")
public class FacilityHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}
		String facilityId = request.getParameter("facilityId");

		FacilityHomeDAO dao = new FacilityHomeDAO();

		// 閲覧数追加
		FacilityViewDAO viewDAO = new FacilityViewDAO();
		viewDAO.insertView(facilityId);
		int viewCount = viewDAO.getViewCount(facilityId);

		FacilityHomeView facility = dao.showFacilityInfo(facilityId);
		List<FacilityPetView> petList = dao.showPetList(facilityId);
		List<PopularPetView> rankingList = dao.showPopularRanking(facilityId);
		FacilityPetView favoritePet = dao.showFavoritePet(facilityId);

		List<String> closedDayList = dao.getClosedDays(facilityId);

		request.setAttribute("viewCount", viewCount);
		request.setAttribute("facility", facility);
		request.setAttribute("petList", petList);
		request.setAttribute("rankingList", rankingList);
		request.setAttribute("favoritePet", favoritePet);
		request.setAttribute("closedDayList", closedDayList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityHome.jsp");
		dispatcher.forward(request, response);
	}
}