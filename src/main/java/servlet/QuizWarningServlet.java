package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/QuizWarningServlet")
public class QuizWarningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer petID = Integer.parseInt(request.getParameter("petID"));
		request.setAttribute("petID", petID);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/quizWarning.jsp");
		dispatcher.forward(request, response);
	}

}
