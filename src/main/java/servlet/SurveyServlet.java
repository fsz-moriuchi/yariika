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

import dao.PetListDAO;
import dao.QuestionSurveyDAO;
import dao.SurveyChoiceDAO;
import model.Choice;
import model.Pet;
import model.PetInformation;
import model.PetSurvey;
import model.Question;

@WebServlet("/SurveyServlet")
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//PetSurveyServlet
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		QuestionSurveyDAO Qdao = new QuestionSurveyDAO();
		SurveyChoiceDAO Cdao = new SurveyChoiceDAO();
		//ペット用質問（10問）をDAOから取る
		List<Question> petQuestionList = Qdao.findPetQuestions();
		List<Choice> allChoiceList = Cdao.findAllChoices();

		request.setAttribute("petQuestionList", petQuestionList);
		request.setAttribute("allChoiceList", allChoiceList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petSurvey.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		// doPost送信時もログイン状態を確認
			if (session == null || session.getAttribute("facilityId") == null) {
				response.sendRedirect("WelcomeServlet");
				return;
			}
		QuestionSurveyDAO Qdao = new QuestionSurveyDAO();
		List<Question> petQuestionList = Qdao.findPetQuestions();
		// ペット用アンケート問題が取得できない場合は処理を中断
		if (petQuestionList == null || petQuestionList.isEmpty()) {
		    response.setContentType("text/html; charset=UTF-8");
		    response.getWriter().println("ペット用アンケート問題を取得できませんでした。");
		    return;
		}
		
		String nowPetID = request.getParameter("petID");
		//新規入力
		if (nowPetID == null || nowPetID.isEmpty()) {
			Pet pet = (Pet) session.getAttribute("pet");
			PetInformation petInformation = (PetInformation) session.getAttribute("petInformation");

			if (pet == null || petInformation == null) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("ペット情報がありません。ペット情報入力画面から登録してください。");
				return;
			}

			PetListDAO dao = new PetListDAO();
			int petID = dao.createPet(pet);

			//追加
			if (petID == -1) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("ペット情報の登録に失敗しました。");
				return;
			}
			//ここまで

			petInformation.setPetID(petID);
			boolean petInformationResult = dao.createPetInformation(petInformation);
			boolean petSurveyResult = true;

			// 回答を登録
			for (Question q : petQuestionList) {
			    int qID = q.getQuestionID();
				Integer surveyChoiceID = getRequiredIntParameter(request, "q" + qID);
				if (surveyChoiceID == null) {
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().println("未回答の項目があります。もう一度入力してください。");
					return;
				}
				
				PetSurvey petSurvey = new PetSurvey(petID, qID, surveyChoiceID);
				if (!dao.createPetSurvey(petSurvey)) {
					petSurveyResult = false;
					break;
				}
			}
			session.removeAttribute("pet");
			session.removeAttribute("petInformation");

			if (petInformationResult && petSurveyResult) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petRegisterSuccess.jsp");
				dispatcher.forward(request, response);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print("登録失敗");
			}

		}
		//内容修正
		else {
			Integer petID = getIntValue(nowPetID);
			
			if (petID == null) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().print("ペットIDが不正です。");
				return;
			}
			PetListDAO dao = new PetListDAO();
			boolean petSurveyResult = true;
			// 回答を更新
			for (Question q : petQuestionList) {
			    int qID = q.getQuestionID();
			    Integer surveyChoiceID = getRequiredIntParameter(request, "q" + qID);
			    if (surveyChoiceID == null) {
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().println("未回答の項目があります。もう一度入力してください。");
					return;
				}
				if (!dao.updatePetSurvey(petID, qID, surveyChoiceID)) {
					petSurveyResult = false;
					break;
				}
			}
			if (petSurveyResult) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petRegisterSuccess.jsp");
				dispatcher.forward(request, response);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("更新失敗");
			}
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

