<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>最初の画面</title>
<link rel="stylesheet" href="style.css">
</head>

<body>

<div class="welcome-container">

    <div class="welcome-card">

        <div class="site-logo-wrap welcome-logo-wrap">
            <h1 class="site-logo">PET MATCH</h1>
        </div>

        <p class="welcome-message">
            あなたとペットの、やさしい出会いをサポートします。
        </p>

        <div class="welcome-menu">

<a class="welcome-button user-button" href="UserLoginServlet">
    ユーザーログイン
</a>

<a class="welcome-button user-register-button" href="UserRegisterServlet">
    新規ユーザー登録
</a>

<a class="welcome-button facility-button" href="FacilityLoginServlet">
    店舗ログイン
</a>

<a class="welcome-button facility-register-button" href="FacilityRegisterServlet">
    新規店舗登録
</a>

        </div>

    </div>

</div>

</body>
</html>