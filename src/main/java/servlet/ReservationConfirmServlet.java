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
import model.Reserve;
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

		List<Reserve> reservedDataList = dao.findByFacilityID(facilityId);

		//予約絞り込み（すべで・今日・明日）
		List<Reserve> showDateList = new ArrayList<>();
		String dateStatus = request.getParameter("dateStatus");
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);

		//すべて表示
		if (dateStatus == null || dateStatus.isEmpty()) {
			dateStatus = "all";
		}

		for (Reserve r : reservedDataList) {
			boolean addIn = true;

			if ("today".equals(dateStatus)) {
				//今日を選択 >>  showDateList に入れる
				if (!r.getReserveTime().toLocalDate().equals(today)) {
					addIn = false;
				}
				//明日を選択 >>  showDateList に入れる
			} else if ("tomorrow".equals(dateStatus)) {
				if (!r.getReserveTime().toLocalDate().equals(tomorrow)) {
					addIn = false;
				}
			}
			//if(addIn is true)showDateList に入れる
			if (addIn) {
				showDateList.add(r);
			}
		}
		reservedDataList = showDateList;
		request.setAttribute("dateStatus", dateStatus);
		request.setAttribute("reservedDataList", reservedDataList);

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
