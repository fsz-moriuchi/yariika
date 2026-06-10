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
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String nowPetID = request.getParameter("petID");
		System.out.println("SurveyServlet: nowPetID = " + nowPetID);
		System.out.println("SurveyServlet: pet = " + session.getAttribute("pet"));
		System.out.println("SurveyServlet: petInformation = " + session.getAttribute("petInformation"));
//新規入力
		if(nowPetID == null || nowPetID.isEmpty()) {
		Pet pet = (Pet)session.getAttribute("pet");
		PetInformation petInformation =(PetInformation)session.getAttribute("petInformation");	
		
		if(pet == null || petInformation == null) {
			response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("ペット情報がありません。ペット情報入力画面から登録してください。");
	        return;
			}
		
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

		if(petID == -1) {
		    response.setContentType("text/html; charset=UTF-8");
		    response.getWriter().println("ペット基本情報の登録に失敗しました。店舗IDを確認してください。");
		    return;
		}
		if(petID != -1 && petInformationResult && petSurveyResult) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petRegisterSuccess.jsp");
		dispatcher.forward(request, response);
		}else {
			response.setContentType("text/html; charset=UTF-8");
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
				if(!dao.updatePetSurvey(petID,qID,surveyChoiceID)) {
					petSurveyResult = false;
					break;
				};
			}
			if(petSurveyResult) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petRegisterSuccess.jsp");
			dispatcher.forward(request, response);
			}else {
				response.setContentType("text/html; charset=UTF-8");
				 response.getWriter().println("更新失敗");
			}
			}
	}
}
