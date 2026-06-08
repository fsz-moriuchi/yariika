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

@WebServlet("/FacilityInformationEditServlet")
public class FacilityInformationEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String facilityId = (String) session.getAttribute("facilityId");

		FacilityInfomationDAO dao = new FacilityInfomationDAO();
		FacilityInformation facilityInfo = dao.findByFacilityId(facilityId);

		request.setAttribute("facilityInfo", facilityInfo);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityInformationEdit.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String facilityId = (String) session.getAttribute("facilityId");

		FacilityInformation facilityInfo = new FacilityInformation(
				facilityId,
				request.getParameter("facilityName"),
				request.getParameter("tel"),
				request.getParameter("address"),
				request.getParameter("mail"),
				request.getParameter("openTime"),
				request.getParameter("closeTime"),
				request.getParameter("closedDay"));

		FacilityInfomationDAO dao = new FacilityInfomationDAO();

		boolean result = dao.update(facilityInfo);
		
		if (result) {
			response.sendRedirect(
					"FacilityInfomationConfirmServlet");
		} else {
			response.sendRedirect(
					"FacilityInformationEditServlet");
		}
	}

}
