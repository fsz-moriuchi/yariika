package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilityClosedDayDAO;
import dao.FacilityInformationDAO;
import dao.ReserveDAO;
import model.FacilityInformation;

@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // 未ログインならログイン画面へ
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("WelcomeServlet");
            return;
        }

        setReservationDateRange(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reserve.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        // doPostでもログインチェックする
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("WelcomeServlet");
            return;
        }

        LocalDate minDate = LocalDate.now().plusDays(3);
        LocalDate maxDate = LocalDate.now().plusWeeks(1);

        request.setAttribute("minDate", minDate);
        request.setAttribute("maxDate", maxDate);

        Integer petID = (Integer) session.getAttribute("reservePetID");
        String facilityID = (String) session.getAttribute("reserveFacilityID");

        if (petID == null || facilityID == null) {
            forwardWithError(request, response, "予約情報が見つかりませんでした。もう一度ペット詳細画面から予約してください。");
            return;
        }

        String reserveDateStr = request.getParameter("reserveDateStr");

        if (isBlank(reserveDateStr)) {
            forwardWithError(request, response, "予約日を選択してください。");
            return;
        }

        LocalDate reserveDate;

        try {
            reserveDate = LocalDate.parse(reserveDateStr);
        } catch (DateTimeParseException e) {
            forwardWithError(request, response, "予約日の形式が正しくありません。");
            return;
        }

        if (reserveDate.isBefore(minDate) || reserveDate.isAfter(maxDate)) {
            forwardWithError(request, response, "予約日は3日後から1週間後までの範囲で選択してください。");
            return;
        }

        FacilityInformationDAO facilityInformationDAO = new FacilityInformationDAO();
        FacilityInformation facilityInformation = facilityInformationDAO.findByFacilityID(facilityID);

        if (facilityInformation == null) {
            forwardWithError(request, response, "施設情報が見つかりませんでした。");
            return;
        }

        LocalTime openTime = facilityInformation.getOpenTime();
        LocalTime closeTime = facilityInformation.getCloseTime();

        if (openTime == null || closeTime == null) {
            forwardWithError(request, response, "施設の営業時間が登録されていません。");
            return;
        }

        ReserveDAO reserveDAO = new ReserveDAO();
        List<LocalTime> reservedTimeList = reserveDAO.findByFacilityAndDate(facilityID, reserveDate);

        FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();
        List<String> closedDayList = closedDayDAO.findByFacilityID(facilityID);

        List<String> timeList = new ArrayList<>();

        // 定休日の曜日の判定
        if (closedDayList.contains(reserveDate.getDayOfWeek().toString())) {
            request.setAttribute("errorMsg", "定休日を選択しています。");
        } else {
            LocalTime lastTime = closeTime.minusMinutes(30);
            LocalTime time = openTime;

            while (!time.isAfter(lastTime)) {
                if (!reservedTimeList.contains(time)) {
                    timeList.add(time.toString());
                }

                time = time.plusMinutes(30);
            }
        }

        request.setAttribute("petID", petID);
        request.setAttribute("facilityID", facilityID);
        request.setAttribute("reserveDate", reserveDate);
        request.setAttribute("timeList", timeList);
        request.setAttribute("minDate", minDate);
        request.setAttribute("maxDate", maxDate);

        session.setAttribute("reserveDate", reserveDateStr);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reserve.jsp");
        dispatcher.forward(request, response);
    }

    private void setReservationDateRange(HttpServletRequest request) {
        LocalDate minDate = LocalDate.now().plusDays(3);
        LocalDate maxDate = LocalDate.now().plusWeeks(1);

        request.setAttribute("minDate", minDate);
        request.setAttribute("maxDate", maxDate);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String errorMsg)
            throws ServletException, IOException {

        request.setAttribute("errorMsg", errorMsg);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reserve.jsp");
        dispatcher.forward(request, response);
    }
}