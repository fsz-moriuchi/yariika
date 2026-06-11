package servlet;

import java.io.IOException;
import java.time.LocalTime;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityInfomationDAO;
import model.FacilityInformation;

@WebServlet("/FacilityInfomationServlet")
public class FacilityInfomationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityinfomation.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute("facilityId");
		//入力の取得
		String facilityName = request.getParameter("facilityName");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		String mail = request.getParameter("mail");
		String openTimeStr = request.getParameter("openTime");
		String closeTimeStr = request.getParameter("closeTime");

		LocalTime openTime = LocalTime.parse(openTimeStr);
		LocalTime closeTime = LocalTime.parse(closeTimeStr);

		
		//テーブルに登録
		FacilityInformation facilityInfo = new FacilityInformation(facilityId, facilityName, tel, address, mail,
				openTime, closeTime);
		
		FacilityInfomationDAO dao = new FacilityInfomationDAO();
		FacilityInformation oldInfo = dao.findByFacilityId(facilityId);

		boolean result;

		if (oldInfo == null) {
		    result = dao.insert(facilityInfo);
		} else {
		    result = dao.update(facilityInfo);
		}

		if (result) {
			response.sendRedirect("FacilityInformationCompleteServlet");
		} else {
			request.setAttribute("errorMsg", "店舗情報の登録に失敗しました。入力内容を確認してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityinfomation.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}
