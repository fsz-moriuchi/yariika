package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityViewDAO;
import dao.MessageDAO;
import dao.PetListDAO;
import dao.ReserveDAO;
import model.FavoritePet;
import model.Reserve;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    HttpSession session = request.getSession();

    if (session == null || session.getAttribute("facilityId") == null) {
        response.sendRedirect("WelcomeServlet");
        return;
    }

    String facilityId = (String) session.getAttribute("facilityId");

    // 本日の予約件数のカウント
    ReserveDAO reserveDao = new ReserveDAO();
    int countTodayReserve = reserveDao.countTodayReserve(facilityId);
    request.setAttribute("countTodayReserve", countTodayReserve);

    // 登録しているペット数のカウント
    PetListDAO petListDao = new PetListDAO();
    int petCount = petListDao.countByfacilityID(facilityId);
    request.setAttribute("petCount", petCount);

    // 直近の予約1件を表示
    Reserve reserve = reserveDao.findnextReserve(facilityId);
    request.setAttribute("reserve", reserve);

    // 最後に追加したペットの表示
    FavoritePet latestPet = petListDao.findLatestPetByFacilityID(facilityId);
    request.setAttribute("latestPet", latestPet);

    // 施設ページアクセス数の表示
    FacilityViewDAO facilityViewDao = new FacilityViewDAO();
    int viewCount = facilityViewDao.getViewCount(facilityId);
    request.setAttribute("viewCount", viewCount);

    // 施設側メッセージ未読数
    MessageDAO messageDao = new MessageDAO();
    int facilityUnreadCount = messageDao.countUnreadByFacilityId(facilityId);
    request.setAttribute("facilityUnreadCount", facilityUnreadCount);

    RequestDispatcher dispatcher =
            request.getRequestDispatcher("WEB-INF/jsp/dashboard.jsp");
    dispatcher.forward(request, response);
}


}
