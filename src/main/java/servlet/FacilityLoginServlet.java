package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.FacilityLogin;
import model.FacilityLoginLogic;
import util.PasswordUtil;

@WebServlet("/FacilityLoginServlet")
public class FacilityLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityLogin.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String facilityId = request.getParameter("facilityId");
		String password = request.getParameter("password");

		String hash = PasswordUtil.hashPassword(password);

		FacilityLogin login = new FacilityLogin(facilityId, hash);
		FacilityLoginLogic bo = new FacilityLoginLogic();
		boolean result = bo.execute(login);

		if (result) {
			//追加
			HttpSession session = request.getSession();

			session.removeAttribute("userId");
			session.removeAttribute("user");

			session.setAttribute("facilityId", facilityId);
			//
			response.sendRedirect("HomeServlet");
		} else {
			request.setAttribute("errorMsg", "ログインに失敗しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityLogin.jsp");
			dispatcher.forward(request, response);
		}
	}

}
