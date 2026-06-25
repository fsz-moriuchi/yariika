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

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String MESSAGE_LIST_JSP_PATH = "/WEB-INF/jsp/messageList.jsp";
	private static final String SESSION_USER_KEY = "user";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_ROLE_KEY = "role";
	private static final String REQUEST_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_READ_STATUS_KEY = "readStatus";
	private static final String REQUEST_KEYWORD_KEY = "keyword";
	private static final String REQUEST_SORT_KEY = "sort";
	private static final String REQUEST_MESSAGE_COUNT_KEY = "messageCount";
	private static final String REQUEST_MESSAGE_LIST_KEY = "messageList";

	private static final String ROLE_FACILITY = "facility";
	private static final String ROLE_USER = "user";
	private static final String READ_STATUS_ALL = "all";
	private static final String READ_STATUS_UNREAD = "unread";
	private static final String READ_STATUS_READ = "read";
	private static final String SORT_TIME_ASC = "timeAsc";
	private static final String DEFAULT_SORT = "timeDesc";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		User loginUser = (User) session.getAttribute(SESSION_USER_KEY);
		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);

		MessageDAO messageDAO = new MessageDAO();
		String role = resolveRole(loginUser, facilityId);
		if (role == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		List<MessageList> messageList = loadMessageList(messageDAO, loginUser, facilityId, role);
		messageList = filterByReadStatus(messageList, request.getParameter(REQUEST_READ_STATUS_KEY));
		messageList = filterByKeyword(messageList, request.getParameter(REQUEST_KEYWORD_KEY), role);
		sortMessages(messageList, request.getParameter(REQUEST_SORT_KEY));

		String readStatus = normalizeReadStatus(request.getParameter(REQUEST_READ_STATUS_KEY));
		String keyword = request.getParameter(REQUEST_KEYWORD_KEY);
		String sort = normalizeSort(request.getParameter(REQUEST_SORT_KEY));

		request.setAttribute(REQUEST_FACILITY_ID_KEY, facilityId);
		request.setAttribute(REQUEST_READ_STATUS_KEY, readStatus);
		request.setAttribute(REQUEST_KEYWORD_KEY, keyword);
		request.setAttribute(REQUEST_SORT_KEY, sort);
		request.setAttribute(REQUEST_ROLE_KEY, role);
		request.setAttribute(REQUEST_MESSAGE_COUNT_KEY, messageList.size());
		request.setAttribute(REQUEST_MESSAGE_LIST_KEY, messageList);

		RequestDispatcher dispatcher = request.getRequestDispatcher(MESSAGE_LIST_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null;
	}

	private String resolveRole(User loginUser, String facilityId) {
		if (facilityId != null) {
			return ROLE_FACILITY;
		}
		if (loginUser != null) {
			return ROLE_USER;
		}
		return null;
	}

	private List<MessageList> loadMessageList(
			MessageDAO messageDAO,
			User loginUser,
			String facilityId,
			String role) {

		if (ROLE_FACILITY.equals(role)) {
			return messageDAO.findMessageListByFacilityId(facilityId);
		}
		return messageDAO.findMessageListByUserId(loginUser.getUserId());
	}

	private List<MessageList> filterByReadStatus(List<MessageList> messageList, String readStatus) {
		String normalizedReadStatus = normalizeReadStatus(readStatus);
		if (READ_STATUS_ALL.equals(normalizedReadStatus)) {
			return messageList;
		}

		List<MessageList> filteredList = new ArrayList<>();
		for (MessageList message : messageList) {
			boolean isUnread = message.getUnreadCount() > 0;
			boolean shouldKeep = READ_STATUS_UNREAD.equals(normalizedReadStatus) ? isUnread : !isUnread;
			if (shouldKeep) {
				filteredList.add(message);
			}
		}
		return filteredList;
	}

	private List<MessageList> filterByKeyword(List<MessageList> messageList, String keyword, String role) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return messageList;
		}

		String normalizedKeyword = keyword.trim().toLowerCase();
		List<MessageList> filteredList = new ArrayList<>();
		for (MessageList message : messageList) {
			if (matchesKeyword(message, normalizedKeyword, role)) {
				filteredList.add(message);
			}
		}
		return filteredList;
	}

	private boolean matchesKeyword(MessageList message, String keyword, String role) {
		if (ROLE_FACILITY.equals(role)) {
			return containsIgnoreCase(message.getUserId(), keyword)
					|| containsIgnoreCase(message.getUserName(), keyword)
					|| String.valueOf(message.getPetID()).contains(keyword)
					|| containsIgnoreCase(message.getPetName(), keyword);
		}
		if (ROLE_USER.equals(role)) {
			return containsIgnoreCase(message.getFacilityId(), keyword)
					|| containsIgnoreCase(message.getFacilityName(), keyword)
					|| String.valueOf(message.getPetID()).contains(keyword)
					|| containsIgnoreCase(message.getPetName(), keyword);
		}
		return false;
	}

	private boolean containsIgnoreCase(String value, String keyword) {
		return value != null && value.toLowerCase().contains(keyword);
	}

	private void sortMessages(List<MessageList> messageList, String sort) {
		if (SORT_TIME_ASC.equals(normalizeSort(sort))) {
			messageList.sort((m1, m2) -> m1.getLatestTime().compareTo(m2.getLatestTime()));
		} else {
			messageList.sort((m1, m2) -> m2.getLatestTime().compareTo(m1.getLatestTime()));
		}
	}

	private String normalizeReadStatus(String readStatus) {
		if (readStatus == null || readStatus.isEmpty()) {
			return READ_STATUS_ALL;
		}
		return readStatus;
	}

	private String normalizeSort(String sort) {
		if (sort == null || sort.isEmpty()) {
			return DEFAULT_SORT;
		}
		return sort;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}