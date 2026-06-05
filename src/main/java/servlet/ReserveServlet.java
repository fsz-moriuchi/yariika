package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityInformationDAO;
import model.FacilityInformation;

@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reserve.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		int petID = (int) session.getAttribute("reservePetID");
		String facilityID = (String) session.getAttribute("reserveFacilityID");

		String reserveDateStr = request.getParameter("reserveDateStr");

		FacilityInformationDAO dao = new FacilityInformationDAO();
		FacilityInformation facilityInformation = dao.findByFacilityID(facilityID);
		
		LocalTime openTime = facilityInformation.getOpenTime();
		LocalTime closeTime = facilityInformation.getCloseTime();
		String closedDay = facilityInformation.getClosedDay();

		LocalTime lastTime = closeTime.minusHours(1);
		LocalTime time = openTime;

		List<String> timeList = new ArrayList<>();
		
		LocalDate reserveDate = LocalDate.parse(reserveDateStr);
		
		if(!reserveDate.getDayOfWeek().name().equals(closedDay)) {
			while (!time.isAfter(lastTime)) {
				timeList.add(time.toString());
				time = time.plusMinutes(30);
			}}else if(reserveDate.getDayOfWeek().name().equals(closedDay)){
				request.setAttribute("errorMsg", "定休日を選択しています");
			}
		
		request.setAttribute("petID", petID);
		request.setAttribute("facilityID", facilityID);
		request.setAttribute("reserveDate", reserveDate);
		request.setAttribute("timeList", timeList);
		
		session.setAttribute("reserveDate", reserveDateStr);

		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/reserve.jsp");
		dispatcher.forward(request, response);

	}
}
