package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.FacilitiesDAO;
import model.Facility;
import util.PasswordUtil;

@WebServlet("/FacilityRegisterServlet")
public class FacilityRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityRegister.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String facilityId = request.getParameter("facilityId");
		String password = request.getParameter("password");

		String hash = PasswordUtil.hashPassword(password);

		Facility facility = new Facility(facilityId, hash);
		FacilitiesDAO dao = new FacilitiesDAO();
		boolean result = dao.registerFacility(facility);

		if (result) {
			response.sendRedirect("FacilityLoginServlet");
		} else {
			request.setAttribute("errorMsg", "その店舗IDは既に使用されています");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityRegister.jsp");
			dispatcher.forward(request, response);
		}
	}
}
