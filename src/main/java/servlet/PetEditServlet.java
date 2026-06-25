package servlet;

import java.io.IOException;
import java.util.ArrayList;
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

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String PET_INFORMATION_JSP_PATH = "WEB-INF/jsp/petInformation.jsp";
	private static final String PET_SURVEY_CONFIRM_JSP_PATH = "WEB-INF/jsp/petSurveyConfirm.jsp";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REQUEST_PET_DETAIL_KEY = "petDetail";
	private static final String REQUEST_PET_SURVEY_LIST_KEY = "petSurveyList";
	private static final String REQUEST_LOGIN_FACILITY_ID_KEY = "loginFacilityId";
	private static final String REQUEST_PET_QUESTION_LIST_KEY = "petQuestionList";
	private static final String REQUEST_ALL_CHOICE_LIST_KEY = "allChoiceList";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String loginFacilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
		Integer petId = parsePetId(request);
		if (petId == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		PetListDAO petListDAO = new PetListDAO();
		PetDetail petDetail = petListDAO.showPetDetail(petId);
		List<PetSurvey> petSurveyList = petListDAO.showPetSurvey(petId);

		request.setAttribute(REQUEST_PET_DETAIL_KEY, petDetail);
		request.setAttribute(REQUEST_PET_SURVEY_LIST_KEY, petSurveyList);
		request.setAttribute(REQUEST_LOGIN_FACILITY_ID_KEY, loginFacilityId);

		forwardToPetInformationPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Integer petId = parsePetId(request);
		if (petId == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		PetListDAO petListDAO = new PetListDAO();
		QuestionSurveyDAO questionSurveyDAO = new QuestionSurveyDAO();
		SurveyChoiceDAO surveyChoiceDAO = new SurveyChoiceDAO();

		List<PetSurvey> petSurveyList = petListDAO.showPetSurvey(petId);
		List<Question> petQuestionList = filterPetQuestions(questionSurveyDAO.findAllQuestion());
		List<Choice> allChoiceList = surveyChoiceDAO.findAllChoices();

		request.setAttribute(REQUEST_PET_QUESTION_LIST_KEY, petQuestionList);
		request.setAttribute(REQUEST_ALL_CHOICE_LIST_KEY, allChoiceList);
		request.setAttribute(REQUEST_PET_SURVEY_LIST_KEY, petSurveyList);
		request.setAttribute(REQUEST_PET_ID_KEY, petId);

		forwardToPetSurveyConfirmPage(request, response);
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_FACILITY_ID_KEY) != null;
	}

	private Integer parsePetId(HttpServletRequest request) {
		String petIdString = request.getParameter(REQUEST_PET_ID_KEY);
		if (petIdString == null || petIdString.isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(petIdString);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private List<Question> filterPetQuestions(List<Question> questionList) {
		List<Question> petQuestionList = new ArrayList<>();
		for (Question question : questionList) {
			if (question.getQuestionID() <= 10) {
				petQuestionList.add(question);
			}
		}
		return petQuestionList;
	}

	private void forwardToPetInformationPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_INFORMATION_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToPetSurveyConfirmPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_SURVEY_CONFIRM_JSP_PATH);
		dispatcher.forward(request, response);
	}
}