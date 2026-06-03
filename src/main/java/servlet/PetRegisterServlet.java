package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.PetListDAO;
import model.Pet;
import model.PetInformation;


@WebServlet("/PetRegisterServlet")
public class PetRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petInformation.jsp");
		dispatcher.forward(request, response);	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		int petID = Integer.parseInt(request.getParameter("petID"));
		String category = request.getParameter("category");
		int petInformationID = Integer.parseInt(request.getParameter("petInformationID"));
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		int age = Integer.parseInt(request.getParameter("age"));
		String[] colorArray  = request.getParameterValues("color");
		String colorText = "";
		for(int i = 0;i < colorArray .length;i++) {
			colorText += colorArray [i];
			if(i < colorArray .length-1) {
				colorText += ",";
			}
		}
		String pet_size = request.getParameter("pet_size");
		String vaccine = request.getParameter("vaccine");
		int price = Integer.parseInt(request.getParameter("price"));
		String commentText = request.getParameter("commentText");
		
		
		Pet pet = new Pet(petID,category);
		PetInformation petInformation = new PetInformation(petInformationID, petID, name, gender, age, colorText, pet_size, vaccine, price, commentText);
		
		PetListDAO dao = new PetListDAO();
		
		boolean petInformation1 = dao.createPet(pet);
		boolean petInformation2 = dao.createPetInformation(petInformation);
		
		if(petInformation1 && petInformation2) {

		    RequestDispatcher dispatcher =
		        request.getRequestDispatcher(
		            "WEB-INF/jsp/petRegisterSuccess.jsp");
		    dispatcher.forward(request, response);

		} else {
		    response.getWriter().println("IDが重複しています");
		}
		
	}

}
