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


@WebServlet("/DogAnswerServlet")
public class DogAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DAOでクイズを取得
		DogQuizDAO dao = new DogQuizDAO();
		List<DogQuiz> dogQuizList = dao.findAll();
				
		//セッションスコープ取得
		HttpSession session = request.getSession();
						
		//JSPに表示
		session.setAttribute("dogQuizList", dogQuizList);
				
		//正答率
		int count = 0;
		int totalCount = dogQuizList.size();
				
		//採点処理
		for(DogQuiz dq : dogQuizList) {
			String ans = request.getParameter("q"+ dq.getId());
			if(ans != null && Integer.parseInt(ans) == dq.getAnswer()) {
				count++;
			}
		}
				
		//正答率計算
		int percent = count * 100 / totalCount;
				
		//セッションに保持
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("count", count);
		request.setAttribute("percent", percent);
				
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/quiz_jsp/dogResult.jsp");
		dispatcher.forward(request, response);
	}
}
