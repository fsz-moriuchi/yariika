package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.UserLogin;
import model.UserLoginLogic;
import util.PasswordUtil;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userLogin.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		String hash = PasswordUtil.hashPassword(password);

		UserLogin login = new UserLogin(userId, hash);
		UserLoginLogic bo = new UserLoginLogic();
		boolean result = bo.execute(login);

		if (result) {
			session.setAttribute("userId", userId);
			//追加
			User user = bo.getUser(login);
			session.setAttribute("user", user);

			response.sendRedirect("HomeServlet");
		} else {
			request.setAttribute("errorMsg", "ログインに失敗しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userLogin.jsp");
			dispatcher.forward(request, response);
		}
	}

}
