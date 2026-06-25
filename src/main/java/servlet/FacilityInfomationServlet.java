package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalTime;

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

@WebServlet("/FacilityInfomationServlet")
public class FacilityInfomationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String COMPLETE_REDIRECT_URL = "FacilityInformationCompleteServlet";
	private static final String FORM_JSP_PATH = "/WEB-INF/jsp/facilityinfomation.jsp";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String ERROR_MESSAGE = "店舗情報の登録に失敗しました。入力内容を確認してください。";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String REQUEST_FACILITY_INFO_KEY = "facilityInfo";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (isNotLoggedIn(request)) {
			redirectToLogin(response);
			return;
		}
		forwardToFormPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		if (isNotLoggedIn(request)) {
			redirectToLogin(response);
			return;
		}

		HttpSession session = request.getSession(false);
		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);

		FacilityInformation facilityInfo = buildFacilityInformation(request, facilityId);
		String[] closedDays = request.getParameterValues("closedDay");

		try (Connection connection = DButil.getConnection()) {
			connection.setAutoCommit(false);

			FacilityInformationDAO facilityDAO = new FacilityInformationDAO();
			FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

			boolean facilityUpdateResult = facilityDAO.update(connection, facilityInfo);
			boolean closedDayUpdateResult = closedDayDAO.replaceByFacilityID(connection, facilityId, closedDays);

			if (facilityUpdateResult && closedDayUpdateResult) {
				connection.commit();
				response.sendRedirect(COMPLETE_REDIRECT_URL);
			} else {
				connection.rollback();
				showFormWithError(request, response, facilityInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	private boolean isNotLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session == null || session.getAttribute(SESSION_FACILITY_ID_KEY) == null;
	}

	private void redirectToLogin(HttpServletResponse response) throws IOException {
		response.sendRedirect(LOGIN_REDIRECT_URL);
	}

	private void forwardToFormPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(FORM_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private FacilityInformation buildFacilityInformation(HttpServletRequest request, String facilityId) {
		String facilityName = request.getParameter("facilityName");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		String mail = request.getParameter("mail");
		LocalTime openTime = LocalTime.parse(request.getParameter("openTime"));
		LocalTime closeTime = LocalTime.parse(request.getParameter("closeTime"));

		return new FacilityInformation(
				facilityId,
				facilityName,
				tel,
				address,
				mail,
				openTime,
				closeTime);
	}

	private void showFormWithError(HttpServletRequest request, HttpServletResponse response,
			FacilityInformation facilityInfo) throws ServletException, IOException {
		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, ERROR_MESSAGE);
		request.setAttribute(REQUEST_FACILITY_INFO_KEY, facilityInfo);
		forwardToFormPage(request, response);
	}
}