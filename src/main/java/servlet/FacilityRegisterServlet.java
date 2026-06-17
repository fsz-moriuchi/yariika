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

import dao.FacilitiesDAO;
import dao.FacilityClosedDayDAO;
import dao.FacilityInfomationDAO;
import model.Facility;
import model.FacilityInformation;
import util.PasswordUtil;

@WebServlet("/FacilityRegisterServlet")
public class FacilityRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityRegister.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String facilityId = request.getParameter("facilityId");
		String password = request.getParameter("password");
		String hash = PasswordUtil.hashPassword(password);
		
		//店舗情報
		String facilityName = request.getParameter("facilityName");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		String mail = request.getParameter("mail");
		String openTimeStr = request.getParameter("openTime");
		String closeTimeStr = request.getParameter("closeTime");

		LocalTime openTime = LocalTime.parse(openTimeStr);
		LocalTime closeTime = LocalTime.parse(closeTimeStr);

		String[] closedDays = request.getParameterValues("closedDay");
		
		//DAO
		FacilitiesDAO dao = new FacilitiesDAO();
		FacilityInfomationDAO dao1 = new FacilityInfomationDAO();
		FacilityInformation oldInfo = dao1.findByFacilityId(facilityId);
		FacilityClosedDayDAO dao2 = new FacilityClosedDayDAO();
		
		//ログイン情報登録
		Facility facility = new Facility(facilityId, hash);
		//FacilitiesDAO dao = new FacilitiesDAO();
		boolean result = dao.registerFacility(facility);

		if (!result) {
			request.setAttribute("errorMsg", "その店舗IDは既に使用されています");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityRegister.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		//店舗情報登録
		FacilityInformation facilityInfo = new FacilityInformation(facilityId, facilityName, tel, address, mail,
				openTime, closeTime);
		
		
		//休日登録
		if (closedDays != null) {
			for (String closedDay : closedDays) {
				//休日をテーブルに登録
				boolean result2 =dao2.insertByFacilityID(facilityId, closedDay);
			}
		}
		
		boolean result2;
		if (oldInfo == null) {
			result2 = dao1.insert(facilityInfo);
		} else {
			result2 = dao1.update(facilityInfo);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("facilityId", facilityId);

		if (result2) {
			response.sendRedirect("FacilityLoginServlet");
		} else {
			request.setAttribute("errorMsg", "店舗情報の登録に失敗しました。入力内容を確認してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityinfomation.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}
}
