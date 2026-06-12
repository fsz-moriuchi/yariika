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


@WebServlet("/MessageListServlet")
public class MessageListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String facilityId = (String)session.getAttribute("facilityId");

		MessageDAO dao = new MessageDAO();
		List<MessageList> messageList = dao.findMessageListByFacilityId(facilityId);

		request.setAttribute("messageList", messageList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/messageList.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
		
