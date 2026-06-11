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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String reserveDateStr = (String) session.getAttribute("reserveDate");
		String userId = (String) session.getAttribute("userId");
		Integer petID = (Integer) session.getAttribute("reservePetID");
		String facilityID = (String) session.getAttribute("reserveFacilityID");
		

		String reserveTimeStr = request.getParameter("reserveTime");
		
		
		if (reserveDateStr == null || userId == null || petID == null || facilityID == null || reserveTimeStr == null) {
			session.removeAttribute("reserveDate");
			session.removeAttribute("reservePetID");
			session.removeAttribute("reserveFacilityID");

			request.setAttribute("errorMsg", "予約情報が確認できませんでした。もう一度ペット詳細画面から予約してください。");

			RequestDispatcher dispatcher =
					request.getRequestDispatcher("/WEB-INF/jsp/reserveError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		LocalDate reserveDate = LocalDate.parse(reserveDateStr);
		LocalTime reserveTime = LocalTime.parse(reserveTimeStr);

		LocalDateTime reserveDateTime = LocalDateTime.of(
				reserveDate, reserveTime);

		Reserve reserve = new Reserve(petID, userId, reserveDateTime);
		ReserveDAO dao1 = new ReserveDAO();
		
		if(dao1.existsReserveByPetID(petID)) {
			session.removeAttribute("reserveDate");
			session.removeAttribute("reservePetID");
			session.removeAttribute("reserveFacilityID");

			request.setAttribute("errorMsg", "予約処理を完了できませんでした。すでに予約中の見学がある、または他の方の予約が先に完了した可能性があります。");

			RequestDispatcher dispatcher =
					request.getRequestDispatcher("/WEB-INF/jsp/reserveError.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		boolean result = dao1.insertReserve(reserve);
		
		System.out.println("insert result = " + result);
		
		if (result) {
			FacilityInformationDAO  dao2 = new FacilityInformationDAO();
			FacilityInformation facilityInformation = dao2.findByFacilityID(facilityID);
			request.setAttribute("reserveDate", reserveDate);
			request.setAttribute("reserveTime", reserveTime);
			request.setAttribute("facilityInformation", facilityInformation);


			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reserveComplete.jsp");
			dispatcher.forward(request, response);

		}else {
			session.removeAttribute("reserveDate");
			session.removeAttribute("reservePetID");
			session.removeAttribute("reserveFacilityID");

			request.setAttribute("errorMsg", "予約処理を完了できませんでした。すでに予約中の見学がある、または他の方の予約が先に完了した可能性があります。");

			RequestDispatcher dispatcher =
					request.getRequestDispatcher("/WEB-INF/jsp/reserveError.jsp");
			dispatcher.forward(request, response);
			return;
		}

	}
}
