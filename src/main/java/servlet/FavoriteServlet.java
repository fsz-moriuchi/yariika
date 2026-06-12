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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		int petID = Integer.parseInt(request.getParameter("petID"));

		FavoriteDAO dao = new FavoriteDAO();
		if (dao.isFavorite(userId, petID)) {
			dao.deleteFavorite(userId, petID);
		} else {
			dao.insertFavorite(userId, petID);
		}

		response.sendRedirect("PetDetailServlet?petID=" + petID);
	}

}