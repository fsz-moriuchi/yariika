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
import model.Choice;
import model.Question;
import model.UserSurvey;

@WebServlet("/UserSuveyServlet")
public class UserSuveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//UserSurveyServlet
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String userId = (String) session.getAttribute("userId");

		UsersDAO uDao = new UsersDAO();
		QuestionSurveyDAO Qdao = new QuestionSurveyDAO();
		SurveyChoiceDAO Cdao = new SurveyChoiceDAO();
		List<UserSurvey> userSurveyList = uDao.showUserSurvey(userId);
		List<Question> questionList = Qdao.findAllQuestion();
		List<Choice> allChoiceList = Cdao.findAllChoices();

		request.setAttribute("userSurveyList", userSurveyList);
		request.setAttribute("questionList", questionList);
		request.setAttribute("allChoiceList", allChoiceList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userSurvey.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		// POST送信時もログイン状態を確認
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}
		String action = request.getParameter("action");
		String userId = (String) session.getAttribute("userId");
		UsersDAO dao = new UsersDAO();
		//新規アンケート登録
		if ("登録".equals(action)) {
			boolean userSurveyResult = true;
			for (int qID = 1; qID <= 14; qID++) {
				Integer surveyChoiceID = getRequiredIntParameter(request, "q" + qID);

				if (surveyChoiceID == null) {
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().println("未回答の項目があります。もう一度入力してください。");
					return;
				}
				UserSurvey userSurvey = new UserSurvey(userId, qID, surveyChoiceID);
				if (!dao.createUserSurvey(userSurvey)) {
					userSurveyResult = false;
					break;
				}
			}
			if (userSurveyResult) {
				System.out.println("action = " + action);
				System.out.println("userId = " + userId);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userSurveySuccess.jsp");
				dispatcher.forward(request, response);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("登録失敗");
			}
		}
		//アンケート内容修正
		else if ("更新".equals(action)) {
			boolean userSurveyResult = true;
			for (int qID = 1; qID <= 14; qID++) {
				Integer surveyChoiceID = getRequiredIntParameter(request, "q" + qID);

				if (surveyChoiceID == null) {
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().println("未回答の項目があります。もう一度入力してください。");
					return;
				}
				if (!dao.updateUserSurvey(userId, qID, surveyChoiceID)) {
					userSurveyResult = false;
					break;
				}
			}
			if (userSurveyResult) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userUpdateSuccess.jsp");
				dispatcher.forward(request, response);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("更新失敗");
			}

		}else {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("不正な操作です。");
		}

	}
	// requestパラメータをIntegerに変換する共通処理
	private Integer getRequiredIntParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		return getIntValue(value);
	}

	private Integer getIntValue(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
