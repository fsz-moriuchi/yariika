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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");

		FavoriteDAO dao = new FavoriteDAO();
		List<FavoriteView> favoriteList = dao.showFavoriteList(userId);
		request.setAttribute("favoriteList", favoriteList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/favoriteList.jsp");
		dispatcher.forward(request, response);
	}
}