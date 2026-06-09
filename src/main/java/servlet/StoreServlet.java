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

import dao.PetListDAO;
import model.PetInformationView;

@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String facilityId = (String)session.getAttribute("facilityId");
		
		PetListDAO dao = new PetListDAO();
		List<PetInformationView>facilityList = dao.showListByFacility(facilityId);

		request.setAttribute("facilityList",facilityList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petManagement.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
