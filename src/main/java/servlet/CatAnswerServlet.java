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


@WebServlet("/CatAnswerServlet")
public class CatAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DAOでクイズを取得
		CatQuizDAO dao = new CatQuizDAO();
		List<CatQuiz> catQuizList = dao.findAll();
						
		//セッションスコープ取得
		HttpSession session = request.getSession();
								
		//JSPに表示
		session.setAttribute("catQuizList", catQuizList);
						
		//正答率
		int count = 0;
		int totalCount = catQuizList.size();
						
		//採点処理
		for(CatQuiz cq : catQuizList) {
			String ans = request.getParameter("q"+ cq.getId());
			if(ans != null && Integer.parseInt(ans) == cq.getAnswer()) {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/quiz_jsp/catResult.jsp");
		dispatcher.forward(request, response);
		}
}

