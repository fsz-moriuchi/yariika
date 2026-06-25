package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalTime;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityClosedDayDAO;
import dao.FacilityInformationDAO;
import model.FacilityInformation;
import util.DButil;

@WebServlet("/FacilityInformationEditServlet")
public class FacilityInformationEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String facilityId = (String) session.getAttribute("facilityId");

		FacilityInformationDAO dao = new FacilityInformationDAO();
		FacilityInformation facilityInfo = dao.findByFacilityId(facilityId);

		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();
		List<String> facilityClosedDayList = closedDayDAO.findByFacilityID(facilityId);

		request.setAttribute("facilityInfo", facilityInfo);
		request.setAttribute("facilityClosedDayList", facilityClosedDayList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityInformationEdit.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String facilityId = (String) session.getAttribute("facilityId");

		LocalTime openTime = LocalTime.parse(request.getParameter("openTime"));
		LocalTime closeTime = LocalTime.parse(request.getParameter("closeTime"));
		String[] closedDays = request.getParameterValues("closedDay");

		FacilityInformation facilityInfo = new FacilityInformation(
				facilityId,
				request.getParameter("facilityName"),
				request.getParameter("tel"),
				request.getParameter("address"),
				request.getParameter("mail"),
				openTime,
				closeTime);

		try (Connection connection = DButil.getConnection()) {
			connection.setAutoCommit(false);

			FacilityInformationDAO facilityDAO = new FacilityInformationDAO();
			FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

			boolean facilityUpdateResult = facilityDAO.update(connection, facilityInfo);
			boolean closedDayResult = closedDayDAO.replaceByFacilityID(connection, facilityId, closedDays);

			if (facilityUpdateResult && closedDayResult) {
				connection.commit();
				response.sendRedirect("FacilityInformationConfirmServlet");
			} else {
				connection.rollback();
				response.sendRedirect("FacilityInformationConfirmServlet");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}