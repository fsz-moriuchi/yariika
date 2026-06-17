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
import model.Reserve;

@WebServlet("/ReserveCheckServlet")
public class ReserveCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String userId = (String) session.getAttribute("userId");

		ReserveDAO dao = new ReserveDAO();
		Reserve reserve = dao.reserveCheck(userId);

		request.setAttribute("reserve", reserve);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reserveCheck.jsp");
		dispatcher.forward(request, response);
	}

}
