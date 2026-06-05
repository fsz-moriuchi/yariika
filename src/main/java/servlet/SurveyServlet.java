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

@WebServlet("/SurveyServlet")
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		QuestionSurveyDAO Qdao = new QuestionSurveyDAO();
		SurveyChoiceDAO Cdao = new SurveyChoiceDAO();
		List<Question> questionList = Qdao.findAllQuestion();
		List<Choice> allChoiceList = Cdao.findAllChoices();
		
		request.setAttribute("questionList", questionList);
		request.setAttribute("allChoiceList", allChoiceList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petSurvey.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String nowPetID = request.getParameter("petID");
//新規入力
		if(nowPetID == null || nowPetID.isEmpty()) {
		Pet pet = (Pet)session.getAttribute("pet");
		PetInformation petInformation =(PetInformation)session.getAttribute("petInformation");	
		
		PetListDAO dao = new PetListDAO();
		int petID = dao.createPet(pet);
		petInformation.setPetID(petID);
		boolean petInformationResult = dao.createPetInformation(petInformation);
		boolean petSurveyResult = true;
		
		for(int qID =1;qID <=6;qID++) {
			int surveyChoiceID =Integer.parseInt(request.getParameter("q" + qID));
			PetSurvey petSurvey =new PetSurvey(petID,qID,surveyChoiceID);
			if(!dao.createPetSurvey(petSurvey)) {
				petSurveyResult = false;
				break;
			};
		}
		session.removeAttribute("pet");
		session.removeAttribute("petInformation");

		if(petID != -1 && petInformationResult && petSurveyResult) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petRegisterSuccess.jsp");
		dispatcher.forward(request, response);
		}else {
			response.getWriter().print("登録失敗");
		}
	
	}
//内容修正
		else {
			int petID = Integer.parseInt(nowPetID);
			PetListDAO dao = new PetListDAO();
			boolean petSurveyResult = true;
			for(int qID =1;qID <=6;qID++) {
				int surveyChoiceID =Integer.parseInt(request.getParameter("q" + qID));
				PetSurvey petSurvey =new PetSurvey(petID,qID,surveyChoiceID);
				if(!dao.updatePetSurvey(petID,qID,surveyChoiceID)) {
					petSurveyResult = false;
					break;
				};
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petRegisterSuccess.jsp");
			dispatcher.forward(request, response);
	}
	}
}
