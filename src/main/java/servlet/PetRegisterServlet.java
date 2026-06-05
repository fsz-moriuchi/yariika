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
import dao.QuestionSurveyDAO;
import dao.SurveyChoiceDAO;
import model.Choice;
import model.Pet;
import model.PetInformation;
import model.PetSurvey;
import model.Question;


@WebServlet("/PetRegisterServlet")
public class PetRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher dispatcher =request.getRequestDispatcher("WEB-INF/jsp/petInformation.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		String action = request.getParameter("action");
//新規ペット登録	
		if("アンケートへ".equals(action)) {
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
			
			session.setAttribute("pet",pet);
			session.setAttribute("petInformation",petInformation);
	
			
			response.sendRedirect("SurveyServlet");

		}

//既存ペット情報更新	
		else if("更新".equals(action)) {
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
			
			if(petResult && petInformationResult ) {
			    RequestDispatcher dispatcher =request.getRequestDispatcher("WEB-INF/jsp/petUpdateSuccess.jsp");
			    dispatcher.forward(request, response);
				} else {
					response.getWriter().println("更新失敗");
				}
			}
//既存ペットアンケート修正
		else if("アンケート修正".equals(action)) {

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
			
		    PetListDAO dao = new PetListDAO();
		    QuestionSurveyDAO qDao = new QuestionSurveyDAO();
		    SurveyChoiceDAO cDao = new SurveyChoiceDAO();
			PetInformation petInformation = new PetInformation(petID, name, gender, age, colorText, pet_size, vaccine, price, commentText);

		    List<PetSurvey> petSurveyList = dao.showPetSurvey(petID);
		    List<Question> questionList = qDao.findAllQuestion();
		    List<Choice> allChoiceList = cDao.findAllChoices();
		   
		    Pet pet = new Pet(category);
			pet.setPetID(petID);
			dao.updatePet(pet);
		    dao.updatePetInformation(petInformation);

		    request.setAttribute("petID", petID);
		    request.setAttribute("petSurveyList", petSurveyList);
		    request.setAttribute("questionList", questionList);
		    request.setAttribute("allChoiceList", allChoiceList);

		    RequestDispatcher dispatcher =request.getRequestDispatcher("WEB-INF/jsp/petSurvey.jsp");

		    dispatcher.forward(request, response);
		}
		
//既存ペット情報削除		
		else if("削除".equals(action)){
			int petID = Integer.parseInt(request.getParameter("petID"));

			PetListDAO dao = new PetListDAO();
			boolean result = dao.deletePet(petID);
			request.setAttribute("petID", petID);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petDeleteSuccess.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

}
