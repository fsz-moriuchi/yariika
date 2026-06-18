<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>個人情報の確認</title>
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

    <h2>個人情報の確認</h2>

    <p class="welcome-message">
        登録されているユーザー情報を確認できます。
    </p>

    <div class="info-list">

        <p>
            <span class="pet-dot">・</span><span class="pet-label">ID:</span>
            ${userInfo.userId}
        </p>

        <p>
            <span class="pet-dot">・</span><span class="pet-label">名前:</span>
            ${userInfo.userName}
        </p>

        <p>
            <span class="pet-dot">・</span><span class="pet-label">性別:</span>
            ${userInfo.userGender}
        </p>

        <p>
            <span class="pet-dot">・</span><span class="pet-label">生年月日:</span>
            ${userInfo.userBirthday}
        </p>

        <p>
            <span class="pet-dot">・</span><span class="pet-label">電話番号:</span>
            ${userInfo.userTel}
        </p>

        <p>
            <span class="pet-dot">・</span><span class="pet-label">メール:</span>
            ${userInfo.userMail}
        </p>

        <p>
            <span class="pet-dot">・</span><span class="pet-label">住所:</span>
            ${userInfo.userAddress}
        </p>

    </div>

    <div class="form-button-area center-button-area">

        <form action="UserEditServlet" method="get">
            <button type="submit">修正</button>
        </form>

        <form action="MyPageServlet" method="get">
            <button class="back-button" type="submit">戻る</button>
        </form>

    </div>

</div>

</div>

</body>
</html>
