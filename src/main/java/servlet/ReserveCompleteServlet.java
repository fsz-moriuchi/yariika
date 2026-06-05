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

import dao.ReserveDAO;
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

		String reserveTimeStr = request.getParameter("reserveTime");

		LocalDate reserveDate = LocalDate.parse(reserveDateStr);
		LocalTime reserveTime = LocalTime.parse(reserveTimeStr);

		LocalDateTime reserveDateTime = LocalDateTime.of(reserveDate, reserveTime);

		Reserve reserve = new Reserve(petID, userId, reserveDateTime);
		ReserveDAO dao = new ReserveDAO();
		boolean result = dao.insertReserve(reserve);

		if (result) {
			request.setAttribute("reserveDate", reserveDate);
			request.setAttribute("reserveTime", reserveTime);

			session.removeAttribute("reserveDate");
			session.removeAttribute("reservePetID");
			session.removeAttribute("reserveFacilityID");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reserveComplete.jsp");
			dispatcher.forward(request, response);

		}

	}
}
