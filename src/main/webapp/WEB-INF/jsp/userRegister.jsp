<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー作成画面</title>
</head>
<body>
<h1>新規ユーザー作成画面</h1>
<form action="UserRegisterServlet" method="post">
<p>ユーザーID:<input type="text" name="userId" required></p>
<p>パスワード:<input type="password" name="password" required></p>
<input type="submit" value="登録"><br>
</form>
<c:if test="${not empty errorMsg}">
<c:out value="${errorMsg}"/>
</c:if>
</body>
</html>