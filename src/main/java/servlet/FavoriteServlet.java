package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FavoriteDAO;

@WebServlet("/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SESSION_USER_ID_KEY = "userId";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REDIRECT_URL_PREFIX = "PetDetailServlet?petID=";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = getUserIdFromSession(session);
		int petId = getPetIdFromRequest(request);

		toggleFavorite(userId, petId);
		redirectToPetDetail(response, petId);
	}

	private String getUserIdFromSession(HttpSession session) {
		return (String) session.getAttribute(SESSION_USER_ID_KEY);
	}

	private int getPetIdFromRequest(HttpServletRequest request) {
		return Integer.parseInt(request.getParameter(REQUEST_PET_ID_KEY));
	}

	private void toggleFavorite(String userId, int petId) {
		FavoriteDAO dao = new FavoriteDAO();
		if (dao.isFavorite(userId, petId)) {
			dao.deleteFavorite(userId, petId);
		} else {
			dao.insertFavorite(userId, petId);
		}
	}

	private void redirectToPetDetail(HttpServletResponse response, int petId) throws IOException {
		response.sendRedirect(REDIRECT_URL_PREFIX + petId);
	}
}