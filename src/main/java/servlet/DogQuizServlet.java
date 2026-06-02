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

import dao.DogQuizDAO;
import model.DogQuiz;



@WebServlet("/DogQuizServlet")
public class DogQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DAOでクイズを取得
		DogQuizDAO dao = new DogQuizDAO();
		List<DogQuiz> dogQuizList = dao.findAll();
		//JSPに表示
		HttpSession session = request.getSession();
		session.setAttribute("dogQuizList", dogQuizList);
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/quiz_jsp/dogQuiz.jsp");
		dispatcher.forward(request, response);
	}
}