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

import dao.FacilityClosedDayDAO;
import dao.FacilityInformationDAO;
import dao.ReserveDAO;
import model.FacilityInformation;

@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String RESERVE_JSP_PATH = "WEB-INF/jsp/reserve.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String SESSION_RESERVE_DATE_KEY = "reserveDate";
	private static final String SESSION_RESERVE_PET_ID_KEY = "reservePetID";
	private static final String SESSION_RESERVE_FACILITY_ID_KEY = "reserveFacilityID";
	private static final String REQUEST_MIN_DATE_KEY = "minDate";
	private static final String REQUEST_MAX_DATE_KEY = "maxDate";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REQUEST_FACILITY_ID_KEY = "facilityID";
	private static final String REQUEST_RESERVE_DATE_KEY = "reserveDate";
	private static final String REQUEST_TIME_LIST_KEY = "timeList";
	private static final String REQUEST_RESERVE_DATE_STR_KEY = "reserveDateStr";
	private static final String ERROR_RESERVED_INFO_MESSAGE = "予約情報が見つかりませんでした。もう一度ペット詳細画面から予約してください。";
	private static final String ERROR_DATE_RANGE_MESSAGE = "予約日は3日後から1週間後までの範囲で選択してください。";
	private static final String ERROR_CLOSED_DAY_MESSAGE = "定休日を選択しています。";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		LocalDate minDate = calculateMinDate();
		LocalDate maxDate = calculateMaxDate();

		request.setAttribute(REQUEST_MIN_DATE_KEY, minDate);
		request.setAttribute(REQUEST_MAX_DATE_KEY, maxDate);

		forwardToReservePage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		LocalDate minDate = calculateMinDate();
		LocalDate maxDate = calculateMaxDate();

		Integer petId = (Integer) session.getAttribute(SESSION_RESERVE_PET_ID_KEY);
		String facilityId = (String) session.getAttribute(SESSION_RESERVE_FACILITY_ID_KEY);
		String reserveDateString = request.getParameter(REQUEST_RESERVE_DATE_STR_KEY);

		if (petId == null || facilityId == null) {
			request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, ERROR_RESERVED_INFO_MESSAGE);
			forwardToReservePage(request, response);
			return;
		}

		LocalDate reserveDate = LocalDate.parse(reserveDateString);
		if (!isWithinAllowedRange(reserveDate, minDate, maxDate)) {
			request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, ERROR_DATE_RANGE_MESSAGE);
			request.setAttribute(REQUEST_MIN_DATE_KEY, minDate);
			request.setAttribute(REQUEST_MAX_DATE_KEY, maxDate);
			forwardToReservePage(request, response);
			return;
		}

		FacilityInformation facilityInformation = loadFacilityInformation(facilityId);
		LocalTime openTime = facilityInformation.getOpenTime();
		LocalTime closeTime = facilityInformation.getCloseTime();

		List<LocalTime> reservedTimeList = loadReservedTimeList(facilityId, reserveDate);
		List<String> closedDayList = loadClosedDayList(facilityId);

		List<String> timeList = buildAvailableTimeList(reserveDate, openTime, closeTime, reservedTimeList,
				closedDayList, request);

		request.setAttribute(REQUEST_PET_ID_KEY, petId);
		request.setAttribute(REQUEST_FACILITY_ID_KEY, facilityId);
		request.setAttribute(REQUEST_RESERVE_DATE_KEY, reserveDate);
		request.setAttribute(REQUEST_TIME_LIST_KEY, timeList);
		request.setAttribute(REQUEST_MIN_DATE_KEY, minDate);
		request.setAttribute(REQUEST_MAX_DATE_KEY, maxDate);

		session.setAttribute(SESSION_RESERVE_DATE_KEY, reserveDateString);

		forwardToReservePage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
	}

	private LocalDate calculateMinDate() {
		return LocalDate.now().plusDays(3);
	}

	private LocalDate calculateMaxDate() {
		return LocalDate.now().plusWeeks(1);
	}

	private boolean isWithinAllowedRange(LocalDate reserveDate, LocalDate minDate, LocalDate maxDate) {
		return !reserveDate.isBefore(minDate) && !reserveDate.isAfter(maxDate);
	}

	private FacilityInformation loadFacilityInformation(String facilityId) {
		FacilityInformationDAO facilityDAO = new FacilityInformationDAO();
		return facilityDAO.findByFacilityId(facilityId);
	}

	private List<LocalTime> loadReservedTimeList(String facilityId, LocalDate reserveDate) {
		ReserveDAO reserveDAO = new ReserveDAO();
		return reserveDAO.findByFacilityAndDate(facilityId, reserveDate);
	}

	private List<String> loadClosedDayList(String facilityId) {
		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();
		return closedDayDAO.findByFacilityID(facilityId);
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
			request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, ERROR_CLOSED_DAY_MESSAGE);
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

	private void forwardToReservePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVE_JSP_PATH);
		dispatcher.forward(request, response);
	}
}