package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.PasswordEdit;
import model.PasswordEditLogic;


@WebServlet("/PasswordEditServlet")
public class PasswordEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId =(String)session.getAttribute("userId");
		String facilityId =(String)session.getAttribute("facilityId");
		
		if(userId == null && facilityId == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passwordEdit.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String userId =(String)session.getAttribute("userId");
		String facilityId =(String)session.getAttribute("facilityId");
		
		if(userId == null && facilityId == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}
		
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String newPasswordConfirm = request.getParameter("newPasswordConfirm");
		
		PasswordEditLogic passwordEditLogic = new PasswordEditLogic();
		PasswordEdit passwordEditResult = passwordEditLogic.execute(userId, facilityId, oldPassword, newPassword, newPasswordConfirm);
		System.out.println(passwordEditResult);
	

		if(passwordEditResult.isSuccess()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passwordEditSuccess.jsp");
			dispatcher.forward(request, response);
			return;

		}else {
			request.setAttribute("errorMsg",passwordEditResult.getErrorMsg());
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passwordEdit.jsp");
			dispatcher.forward(request, response);
		}
		}
}
