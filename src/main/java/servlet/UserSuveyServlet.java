package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.QuestionSurveyDAO;
import dao.SurveyChoiceDAO;
import model.Choice;
import model.Question;

@WebServlet("/UserSuveyServlet")
public class UserSuveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuestionSurveyDAO Qdao = new QuestionSurveyDAO();
		SurveyChoiceDAO Cdao = new SurveyChoiceDAO();
		List<Question> questionList = Qdao.findAllQuestion();
		List<Choice> allChoiceList = Cdao.findAllChoices();
		
		request.setAttribute("questionList", questionList);
		request.setAttribute("allChoiceList", allChoiceList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userSurvey.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
