package servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.FacilitiesDAO;
import dao.FacilityClosedDayDAO;
import dao.FacilityInfomationDAO;
import model.Facility;
import model.FacilityInformation;
import util.PasswordUtil;

@WebServlet("/FacilityRegisterServlet")
public class FacilityRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Set<String> VALID_CLOSED_DAYS = new HashSet<String>(Arrays.asList(
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY",
            "SUNDAY"
    ));

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityRegister.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // アカウント情報
        String facilityId = request.getParameter("facilityId");
        String password = request.getParameter("password");

        // 店舗情報
        String facilityName = request.getParameter("facilityName");
        String tel = request.getParameter("tel");
        String address = request.getParameter("address");
        String mail = request.getParameter("mail");
        String openTimeStr = request.getParameter("openTime");
        String closeTimeStr = request.getParameter("closeTime");
        String[] closedDays = request.getParameterValues("closedDay");

        // DB定義・JSPのrequiredに合わせた必須チェック
        if (isBlank(facilityId)
                || isBlank(password)
                || isBlank(facilityName)
                || isBlank(openTimeStr)
                || isBlank(closeTimeStr)) {

            forwardToRegisterWithError(request, response, "必須項目が入力されていません。");
            return;
        }

        // DBのVARCHAR長に合わせた簡単な文字数チェック
        if (facilityId.length() > 50) {
            forwardToRegisterWithError(request, response, "店舗IDは50文字以内で入力してください。");
            return;
        }

        if (facilityName.length() > 100) {
            forwardToRegisterWithError(request, response, "店舗名は100文字以内で入力してください。");
            return;
        }

        if (!isBlank(tel) && tel.length() > 20) {
            forwardToRegisterWithError(request, response, "電話番号は20文字以内で入力してください。");
            return;
        }

        if (!isBlank(address) && address.length() > 255) {
            forwardToRegisterWithError(request, response, "住所は255文字以内で入力してください。");
            return;
        }

        if (!isBlank(mail) && mail.length() > 100) {
            forwardToRegisterWithError(request, response, "メールアドレスは100文字以内で入力してください。");
            return;
        }

        // 時刻はparse前にチェックし、形式不正なら500エラーにしない
        LocalTime openTime;
        LocalTime closeTime;

        try {
            openTime = LocalTime.parse(openTimeStr);
            closeTime = LocalTime.parse(closeTimeStr);
        } catch (DateTimeParseException e) {
            forwardToRegisterWithError(request, response, "営業時間の形式が正しくありません。");
            return;
        }

        // 定休日は任意。ただし、送られてきた値は信用しない
        Set<String> uniqueClosedDays = new LinkedHashSet<String>();

        if (closedDays != null) {
            for (String closedDay : closedDays) {
                if (!VALID_CLOSED_DAYS.contains(closedDay)) {
                    forwardToRegisterWithError(request, response, "定休日の値が正しくありません。");
                    return;
                }

                uniqueClosedDays.add(closedDay);
            }
        }

        // 入力チェック後にハッシュ化する
        String hash = PasswordUtil.hashPassword(password);

        // DAO
        FacilitiesDAO facilitiesDAO = new FacilitiesDAO();
        FacilityInfomationDAO facilityInfoDAO = new FacilityInfomationDAO();
        FacilityClosedDayDAO closedDayDAO = new FacilityClosedDayDAO();

        // ログイン情報登録
        Facility facility = new Facility(facilityId, hash);
        boolean facilityRegisterResult = facilitiesDAO.registerFacility(facility);

        if (!facilityRegisterResult) {
            request.setAttribute("errorMsg", "その店舗IDは既に使用されています");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityRegister.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 既存コードの動作に寄せるため、insert / update の分岐は残す
        FacilityInformation oldInfo = facilityInfoDAO.findByFacilityId(facilityId);

        FacilityInformation facilityInfo = new FacilityInformation(
                facilityId,
                facilityName,
                tel,
                address,
                mail,
                openTime,
                closeTime
        );

        // 休日登録
        for (String closedDay : uniqueClosedDays) {
            boolean closedDayRegisterResult = closedDayDAO.insertByFacilityID(facilityId, closedDay);

            if (!closedDayRegisterResult) {
                forwardToRegisterWithError(request, response, "定休日の登録に失敗しました。");
                return;
            }
        }

        // 店舗情報登録
        boolean facilityInfoResult;

        if (oldInfo == null) {
            facilityInfoResult = facilityInfoDAO.insert(facilityInfo);
        } else {
            facilityInfoResult = facilityInfoDAO.update(facilityInfo);
        }

        // 元コードに合わせて、sessionへのfacilityId保存は残す
        HttpSession session = request.getSession();
        session.setAttribute("facilityId", facilityId);

        if (facilityInfoResult) {
            response.sendRedirect("FacilityLoginServlet");
        } else {
            request.setAttribute("errorMsg", "店舗情報の登録に失敗しました。入力内容を確認してください。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityinfomation.jsp");
            dispatcher.forward(request, response);
            return;
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void forwardToRegisterWithError(HttpServletRequest request, HttpServletResponse response, String errorMsg)
            throws ServletException, IOException {

        request.setAttribute("errorMsg", errorMsg);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/facilityRegister.jsp");
        dispatcher.forward(request, response);
    }
}