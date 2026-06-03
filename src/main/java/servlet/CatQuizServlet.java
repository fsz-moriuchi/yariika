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

import dao.CatQuizDAO;
import model.CatQuiz;


@WebServlet("/CatQuizServlet")
public class CatQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DAOでクイズを取得
		CatQuizDAO catdao = new CatQuizDAO();
		List<CatQuiz> catQuizList = catdao.findAll();
		//JSPに表示
		HttpSession session = request.getSession();
		session.setAttribute("catQuizList", catQuizList);
								
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/catQuiz.jsp");
		dispatcher.forward(request, response);
	}
}
