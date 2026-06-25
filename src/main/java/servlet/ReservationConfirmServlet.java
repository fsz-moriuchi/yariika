package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ReserveDAO;
import model.ReserveView;

@WebServlet("/ReservationConfirmServlet")
public class ReservationConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String RESERVATION_CONFIRM_JSP_PATH = "WEB-INF/jsp/reservationConfirm.jsp";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_DATE_STATUS_KEY = "dateStatus";
	private static final String REQUEST_RESERVE_VIEW_LIST_KEY = "reserveViewList";
	private static final String REQUEST_RESERVATION_ID_KEY = "reservationID";
	private static final String DEFAULT_DATE_STATUS = "all";
	private static final String DATE_STATUS_TODAY = "today";
	private static final String DATE_STATUS_TOMORROW = "tomorrow";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
		ReserveDAO reserveDAO = new ReserveDAO();

		List<ReserveView> reserveViewList = reserveDAO.findByFacilityIDForView(facilityId);
		String dateStatus = normalizeDateStatus(request.getParameter(REQUEST_DATE_STATUS_KEY));
		List<ReserveView> filteredList = filterByDateStatus(reserveViewList, dateStatus);

		request.setAttribute(REQUEST_DATE_STATUS_KEY, dateStatus);
		request.setAttribute(REQUEST_RESERVE_VIEW_LIST_KEY, filteredList);

		forwardToReservationConfirmPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
		Integer reservationId = Integer.parseInt(request.getParameter(REQUEST_RESERVATION_ID_KEY));

		ReserveDAO reserveDAO = new ReserveDAO();
		reserveDAO.deleteReservation(reservationId);

		List<ReserveView> reserveViewList = reserveDAO.findByFacilityIDForView(facilityId);
		request.setAttribute(REQUEST_RESERVE_VIEW_LIST_KEY, reserveViewList);

		response.sendRedirect("ReservationConfirmServlet");
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_FACILITY_ID_KEY) != null;
	}

	private String normalizeDateStatus(String dateStatus) {
		if (dateStatus == null || dateStatus.isEmpty()) {
			return DEFAULT_DATE_STATUS;
		}
		return dateStatus;
	}

	private List<ReserveView> filterByDateStatus(List<ReserveView> reserveViewList, String dateStatus) {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);

		List<ReserveView> filteredList = new ArrayList<>();
		for (ReserveView reserveView : reserveViewList) {
			if (shouldInclude(reserveView, dateStatus, today, tomorrow)) {
				filteredList.add(reserveView);
			}
		}
		return filteredList;
	}

	private boolean shouldInclude(ReserveView reserveView, String dateStatus, LocalDate today, LocalDate tomorrow) {
		LocalDate reserveDate = reserveView.getReserveTime().toLocalDate();

		if (DATE_STATUS_TODAY.equals(dateStatus)) {
			return reserveDate.equals(today);
		}
		if (DATE_STATUS_TOMORROW.equals(dateStatus)) {
			return reserveDate.equals(tomorrow);
		}
		return true;
	}

	private void forwardToReservationConfirmPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVATION_CONFIRM_JSP_PATH);
		dispatcher.forward(request, response);
	}
}