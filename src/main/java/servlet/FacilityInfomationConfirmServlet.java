package servlet;

import java.io.IOException;
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

/**
 * Servlet implementation class FacilityInfomationConfirmServlet
 */
@WebServlet("/FacilityInfomationConfirmServlet")
public class FacilityInfomationConfirmServlet extends HttpServlet {
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
		//店舗情報の取得
		FacilityInformationDAO dao1 = new FacilityInformationDAO();
		FacilityInformation facilityInfo = dao1.findByFacilityId(facilityId);

		//店舗の定休日の取得
		FacilityClosedDayDAO dao2 = new FacilityClosedDayDAO();
		List<String> facilityClosedDayList = dao2.findByFacilityID(facilityId);

		request.setAttribute("facilityInfo", facilityInfo);
		request.setAttribute("facilityClosedDayList", facilityClosedDayList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/facilityInfomationConfirm.jsp");
		dispatcher.forward(request, response);
	}

}
