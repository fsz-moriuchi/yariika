package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
import model.Reserve;

@WebServlet("/ReserveCompleteServlet")
public class ReserveCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String RESERVE_DATE_KEY = "reserveDate";
	private static final String USER_ID_KEY = "userId";
	private static final String RESERVE_PET_ID_KEY = "reservePetID";
	private static final String RESERVE_FACILITY_ID_KEY = "reserveFacilityID";
	private static final String REQUEST_RESERVE_TIME_KEY = "reserveTime";
	private static final String REQUEST_RESERVE_DATE_KEY = "reserveDate";
	private static final String REQUEST_RESERVE_TIME_VIEW_KEY = "reserveTime";
	private static final String REQUEST_FACILITY_INFORMATION_KEY = "facilityInformation";
	private static final String ERROR_RESERVED_BY_OTHER_MESSAGE = "申し訳ありません。先ほど他の方の予約が完了したため、このペットは予約できません。";
	private static final String ERROR_ACTIVE_RESERVATION_MESSAGE = "すでに予約中の見学があります。新しい予約は、現在の予約が完了またはキャンセルされた後に可能です。";
	private static final String PET_DETAIL_REDIRECT_PREFIX = "PetDetailServlet?petID=";
	private static final String RESERVE_COMPLETE_JSP_PATH = "/WEB-INF/jsp/reserveComplete.jsp";
	private static final String RESERVE_JSP_PATH = "/WEB-INF/jsp/reserve.jsp";
	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String HOME_REDIRECT_URL = "HomeServlet";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String reserveDateString = (String) session.getAttribute(RESERVE_DATE_KEY);
		String userId = (String) session.getAttribute(USER_ID_KEY);
		Integer petId = (Integer) session.getAttribute(RESERVE_PET_ID_KEY);
		String facilityId = (String) session.getAttribute(RESERVE_FACILITY_ID_KEY);
		String reserveTimeString = request.getParameter(REQUEST_RESERVE_TIME_KEY);

		if (isMissing(reserveDateString) || isMissing(userId) || petId == null || isMissing(facilityId)
				|| isMissing(reserveTimeString)) {
			response.sendRedirect(HOME_REDIRECT_URL);
			return;
		}

		LocalDate reserveDate;
		LocalTime reserveTime;
		try {
			reserveDate = LocalDate.parse(reserveDateString);
			reserveTime = LocalTime.parse(reserveTimeString);
		} catch (Exception e) {
			response.sendRedirect(HOME_REDIRECT_URL);
			return;
		}

		LocalDateTime reserveDateTime = LocalDateTime.of(reserveDate, reserveTime);
		Reserve reserve = new Reserve(petId, userId, reserveDateTime);
		ReserveDAO reserveDAO = new ReserveDAO();

		if (reserveDAO.existsReserveByPetID(petId)) {
			clearReserveSession(session);
			session.setAttribute("errorMsg", ERROR_RESERVED_BY_OTHER_MESSAGE);
			response.sendRedirect(PET_DETAIL_REDIRECT_PREFIX + petId);
			return;
		}

		boolean inserted = reserveDAO.insertReserve(reserve);
		System.out.println("insert result = " + inserted);

		if (inserted) {
			handleReserveSuccess(request, response, session, facilityId, reserveDate, reserveTime);
		} else {
			handleReserveFailure(request, response);
		}
	}

	private boolean isMissing(String value) {
		return value == null || value.isEmpty();
	}

	private void handleReserveSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			String facilityId,
			LocalDate reserveDate,
			LocalTime reserveTime) throws ServletException, IOException {

		FacilityInformationDAO facilityInformationDAO = new FacilityInformationDAO();
		FacilityInformation facilityInformation = facilityInformationDAO.findByFacilityId(facilityId);

		request.setAttribute(REQUEST_RESERVE_DATE_KEY, reserveDate);
		request.setAttribute(REQUEST_RESERVE_TIME_VIEW_KEY, reserveTime);
		request.setAttribute(REQUEST_FACILITY_INFORMATION_KEY, facilityInformation);

		clearReserveSession(session);

		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVE_COMPLETE_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void handleReserveFailure(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("errorMsg", ERROR_ACTIVE_RESERVATION_MESSAGE);
		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVE_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void clearReserveSession(HttpSession session) {
		session.removeAttribute(RESERVE_DATE_KEY);
		session.removeAttribute(RESERVE_PET_ID_KEY);
		session.removeAttribute(RESERVE_FACILITY_ID_KEY);
	}
}