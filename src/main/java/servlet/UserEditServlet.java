package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//表示
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		//セッション
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(user);
				
		int userId = user.getId();
				
		//UserDAOでDBからユーザー情報を取得
		UserDAO dao = new UserDAO();
		User user = dao.findById(userId);
				
				
		//リクエストにセット
		request.setAttribute("user", user);
		*/
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userEdit.jsp");
		dispatcher.forward(request, response);
	}

	
	//データの更新
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		//セッション
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(user);
		
		//フォーム値で取得
		
		
		int userId = user.getId();
		
		//UserDAOでDBをアップデート
		UserDAO dao = new UserDAO();
		User user = dao.update(userId);
		
		
		//リクエストにセット
		request.setAttribute("user", user);
		*/
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userEdit.jsp");
		dispatcher.forward(request, response);
	}

}
