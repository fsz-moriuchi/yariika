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
import dao.FacilityInfomationDAO;
import model.FacilityInformation;

/**
 * 施設情報確認画面を表示するServlet
 */
@WebServlet("/FacilityInfomationConfirmServlet")
public class FacilityInfomationConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String WELCOME_SERVLET = "WelcomeServlet";
	private static final String FACILITY_INFORMATION_CONFIRM_JSP =
			"/WEB-INF/jsp/facilityInfomationConfirm.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String facilityId = getLoggedInFacilityId(request);

		if (facilityId == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		setFacilityInformationAttributes(request, facilityId);
		forwardToConfirmPage(request, response);
	}

	// ログイン中の施設IDを取得する
	private String getLoggedInFacilityId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("facilityId") == null) {
			return null;
		}

		return (String) session.getAttribute("facilityId");
	}

	// 施設情報確認画面に表示する情報をrequestにセットする
	private void setFacilityInformationAttributes(
			HttpServletRequest request,
			String facilityId) {

		FacilityInfomationDAO facilityInfoDAO = new FacilityInfomationDAO();
		FacilityInformation facilityInfo =
				facilityInfoDAO.findByFacilityId(facilityId);

		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();
		List<String> facilityClosedDayList =
				closedDayDAO.findByFacilityID(facilityId);

		request.setAttribute("facilityInfo", facilityInfo);
		request.setAttribute("facilityClosedDayList", facilityClosedDayList);
	}

	// 施設情報確認画面へ遷移する
	private void forwardToConfirmPage(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher =
				request.getRequestDispatcher(FACILITY_INFORMATION_CONFIRM_JSP);
		dispatcher.forward(request, response);
	}
}

