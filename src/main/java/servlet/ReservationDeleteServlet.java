package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ReserveDAO;

@WebServlet("/ReservationDeleteServlet")
public class ReservationDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String RESERVATION_CONFIRM_REDIRECT_URL = "ReservationConfirmServlet";
	private static final String RESERVATION_DELETE_SUCCESS_JSP_PATH = "WEB-INF/jsp/reservationDeleteSuccess.jsp";
	private static final String RESERVATION_EDIT_JSP_PATH = "WEB-INF/jsp/reservationEdit.jsp";
	private static final String REQUEST_RESERVATION_ID_KEY = "reservationID";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String ERROR_DELETE_MESSAGE = "予約情報の削除に失敗しました。";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String reservationIdString = request.getParameter(REQUEST_RESERVATION_ID_KEY);
		if (isEmpty(reservationIdString)) {
			response.sendRedirect(RESERVATION_CONFIRM_REDIRECT_URL);
			return;
		}

		Integer reservationId = parseReservationId(reservationIdString);
		if (reservationId == null) {
			forwardToReservationEditPage(request, response, ERROR_DELETE_MESSAGE);
			return;
		}

		boolean deleted = deleteReservation(reservationId);
		request.setAttribute(REQUEST_RESERVATION_ID_KEY, reservationId);

		logDeleteResult(reservationIdString, reservationId, deleted);

		if (deleted) {
			forwardToReservationDeleteSuccessPage(request, response);
		} else {
			forwardToReservationEditPage(request, response, ERROR_DELETE_MESSAGE);
		}
	}

	private boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private Integer parseReservationId(String reservationIdString) {
		try {
			return Integer.parseInt(reservationIdString);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private boolean deleteReservation(Integer reservationId) {
		ReserveDAO reserveDAO = new ReserveDAO();
		return reserveDAO.deleteReservation(reservationId);
	}

	private void logDeleteResult(String reservationIdString, Integer reservationId, boolean result) {
		System.out.println("削除対象 reservationIDStr = " + reservationIdString);
		System.out.println("削除対象 reservationID = " + reservationId);
		System.out.println("削除結果 result = " + result);
	}

	private void forwardToReservationDeleteSuccessPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVATION_DELETE_SUCCESS_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToReservationEditPage(HttpServletRequest request, HttpServletResponse response,
			String errorMessage)
			throws ServletException, IOException {
		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, errorMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher(RESERVATION_EDIT_JSP_PATH);
		dispatcher.forward(request, response);
	}
}