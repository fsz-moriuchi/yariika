package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

import dao.FacilityClosedDayDAO;
import dao.FacilityInformationDAO;
import dao.ReserveDAO;
import model.FacilityInformation;

@WebServlet("/ReservationEditServlet")
public class ReservationEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String reservationIDStr = request.getParameter("reservationID");
		String reserveTimeStr = request.getParameter("reserveTime");

		int reservationID = Integer.parseInt(reservationIDStr);
		LocalDateTime reserveTime = LocalDateTime.parse(reserveTimeStr);

		request.setAttribute("reservationID", reservationID);

		request.setAttribute("reserveTime", reserveTime);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reservationEdit.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String facilityID = (String) session.getAttribute("facilityId");

		int reservationID = Integer.parseInt(
				request.getParameter("reservationID"));

		String action = request.getParameter("action");

		if ("showTimes".equals(action)) {

			String reserveDateStr = request.getParameter("reserveDateStr");

			LocalDate reserveDate = LocalDate.parse(reserveDateStr);

			FacilityInformationDAO facilityDAO = new FacilityInformationDAO();

			FacilityInformation facilityInformation = facilityDAO.findByFacilityID(facilityID);

			LocalTime openTime = facilityInformation.getOpenTime();

			LocalTime closeTime = facilityInformation.getCloseTime();

			ReserveDAO reserveDAO = new ReserveDAO();

			List<LocalTime> reservedTimeList = reserveDAO.findByFacilityAndDate(
					facilityID,
					reserveDate);

			FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

			List<String> closedDayList = closedDayDAO.findByFacilityID(facilityID);

			List<String> timeList = new ArrayList<>();

			if (closedDayList.contains(reserveDate.getDayOfWeek().toString())) {
				request.setAttribute("errorMsg", "定休日を選択しています。");
			} else {
				LocalTime lastTime = closeTime.minusMinutes(30);
				LocalTime time = openTime;

				while (!time.isAfter(lastTime)) {
					if (!reservedTimeList.contains(time)) {
						timeList.add(time.toString());
					}
					time = time.plusMinutes(30);
				}
			}
			request.setAttribute("reservationID", reservationID);
			request.setAttribute("reserveDate", reserveDate);
			request.setAttribute("timeList", timeList);
			request.setAttribute("reserveTime",request.getParameter("currentReserveTime"));
			request.getRequestDispatcher("/WEB-INF/jsp/reservationEdit.jsp").forward(request, response);
		} else if ("update".equals(action)) {
			LocalDate reserveDate = LocalDate.parse(request.getParameter("reserveDateStr"));
			LocalTime reserveTime = LocalTime.parse(request.getParameter("reserveTimeStr"));
			LocalDateTime newReserveDateTime = LocalDateTime.of(reserveDate, reserveTime);

			ReserveDAO reserveDAO = new ReserveDAO();
			boolean result = reserveDAO.updateDateTime(reservationID, newReserveDateTime);

			if (result) {
				response.sendRedirect(
						"ReservationConfirmServlet");
			} else {
				request.setAttribute("errorMsg","予約日時の変更に失敗しました。");
				request.setAttribute("reservationID", reservationID);
				request.getRequestDispatcher("/WEB-INF/jsp/reservationEdit.jsp").forward(request, response);
			}
		}
	}
}