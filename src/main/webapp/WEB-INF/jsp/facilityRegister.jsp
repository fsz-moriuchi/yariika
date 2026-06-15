<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規店舗登録画面</title>
</head>
<body>
<h1>新規店舗店舗登録画面</h1>
<form action="FacilityRegisterServlet" method="post">
<p>店舗ID:<input type="text" name="facilityId" required></p>
<p>パスワード:<input type="password" name="password" required></p>
<input type="submit" value="登録"><br>
</form>
<form action="WelcomeServlet" method="get">
		<input type="submit" value="戻る">
	</form>
<c:if test="${not empty errorMsg}">
<c:out value="${errorMsg}"/>
</c:if>
</body>
</html>