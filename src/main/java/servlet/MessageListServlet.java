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
import model.MessageList;
import model.User;


@WebServlet("/MessageListServlet")
public class MessageListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String facilityId = (String)session.getAttribute("facilityId");
		//String userId = (String)session.getAttribute("userId");
		User login = (User)session.getAttribute("user");
		
		MessageDAO dao = new MessageDAO();
		List<MessageList> messageList = null; //= dao.findMessageListByFacilityId(facilityId);
		
		if (facilityId == null && login == null) {
		    request.setAttribute("errorMessage", "ログインしてください");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/messageList.jsp");
		    dispatcher.forward(request, response);
		    return;
		}
		
		if (facilityId != null) {
			// 店舗
			messageList = dao.findMessageListByFacilityId(facilityId);
			request.setAttribute("role", "facility");
		} else if(login != null){
			// ユーザ
			String userId = login.getUserId();
			messageList = dao.findMessageListByUserId(userId);
			request.setAttribute("role", "user");
		}
		
		request.setAttribute("messageList", messageList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/messageList.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
		
