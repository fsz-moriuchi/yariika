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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		String reservationIDStr = request.getParameter("reservationID");

		if (reservationIDStr == null || reservationIDStr.isEmpty()) {
			response.sendRedirect("ReservationConfirmServlet");
			return;
		}

		int reservationID = Integer.parseInt(reservationIDStr);

		ReserveDAO dao = new ReserveDAO();
		boolean result = dao.deleteReservation(reservationID);

		request.setAttribute("reservationID", reservationID);

		if (result) {
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("WEB-INF/jsp/reservationDeleteSuccess.jsp");
			dispatcher.forward(request, response);
		} else {
			request.setAttribute("errorMsg", "予約情報の削除に失敗しました。");
			RequestDispatcher dispatcher =
					request.getRequestDispatcher("WEB-INF/jsp/reservationEdit.jsp");
			dispatcher.forward(request, response);
		}
	}

	}