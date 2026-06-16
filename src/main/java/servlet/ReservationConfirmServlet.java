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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute("facilityId");

		ReserveDAO dao = new ReserveDAO();

		// 予約一覧取得
		List<ReserveView> reserveViewList = dao.findByFacilityIDForView(facilityId);

		// 絞り込み条件取得
		String dateStatus = request.getParameter("dateStatus");

		if (dateStatus == null || dateStatus.isEmpty()) {
			dateStatus = "all";
		}

		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);

		// 表示用リスト
		List<ReserveView> showDateList = new ArrayList<>();

		for (ReserveView reserveView : reserveViewList) {

			boolean addIn = true;

			// 今日
			if ("today".equals(dateStatus)) {
				if (!reserveView.getReserveTime().toLocalDate().equals(today)) {
					addIn = false;
				}
			}
			// 明日
			else if ("tomorrow".equals(dateStatus)) {
				if (!reserveView.getReserveTime().toLocalDate().equals(tomorrow)) {
					addIn = false;
				}
			}
			// 条件に合うものだけ追加
			if (addIn) {
				showDateList.add(reserveView);
			}
		}

		request.setAttribute("dateStatus", dateStatus);
		request.setAttribute("reserveViewList", showDateList);

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
