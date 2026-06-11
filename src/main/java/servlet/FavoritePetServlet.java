package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityInformationDAO;

@WebServlet("/FavoritePetServlet")
public class FavoritePetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String facilityId = (String) session.getAttribute("facilityId");

		int petID = Integer.parseInt(request.getParameter("petID"));
		FacilityInformationDAO dao = new FacilityInformationDAO();
		dao.updateFavoritePet(facilityId, petID);

		response.sendRedirect("StoreServlet");
	}

}
