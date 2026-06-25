package servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
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

@WebServlet("/FacilityInfomationServlet")
public class FacilityInfomationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String WELCOME_SERVLET = "WelcomeServlet";
	private static final String FACILITY_INFORMATION_JSP = "WEB-INF/jsp/facilityinfomation.jsp";
	private static final String COMPLETE_SERVLET = "FacilityInformationCompleteServlet";

	private static final String ERROR_MESSAGE =
			"店舗情報の登録に失敗しました。入力内容を確認してください。";
	private static final String REQUIRED_ERROR_MESSAGE =
			"必須項目を入力してください。";
	private static final String TIME_FORMAT_ERROR_MESSAGE =
			"営業時間の形式が正しくありません。";
	private static final String CLOSED_DAY_ERROR_MESSAGE =
			"定休日の値が正しくありません。";

	private static final Set<String> VALID_CLOSED_DAYS =
			new HashSet<>(Arrays.asList(
					"MONDAY",
					"TUESDAY",
					"WEDNESDAY",
					"THURSDAY",
					"FRIDAY",
					"SATURDAY",
					"SUNDAY"
			));

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String facilityId = getLoggedInFacilityId(request);

		if (facilityId == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		forwardToInputPage(request, response);
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
			forwardToInputPageWithError(request, response, REQUIRED_ERROR_MESSAGE);
			return;
		}

		LocalTime openTime = parseTime(openTimeStr);
		LocalTime closeTime = parseTime(closeTimeStr);

		if (openTime == null || closeTime == null) {
			forwardToInputPageWithError(request, response, TIME_FORMAT_ERROR_MESSAGE);
			return;
		}

		if (!isValidClosedDays(closedDays)) {
			forwardToInputPageWithError(request, response, CLOSED_DAY_ERROR_MESSAGE);
			return;
		}

		FacilityInformation facilityInfo =
				new FacilityInformation(
						facilityId,
						facilityName,
						tel,
						address,
						mail,
						openTime,
						closeTime
				);

		boolean facilityInfoSaveResult = saveFacilityInformation(facilityId, facilityInfo);

		if (!facilityInfoSaveResult) {
			forwardToInputPageWithError(request, response, ERROR_MESSAGE);
			return;
		}

		boolean closedDaySaveResult = saveClosedDays(facilityId, closedDays);

		if (closedDaySaveResult) {
			response.sendRedirect(COMPLETE_SERVLET);
		} else {
			forwardToInputPageWithError(request, response, ERROR_MESSAGE);
		}
	}

	// ログイン中の施設IDを取得する
	private String getLoggedInFacilityId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("facilityId") == null) {
			return null;
		}

		return (String) session.getAttribute("facilityId");
	}

	// 施設情報を登録または更新する
	private boolean saveFacilityInformation(
			String facilityId,
			FacilityInformation facilityInfo) {

		FacilityInfomationDAO facilityInfoDAO = new FacilityInfomationDAO();
		FacilityInformation oldInfo = facilityInfoDAO.findByFacilityId(facilityId);

		if (oldInfo == null) {
			return facilityInfoDAO.insert(facilityInfo);
		}

		return facilityInfoDAO.update(facilityInfo);
	}

	// 定休日を削除してから再登録する
	private boolean saveClosedDays(String facilityId, String[] closedDays) {
		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

		boolean deleteResult = closedDayDAO.deleteByFacilityID(facilityId);

		if (!deleteResult) {
			return false;
		}

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

	// 時刻文字列をLocalTimeに変換する
	private LocalTime parseTime(String timeText) {
		try {
			return LocalTime.parse(timeText);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	// 定休日の値が想定範囲内か確認する
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

	// 空文字・空白のみか確認する
	private boolean isBlank(String value) {
		return value == null || value.isBlank();
	}

	// 入力画面へ遷移する
	private void forwardToInputPage(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher =
				request.getRequestDispatcher(FACILITY_INFORMATION_JSP);
		dispatcher.forward(request, response);
	}

	// エラーメッセージを設定して入力画面へ戻る
	private void forwardToInputPageWithError(
			HttpServletRequest request,
			HttpServletResponse response,
			String errorMessage)
			throws ServletException, IOException {

		request.setAttribute("errorMsg", errorMessage);
		forwardToInputPage(request, response);
	}
}
