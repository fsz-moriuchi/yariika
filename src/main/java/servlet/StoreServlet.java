package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.PetListDAO;

@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PetListDAO dao = new PetListDAO();

		request.setAttribute("petList",dao.showList());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petManagement.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
