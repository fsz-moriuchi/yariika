package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityInfomationDAO;
import model.FacilityInformation;

@WebServlet("/FacilityPageServlet")
public class FacilityPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String facilityId = (String) session.getAttribute("facilityId");

		FacilityInfomationDAO dao = new FacilityInfomationDAO();
		FacilityInformation facilityInfo = dao.findByFacilityId(facilityId);
		boolean registered = (facilityInfo != null);
		request.setAttribute("registered", registered);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/facilitypage.jsp");
		dispatcher.forward(request, response);
	}
}
