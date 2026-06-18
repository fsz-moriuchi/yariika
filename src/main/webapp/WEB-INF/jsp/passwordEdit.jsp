<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>passwordEdit</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="info-card">

    <h1>パスワード変更</h1>

    <p class="welcome-message">
        現在のパスワードと新しいパスワードを入力してください。
    </p>

    <form class="edit-form" action="PasswordEditServlet" method="post">

        <p>
            元パスワード：<br>
            <input type="password" name="oldPassword" required>
        </p>

        <p>
            新しいパスワード：<br>
            <input type="password" name="newPassword" required>
        </p>

        <p>
            もう一度入力：<br>
            <input type="password" name="newPasswordConfirm" required>
        </p>

        <c:if test="${not empty errorMsg}">
            <p class="error-message">
                <c:out value="${errorMsg}"/><br>
            </p>
        </c:if>

        <div class="form-button-area center-button-area">
            <input type="submit" value="パスワードを変更">
        </div>

    </form>

    <c:choose>
        <c:when test="${not empty sessionScope.userId}">
            <div class="form-button-area center-button-area">
                <form action="MyPageServlet" method="get">
                    <button class="back-button" type="submit">キャンセル</button>
                </form>
            </div>
        </c:when>

        <c:when test="${not empty sessionScope.facilityId}">
            <div class="form-button-area center-button-area">
                <form action="FacilityPageServlet" method="get">
                    <button class="back-button" type="submit">キャンセル</button>
                </form>
            </div>
        </c:when>
    </c:choose>

</div>

</div>

</body>
</html>
