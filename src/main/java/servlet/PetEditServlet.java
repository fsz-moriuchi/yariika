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
import model.PetDetail;
import model.PetSurvey;
import model.Question;

@WebServlet("/PetEditServlet")
public class PetEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String loginFacilityId = (String) session.getAttribute("facilityId");
		Integer petID = getIntValue(request.getParameter("petID"));

		if (petID == null) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("ペットIDが不正です。");
			return;
		}
		PetListDAO dao = new PetListDAO();
		PetDetail petDetail = dao.showPetDetail(petID);
		if (petDetail == null) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("ペット情報が見つかりません。");
			return;
		}
		List<PetSurvey> petSurveyList = dao.showPetSurvey(petID);

		request.setAttribute("petDetail", petDetail);
		request.setAttribute("petSurveyList", petSurveyList);
		request.setAttribute("loginFacilityId", loginFacilityId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petInformation.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(false);
		// POST送信時もログイン状態を確認
		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}
		Integer petID = getIntValue(request.getParameter("petID"));

		if (petID == null) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("ペットIDが不正です。");
			return;
		}

		PetListDAO dao = new PetListDAO();
		QuestionSurveyDAO qDao = new QuestionSurveyDAO();
		SurveyChoiceDAO cDao = new SurveyChoiceDAO();
		PetDetail petDetail = dao.showPetDetail(petID);

		if (petDetail == null) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("ペット情報が見つかりません。");
			return;
		}
		List<PetSurvey> petSurveyList = dao.showPetSurvey(petID);
		List<Question> petQuestionList = qDao.findPetQuestions();
		List<Choice> allChoiceList = cDao.findAllChoices();

		request.setAttribute("petQuestionList", petQuestionList);
		request.setAttribute("allChoiceList", allChoiceList);
		request.setAttribute("petSurveyList", petSurveyList);
		request.setAttribute("petID", petID);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petSurveyConfirm.jsp");
		dispatcher.forward(request, response);
	}
	// requestパラメータをIntegerに変換する共通処理
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
