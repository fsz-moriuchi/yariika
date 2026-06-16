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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DBからメッセージ取得、message.jspへ

		HttpSession session = request.getSession();
		//ID取得
		User login = (User) session.getAttribute("user");
		String facilityId = (String) session.getAttribute("facilityId");
		//ログイン判定
		if (login == null && facilityId == null) {
			request.setAttribute("errorMessage", "ログインしてください");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
			dispatcher.forward(request, response);
			return;
		}

		/*if (facilityId == null) {
		    facilityId = request.getParameter("facilityId");
		}*/

		String userId;
		// 権限チェック
		if (login != null) {
			// ユーザー
			userId = login.getUserId();
			facilityId = request.getParameter("facilityId");
			if (facilityId == null) {
				request.setAttribute("errorMessage", "施設情報が取得できません");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/messageList.jsp");
				dispatcher.forward(request, response);
				return;
			}

		} else {
			// 店舗
			userId = request.getParameter("userId");
			if (userId == null) {
				request.setAttribute("errorMessage", "ユーザーIDが取得できません");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/messageList.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}

		//共通パラメータ
		String petIDStr = request.getParameter("petID");
		int petID = 0;
		if (petIDStr != null && !petIDStr.isEmpty()) {
			petID = Integer.parseInt(petIDStr);
		} else {
			request.setAttribute("errorMessage", "ペット情報が不正です");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/messageList.jsp");
			dispatcher.forward(request, response);
			return;
		}

		//メッセージ取得
		MessageDAO messageDao = new MessageDAO();

		//未読処理
		String viewerType;
		if (login != null) {
			viewerType = "USER";
		} else {
			viewerType = "FACILITY";
		}

		List<Message> messageList = messageDao.getMessage(userId, facilityId, petID);
		request.setAttribute("messageList", messageList);

		//ペット詳細取得
		PetListDAO dao = new PetListDAO();
		PetDetail petDetail = dao.showPetDetail(petID);
		request.setAttribute("petDetail", petDetail);

		//既読処理へ
		messageDao.markAsRead(userId, facilityId, petID, viewerType);

		String from = request.getParameter("from");
		// null対策
		if (from == null) {
			from = "list";
		}
		request.setAttribute("from", from);

		String backFrom = request.getParameter("backFrom");
		request.setAttribute("backFrom", backFrom);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//入力を受け取る、INSERT、リダイレクト
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		System.out.println("session facilityId=" + session.getAttribute("facilityId"));
		System.out.println("request facilityId=" + request.getParameter("facilityId"));

		User login = (User) session.getAttribute("user");
		String facilityId = (String) session.getAttribute("facilityId");
		//ログイン判定
		if (login == null && facilityId == null) {
			request.setAttribute("errorMessage", "ログインしてください");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
			dispatcher.forward(request, response);
			return;
		}
		if (facilityId == null) {
			facilityId = request.getParameter("facilityId");
		}

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
			senderType = "FACILITY";
			userId = request.getParameter("userId");
			if (userId == null) {
				request.setAttribute("errorMessage", "不正なアクセスです");
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

		String from = request.getParameter("from");
		response.sendRedirect(
				"MessageServlet?petID=" + petID + "&facilityId=" + facilityId + "&userId=" + userId + "&from=" + from);
	}

}
