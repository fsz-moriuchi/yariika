package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import model.PetDetail;
import model.PetSurvey;
import model.Question;

@WebServlet("/PetEditServlet")
public class PetEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String loginFacilityId = (String)session.getAttribute("facilityId");
		int petID = Integer.parseInt(request.getParameter("petID"));

		PetListDAO dao = new PetListDAO();
		PetDetail petDetail = dao.showPetDetail(petID);
		
		List<PetSurvey> petSurveyList = dao.showPetSurvey(petID);
		
		request.setAttribute("petDetail", petDetail);
		request.setAttribute("petSurveyList", petSurveyList);
		request.setAttribute("loginFacilityId", loginFacilityId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petInformation.jsp");
		dispatcher.forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    request.setCharacterEncoding("UTF-8");

	    int petID = Integer.parseInt(request.getParameter("petID"));

	    PetListDAO dao = new PetListDAO();
	    QuestionSurveyDAO qDao = new QuestionSurveyDAO();
	    SurveyChoiceDAO cDao = new SurveyChoiceDAO();

	    List<PetSurvey> petSurveyList = dao.showPetSurvey(petID);
	    List<Question> questionList = qDao.findAllQuestion();
	    List<Question> petQuestionList = new ArrayList<>();
	    for(Question petQ:questionList) {
	    	if(petQ.getQuestionID() <=10) {
	    		petQuestionList.add(petQ);
	    	}
	    }List<Choice> allChoiceList = cDao.findAllChoices();
		
		request.setAttribute("petQuestionList", petQuestionList);
		request.setAttribute("allChoiceList", allChoiceList);
		request.setAttribute("petSurveyList", petSurveyList);
		request.setAttribute("petID", petID);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petSurveyConfirm.jsp");
		dispatcher.forward(request, response);
	}

}
