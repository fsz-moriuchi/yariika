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

/**
 * Servlet implementation class FacilityInfomationConfirmServlet
 */
@WebServlet("/FacilityInfomationConfirmServlet")
public class FacilityInfomationConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute("facilityId");
		//店舗情報の取得
		FacilityInfomationDAO dao = new FacilityInfomationDAO();
		FacilityInformation facilityInfo = dao.findByFacilityId(facilityId);

		request.setAttribute("facilityInfo",facilityInfo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/facilityInfomationConfirm.jsp");
		dispatcher.forward(request, response);
	}

}
