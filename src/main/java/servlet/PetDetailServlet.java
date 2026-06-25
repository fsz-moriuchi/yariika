package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FavoriteDAO;
import dao.PetListDAO;
import dao.ReserveDAO;
import model.PetDetail;

@WebServlet("/PetDetailServlet")
public class PetDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String WELCOME_SERVLET = "WelcomeServlet";
	private static final String HOME_SERVLET = "HomeServlet";
	private static final String PET_DETAIL_JSP = "WEB-INF/jsp/petDetail.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		String userId = getLoggedInUserId(session);

		if (userId == null) {
			response.sendRedirect(WELCOME_SERVLET);
			return;
		}

		Integer petID = getPetId(request);

		if (petID == null) {
			response.sendRedirect(HOME_SERVLET);
			return;
		}

		PetListDAO petListDAO = new PetListDAO();
		PetDetail petDetail = petListDAO.showPetDetail(petID);

		if (petDetail == null) {
			response.sendRedirect(HOME_SERVLET);
			return;
		}

		setTransitionSource(request, session);
		setDetailFacilityId(request, session);
		setReservationInfo(request, session, petID, petDetail);
		setCategoryInfo(session, petDetail);
		setFavoriteInfo(request, userId, petID);

		request.setAttribute("petID", petID);
		request.setAttribute("petDetail", petDetail);

		RequestDispatcher dispatcher = request.getRequestDispatcher(PET_DETAIL_JSP);
		dispatcher.forward(request, response);
	}

	// ログイン中のユーザーIDを取得する
	private String getLoggedInUserId(HttpSession session) {

		if (session == null || session.getAttribute("userId") == null) {
			return null;
		}

		return (String) session.getAttribute("userId");
	}

	// リクエストからpetIDを取得し、数値に変換する
	private Integer getPetId(HttpServletRequest request) {

		String petIdStr = request.getParameter("petID");

		if (petIdStr == null || petIdStr.isBlank()) {
			return null;
		}

		try {
			return Integer.parseInt(petIdStr);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	// 遷移元情報を保持する
	private void setTransitionSource(HttpServletRequest request, HttpSession session) {

		String from = request.getParameter("from");

		if (from != null) {
			session.setAttribute("detailFrom", from);
		} else {
			from = (String) session.getAttribute("detailFrom");
		}

		request.setAttribute("from", from);
	}

	// 施設ページから来た場合の施設IDを保持する
	private void setDetailFacilityId(HttpServletRequest request, HttpSession session) {

		String facilityId = request.getParameter("facilityId");

		if (facilityId != null) {
			session.setAttribute("detailFacilityId", facilityId);
		}
	}

	// 予約画面で使うペットID・施設ID・予約済み状態を保存する
	private void setReservationInfo(
			HttpServletRequest request,
			HttpSession session,
			Integer petID,
			PetDetail petDetail) {

		String facilityID = petDetail.getFacilityID();

		ReserveDAO reserveDAO = new ReserveDAO();
		boolean reserved = reserveDAO.existsReserveByPetID(petID);

		request.setAttribute("reserved", reserved);

		session.setAttribute("reservePetID", petID);
		session.setAttribute("reserveFacilityID", facilityID);
		session.setAttribute("reserved", reserved);
	}

	// カテゴリ情報を保存する
	private void setCategoryInfo(HttpSession session, PetDetail petDetail) {

		session.setAttribute("categoryName", petDetail.getCategoryName());
		session.setAttribute("categoryId", petDetail.getCategoryId());
	}

	// お気に入り登録済みかを確認する
	private void setFavoriteInfo(HttpServletRequest request, String userId, Integer petID) {

		FavoriteDAO favoriteDAO = new FavoriteDAO();
		boolean favorite = favoriteDAO.isFavorite(userId, petID);

		request.setAttribute("favorite", favorite);
	}
}
