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

	private static final String WELCOME_SERVLET = "WelcomeServlet";
	private static final String RESERVATION_CONFIRM_SERVLET = "ReservationConfirmServlet";
	private static final String RESERVATION_EDIT_JSP = "/WEB-INF/jsp/reservationEdit.jsp";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		String facilityID = (String) session.getAttribute("facilityId");
		Integer reservationID = parseReservationID(request.getParameter("reservationID"));

		if (reservationID == null) {
			response.sendRedirect(RESERVATION_CONFIRM_SERVLET);
			return;
		}

		ReserveDAO reserveDAO = new ReserveDAO();
		LocalDateTime reserveTime =
				reserveDAO.findReserveTimeByFacility(reservationID, facilityID);

		if (reserveTime == null) {
			response.sendRedirect(RESERVATION_CONFIRM_SERVLET);
			return;
		}

		request.setAttribute("reservationID", reservationID);
		request.setAttribute("reserveTime", reserveTime);

		forwardToEditPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		String facilityID = (String) session.getAttribute("facilityId");
		Integer reservationID = parseReservationID(request.getParameter("reservationID"));

		if (reservationID == null) {
			response.sendRedirect(RESERVATION_CONFIRM_SERVLET);
			return;
		}

		String action = request.getParameter("action");

		if ("showTimes".equals(action)) {
			showAvailableTimes(request, response, reservationID, facilityID);
			return;
		}

		if ("update".equals(action)) {
			updateReservationTime(request, response, reservationID, facilityID);
			return;
		}

		response.sendRedirect(RESERVATION_CONFIRM_SERVLET);
	}

	private void showAvailableTimes(
			HttpServletRequest request,
			HttpServletResponse response,
			int reservationID,
			String facilityID)
			throws ServletException, IOException {

		String reserveDateStr = request.getParameter("reserveDateStr");
		LocalDate reserveDate = parseLocalDate(reserveDateStr);

		ReserveDAO reserveDAO = new ReserveDAO();
		LocalDateTime currentReserveTime =
				reserveDAO.findReserveTimeByFacility(reservationID, facilityID);

		request.setAttribute("reservationID", reservationID);
		request.setAttribute("reserveTime", currentReserveTime);

		if (reserveDate == null) {
			request.setAttribute("errorMsg", "変更日付を正しく選択してください。");
			forwardToEditPage(request, response);
			return;
		}

		FacilityInformationDAO facilityDAO = new FacilityInformationDAO();
		FacilityInformation facilityInformation =
				facilityDAO.findByFacilityID(facilityID);

		if (facilityInformation == null) {
			request.setAttribute("errorMsg", "施設情報が見つかりませんでした。");
			forwardToEditPage(request, response);
			return;
		}

		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();
		List<String> closedDayList = closedDayDAO.findByFacilityID(facilityID);

		List<String> timeList = new ArrayList<>();

		if (closedDayList.contains(reserveDate.getDayOfWeek().toString())) {
			request.setAttribute("errorMsg", "定休日を選択しています。");
		} else {
			List<LocalTime> reservedTimeList =
					reserveDAO.findByFacilityAndDate(facilityID, reserveDate);

			timeList = createAvailableTimeList(
					facilityInformation.getOpenTime(),
					facilityInformation.getCloseTime(),
					reservedTimeList);
		}

		request.setAttribute("reserveDate", reserveDate);
		request.setAttribute("timeList", timeList);

		forwardToEditPage(request, response);
	}

	private void updateReservationTime(
			HttpServletRequest request,
			HttpServletResponse response,
			int reservationID,
			String facilityID)
			throws ServletException, IOException {

		LocalDate reserveDate =
				parseLocalDate(request.getParameter("reserveDateStr"));
		LocalTime reserveTime =
				parseLocalTime(request.getParameter("reserveTimeStr"));

		if (reserveDate == null || reserveTime == null) {
			request.setAttribute("errorMsg", "予約日時を正しく選択してください。");
			request.setAttribute("reservationID", reservationID);

			ReserveDAO reserveDAO = new ReserveDAO();
			request.setAttribute(
					"reserveTime",
					reserveDAO.findReserveTimeByFacility(reservationID, facilityID));

			forwardToEditPage(request, response);
			return;
		}

		LocalDateTime newReserveDateTime =
				LocalDateTime.of(reserveDate, reserveTime);

		ReserveDAO reserveDAO = new ReserveDAO();
		boolean updateResult =
				reserveDAO.updateDateTimeByFacility(
						reservationID,
						facilityID,
						newReserveDateTime);

		if (updateResult) {
			response.sendRedirect(RESERVATION_CONFIRM_SERVLET);
			return;
		}

		request.setAttribute("errorMsg", "予約日時の変更に失敗しました。");
		request.setAttribute("reservationID", reservationID);
		request.setAttribute(
				"reserveTime",
				reserveDAO.findReserveTimeByFacility(reservationID, facilityID));

		forwardToEditPage(request, response);
	}

	private List<String> createAvailableTimeList(
			LocalTime openTime,
			LocalTime closeTime,
			List<LocalTime> reservedTimeList) {

		List<String> timeList = new ArrayList<>();

		LocalTime lastTime = closeTime.minusMinutes(30);
		LocalTime time = openTime;

		while (!time.isAfter(lastTime)) {
			if (!reservedTimeList.contains(time)) {
				timeList.add(time.toString());
			}
			time = time.plusMinutes(30);
		}

		return timeList;
	}

	private Integer parseReservationID(String reservationIDStr) {
		if (reservationIDStr == null || reservationIDStr.isEmpty()) {
			return null;
		}

		try {
			return Integer.parseInt(reservationIDStr);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private LocalDate parseLocalDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}

		try {
			return LocalDate.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	private LocalTime parseLocalTime(String timeStr) {
		if (timeStr == null || timeStr.isEmpty()) {
			return null;
		}

		try {
			return LocalTime.parse(timeStr);
		} catch (Exception e) {
			return null;
		}
	}

	private void forwardToEditPage(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher =
				request.getRequestDispatcher(RESERVATION_EDIT_JSP);
		dispatcher.forward(request, response);
	}
}
