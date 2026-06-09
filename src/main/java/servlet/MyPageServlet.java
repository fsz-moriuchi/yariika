package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserInfoDAO;
import dao.UsersDAO;
import model.User;
import model.UserInfo;
import model.UserSurvey;



@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ログインユーザーをセッションから取得
		HttpSession session = request.getSession();
		User login = (User) session.getAttribute("user");
			
		String userId = login.getUserId();
		
		//DBから取得
		UserInfoDAO dao = new UserInfoDAO();
		UserInfo userInfo = dao.findByUserId(userId);
		
		//JSPへ渡す
		request.setAttribute("userInfo", userInfo);		
		
		if(userId == null) {
			response.sendRedirect("UserLoginServlet");
			return;
		}
		UsersDAO dao1 = new UsersDAO();
		List<UserSurvey> userSurveyList = dao1.showUserSurvey(userId);
		request.setAttribute("userSurveyList", userSurveyList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/mypage.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
