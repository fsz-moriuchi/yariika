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

import dao.DogQuizAnswerDAO;
import dao.DogQuizDAO;
import dao.DogQuizResultDAO;
import model.DogQuiz;
import model.DogQuizAnswer;
import model.DogQuizResult;


@WebServlet("/DogAnswerServlet")
public class DogAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DAOでクイズを取得
		DogQuizDAO dao = new DogQuizDAO();
		//List<DogQuiz> dogQuizList = dao.findAll();
		HttpSession session = request.getSession();
		List<DogQuiz>dogQuizList = (List<DogQuiz>) session.getAttribute("dogQuizList");				
		
		//今はログイン機能と結びついていないため、仮ユーザーID
		String userId = "1212";
		/*
		//★実際はセッションから取得
		HttpSession session = request.getSession();
		User login = (User) session.getAttribute("user");
		String userId = login.getUserId();
		*/
		
		DogQuizAnswerDAO dogAnswerDao = new DogQuizAnswerDAO();
		
		//正答率
		int count = 0;
		int totalCount = dogQuizList.size();
				
		//採点処理
		for (DogQuiz dq : dogQuizList) {

            // q1, q2, q3…
            String paramName = "q" + dq.getId();
            String value = request.getParameter(paramName);

            if (value == null) {
                continue;
            }

            int dogUserAnswer = Integer.parseInt(value);
            
            DogQuizAnswer answer = new DogQuizAnswer(userId, dq.getId(), dogUserAnswer);
            //DB保存
            dogAnswerDao.insert(answer);
            
            if(dogUserAnswer == dq.getAnswer()) {
            	count++;
            }
        }
				
		//正答率計算
		int percent = count * 100 / totalCount;
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("count", count);
		request.setAttribute("percent", percent);
		
		//JOIN
		DogQuizResultDAO resultDao = new DogQuizResultDAO();
		List<DogQuizResult> dogResultList = resultDao.findByUserId(userId);
		//クイズ表示順を逆に
		Collections.reverse(dogResultList);
		// JSPに渡す
		request.setAttribute("dogResultList", dogResultList);
		
		//フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/dogResult.jsp");
		dispatcher.forward(request, response);
	}
}
