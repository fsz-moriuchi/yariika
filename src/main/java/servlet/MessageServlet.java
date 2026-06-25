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

	//共通メソッド
	private String getLoginId(HttpSession session) {
		User login = (User) session.getAttribute("user");
		String facilityId = (String) session.getAttribute("facilityId");

		if (login != null)
			return login.getUserId();
		if (facilityId != null)
			return facilityId;
		return null;
	}

	private String getRole(HttpSession session) {
		if (session.getAttribute("user") != null)
			return "USER";
		if (session.getAttribute("facilityId") != null)
			return "FACILITY";
		return null;
	}

	private boolean isAuthorized(String role, String loginId, String userId, String facilityId, int petID) {
	    MessageDAO messageDao = new MessageDAO();

	    // ① なりすましチェック
	    if ("USER".equals(role)) {
	        if (!loginId.equals(userId)) {
	            return false;
	        }
	    } else if ("FACILITY".equals(role)) {
	        if (!loginId.equals(facilityId)) {
	            return false;
	        }
	    }

	    // ② ペットと施設の整合性チェック（←これがさっきのexistsPet）
	    return messageDao.existsPet(petID, facilityId);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DBからメッセージ取得、message.jspへ

		// セッション取得（なければ新規作成しない）
		HttpSession session = request.getSession(false);

		// セッションが存在しない
		if (session == null) {
		    request.setAttribute("errorMessage", "セッションが切れました");
		    request.getRequestDispatcher("WEB-INF/jsp/messageList.jsp").forward(request, response);
		    return;
		}
		String role = getRole(session);
		String loginId = getLoginId(session);

		if (role == null) {
		    request.setAttribute("errorMessage", "ログインしてください");
		    request.getRequestDispatcher("WEB-INF/jsp/messageList.jsp").forward(request, response);
		    return;
		}

		// petID
		int petID;
		try {
		    petID = Integer.parseInt(request.getParameter("petID"));
		} catch (Exception e) {
		    request.setAttribute("errorMessage", "不正なペットIDです");
		    request.getRequestDispatcher("WEB-INF/jsp/messageList.jsp").forward(request, response);
		    return;
		}

		// ID組み立て
		String userId = role.equals("USER")
				? loginId
				: request.getParameter("userId");

		String facilityId = role.equals("FACILITY")
				? loginId
				: request.getParameter("facilityId");

		// 🔴 認可チェック
		if (!isAuthorized(role, loginId, userId, facilityId, petID)) {
		    request.setAttribute("errorMessage", "このメッセージにアクセスできません");
		    request.getRequestDispatcher("WEB-INF/jsp/messageList.jsp").forward(request, response);
		    return;
		}

		MessageDAO messageDao = new MessageDAO();

		List<Message> messageList = messageDao.getMessage(userId, facilityId, petID);
		request.setAttribute("messageList", messageList);

		PetListDAO dao = new PetListDAO();
		PetDetail petDetail = dao.showPetDetail(petID);
		request.setAttribute("petDetail", petDetail);

		String viewerType = role;
		messageDao.markAsRead(userId, facilityId, petID, viewerType);

		String from = request.getParameter("from");
		if (from == null)
			from = "list";
		request.setAttribute("from", from);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/message.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//入力を受け取る、INSERT、リダイレクト
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String role = getRole(session);
		String loginId = getLoginId(session);
		
		if (session == null || role == null) {
		    request.setAttribute("errorMessage", "ログインしてください");
		    request.getRequestDispatcher("WEB-INF/jsp/message.jsp").forward(request, response);
		    return;
		}
		
		String messageText = request.getParameter("messageText");

		if (messageText == null || messageText.trim().isEmpty()) {
			request.setAttribute("errorMessage", "メッセージを入力してください");
			request.getRequestDispatcher("WEB-INF/jsp/message.jsp").forward(request, response);
			return;
		}

		int petID;
		try {
		    petID = Integer.parseInt(request.getParameter("petID"));
		} catch (Exception e) {
		    request.setAttribute("errorMessage", "不正なペットIDです");
		    request.getRequestDispatcher("WEB-INF/jsp/message.jsp").forward(request, response);
		    return;
		}

		// ID組み立て
		String userId = role.equals("USER")
				? loginId
				: request.getParameter("userId");

		String facilityId = role.equals("FACILITY")
				? loginId
				: request.getParameter("facilityId");

		// 認可チェック（ここ超重要）
		if (!isAuthorized(role, loginId, userId, facilityId, petID)) {
		    request.setAttribute("errorMessage", "不正な操作です");
		    request.getRequestDispatcher("WEB-INF/jsp/message.jsp").forward(request, response);
		    return;
		}

		Message message = new Message(userId, facilityId, petID, messageText, role);

		MessageDAO dao = new MessageDAO();
		dao.insertMessage(message);

		String from = request.getParameter("from");

		response.sendRedirect(
				"MessageServlet?petID=" + petID +
						"&facilityId=" + facilityId +
						"&userId=" + userId +
						"&from=" + from);
	}
}
