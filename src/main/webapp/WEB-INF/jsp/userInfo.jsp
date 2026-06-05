<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>個人情報の確認</title>
</head>
<body>
<h1>個人情報の確認</h1>

<h2>ユーザー情報</h2>

<p>ID: ${userInfo.userId}</p>
<p>名前: ${userInfo.userName}</p>
<p>性別: ${userInfo.userGender}</p>
<p>生年月日: ${userInfo.userBirthday}</p>
<p>電話番号: ${userInfo.userTel}</p>
<p>メール: ${userInfo.userMail}</p>
<p>住所: ${userInfo.userAddress}</p>


<form action="UserEditServlet" method="get">
<button type="submit">修正</button>
</form>

<form action="MyPageServlet" method="get">
<button type="submit">戻る</button>
</form>

</body>
</html>