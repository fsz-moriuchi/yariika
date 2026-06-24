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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String facilityId = (String) session.getAttribute("facilityId");

		setTodayReserveCount(request, facilityId);
		setPetCount(request, facilityId);
		setNextReserve(request, facilityId);
		setLatestPet(request, facilityId);
		setViewCount(request, facilityId);
		setUnreadMessageCount(request, facilityId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	private void setTodayReserveCount(HttpServletRequest request, String facilityId) {
		ReserveDAO reserveDAO = new ReserveDAO();
		int countTodayReserve = reserveDAO.countTodayReserve(facilityId);
		request.setAttribute("countTodayReserve", countTodayReserve);
	}

	private void setPetCount(HttpServletRequest request, String facilityId) {
		PetListDAO petListDAO = new PetListDAO();
		int petCount = petListDAO.countByfacilityID(facilityId);
		request.setAttribute("petCount", petCount);
	}

	private void setNextReserve(HttpServletRequest request, String facilityId) {
		ReserveDAO reserveDAO = new ReserveDAO();
		Reserve nextReserve = reserveDAO.findnextReserve(facilityId);
		request.setAttribute("reserve", nextReserve);
	}

	private void setLatestPet(HttpServletRequest request, String facilityId) {
		PetListDAO petListDAO = new PetListDAO();
		FavoritePet latestPet = petListDAO.findLatestPetByFacilityID(facilityId);
		request.setAttribute("latestPet", latestPet);
	}

	private void setViewCount(HttpServletRequest request, String facilityId) {
		FacilityViewDAO facilityViewDAO = new FacilityViewDAO();
		int viewCount = facilityViewDAO.getViewCount(facilityId);
		request.setAttribute("viewCount", viewCount);
	}

	private void setUnreadMessageCount(HttpServletRequest request, String facilityId) {
		MessageDAO messageDAO = new MessageDAO();
		int facilityUnreadCount = messageDAO.countUnreadByFacilityId(facilityId);
		request.setAttribute("facilityUnreadCount", facilityUnreadCount);
	}
}