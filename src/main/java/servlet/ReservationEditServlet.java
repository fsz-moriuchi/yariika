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

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String RESERVATION_EDIT_JSP_PATH = "WEB-INF/jsp/reservationEdit.jsp";
	private static final String RESERVATION_CONFIRM_REDIRECT_URL = "ReservationConfirmServlet";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_RESERVATION_ID_KEY = "reservationID";
	private static final String REQUEST_RESERVE_TIME_KEY = "reserveTime";
	private static final String REQUEST_RESERVE_DATE_KEY = "reserveDate";
	private static final String REQUEST_RESERVE_DATE_STR_KEY = "reserveDateStr";
	private static final String REQUEST_RESERVE_TIME_STR_KEY = "reserveTimeStr";
	private static final String REQUEST_CURRENT_RESERVE_TIME_KEY = "currentReserveTime";
	private static final String REQUEST_ACTION_KEY = "action";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String ACTION_SHOW_TIMES = "showTimes";
	private static final String ACTION_UPDATE = "update";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		Integer reservationId = Integer.parseInt(request.getParameter(REQUEST_RESERVATION_ID_KEY));
		LocalDateTime reserveTime = LocalDateTime.parse(request.getParameter(REQUEST_RESERVE_TIME_KEY));

		request.setAttribute(REQUEST_RESERVATION_ID_KEY, reservationId);
		request.setAttribute(REQUEST_RESERVE_TIME_KEY, reserveTime);

		forwardToReservationEditPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);

		Integer reservationId = Integer.parseInt(request.getParameter(REQUEST_RESERVATION_ID_KEY));
		String action = request.getParameter(REQUEST_ACTION_KEY);

		if (ACTION_SHOW_TIMES.equals(action)) {
			handleShowTimes(request, response, facilityId, reservationId);
		} else if (ACTION_UPDATE.equals(action)) {
			handleUpdate(request, response, reservationId);
		}
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_FACILITY_ID_KEY) != null;
	}

	private void handleShowTimes(
			HttpServletRequest request,
			HttpServletResponse response,
			String facilityId,
			Integer reservationId) throws ServletException, IOException {

		LocalDate reserveDate = LocalDate.parse(request.getParameter(REQUEST_RESERVE_DATE_STR_KEY));

		FacilityInformationDAO facilityDAO = new FacilityInformationDAO();
		FacilityInformation facilityInformation = facilityDAO.findByFacilityId(facilityId);

		LocalTime openTime = facilityInformation.getOpenTime();
		LocalTime closeTime = facilityInformation.getCloseTime();

		ReserveDAO reserveDAO = new ReserveDAO();
		List<LocalTime> reservedTimeList = reserveDAO.findByFacilityAndDate(facilityId, reserveDate);

		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();
		List<String> closedDayList = closedDayDAO.findByFacilityID(facilityId);

		List<String> timeList = buildAvailableTimeList(
				reserveDate,
				openTime,
				closeTime,
				reservedTimeList,
				closedDayList,
				request);

		request.setAttribute(REQUEST_RESERVATION_ID_KEY, reservationId);
		request.setAttribute(REQUEST_RESERVE_DATE_KEY, reserveDate);
		request.setAttribute("timeList", timeList);
		request.setAttribute(REQUEST_RESERVE_TIME_KEY, request.getParameter(REQUEST_CURRENT_RESERVE_TIME_KEY));

		forwardToReservationEditPage(request, response);
	}

	private List<String> buildAvailableTimeList(
			LocalDate reserveDate,
			LocalTime openTime,
			LocalTime closeTime,
			List<LocalTime> reservedTimeList,
			List<String> closedDayList,
			HttpServletRequest request) {

		List<String> timeList = new ArrayList<>();

		if (closedDayList.contains(reserveDate.getDayOfWeek().toString())) {
			request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, "定休日を選択しています。");
			return timeList;
		}

		LocalTime lastTime = closeTime.minusMinutes(30);
		LocalTime currentTime = openTime;

		while (!currentTime.isAfter(lastTime)) {
			if (!reservedTimeList.contains(currentTime)) {
				timeList.add(currentTime.toString());
			}
			currentTime = currentTime.plusMinutes(30);
		}

		return timeList;
	}

	private void handleUpdate(HttpServletRequest request, HttpServletResponse response, Integer reservationId)
			throws ServletException, IOException {

		LocalDate reserveDate = LocalDate.parse(request.getParameter(REQUEST_RESERVE_DATE_STR_KEY));
		LocalTime reserveTime = LocalTime.parse(request.getParameter(REQUEST_RESERVE_TIME_STR_KEY));
		LocalDateTime newReserveDateTime = LocalDateTime.of(reserveDate, reserveTime);

		ReserveDAO reserveDAO = new ReserveDAO();
		boolean result = reserveDAO.updateDateTime(reservationId, newReserveDateTime);

		if (result) {
			response.sendRedirect(RESERVATION_CONFIRM_REDIRECT_URL);
		} else {
			request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, "予約日時の変更に失敗しました。");
			request.setAttribute(REQUEST_RESERVATION_ID_KEY, reservationId);
			forwardToReservationEditPage(request, response);
		}
	}

	private void forwardToReservationEditPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVATION_EDIT_JSP_PATH);
		dispatcher.forward(request, response);
	}
}