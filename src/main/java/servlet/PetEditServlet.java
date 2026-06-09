package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.PetListDAO;
import model.PetDetail;
import model.PetSurvey;

@WebServlet("/PetEditServlet")
public class PetEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int petID = Integer.parseInt(request.getParameter("petID"));


		PetListDAO dao = new PetListDAO();
		PetDetail petDetail = dao.showPetDetail(petID);
		List<PetSurvey> petSurveyList = dao.showPetSurvey(petID);
		request.setAttribute("petDetail", petDetail);
		request.setAttribute("petSurveyList", petSurveyList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/petInformation.jsp");
		dispatcher.forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
