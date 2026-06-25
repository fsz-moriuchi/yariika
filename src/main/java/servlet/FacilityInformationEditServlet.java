package servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

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

@WebServlet("/FacilityInformationEditServlet")
public class FacilityInformationEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String WELCOME_SERVLET = "WelcomeServlet";
	private static final String EDIT_JSP = "WEB-INF/jsp/facilityInformationEdit.jsp";

	// 既存のServlet名に合わせる
	private static final String CONFIRM_SERVLET = "FacilityInfomationConfirmServlet";

	private static final Set<String> VALID_CLOSED_DAYS = Set.of(
			"MONDAY",
			"TUESDAY",
			"WEDNESDAY",
			"THURSDAY",
			"FRIDAY",
			"SATURDAY",
			"SUNDAY"
	);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String facilityId = getLoggedInFacilityId(request);

		if (facilityId == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		setEditPageAttributes(request, facilityId);
		forwardToEditPage(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String facilityId = getLoggedInFacilityId(request);

		if (facilityId == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		String facilityName = request.getParameter("facilityName");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		String mail = request.getParameter("mail");
		String openTimeStr = request.getParameter("openTime");
		String closeTimeStr = request.getParameter("closeTime");
		String[] closedDays = request.getParameterValues("closedDay");

		if (isBlank(facilityName) || isBlank(openTimeStr) || isBlank(closeTimeStr)) {
			request.setAttribute("errorMsg", "必須項目を入力してください。");
			setEditPageAttributes(request, facilityId);
			forwardToEditPage(request, response);
			return;
		}

		if (!isValidClosedDays(closedDays)) {
			request.setAttribute("errorMsg", "定休日の値が不正です。");
			setEditPageAttributes(request, facilityId);
			forwardToEditPage(request, response);
			return;
		}

		LocalTime openTime;
		LocalTime closeTime;

		try {
			openTime = LocalTime.parse(openTimeStr);
			closeTime = LocalTime.parse(closeTimeStr);
		} catch (DateTimeParseException e) {
			request.setAttribute("errorMsg", "営業時間の形式が正しくありません。");
			setEditPageAttributes(request, facilityId);
			forwardToEditPage(request, response);
			return;
		}

		FacilityInformation facilityInfo = new FacilityInformation(
				facilityId,
				facilityName,
				tel,
				address,
				mail,
				openTime,
				closeTime
		);

		boolean updateResult = updateFacilityInformationAndClosedDays(
				facilityInfo,
				closedDays
		);

		if (updateResult) {
			response.sendRedirect(CONFIRM_SERVLET);
			return;
		}

		request.setAttribute("errorMsg", "施設情報の更新に失敗しました。");
		setEditPageAttributes(request, facilityId);
		forwardToEditPage(request, response);
	}

	// ログイン中の施設IDを取得する
	private String getLoggedInFacilityId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("facilityId") == null) {
			return null;
		}

		return (String) session.getAttribute("facilityId");
	}

	// 編集画面に表示する施設情報と定休日をセットする
	private void setEditPageAttributes(HttpServletRequest request, String facilityId) {
		FacilityInfomationDAO facilityDAO = new FacilityInfomationDAO();
		FacilityInformation facilityInfo = facilityDAO.findByFacilityId(facilityId);

		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();
		List<String> facilityClosedDayList = closedDayDAO.findByFacilityID(facilityId);

		request.setAttribute("facilityInfo", facilityInfo);
		request.setAttribute("facilityClosedDayList", facilityClosedDayList);
	}

	// 施設情報と定休日を更新する
	private boolean updateFacilityInformationAndClosedDays(
			FacilityInformation facilityInfo,
			String[] closedDays) {

		FacilityInfomationDAO facilityDAO = new FacilityInfomationDAO();
		boolean facilityUpdateResult = facilityDAO.update(facilityInfo);

		if (!facilityUpdateResult) {
			return false;
		}

		return updateClosedDays(facilityInfo.getFacilityId(), closedDays);
	}

	// 定休日を一度削除してから、選択された曜日を再登録する
	private boolean updateClosedDays(String facilityId, String[] closedDays) {
		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

		boolean deleteResult = closedDayDAO.deleteByFacilityID(facilityId);

		if (!deleteResult) {
			return false;
		}

		// 何も選択されていない場合は、削除だけ行って「定休日なし」とする
		if (closedDays == null) {
			return true;
		}

		for (String closedDay : closedDays) {
			boolean insertResult = closedDayDAO.insertByFacilityID(facilityId, closedDay);

			if (!insertResult) {
				return false;
			}
		}

		return true;
	}

	// 定休日として許可された値だけか確認する
	private boolean isValidClosedDays(String[] closedDays) {
		if (closedDays == null) {
			return true;
		}

		for (String closedDay : closedDays) {
			if (!VALID_CLOSED_DAYS.contains(closedDay)) {
				return false;
			}
		}

		return true;
	}

	private boolean isBlank(String value) {
		return value == null || value.isBlank();
	}

	private void forwardToEditPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(EDIT_JSP);
		dispatcher.forward(request, response);
	}
}

