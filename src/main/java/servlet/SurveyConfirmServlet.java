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

import dao.QuestionSurveyDAO;
import dao.SurveyChoiceDAO;
import dao.UsersDAO;
import model.UserSurvey;


@WebServlet("/SurveyConfirmServlet")
public class SurveyConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {

		HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("userId");

	    UsersDAO dao = new UsersDAO();
	    QuestionSurveyDAO qDao = new QuestionSurveyDAO();
	    SurveyChoiceDAO cDao = new SurveyChoiceDAO();
	    List<UserSurvey> userSurveyList =dao.showUserSurvey(userId);
	    request.setAttribute("userSurveyList",userSurveyList);
	    request.setAttribute("questionList",qDao.findAllQuestion());
	    request.setAttribute("allChoiceList",cDao.findAllChoices());
	    

	    RequestDispatcher dispatcher =request.getRequestDispatcher("WEB-INF/jsp/surveyConfirm.jsp");
	    dispatcher.forward(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
