package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityInformationDAO;

@WebServlet("/FavoritePetServlet")
public class FavoritePetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String SESSION_FACILITY_ID_KEY = "facilityId";
	private static final String REQUEST_ERROR_MESSAGE_KEY = "errorMsg";
	private static final String STORE_REDIRECT_URL = "StoreServlet";
	private static final String STORE_JSP_PATH = "/WEB-INF/jsp/store.jsp";
	private static final String UPDATE_ERROR_MESSAGE = "お気に入りペットの更新に失敗しました。";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(SESSION_FACILITY_ID_KEY) == null) {
			response.sendRedirect("WelcomeServlet");
			return;
		}

		String facilityId = (String) session.getAttribute(SESSION_FACILITY_ID_KEY);
		int petId = Integer.parseInt(request.getParameter("petID"));

		FacilityInformationDAO dao = new FacilityInformationDAO();
		boolean updated = dao.updateFavoritePet(facilityId, petId);

		if (updated) {
			response.sendRedirect(STORE_REDIRECT_URL);
			return;
		}

		request.setAttribute(REQUEST_ERROR_MESSAGE_KEY, UPDATE_ERROR_MESSAGE);
		RequestDispatcher dispatcher = request.getRequestDispatcher(STORE_JSP_PATH);
		dispatcher.forward(request, response);
	}
}