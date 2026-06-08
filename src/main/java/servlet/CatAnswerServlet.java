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

import dao.CatQuizAnswerDAO;
import dao.CatQuizDAO;
import dao.CatQuizResultDAO;
import model.CatQuiz;
import model.CatQuizAnswer;
import model.CatQuizResult;


@WebServlet("/CatAnswerServlet")
public class CatAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//DAOでクイズを取得
		CatQuizDAO dao = new CatQuizDAO();
		//List<CatQuiz> catQuizList = dao.findAll();
		HttpSession session = request.getSession();
		List<CatQuiz> catQuizList = (List<CatQuiz>) session.getAttribute("catQuizList");
		
		//今はログイン機能と結びついていないため、仮ユーザーID
		String userId = "1212";
		/*
		//★実際はセッションから取得
		HttpSession session = request.getSession();
		User login = (User) session.getAttribute("user");
		String userId = login.getUserId();
		
		/*
		//JSPに表示(いる？)
		session.setAttribute("catQuizList", catQuizList);
		*/
		
		//追加
		//DAOでJOIN結果取得
		CatQuizAnswerDAO answerDao = new CatQuizAnswerDAO();
		int count = 0;
		
		/*
		for (CatQuiz cq : catQuizList) {
        	int userAnswer = Integer.parseInt(request.getParameter("q" + cq.getId()));
            //DB保存
        	CatQuizAnswer answer = new CatQuizAnswer(userId, cq.getId(), catUserAnswer);
        	answer.setUserId(userId);
            answer.setCatQuizId(quiz.getCatQuizId());
            answer.setCatUserAnswer(userAnswer);

        	
        	answerDao.insert(answer);
        }
		
        if(catUserAnswer == cq.getAnswer()) {
        	count++;
        }
		*/
        //1問ずつ処理
        for (CatQuiz cq : catQuizList) {

            // q1, q2, q3…
            String paramName = "q" + cq.getId();
            String value = request.getParameter(paramName);

            if (value == null) {
                continue;
            }

            int catUserAnswer = Integer.parseInt(value);

            //モデルに詰める
            CatQuizAnswer answer = new CatQuizAnswer(userId, cq.getId(), catUserAnswer);

            //DB保存
            answerDao.insert(answer);
            
            if(catUserAnswer == cq.getAnswer()) {
            	count++;
            }
        }
        
		/*もともと
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
		*/	
        
        
		//正答率計算
		int totalCount = catQuizList.size();
		int percent = count * 100 / totalCount;
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("count", count);
		request.setAttribute("percent", percent);
		
		//JOIN
		CatQuizResultDAO resultDao = new CatQuizResultDAO();
		List<CatQuizResult> catResultList = resultDao.findByUserId(userId);
		//クイズ表示順を逆に
		Collections.reverse(catResultList);
		// JSPに渡す
		request.setAttribute("catResultList", catResultList);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/catResult.jsp");
		dispatcher.forward(request, response);
	}
}

