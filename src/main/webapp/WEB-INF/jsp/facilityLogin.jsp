<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>店舗ログイン画面</title>
</head>
<body>
<h1>店舗ログイン画面</h1>
<form action="FacilityLoginServlet" method="post">
<p>店舗ID:<input type="text" name="facilityId"></p>
<p>パスワード:<input type="password" name="password"></p>
<input type="submit" value="ログイン">
</form>
<c:if test="${not empty errorMsg}">
<c:out value="${errorMsg}"/>
</c:if>
</body>
</html>