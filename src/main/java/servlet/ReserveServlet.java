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
import dao.ReserveDAO;
import model.FacilityInformation;

@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		LocalDate minDate = LocalDate.now().plusDays(3);
		LocalDate maxDate = LocalDate.now().plusWeeks(1);

		request.setAttribute("minDate", minDate);
		request.setAttribute("maxDate", maxDate);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reserve.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		LocalDate minDate = LocalDate.now().plusDays(3);
		LocalDate maxDate = LocalDate.now().plusWeeks(1);

		Integer petID = (Integer)session.getAttribute("reservePetID");
		String facilityID = (String) session.getAttribute("reserveFacilityID");

		String reserveDateStr = request.getParameter("reserveDateStr");
		
		if (petID == null || facilityID == null) {
			request.setAttribute("errorMsg", "予約情報が見つかりませんでした。もう一度ペット詳細画面から予約してください。");
			request.getRequestDispatcher("/WEB-INF/jsp/reserve.jsp").forward(request, response);
			return;
		}

		FacilityInformationDAO dao1 = new FacilityInformationDAO();
		FacilityInformation facilityInformation = dao1.findByFacilityID(facilityID);
		

		LocalTime openTime = facilityInformation.getOpenTime();
		LocalTime closeTime = facilityInformation.getCloseTime();
		String closedDay = facilityInformation.getClosedDay();
		
		LocalTime lastTime = closeTime.minusMinutes(30);
		LocalTime time = openTime;

		List<String> timeList = new ArrayList<>();

		LocalDate reserveDate = LocalDate.parse(reserveDateStr);

		ReserveDAO dao2 = new ReserveDAO();
		List<LocalTime> reservedTimeList = dao2.findByFacilityAndDate(facilityID, reserveDate);
		
		if(reserveDate.isBefore(minDate) || reserveDate.isAfter(maxDate)) {
			request.setAttribute("errorMsg", "予約日は3日後から1週間後までの範囲で選択してください。");
			request.setAttribute("minDate", minDate);
			request.setAttribute("maxDate", maxDate);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reserve.jsp");
			dispatcher.forward(request, response);
			return;
		}

		if (!reserveDate.getDayOfWeek().name().equals(closedDay)) {
			while (!time.isAfter(lastTime)) {
				if (!reservedTimeList.contains(time)) {
					timeList.add(time.toString());
				}
				time = time.plusMinutes(30);
			}
		} else {
			request.setAttribute("errorMsg", "定休日を選択しています。");
		}

		request.setAttribute("petID", petID);
		request.setAttribute("facilityID", facilityID);
		request.setAttribute("reserveDate", reserveDate);
		request.setAttribute("timeList", timeList);

		session.setAttribute("reserveDate", reserveDateStr);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reserve.jsp");
		dispatcher.forward(request, response);

	}
}
