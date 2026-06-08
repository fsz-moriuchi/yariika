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

import dao.QuizDAO;
import model.Quiz;


@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DAOでクイズを取得
		QuizDAO quizDao = new QuizDAO();
		List<Quiz> quizList = quizDao.findAll();
		//JSPに表示
		HttpSession session = request.getSession();
		session.setAttribute("quizList", quizList);
										
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/catQuiz.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
