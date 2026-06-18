package servlet;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserInfoDAO;
import dao.UsersDAO;
import model.User;
import model.UserInfo;
import util.PasswordUtil;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userRegister.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String birthdayStr = request.getParameter("userBirthday");
		Date userBirthday = Date.valueOf(birthdayStr);
		String userTel = request.getParameter("userTel");
		String userMail = request.getParameter("userMail");
		String userAddress = request.getParameter("userAddress");
		UserInfo userInfo = new UserInfo(0, userId, userName, userGender, userBirthday, userTel, userMail, userAddress);

		String hash = PasswordUtil.hashPassword(password);

		User user = new User(userId, hash);
		UsersDAO dao = new UsersDAO();
		boolean result = dao.registerUser(user);	//Userテーブル登録(ログイン用)

		if (result) {
			 // 追加　UserInfoテーブルに個人情報を登録
		    UserInfoDAO userInfoDao = new UserInfoDAO();
		    userInfoDao.insert(userInfo);
		    
			response.sendRedirect("UserLoginServlet");
		} else {
			request.setAttribute("errorMsg", "そのユーザーIDは既に使用されています");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userRegister.jsp");
			dispatcher.forward(request, response);
		}
	}

}
