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

import dao.FavoriteDAO;
import model.FavoriteView;

@WebServlet("/FavoriteListServlet")
public class FavoriteListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String FAVORITE_LIST_JSP_PATH = "/WEB-INF/jsp/favoriteList.jsp";
	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String REQUEST_FAVORITE_LIST_KEY = "favoriteList";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(SESSION_USER_ID_KEY) == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String userId = (String) session.getAttribute(SESSION_USER_ID_KEY);
		FavoriteDAO dao = new FavoriteDAO();
		List<FavoriteView> favoriteList = dao.showFavoriteList(userId);

		request.setAttribute(REQUEST_FAVORITE_LIST_KEY, favoriteList);
		RequestDispatcher dispatcher = request.getRequestDispatcher(FAVORITE_LIST_JSP_PATH);
		dispatcher.forward(request, response);
	}
}