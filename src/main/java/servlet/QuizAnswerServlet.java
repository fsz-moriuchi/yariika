package servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.PetQuizDAO;
import dao.QuizAnswerDAO;
import dao.QuizResultDAO;
import model.PetQuiz;
import model.QuizAnswer;
import model.QuizResult;
import model.User;


@WebServlet("/QuizAnswerServlet")
public class QuizAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		//DAOでクイズを取得
		PetQuizDAO dao = new PetQuizDAO();
		
		HttpSession session = request.getSession();
		List<PetQuiz> petQuizList = (List<PetQuiz>) session.getAttribute("quizList");
		String quizSessionId = (String) session.getAttribute("quizSessionId");
		
		User login = (User) session.getAttribute("user");
		if (login == null) {
		    response.sendRedirect("LoginServlet");
		    return;
		}
		String userId = login.getUserId();
		
		Integer categoryId = (Integer) session.getAttribute("categoryId");
		if (categoryId == null) {
		    response.sendRedirect("HomeServlet");
		    return;
		}
		
		//DAOでJOIN結果取得
		QuizAnswerDAO answerDao = new QuizAnswerDAO();
		int count = 0;
		
        //1問ずつ処理
        for (PetQuiz pq : petQuizList) {

            // q1, q2, q3…
            String paramName = "q" + pq.getQuizId();
            String value = request.getParameter(paramName);

            if (value == null) {
                continue;
            }

            int userAnswer = Integer.parseInt(value);

            //モデルに詰める
            QuizAnswer answer = new QuizAnswer(userId, pq.getQuizId(), userAnswer, quizSessionId);

            //DB保存
            answerDao.insert(answer);
            
            if(userAnswer == pq.getAnswer()) {
            	count++;
            }
        }
        
		//正答率計算
		int totalCount = petQuizList.size();
		int percent = count * 100 / totalCount;
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("count", count);
		request.setAttribute("percent", percent);
		
		//JOIN
		QuizResultDAO resultDao = new QuizResultDAO();
		List<QuizResult> resultList = resultDao.findByUserId(userId, quizSessionId);
		//クイズ表示順を逆に
		Collections.reverse(resultList);
		// JSPに渡す
		request.setAttribute("resultList", resultList);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/quizResult.jsp");
		dispatcher.forward(request, response);
	}

}
