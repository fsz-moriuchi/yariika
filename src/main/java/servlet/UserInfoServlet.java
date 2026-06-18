package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserInfoDAO;
import model.User;
import model.UserInfo;

@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッションからuserID取得
		HttpSession session = request.getSession(false);
		
		// セッションが存在しない
		if (session == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		// ログインユーザー取得
		User user = (User) session.getAttribute("user");

		// 未ログイン
		if (user == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}
		
		String userId = user.getUserId();

		//UserDAOでDBからユーザー情報を取得
		UserInfoDAO dao = new UserInfoDAO();
		UserInfo userInfo = dao.findByUserId(userId);

		//リクエストにセット
		request.setAttribute("userInfo", userInfo);

		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userInfo.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
