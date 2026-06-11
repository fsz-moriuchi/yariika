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
import model.Message;
import model.User;


@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DBからメッセージ取得、message.jspへ
		request.setCharacterEncoding("UTF-8");
		
		MessageDAO messageDao = new MessageDAO();
		int petId = Integer.parseInt(request.getParameter("petId"));
		String userId = request.getParameter("userId");
		String facilityId = request.getParameter("facilityId");
		
		List<Message>messageList = messageDao.getMessage(userId, facilityId, petId);
		request.setAttribute("messageList", messageList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//入力を受け取る、INSERT、リダイレクト
		HttpSession session = request.getSession();
		
		//ユーザーIDを取得
		User login = (User) session.getAttribute("user");
		if (login == null) {
		    response.sendRedirect("UserLoginServlet");
		    return;
		}
		String userId = login.getUserId();
		
		String messageText = request.getParameter("messageText");
		int petId = Integer.parseInt(request.getParameter("petId"));
		String facilityId = request.getParameter("facilityId");

		Message message = new Message(userId, facilityId, petId, messageText, "USER");

		MessageDAO dao = new MessageDAO();
		dao.insertMessage(message);

		response.sendRedirect("MessageServlet?petId=" + petId + "&facilityId=" + facilityId + "&userId=" + userId);		
	}

}
