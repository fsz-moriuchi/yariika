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
import model.PetSurvey;


@WebServlet("/PetRegisterServlet")
public class PetRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher dispatcher =request.getRequestDispatcher("WEB-INF/jsp/petInformation.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
//新規ペット登録	
		if(action.equals("登録")) {
			String category = request.getParameter("category");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			int age = Integer.parseInt(request.getParameter("age"));
			
			String[] colorArray  = request.getParameterValues("color");
			String colorText = "";
	
			if(colorArray != null) {
				for(int i = 0;i < colorArray.length;i++) {
					colorText += colorArray[i];
						if(i < colorArray.length-1) {
							colorText += ",";
							}
						}
				}
			String pet_size = request.getParameter("pet_size");
			String vaccine = request.getParameter("vaccine");
			int price = Integer.parseInt(request.getParameter("price"));
			String commentText = request.getParameter("commentText");
					
					
			Pet pet = new Pet(category);
			PetInformation petInformation = new PetInformation(0, name, gender, age, colorText, pet_size, vaccine, price, commentText);
					
			PetListDAO dao = new PetListDAO();
			int petID = dao.createPet(pet);
			petInformation.setPetID(petID);
			boolean petInformation2 = dao.createPetInformation(petInformation);
				
			boolean petSurveyResult = true;
			for(int qID =1;qID <=6;qID++) {
				int surveyChoiceID =Integer.parseInt(request.getParameter("q" + qID));
				PetSurvey petSurvey =new PetSurvey(petID,qID,surveyChoiceID);
				
				if(!dao.createPetSurvey(petSurvey)) {
					petSurveyResult = false;
					break;
				}
			}
			
			request.setAttribute("category", category);
			request.setAttribute("name", name);
			request.setAttribute("gender", gender);
			request.setAttribute("age", age);
			request.setAttribute("colorText", colorText);
			request.setAttribute("colorArray", colorArray);
			request.setAttribute("pet_size", pet_size);
			request.setAttribute("vaccine", vaccine);
			request.setAttribute("price", price);
			request.setAttribute("commentText", commentText);
					
			if(petID != -1 && petInformation2 && petSurveyResult) {
				RequestDispatcher dispatcher =request.getRequestDispatcher("WEB-INF/jsp/petRegisterSuccess.jsp");
			   dispatcher.forward(request, response);
			} else {
			response.getWriter().println("登録失敗");
				}
		}
			
		
//既存ペット情報更新	
		else if(action.equals("更新")) {
			int petID = Integer.parseInt(request.getParameter("petID"));
			String category = request.getParameter("category");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			int age = Integer.parseInt(request.getParameter("age"));
			
			String[] colorArray  = request.getParameterValues("color");
			String colorText = "";
			if(colorArray != null) {
			for(int i = 0;i < colorArray.length;i++) {
				colorText += colorArray[i];
				if(i < colorArray.length-1) {
					colorText += ",";
					}
				}
			}
			String pet_size = request.getParameter("pet_size");
			String vaccine = request.getParameter("vaccine");
			int price = Integer.parseInt(request.getParameter("price"));
			String commentText = request.getParameter("commentText");
			
			Pet pet = new Pet(category);
			pet.setPetID(petID);
			PetListDAO dao = new PetListDAO();
			PetInformation petInformation = new PetInformation(petID, name, gender, age, colorText, pet_size, vaccine, price, commentText);
			
			boolean petResult = dao.updatePet(pet);
			boolean petInformationResult = dao.updatePetInformation(petInformation);
			
			if(petResult && petInformationResult) {
			    RequestDispatcher dispatcher =request.getRequestDispatcher("WEB-INF/jsp/petUpdateSuccess.jsp");
			    dispatcher.forward(request, response);
				} else {
					response.getWriter().println("更新失敗");
				}
			
		}
		
//既存ペット情報削除		
		else if(action.equals("削除")){
			int petID = Integer.parseInt(request.getParameter("petID"));

			PetListDAO dao = new PetListDAO();
			boolean result = dao.deletePet(petID);
			request.setAttribute("petID", petID);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petDeleteSuccess.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

}
