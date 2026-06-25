package servlet;

import java.io.File;
import java.io.IOException;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// 未ログインならログイン画面へ
		if (session == null || session.getAttribute("facilityId") == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String loginFacilityId = (String) session.getAttribute("facilityId");

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

		String action = request.getParameter("action");
		String facilityId = (String) session.getAttribute("facilityId");		//新規ペット登録	
		
		if ("アンケートへ".equals(action)) {
			Integer categoryId = getIntValue(request.getParameter("categoryId"));
			Integer age = getIntValue(request.getParameter("age"));
			Integer price = getIntValue(request.getParameter("price"));

			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			String[] colorArray = request.getParameterValues("color");
			String colorText = "";

			if (colorArray != null) {
				for (int i = 0; i < colorArray.length; i++) {
					colorText += colorArray[i];
					if (i < colorArray.length - 1) {
						colorText += ",";
					}
				}
			}
			String pet_size = request.getParameter("pet_size");
			String vaccine = request.getParameter("vaccine");
			String commentText = request.getParameter("commentText");
			Part filePart = request.getPart("imageFile");

			String imagePath = null;

			if (categoryId == null || categoryId < 1 || categoryId > 4
					|| age == null || age < 0
					|| price == null || price < 0) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("入力値が不正です。");
				return;
			}
			
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

			Pet pet = new Pet(facilityId, categoryId);

			PetInformation petInformation = new PetInformation(
					0,
					name,
					gender,
					age,
					colorText,
					pet_size,
					vaccine,
					price,
					commentText,
					imagePath);

			session.setAttribute("pet", pet);
			session.setAttribute("petInformation", petInformation);

			response.sendRedirect("SurveyServlet");
		}

		//既存ペット情報更新	
		else if ("更新".equals(action)) {
			Integer petID = getIntValue(request.getParameter("petID"));
			Integer categoryId = getIntValue(request.getParameter("categoryId"));
			Integer age = getIntValue(request.getParameter("age"));
			Integer price = getIntValue(request.getParameter("price"));
			
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			String[] colorArray = request.getParameterValues("color");
			String colorText = "";
			if (colorArray != null) {
				for (int i = 0; i < colorArray.length; i++) {
					colorText += colorArray[i];
					if (i < colorArray.length - 1) {
						colorText += ",";
					}
				}
			}
			String pet_size = request.getParameter("pet_size");
			String vaccine = request.getParameter("vaccine");
			String commentText = request.getParameter("commentText");

			Part filePart = request.getPart("imageFile");
			String imagePath;
			
			if (petID == null
					|| categoryId == null || categoryId < 1 || categoryId > 4
					|| age == null || age < 0
					|| price == null || price < 0) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("入力値が不正です。");
				return;
			}

			// 新しい画像が選択された場合
			if (filePart != null && filePart.getSize() > 0) {
				String fileName = filePart.getSubmittedFileName();
				String uploadPath = getServletContext().getRealPath("/images");
				File dir = new File(uploadPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				filePart.write(uploadPath + File.separator + fileName);
				imagePath = "images/" + fileName;
			} else {
				PetListDAO dao = new PetListDAO();
				PetDetail petDetail = dao.showPetDetail(petID);
				if (petDetail == null) {
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().println("ペット情報が見つかりません。");
					return;
				}
				imagePath = petDetail.getImagePath();
			}

			Pet pet = new Pet(facilityId, categoryId);
			pet.setPetID(petID);
			PetListDAO dao = new PetListDAO();
			PetInformation petInformation = new PetInformation(
					petID,
					name,
					gender,
					age,
					colorText,
					pet_size,
					vaccine,
					price,
					commentText,
					imagePath);

			boolean petResult = dao.updatePet(pet);
			boolean petInformationResult = dao.updatePetInformation(petInformation);

			if (petResult && petInformationResult) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petUpdateSuccess.jsp");
				dispatcher.forward(request, response);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("更新失敗");
			}
		}

		else if ("アンケート修正".equals(action)) {

			Integer petID = getIntValue(request.getParameter("petID"));

			if (petID == null) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("ペットIDが不正です。");
				return;
			}
			PetListDAO dao = new PetListDAO();
			QuestionSurveyDAO qDao = new QuestionSurveyDAO();
			SurveyChoiceDAO cDao = new SurveyChoiceDAO();

			// 現在の回答
			List<PetSurvey> petSurveyList = dao.showPetSurvey(petID);
			// ペット用質問のみ
			List<Question> petQuestionList = qDao.findPetQuestions();


			// 全選択肢
			List<Choice> allChoiceList = cDao.findAllChoices();

			request.setAttribute("petID", petID);
			request.setAttribute("petSurveyList", petSurveyList);
			request.setAttribute("petQuestionList", petQuestionList);
			request.setAttribute("allChoiceList", allChoiceList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petSurvey.jsp");
			dispatcher.forward(request, response);
		}

		//既存ペット情報削除		
		else if ("削除".equals(action)) {
			Integer petID = getIntValue(request.getParameter("petID"));

			if (petID == null) {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("ペットIDが不正です。");
				return;
			}
			PetListDAO dao = new PetListDAO();
			boolean result = dao.deletePet(petID);
			request.setAttribute("petID", petID);
			if (result) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petDeleteSuccess.jsp");
				dispatcher.forward(request, response);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("削除失敗");
			}
		}
		else {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("不正な操作です。");
		}

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
