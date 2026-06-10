package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.PetListDAO;
import dao.ReserveDAO;
import model.PetDetail;

@WebServlet("/PetDetailServlet")
public class PetDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		
		int petID = Integer.parseInt(request.getParameter("petID"));

		PetListDAO dao1 = new PetListDAO();
		PetDetail petDetail = dao1.showPetDetail(petID);
		String facilityID = petDetail.getFacilityID();
		
		ReserveDAO dao2 = new ReserveDAO();
		boolean reserved = dao2.existsReserveByPetID(petID);
		
		request.setAttribute("petDetail", petDetail);
		request.setAttribute("petID", petID);
		request.setAttribute("reserved", reserved);
		
		HttpSession session = request.getSession();
		session.setAttribute("reservePetID", petID);
		session.setAttribute("reserveFacilityID", facilityID);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petDetail.jsp");
		dispatcher.forward(request, response);
	}

}
