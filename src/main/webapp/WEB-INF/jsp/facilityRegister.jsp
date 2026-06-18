<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規店舗登録画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="welcome-container">

    <div class="register-card">

        <div class="site-logo-wrap welcome-logo-wrap">
            <h1 class="site-logo">PET MATCH</h1>
        </div>

        <h2>新規店舗店舗登録画面</h2>

        <p class="welcome-message">全項目登録してください。</p>

        <form class="register-form" action="FacilityRegisterServlet" method="post">

            <p>
                店舗ID:<br>
                <input type="text" name="facilityId" required>
            </p>

            <p>
                パスワード:<br>
                <input type="password" name="password" required>
            </p>

            <!-- 新規店舗情報もここで登録 -->
            <p>
                1.店舗名<br>
                <input type="text" name="facilityName" required>
            </p>

            <p>
                2.電話番号<br>
                <input type="text" name="tel">
            </p>

            <p>
                3.住所<br>
                <input type="text" name="address">
            </p>

            <p>
                4.メールアドレス<br>
                <input type="email" name="mail">
            </p>

            <p>
                5.開店時間<br>
                <input type="time" name="openTime" required>
            </p>

            <p>
                6.閉店時間<br>
                <input type="time" name="closeTime" required>
            </p>

            <p>
                7.定休日<br>

                <label>
                    <input type="checkbox" name="closedDay" value="MONDAY">月曜日
                </label>

                <label>
                    <input type="checkbox" name="closedDay" value="TUESDAY">火曜日
                </label>

                <label>
                    <input type="checkbox" name="closedDay" value="WEDNESDAY">水曜日
                </label>

                <label>
                    <input type="checkbox" name="closedDay" value="THURSDAY">木曜日
                </label>

                <label>
                    <input type="checkbox" name="closedDay" value="FRIDAY">金曜日
                </label>

                <label>
                    <input type="checkbox" name="closedDay" value="SATURDAY">土曜日
                </label>

                <label>
                    <input type="checkbox" name="closedDay" value="SUNDAY">日曜日
                </label>
            </p>

            <div class="form-button-area">
                <input type="submit" value="登録">

                <a class="clear-button" href="WelcomeServlet">
                    戻る
                </a>
            </div>

        </form>

        <p class="notice">
            施設情報の変更の際は、施設専用ページから変更を行ってください。
        </p>

        <c:if test="${not empty errorMsg}">
            <p class="error-message">
                <c:out value="${errorMsg}" />
            </p>
        </c:if>

    </div>

</div>

</body>
</html>