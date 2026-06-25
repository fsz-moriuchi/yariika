package servlet;

import java.io.IOException;

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
import model.Reserve;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String WELCOME_SERVLET = "WelcomeServlet";
	private static final String DASHBOARD_JSP = "WEB-INF/jsp/dashboard.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String facilityId = getLoggedInFacilityId(request);

		if (facilityId == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		setDashboardAttributes(request, facilityId);
		forwardToDashboard(request, response);
	}

	// ログイン中の施設IDを取得する
	private String getLoggedInFacilityId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("facilityId") == null) {
			return null;
		}

		return (String) session.getAttribute("facilityId");
	}

	// ダッシュボードに表示する情報を取得してrequestにセットする
	private void setDashboardAttributes(HttpServletRequest request, String facilityId) {

		ReserveDAO reserveDao = new ReserveDAO();
		PetListDAO petListDao = new PetListDAO();
		FacilityViewDAO facilityViewDao = new FacilityViewDAO();
		MessageDAO messageDao = new MessageDAO();

		int countTodayReserve = reserveDao.countTodayReserve(facilityId);
		int petCount = petListDao.countByfacilityID(facilityId);
		Reserve reserve = reserveDao.findnextReserve(facilityId);
		FavoritePet latestPet = petListDao.findLatestPetByFacilityID(facilityId);
		int viewCount = facilityViewDao.getViewCount(facilityId);
		int facilityUnreadCount = messageDao.countUnreadByFacilityId(facilityId);

		request.setAttribute("countTodayReserve", countTodayReserve);
		request.setAttribute("petCount", petCount);
		request.setAttribute("reserve", reserve);
		request.setAttribute("latestPet", latestPet);
		request.setAttribute("viewCount", viewCount);
		request.setAttribute("facilityUnreadCount", facilityUnreadCount);
	}

	// ダッシュボード画面へ遷移する
	private void forwardToDashboard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(DASHBOARD_JSP);
		dispatcher.forward(request, response);
	}
}
