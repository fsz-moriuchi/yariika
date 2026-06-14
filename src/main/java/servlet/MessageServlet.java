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

import dao.MessageDAO;
import dao.PetListDAO;
import model.Message;
import model.PetDetail;
import model.User;


@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DBからメッセージ取得、message.jspへ
		
		HttpSession session = request.getSession();
		/*//ユーザーログインチェック
		User login = (User)session.getAttribute("user");
		if (login == null) {
		    response.sendRedirect("UserLoginServlet");
		    return;
		}
		String userId = login.getUserId();
		//パラメータ取得
		String petIDStr = request.getParameter("petID");
		String facilityId = (String)session.getAttribute("facilityId");
		
		int petID= 0;
		if (petIDStr != null && !petIDStr.isEmpty()) {
		    petID = Integer.parseInt(petIDStr);
		} else {
		    // エラー回避 or デフォルト処理
		    System.out.println("petIDが取得できていません");
		}
		*/
		
		//ID取得
		User login = (User)session.getAttribute("user");
		String facilityId = (String)session.getAttribute("facilityId");
		//ログイン判定
		if(login == null && facilityId == null) {
			request.setAttribute("errorMessage", "ログインしてください");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (facilityId == null) {
		    facilityId = request.getParameter("facilityId");
		    if (facilityId != null) {
		        session.setAttribute("facilityId", facilityId); // ←これが大事！！
		    }
		}
		
		//共通パラメータ
		String petIDStr = request.getParameter("petID");
		int petID= 0;
		if (petIDStr != null && !petIDStr.isEmpty()) {
		    petID = Integer.parseInt(petIDStr);
		} else {
		    // エラー回避 or デフォルト処理
		    System.out.println("petIDが取得できていません");
		}
		//ユーザーor店舗　分岐
		String userId;
		if(login != null) {		//ユーザーの場合
			userId = login.getUserId();
		}else {
			userId = request.getParameter("userId");
			
			if(userId == null) {
				request.setAttribute("errorMessage", "ユーザーIDが取得できません");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
		//メッセージ取得
		MessageDAO messageDao = new MessageDAO();
		List<Message>messageList = messageDao.getMessage(userId, facilityId, petID);
		request.setAttribute("messageList", messageList);
		
		//ペット詳細取得
		PetListDAO dao = new PetListDAO();
		PetDetail petDetail = dao.showPetDetail(petID);
		request.setAttribute("petDetail", petDetail);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//入力を受け取る、INSERT、リダイレクト
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		System.out.println("session facilityId=" + session.getAttribute("facilityId"));
		System.out.println("request facilityId=" + request.getParameter("facilityId"));
		/*//ログインチェック
		User login = (User) session.getAttribute("user");
		if (login == null) {
		    response.sendRedirect("UserLoginServlet");
		    return;
		}
		String userId = login.getUserId();
		
		//パラメータ取得
		String messageText = request.getParameter("messageText");
		String facilityId = (String)session.getAttribute("facilityId");
		
		String petIDStr = request.getParameter("petID");
		int petID = 0;

		if (petIDStr != null && !petIDStr.isEmpty()) {
		    petID = Integer.parseInt(petIDStr);
		} else {
		    System.out.println("POSTでpetID取れてない");
		    response.sendRedirect("error.jsp");
		    return;
		}*/
		
		User login = (User)session.getAttribute("user");
		String facilityId = (String)session.getAttribute("facilityId");
		//ログイン判定
		if(login == null && facilityId == null) {
			request.setAttribute("errorMessage", "ログインしてください");
			return;
		}
		if (facilityId == null) {
		    facilityId = request.getParameter("facilityId");		}
		
		String messageText = request.getParameter("messageText");
		String petIDStr = request.getParameter("petID");
		int petID = Integer.parseInt(petIDStr);

		String userId;
		String senderType;

		//ユーザー
		if (login != null) {
		    userId = login.getUserId();
		    senderType = "USER";
		}
		//店舗
		else {
		    userId = request.getParameter("userId");
		    senderType = "FACILITY";
		    if(userId == null) {
				request.setAttribute("errorMessage", "ユーザーIDが取得できません");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
		
		//DB登録
		Message message = new Message(userId, facilityId, petID, messageText, senderType);

		MessageDAO dao = new MessageDAO();
		dao.insertMessage(message);
		
		System.out.println("petID: " + petIDStr);
		System.out.println("facilityId: " + facilityId);
		System.out.println("messageText: " + messageText);
		response.sendRedirect("MessageServlet?petID=" + petID + "&facilityId=" + facilityId + "&userId=" + userId);		
	}

}
