<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー作成画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="welcome-container">

    <div class="register-card">

        <div class="site-logo-wrap welcome-logo-wrap">
            <h1 class="site-logo">PET MATCH</h1>
        </div>

        <h2>新規ユーザー作成画面</h2>

        <p class="welcome-message">全項目登録してください。</p>

        <form class="register-form" action="UserRegisterServlet" method="post">

            <p>
                ユーザーID:<br>
                <input type="text" name="userId" required>
            </p>

            <p>
                パスワード:<br>
                <input type="password" name="password" required>
            </p>

            <p>
                名前：<br>
                <input type="text" name="userName" required>
            </p>

            <p>
                性別：<br>
                <select name="userGender" required>
                    <option value="男">男</option>
                    <option value="女">女</option>
                    <option value="選択しない">選択しない</option>
                </select>
            </p>

            <p>
                生年月日:<br>
                <input type="date" name="userBirthday" required>
            </p>

            <p>
                電話番号：<br>
                <input type="text" name="userTel" required>
            </p>

            <p>
                メール：<br>
                <input type="email" name="userMail" required>
            </p>

            <p>
                住所：<br>
                <input type="text" name="userAddress" required>
            </p>

            <div class="form-button-area">
                <input type="submit" value="登録">

                <a class="clear-button" href="WelcomeServlet">
                    戻る
                </a>
            </div>

        </form>

        <p class="notice">
            登録内容の変更はマイページからいつでも行えます。
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