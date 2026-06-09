package servlet;

import java.io.IOException;
import java.sql.Date;

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

@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//表示
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッション
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
				
		String userId = user.getUserId();
				
		//UserDAOでDBからユーザー情報を取得
		UserInfoDAO dao = new UserInfoDAO();
		UserInfo userInfo = dao.findByUserId(userId);
				
		//リクエストにセット
		request.setAttribute("userInfo", userInfo);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userEdit.jsp");
		dispatcher.forward(request, response);
	}

	
	//データの更新
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//セッション
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		String userId = user.getUserId();
		
		 // フォームから取得
		String userInfoIdStr = request.getParameter("userInfoId");
		int userInfoId = Integer.parseInt(userInfoIdStr);
		
	    String userName = request.getParameter("userName");
	    String userGender = request.getParameter("userGender");
	    
	    //日付はjavaとDBでフォームが違う事に注意
	    String birthdayStr = request.getParameter("userBirthday");
	    Date userBirthday = null;
	    if (birthdayStr != null && !birthdayStr.isEmpty()) {
	        userBirthday = Date.valueOf(birthdayStr);
	    } else {
	        // DBから元の値を取得
	        UserInfoDAO dao = new UserInfoDAO();
	        UserInfo oldInfo = dao.findByUserId(userId);
	        userBirthday = oldInfo.getUserBirthday();
	    }
	    String userTel = request.getParameter("userTel");
		String userMail = request.getParameter("userMail");
		String userAddress = request.getParameter("userAddress");
	    UserInfo userInfo = new UserInfo(userInfoId, userId, userName, userGender, userBirthday, userTel, userMail, userAddress);
		
		//UserDAOでDBをアップデート
		UserInfoDAO dao = new UserInfoDAO();
		boolean result = dao.updateInfo(userInfo);
		
		
		if (result) {
	        response.sendRedirect("UserInfoServlet"); // 更新後表示
	    } else {
	        request.setAttribute("errorMsg", "更新に失敗しました");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userEdit.jsp");
	        dispatcher.forward(request, response);
	    }
	}
}
