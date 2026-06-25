package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ReserveDAO;

@WebServlet("/ReservationDeleteServlet")
public class ReservationDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String WELCOME_SERVLET = "WelcomeServlet";
	private static final String RESERVATION_CONFIRM_SERVLET = "ReservationConfirmServlet";
	private static final String DELETE_SUCCESS_JSP = "WEB-INF/jsp/reservationDeleteSuccess.jsp";

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

		ReserveDAO reserveDAO = new ReserveDAO();
		boolean deleteResult = reserveDAO.deleteReservationByFacility(reservationID, facilityID);

		if (deleteResult) {
			request.setAttribute("reservationID", reservationID);

			RequestDispatcher dispatcher =
					request.getRequestDispatcher(DELETE_SUCCESS_JSP);
			dispatcher.forward(request, response);
			return;
		}

		request.setAttribute("errorMsg", "予約情報の削除に失敗しました。");
		response.sendRedirect(RESERVATION_CONFIRM_SERVLET);
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
}

