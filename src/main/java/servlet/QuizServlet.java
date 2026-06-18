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

import dao.PetQuizDAO;
import model.PetQuiz;

@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		//カテゴリーを取得
		Integer categoryId = (Integer) session.getAttribute("categoryId");
		if (categoryId == null) {
			response.sendRedirect("HomeServlet");
			return;
		}

		//DAOでクイズを取得
		PetQuizDAO quizDao = new PetQuizDAO();
		List<PetQuiz> quizList = quizDao.findByCategory(categoryId);

		session.setAttribute("quizList", quizList);

		session.setAttribute("categoryId", categoryId);

		//sessionId生成
		String quizSessionId = java.util.UUID.randomUUID().toString();
		session.setAttribute("quizSessionId", quizSessionId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/quiz.jsp");
		dispatcher.forward(request, response);
	}

}
