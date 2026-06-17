<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

    <div class="site-header">

        <div class="site-logo-wrap">
            <h1 class="site-logo">PET MATCH</h1>
        </div>

    </div>

    <div class="mypage-card">

        <h2>マイページ</h2>

        <p class="welcome-message">
            登録情報やアンケート、予約などを確認できます。
        </p>

        <div class="mypage-menu">

            <form action="UserInfoServlet" method="get">
                <button type="submit">個人情報確認・変更</button>
            </form>

            <form action="PasswordEditServlet" method="get">
                <button type="submit">パスワード変更</button>
            </form>

            <c:choose>
                <c:when test="${empty userSurveyList}">
                    <form action="UserSuveyServlet" method="get">
                        <button type="submit">アンケート回答</button>
                    </form>
                </c:when>

                <c:otherwise>
                    <form action="SurveyConfirmServlet" method="get">
                        <button type="submit">アンケート確認</button>
                    </form>
                </c:otherwise>
            </c:choose>

            <form action="ReserveCheckServlet" method="get">
                <button type="submit">予約確認</button>
            </form>

            <form action="FavoriteListServlet" method="get">
                <button type="submit">お気に入り一覧へ</button>
            </form>

            <form action="MessageListServlet" method="get">
                <button type="submit">メッセージ一覧へ</button>
            </form>

        </div>

        <div class="mypage-bottom">
            <form action="HomeServlet" method="get">
                <button class="back-button" type="submit">戻る</button>
            </form>
        </div>

    </div>

</div>

</body>
</html>