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
import model.Pet;
import model.PetInformation;
import model.PetSurvey;
import model.Question;

@WebServlet("/SurveyServlet")
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String PET_SURVEY_JSP_PATH = "WEB-INF/jsp/petSurvey.jsp";
	private static final String PET_REGISTER_SUCCESS_JSP_PATH = "WEB-INF/jsp/petRegisterSuccess.jsp";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String SESSION_PET_KEY = "pet";
	private static final String SESSION_PET_INFORMATION_KEY = "petInformation";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REQUEST_QUESTION_PREFIX = "q";
	private static final int QUESTION_COUNT = 10;
	private static final int PET_QUESTION_MAX_ID = 10;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!isLoggedIn(session)) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		List<Question> petQuestionList = loadPetQuestionList();
		List<Choice> allChoiceList = loadAllChoiceList();

		request.setAttribute("petQuestionList", petQuestionList);
		request.setAttribute("allChoiceList", allChoiceList);

		forwardToPetSurveyPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return;
		}

		String nowPetId = request.getParameter(REQUEST_PET_ID_KEY);

		if (isEmpty(nowPetId)) {
			handleNewPetRegistration(request, response, session);
		} else {
			handlePetSurveyUpdate(request, response, nowPetId);
		}
	}

	private boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute(SESSION_FACILITY_ID_KEY) != null;
	}

	private boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	private List<Question> loadPetQuestionList() {
		QuestionSurveyDAO questionSurveyDAO = new QuestionSurveyDAO();
		List<Question> questionList = questionSurveyDAO.findAllQuestion();
		List<Question> petQuestionList = new ArrayList<>();

		for (Question question : questionList) {
			if (question.getQuestionID() <= PET_QUESTION_MAX_ID) {
				petQuestionList.add(question);
			}
		}
		return petQuestionList;
	}

	private List<Choice> loadAllChoiceList() {
		SurveyChoiceDAO surveyChoiceDAO = new SurveyChoiceDAO();
		return surveyChoiceDAO.findAllChoices();
	}

	private void handleNewPetRegistration(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		Pet pet = (Pet) session.getAttribute(SESSION_PET_KEY);
		PetInformation petInformation = (PetInformation) session.getAttribute(SESSION_PET_INFORMATION_KEY);

		if (pet == null || petInformation == null) {
			writeHtmlMessage(response, "ペット情報がありません。ペット情報入力画面から登録してください。");
			return;
		}

		PetListDAO petListDAO = new PetListDAO();
		int petId = petListDAO.createPet(pet);

		if (petId == -1) {
			writeHtmlMessage(response, "ペット情報の登録に失敗しました。");
			return;
		}

		petInformation.setPetID(petId);
		boolean petInformationResult = petListDAO.createPetInformation(petInformation);
		boolean petSurveyResult = registerPetSurveyAnswers(request, petListDAO, petId);

		session.removeAttribute(SESSION_PET_KEY);
		session.removeAttribute(SESSION_PET_INFORMATION_KEY);

		if (petInformationResult && petSurveyResult) {
			forwardToPetRegisterSuccessPage(request, response);
		} else {
			writeHtmlMessage(response, "登録失敗");
		}
	}

	private void handlePetSurveyUpdate(HttpServletRequest request, HttpServletResponse response, String nowPetId)
			throws ServletException, IOException {

		int petId;
		try {
			petId = Integer.parseInt(nowPetId);
		} catch (NumberFormatException e) {
			writeHtmlMessage(response, "更新失敗");
			return;
		}

		PetListDAO petListDAO = new PetListDAO();
		boolean petSurveyResult = updatePetSurveyAnswers(request, petListDAO, petId);

		if (petSurveyResult) {
			forwardToPetRegisterSuccessPage(request, response);
		} else {
			writeHtmlMessage(response, "更新失敗");
		}
	}

	private boolean registerPetSurveyAnswers(HttpServletRequest request, PetListDAO petListDAO, int petId) {
		for (int questionId = 1; questionId <= QUESTION_COUNT; questionId++) {
			Integer surveyChoiceId = parseSurveyChoiceId(request.getParameter(REQUEST_QUESTION_PREFIX + questionId));
			if (surveyChoiceId == null) {
				return false;
			}
			PetSurvey petSurvey = new PetSurvey(petId, questionId, surveyChoiceId);
			if (!petListDAO.createPetSurvey(petSurvey)) {
				return false;
			}
		}
		return true;
	}

	private boolean updatePetSurveyAnswers(HttpServletRequest request, PetListDAO petListDAO, int petId) {
		for (int questionId = 1; questionId <= QUESTION_COUNT; questionId++) {
			Integer surveyChoiceId = parseSurveyChoiceId(request.getParameter(REQUEST_QUESTION_PREFIX + questionId));
			if (surveyChoiceId == null) {
				return false;
			}
			if (!petListDAO.updatePetSurvey(petId, questionId, surveyChoiceId)) {
				return false;
			}
		}
		return true;
	}

	private Integer parseSurveyChoiceId(String value) {
		if (isEmpty(value)) {
			return null;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void forwardToPetSurveyPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_SURVEY_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToPetRegisterSuccessPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_REGISTER_SUCCESS_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void writeHtmlMessage(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println(message);
	}
}