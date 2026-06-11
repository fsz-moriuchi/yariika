<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メッセージ画面</title>
</head>


<body>
<h1>メッセージ</h1>
<form action="MessageServlet" method="post">
<input type="hidden" name="petId" value="${param.petId}">
<input type="hidden" name="facilityId" value="${param.facilityId}">
<p><input type="text" name="message"></p>
<button type="submit">送信</button>
</form>

</body>
</html>