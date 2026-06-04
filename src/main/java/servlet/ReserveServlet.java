package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.FacilityInformationDAO;
import dao.ReserveDAO;
import model.Reserve;

@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String facilityID = request.getParameter("facilityID");
		request.setAttribute("facilityID", facilityID);
		
		FacilityInformationDAO dao = new FacilityInformationDAO();
		 dao.findByFacilityID(facilityID);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reserve.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		int reservationID = Integer.parseInt(request.getParameter("reservationID"));
		int petID = Integer.parseInt(request.getParameter("petID"));
		String userID = request.getParameter("userID");
		String facilityID = request.getParameter("facilityID");
		String reserveStatus = request.getParameter("reserveStatus");
		String reserveDate = request.getParameter("reserveDate");
		String reserveTimeStr = request.getParameter("reserveTime");

		LocalDateTime reserveTime = LocalDateTime.parse(reserveDate + "T" + reserveTimeStr);

		request.setAttribute("petID", petID);
		request.setAttribute("userID", userID);
		request.setAttribute("facilityID", facilityID);
		request.setAttribute("reserveTime", reserveTime);

		Reserve reserve = new Reserve(reservationID, petID, userID, facilityID, reserveStatus, reserveTime);
		ReserveDAO dao = new ReserveDAO();
		boolean result = dao.insertReserve(reserve);

		if (result) {
			response.sendRedirect("ReserveCompleteServlet");
		}

	}
}
