package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import dao.PetListDAO;
import dao.QuestionSurveyDAO;
import dao.SurveyChoiceDAO;
import model.Choice;
import model.Pet;
import model.PetDetail;
import model.PetInformation;
import model.PetSurvey;
import model.Question;

@WebServlet("/PetRegisterServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class PetRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String LOGIN_REDIRECT_URL = "WelcomeServlet";
	private static final String PET_INFORMATION_JSP_PATH = "WEB-INF/jsp/petInformation.jsp";
	private static final String PET_SURVEY_JSP_PATH = "WEB-INF/jsp/petSurvey.jsp";
	private static final String PET_SURVEY_CONFIRM_JSP_PATH = "WEB-INF/jsp/petSurveyConfirm.jsp";
	private static final String PET_UPDATE_SUCCESS_JSP_PATH = "WEB-INF/jsp/petUpdateSuccess.jsp";
	private static final String PET_DELETE_SUCCESS_JSP_PATH = "WEB-INF/jsp/petDeleteSuccess.jsp";
	private static final String REDIRECT_SURVEY_SERVLET_URL = "SurveyServlet";
	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_ACTION_KEY = "action";
	private static final String REQUEST_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_CATEGORY_ID_KEY = "categoryId";
	private static final String REQUEST_PET_ID_KEY = "petID";
	private static final String REQUEST_NAME_KEY = "name";
	private static final String REQUEST_GENDER_KEY = "gender";
	private static final String REQUEST_AGE_KEY = "age";
	private static final String REQUEST_COLOR_KEY = "color";
	private static final String REQUEST_PET_SIZE_KEY = "pet_size";
	private static final String REQUEST_VACCINE_KEY = "vaccine";
	private static final String REQUEST_PRICE_KEY = "price";
	private static final String REQUEST_COMMENT_TEXT_KEY = "commentText";
	private static final String REQUEST_IMAGE_FILE_KEY = "imageFile";
	private static final String REQUEST_PET_KEY = "pet";
	private static final String REQUEST_PET_INFORMATION_KEY = "petInformation";
	private static final String REQUEST_LOGIN_FACILITY_ID_KEY = "loginFacilityId";
	private static final String REQUEST_PET_DETAIL_KEY = "petDetail";
	private static final String REQUEST_PET_SURVEY_LIST_KEY = "petSurveyList";
	private static final String REQUEST_PET_QUESTION_LIST_KEY = "petQuestionList";
	private static final String REQUEST_ALL_CHOICE_LIST_KEY = "allChoiceList";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMessage";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (rejectIfNotLoggedIn(request, response)) {
			return;
		}

		HttpSession session = request.getSession(false);
		String loginFacilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
		request.setAttribute(REQUEST_LOGIN_FACILITY_ID_KEY, loginFacilityId);

		forwardToPetInformationPage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		if (rejectIfNotLoggedIn(request, response)) {
			return;
		}

		HttpSession session = request.getSession(false);

		String action = request.getParameter(REQUEST_ACTION_KEY);
		String facilityId = request.getParameter(REQUEST_FACILITY_ID_KEY);

		if ("アンケートへ".equals(action)) {
			handleNewPetRegistration(request, response, session, facilityId);
		} else if ("更新".equals(action)) {
			handlePetUpdate(request, response, facilityId);
		} else if ("アンケート修正".equals(action)) {
			handleSurveyEdit(request, response);
		} else if ("削除".equals(action)) {
			handlePetDelete(request, response);
		} else {
			forwardWithError(request, response, "不正な操作です", PET_INFORMATION_JSP_PATH);
		}
	}

	private boolean rejectIfNotLoggedIn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(SESSION_FACILITY_ID_KEY) == null) {
			response.sendRedirect(LOGIN_REDIRECT_URL);
			return true;
		}
		return false;
	}

	private Integer parseIntOrNull(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void handleNewPetRegistration(
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session,
			String facilityId) throws ServletException, IOException {

		Integer categoryId = parseIntOrNull(request.getParameter(REQUEST_CATEGORY_ID_KEY));
		Integer age = parseIntOrNull(request.getParameter(REQUEST_AGE_KEY));
		Integer price = parseIntOrNull(request.getParameter(REQUEST_PRICE_KEY));

		if (categoryId == null || age == null || price == null) {
			forwardWithError(request, response, "数値の入力が不正です", PET_INFORMATION_JSP_PATH);
			return;
		}

		String name = request.getParameter(REQUEST_NAME_KEY);
		String gender = request.getParameter(REQUEST_GENDER_KEY);
		String colorText = joinColors(request.getParameterValues(REQUEST_COLOR_KEY));
		String petSize = request.getParameter(REQUEST_PET_SIZE_KEY);
		String vaccine = request.getParameter(REQUEST_VACCINE_KEY);
		String commentText = request.getParameter(REQUEST_COMMENT_TEXT_KEY);
		Part filePart = request.getPart(REQUEST_IMAGE_FILE_KEY);
		String imagePath = saveUploadedImage(filePart);

		Pet pet = new Pet(facilityId, categoryId);
		PetInformation petInformation = new PetInformation(
				0, name, gender, age, colorText, petSize, vaccine, price, commentText, imagePath);

		session.setAttribute(REQUEST_PET_KEY, pet);
		session.setAttribute(REQUEST_PET_INFORMATION_KEY, petInformation);

		response.sendRedirect(REDIRECT_SURVEY_SERVLET_URL);
	}

	private void handlePetUpdate(
			HttpServletRequest request,
			HttpServletResponse response,
			String facilityId) throws ServletException, IOException {

		Integer petId = parseIntOrNull(request.getParameter(REQUEST_PET_ID_KEY));
		Integer categoryId = parseIntOrNull(request.getParameter(REQUEST_CATEGORY_ID_KEY));
		Integer age = parseIntOrNull(request.getParameter(REQUEST_AGE_KEY));
		Integer price = parseIntOrNull(request.getParameter(REQUEST_PRICE_KEY));

		if (petId == null || categoryId == null || age == null || price == null) {
			forwardWithError(request, response, "数値の入力が不正です", PET_INFORMATION_JSP_PATH);
			return;
		}

		String name = request.getParameter(REQUEST_NAME_KEY);
		String gender = request.getParameter(REQUEST_GENDER_KEY);
		String colorText = joinColors(request.getParameterValues(REQUEST_COLOR_KEY));
		String petSize = request.getParameter(REQUEST_PET_SIZE_KEY);
		String vaccine = request.getParameter(REQUEST_VACCINE_KEY);
		String commentText = request.getParameter(REQUEST_COMMENT_TEXT_KEY);

		Part filePart = request.getPart(REQUEST_IMAGE_FILE_KEY);
		String imagePath = resolveImagePathForUpdate(filePart, petId);

		Pet pet = new Pet(facilityId, categoryId);
		pet.setPetID(petId);

		PetInformation petInformation = new PetInformation(
				petId, name, gender, age, colorText, petSize, vaccine, price, commentText, imagePath);

		PetListDAO petListDAO = new PetListDAO();
		boolean petResult = petListDAO.updatePet(pet);
		boolean petInformationResult = petListDAO.updatePetInformation(petInformation);

		if (petResult && petInformationResult) {
			forwardToPetUpdateSuccessPage(request, response);
		} else {
			writeTextResponse(response, "更新失敗");
		}
	}

	private void handleSurveyEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer petId = parseIntOrNull(request.getParameter(REQUEST_PET_ID_KEY));
		if (petId == null) {
			forwardWithError(request, response, "ペットIDが不正です", PET_SURVEY_JSP_PATH);
			return;
		}

		PetListDAO petListDAO = new PetListDAO();
		QuestionSurveyDAO questionSurveyDAO = new QuestionSurveyDAO();
		SurveyChoiceDAO surveyChoiceDAO = new SurveyChoiceDAO();

		List<PetSurvey> petSurveyList = petListDAO.showPetSurvey(petId);
		List<Question> questionList = questionSurveyDAO.findAllQuestion();
		List<Question> petQuestionList = filterPetQuestions(questionList);
		List<Choice> allChoiceList = surveyChoiceDAO.findAllChoices();

		request.setAttribute(REQUEST_PET_ID_KEY, petId);
		request.setAttribute(REQUEST_PET_SURVEY_LIST_KEY, petSurveyList);
		request.setAttribute(REQUEST_PET_QUESTION_LIST_KEY, petQuestionList);
		request.setAttribute(REQUEST_ALL_CHOICE_LIST_KEY, allChoiceList);

		forwardToPetSurveyPage(request, response);
	}

	private void handlePetDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer petId = parseIntOrNull(request.getParameter(REQUEST_PET_ID_KEY));
		if (petId == null) {
			forwardWithError(request, response, "ペットIDが不正です", PET_DELETE_SUCCESS_JSP_PATH);
			return;
		}

		PetListDAO petListDAO = new PetListDAO();
		boolean result = petListDAO.deletePet(petId);
		request.setAttribute(REQUEST_PET_ID_KEY, petId);

		if (result) {
			forwardToPetDeleteSuccessPage(request, response);
		} else {
			writeTextResponse(response, "削除失敗");
		}
	}

	private String joinColors(String[] colorArray) {
		if (colorArray == null || colorArray.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < colorArray.length; i++) {
			builder.append(colorArray[i]);
			if (i < colorArray.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}

	private String saveUploadedImage(Part filePart) throws IOException {
		String imagePath = null;
		if (filePart != null && filePart.getSize() > 0) {
			String fileName = filePart.getSubmittedFileName();
			String uploadPath = getServletContext().getRealPath("/images");
			File dir = new File(uploadPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			filePart.write(uploadPath + File.separator + fileName);
			imagePath = "images/" + fileName;
		}
		return imagePath;
	}

	private String resolveImagePathForUpdate(Part filePart, int petId) {
		try {
			if (filePart != null && filePart.getSize() > 0) {
				return saveUploadedImage(filePart);
			}
			PetListDAO petListDAO = new PetListDAO();
			PetDetail petDetail = petListDAO.showPetDetail(petId);
			return petDetail.getImagePath();
		} catch (IOException e) {
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

	private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String errorMessage,
			String jspPath) throws ServletException, IOException {
		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, errorMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
		dispatcher.forward(request, response);
	}

	private void forwardToPetInformationPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_INFORMATION_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToPetSurveyPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_SURVEY_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToPetUpdateSuccessPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_UPDATE_SUCCESS_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void forwardToPetDeleteSuccessPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_DELETE_SUCCESS_JSP_PATH);
		dispatcher.forward(request, response);
	}

	private void writeTextResponse(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().println(message);
	}
}