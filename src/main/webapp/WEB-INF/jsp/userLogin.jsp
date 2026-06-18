<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="welcome-container">

<div class="welcome-card">

    <div class="site-logo-wrap welcome-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

    <h2>ユーザーログイン</h2>

    <p class="welcome-message">
        ユーザーIDとパスワードを入力してください。
    </p>

    <form class="login-form" action="UserLoginServlet" method="post">

        <p>
            ユーザーID：<br>
            <input type="text" name="userId">
        </p>

        <p>
            パスワード：<br>
            <input type="password" name="password">
        </p>

        <div class="form-button-area">
            <input type="submit" value="ログイン">

            <a class="clear-button" href="WelcomeServlet">
                戻る
            </a>
        </div>

    </form>

    <c:if test="${not empty errorMsg}">
        <p class="error-message">
            <c:out value="${errorMsg}" />
        </p>
    </c:if>

</div>


</div>

</body>
</html>
