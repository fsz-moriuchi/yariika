package servlet;

import java.io.IOException;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute("facilityId");

		ReserveDAO dao = new ReserveDAO();
		List<ReserveView> reserveViewList = dao.findByFacilityIDForView(facilityId);
		request.setAttribute("reserveViewList", reserveViewList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reservationConfirm.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute("facilityId");

		int reservationID = Integer.parseInt(request.getParameter("reservationID"));

		ReserveDAO dao = new ReserveDAO();
		boolean result = dao.deleteReservation(reservationID);

		List<ReserveView> reserveViewList = dao.findByFacilityIDForView(facilityId);
		request.setAttribute("reserveViewList", reserveViewList);

		response.sendRedirect("ReservationConfirmServlet");
	}

}
