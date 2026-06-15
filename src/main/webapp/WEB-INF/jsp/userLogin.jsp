<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
<h1>ログイン画面</h1>
<form action="UserLoginServlet" method="post">
<p>ユーザーID:<input type="text" name="userId"></p>
<p>パスワード:<input type="password" name="password"></p>
<input type="submit" value="ログイン">
</form>
<form action="WelcomeServlet" method="get">
		<input type="submit" value="戻る">
	</form>
<c:if test="${not empty errorMsg}">
<c:out value="${errorMsg}"/>
</c:if>
</body>
</html>