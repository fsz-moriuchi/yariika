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

import dao.FacilitiesDAO;
import dao.FacilityClosedDayDAO;
import dao.FacilityInformationDAO;
import model.Facility;
import model.FacilityInformation;
import util.DButil;
import util.PasswordUtil;

@WebServlet("/FacilityRegisterServlet")
public class FacilityRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String REGISTER_JSP_PATH = "/WEB-INF/jsp/facilityRegister.jsp";
	private static final String INFORMATION_JSP_PATH = "/WEB-INF/jsp/facilityinfomation.jsp";
	private static final String LOGIN_REDIRECT_URL = "FacilityLoginServlet";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String ERROR_DUPLICATE_ID = "その店舗IDは既に使用されています";
	private static final String ERROR_REGISTER_FAILED = "店舗情報の登録に失敗しました。入力内容を確認してください。";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forwardToRegisterPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Facility facility = buildFacility(request);
		FacilityInformation facilityInfo = buildFacilityInformation(request);

		FacilitiesDAO facilitiesDAO = new FacilitiesDAO();
		FacilityInformationDAO facilityInformationDAO = new FacilityInformationDAO();
		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

		try (Connection connection = DButil.getConnection()) {
			connection.setAutoCommit(false);

			if (!registerLoginInfo(facilitiesDAO, facility)) {
				connection.rollback();
				showDuplicateIdError(request, response);
				return;
			}

			boolean infoSaved = facilityInformationDAO.insert(facilityInfo);
			boolean closedDaysSaved = saveClosedDays(connection, closedDayDAO, request, facility.getFacilityId());

			if (infoSaved && closedDaysSaved) {
				connection.commit();
				saveFacilityIdToSession(request, facility.getFacilityId());
				response.sendRedirect(LOGIN_REDIRECT_URL);
				return;
			}

			connection.rollback();
			showRegisterError(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	private void forwardToRegisterPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(REGISTER_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private Facility buildFacility(HttpServletRequest request) {
		String facilityId = request.getParameter("facilityId");
		String password = request.getParameter("password");
		String hash = PasswordUtil.hashPassword(password);
		return new Facility(facilityId, hash);
	}

	private FacilityInformation buildFacilityInformation(HttpServletRequest request) {
		String facilityId = request.getParameter("facilityId");
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

	private boolean registerLoginInfo(FacilitiesDAO facilitiesDAO, Facility facility) {
		return facilitiesDAO.registerFacility(facility);
	}

	private boolean saveClosedDays(Connection connection, FacilityClosedDayDAO closedDayDAO,
			HttpServletRequest request, String facilityId) {
		String[] closedDays = request.getParameterValues("closedDay");
		return closedDayDAO.replaceByFacilityID(connection, facilityId, closedDays);
	}

	private void saveFacilityIdToSession(HttpServletRequest request, String facilityId) {
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_FACILITY_ID_KEY, facilityId);
	}

	private void showDuplicateIdError(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorMsg", ERROR_DUPLICATE_ID);
		forwardToRegisterPage(request, response);
	}

	private void showRegisterError(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorMsg", ERROR_REGISTER_FAILED);
		RequestDispatcher dispatcher = request.getRequestDispatcher(INFORMATION_JSP_PATH);
		dispatcher.forward(request, response);
	}
}