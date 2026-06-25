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

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String MESSAGE_LIST_JSP_PATH = "WEB-INF/jsp/messageList.jsp";
	private static final String MESSAGE_JSP_PATH = "WEB-INF/jsp/message.jsp";
	private static final String SESSION_USER_KEY = "user";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMessage";
	private static final String REQUEST_FROM_KEY = "from";
	private static final String REQUEST_MESSAGE_LIST_KEY = "messageList";
	private static final String REQUEST_PET_DETAIL_KEY = "petDetail";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REQUEST_USER_ID_KEY = "userId";
	private static final String REQUEST_FACILITY_ID_PARAM_KEY = "facilityId";
	private static final String REQUEST_MESSAGE_TEXT_KEY = "messageText";
	private static final String DEFAULT_FROM_VALUE = "list";
	private static final String VIEWER_TYPE_USER = "USER";
	private static final String VIEWER_TYPE_FACILITY = "FACILITY";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		User loginUser = (User) session.getAttribute(SESSION_USER_KEY);
		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);

		if (loginUser == null && facilityId == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String viewerType = resolveViewerType(loginUser);
		String userId = resolveUserIdForGet(request, loginUser);
		if (userId == null) {
			return;
		}

		facilityId = resolveFacilityIdForGet(request, loginUser, facilityId);
		if (facilityId == null) {
			return;
		}

		Integer petID = resolvePetId(request);
		if (petID == null) {
			return;
		}

		MessageDAO messageDAO = new MessageDAO();
		List<Message> messageList = messageDAO.getMessage(userId, facilityId, petID);
		request.setAttribute(REQUEST_MESSAGE_LIST_KEY, messageList);

		PetListDAO petListDAO = new PetListDAO();
		PetDetail petDetail = petListDAO.showPetDetail(petID);
		request.setAttribute(REQUEST_PET_DETAIL_KEY, petDetail);

		messageDAO.markAsRead(userId, facilityId, petID, viewerType);

		request.setAttribute(REQUEST_FROM_KEY, normalizeFrom(request.getParameter(REQUEST_FROM_KEY)));
		RequestDispatcher dispatcher = request.getRequestDispatcher(MESSAGE_JSP_PATH);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute(SESSION_USER_KEY);
		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);

		if (!isLoggedInForPost(loginUser, facilityId)) {
			forwardWithErrorMessage(request, response, MESSAGE_JSP_PATH, "ログインしてください");
			return;
		}

		if (facilityId == null) {
			facilityId = request.getParameter(REQUEST_FACILITY_ID_PARAM_KEY);
		}

		String messageText = request.getParameter(REQUEST_MESSAGE_TEXT_KEY);
		Integer petID = resolvePetId(request);
		if (petID == null) {
			forwardWithErrorMessage(request, response, MESSAGE_JSP_PATH, "ペット情報が不正です");
			return;
		}

		String userId;
		String senderType;
		if (loginUser != null) {
			userId = loginUser.getUserId();
			senderType = VIEWER_TYPE_USER;
		} else {
			userId = request.getParameter(REQUEST_USER_ID_KEY);
			if (userId == null) {
				forwardWithErrorMessage(request, response, MESSAGE_JSP_PATH, "不正なアクセスです");
				return;
			}
			senderType = VIEWER_TYPE_FACILITY;
		}

		Message message = new Message(userId, facilityId, petID, messageText, senderType);
		MessageDAO messageDAO = new MessageDAO();
		messageDAO.insertMessage(message);

		String from = request.getParameter(REQUEST_FROM_KEY);
		response.sendRedirect(buildMessageRedirectUrl(petID, facilityId, userId, from));
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null;
	}

	private boolean isLoggedInForPost(User loginUser, String facilityId) {
		return loginUser != null || facilityId != null;
	}

	private String resolveViewerType(User loginUser) {
		return loginUser != null ? VIEWER_TYPE_USER : VIEWER_TYPE_FACILITY;
	}

	private String resolveUserIdForGet(HttpServletRequest request, User loginUser)
			throws ServletException, IOException {
		if (loginUser != null) {
			return loginUser.getUserId();
		}

		String userId = request.getParameter(REQUEST_USER_ID_KEY);
		if (userId == null || userId.isEmpty()) {
			forwardWithErrorMessage(request, responseFor(request), MESSAGE_LIST_JSP_PATH, "ユーザーIDが取得できません");
			return null;
		}
		return userId;
	}

	private String resolveFacilityIdForGet(HttpServletRequest request, User loginUser, String facilityId)
			throws ServletException, IOException {
		if (loginUser != null) {
			facilityId = request.getParameter(REQUEST_FACILITY_ID_PARAM_KEY);
			if (facilityId == null || facilityId.isEmpty()) {
				forwardWithErrorMessage(request, responseFor(request), MESSAGE_LIST_JSP_PATH, "施設情報が取得できません");
				return null;
			}
		}
		return facilityId;
	}

	private Integer resolvePetId(HttpServletRequest request) {
		String petIDStr = request.getParameter(REQUEST_PET_ID_KEY);
		if (petIDStr == null || petIDStr.isEmpty()) {
			return null;
		}
		return Integer.parseInt(petIDStr);
	}

	private String normalizeFrom(String from) {
		return (from == null) ? DEFAULT_FROM_VALUE : from;
	}

	private String buildMessageRedirectUrl(Integer petID, String facilityId, String userId, String from) {
		return "MessageServlet?petID=" + petID + "&facilityId=" + facilityId + "&userId=" + userId + "&from=" + from;
	}

	private void forwardWithErrorMessage(
			HttpServletRequest request,
			HttpServletResponse response,
			String jspPath,
			String errorMessage) throws ServletException, IOException {

		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, errorMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	private HttpServletResponse responseFor(HttpServletRequest request) {
		return null;
	}
}