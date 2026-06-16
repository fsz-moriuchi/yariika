package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FavoriteDAO;
import dao.PetListDAO;
import dao.ReserveDAO;
import model.PetDetail;

@WebServlet("/PetDetailServlet")
public class PetDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		Integer petID = Integer.parseInt(request.getParameter("petID"));
		request.setAttribute("petID", petID);

		// 遷移元を保持
		String from = request.getParameter("from");
		if (from != null) {
			session.setAttribute("from", from);
		}
		request.setAttribute("from", session.getAttribute("from"));

		PetListDAO dao = new PetListDAO();
		PetDetail petDetail = dao.showPetDetail(petID);
		request.setAttribute("petDetail", petDetail);

		///////////////////コンフリ部分////////////////////
		String facilityID = petDetail.getFacilityID();
		ReserveDAO dao2 = new ReserveDAO();
		boolean reserved = dao2.existsReserveByPetID(petID);

		request.setAttribute("petID", petID);
		request.setAttribute("reserved", reserved);
		String userId = (String) session.getAttribute("userId");
		session.setAttribute("reservePetID", petID);
		session.setAttribute("reserveFacilityID", facilityID);
		session.setAttribute("reserved", reserved);
		////////////////////////////////////////////

		session.setAttribute("categoryName", petDetail.getCategoryName());
		session.setAttribute("categoryId", petDetail.getCategoryId());

		request.setAttribute("petDetail", petDetail);

		if (userId != null) {
			FavoriteDAO favoriteDAO = new FavoriteDAO();
			boolean favorite = favoriteDAO.isFavorite(userId, petID);
			request.setAttribute("favorite", favorite);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petDetail.jsp");
		dispatcher.forward(request, response);
	}

}
