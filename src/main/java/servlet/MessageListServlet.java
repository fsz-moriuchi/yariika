package servlet;

import java.io.IOException;
import java.util.ArrayList;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		// セッションが存在しない
		if (session == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		// ログイン情報取得
		User login = (User) session.getAttribute("user");
		String facilityId = (String) session.getAttribute("facilityId");

		// ユーザーでも施設でもない場合は未ログイン
		if (login == null && facilityId == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String role = "";

		MessageDAO dao = new MessageDAO();
		List<MessageList> messageList = null; //= dao.findMessageListByFacilityId(facilityId);

		if (facilityId == null && login == null) {
			request.setAttribute("errorMessage", "ログインしてください");
			response.sendRedirect("WelcomeServlet");
			return;
		}

		if (facilityId != null) {
			// 店舗
			messageList = dao.findMessageListByFacilityId(facilityId);
			role = "facility";
			request.setAttribute("role", role);
		} else if (login != null) {
			// ユーザ
			String userId = login.getUserId();
			messageList = dao.findMessageListByUserId(userId);
			role = "user";
			request.setAttribute("role", role);
		}

		//キーワード検索・既読未読・並び替え
		String readStatus = request.getParameter("readStatus");
		String keyword = request.getParameter("keyword");
		String sort = request.getParameter("sort");
		//すべて表示
		if (readStatus == null || readStatus.isEmpty()) {
			readStatus = "all";
		}
		if (sort == null || sort.isEmpty()) {
			sort = "timeDesc";
		}

		// 既読・未読フィルター
		List<MessageList> showReadList = new ArrayList<>();

		for (MessageList m : messageList) {
			//すべてのメッセージ表示
			boolean addIn = true;

			if ("unread".equals(readStatus)) {
				//未読を選択 >> 未読数=0、表示しないから showReadList に入れない
				if (m.getUnreadCount() <= 0) {
					addIn = false;
				}
				//既読を選択 >> 未読数>0、表示しないから showReadList に入れない
			} else if ("read".equals(readStatus)) {
				if (m.getUnreadCount() > 0) {
					addIn = false;
				}
			}
			//if(addIn is true)showReadList に入れる
			if (addIn) {
				showReadList.add(m);
			}
		}
		//表示する
		messageList = showReadList;

		// 絞り込み検索
		if (keyword != null && !keyword.trim().isEmpty()) {

			String searchKeyword = keyword.trim().toLowerCase();
			List<MessageList> showSearchList = new ArrayList<>();

			for (MessageList m : messageList) {
				boolean foundKeyword = false;

				// 店舗側：ユーザーID・ユーザー名・ペットID・ペット名で検索
				if ("facility".equals(role)) {

					if (m.getUserId() != null && m.getUserId().toLowerCase().contains(searchKeyword)) {
						foundKeyword = true;
					}

					if (m.getUserName() != null && m.getUserName().toLowerCase().contains(searchKeyword)) {
						foundKeyword = true;
					}

					if (String.valueOf(m.getPetID()).contains(searchKeyword)) {
						foundKeyword = true;
					}

					if (m.getPetName() != null && m.getPetName().toLowerCase().contains(searchKeyword)) {
						foundKeyword = true;
					}
				}

				// ユーザー側：施設ID・施設名・ペットID・ペット名で検索
				else if ("user".equals(role)) {

					if (m.getFacilityId() != null && m.getFacilityId().toLowerCase().contains(searchKeyword)) {
						foundKeyword = true;
					}

					if (m.getFacilityName() != null && m.getFacilityName().toLowerCase().contains(searchKeyword)) {
						foundKeyword = true;
					}

					if (String.valueOf(m.getPetID()).contains(searchKeyword)) {
						foundKeyword = true;
					}

					if (m.getPetName() != null && m.getPetName().toLowerCase().contains(searchKeyword)) {
						foundKeyword = true;
					}
				}

				if (foundKeyword) {
					showSearchList.add(m);
				}
			}

			messageList = showSearchList;
		}

		//並び順（date time）
		if ("timeAsc".equals(sort)) {
			// 古い順
			messageList.sort((m1, m2) -> m1.getLatestTime().compareTo(m2.getLatestTime()));
		} else {
			// 新しい順
			messageList.sort((m1, m2) -> m2.getLatestTime().compareTo(m1.getLatestTime()));
		}

		request.setAttribute("facilityId", facilityId);
		request.setAttribute("readStatus", readStatus);
		request.setAttribute("keyword", keyword);
		request.setAttribute("sort", sort);
		request.setAttribute("role", role);
		request.setAttribute("messageCount", messageList.size());
		request.setAttribute("messageList", messageList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/messageList.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
