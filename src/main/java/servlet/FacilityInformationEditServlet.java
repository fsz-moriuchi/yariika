package servlet;

import java.io.IOException;
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
import dao.FacilityInfomationDAO;
import model.FacilityInformation;

@WebServlet("/FacilityInformationEditServlet")
public class FacilityInformationEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String facilityId = (String) session.getAttribute("facilityId");

		// 施設情報取得
		FacilityInfomationDAO dao = new FacilityInfomationDAO();
		FacilityInformation facilityInfo = dao.findByFacilityId(facilityId);

		// 定休日取得
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

		HttpSession session = request.getSession();
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

		// 施設情報を更新
		FacilityInfomationDAO facilityDAO = new FacilityInfomationDAO();

		boolean facilityUpdateResult = facilityDAO.update(facilityInfo);

		// 既存の定休日を削除
		FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

		boolean closedDayDeleteResult = closedDayDAO.deleteByFacilityID(facilityId);

		// 定休日の登録結果
		boolean closedDayInsertResult = true;

		/*
		 * 削除に成功し、定休日が1つ以上選択されている場合のみ登録する。
		 * 何も選択されていない場合は、削除だけ行って
		 * 「定休日なし」として扱う。
		 */
		if (closedDayDeleteResult && closedDays != null) {

			for (String closedDay : closedDays) {

				boolean insertResult = closedDayDAO.insertByFacilityID(
						facilityId,
						closedDay);

				if (!insertResult) {
					closedDayInsertResult = false;
					break;
				}
			}
		}

		// すべて成功したか判定
		if (facilityUpdateResult
				&& closedDayDeleteResult
				&& closedDayInsertResult) {

			response.sendRedirect(
					"FacilityInfomationConfirmServlet");

		} else {

			response.sendRedirect(
					"FacilityInformationConfirmServlet");
		}
	}
}