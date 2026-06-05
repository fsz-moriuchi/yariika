<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約画面</title>
</head>
<body>
<h1>日時の予約</h1>
<p>所要時間は1時間程度です。前後のお時間に余裕をもってご予約ください。</p>
<form action="ReserveServlet" method="post">
<p>日付を選択してください<input type="date" name="reserveDateStr" value="${reserveDate}"required><input type="submit" value="この日付で検索する"></p>
</form>
<form action ="ReserveCompleteServlet" method="post">
<c:if test="${not empty timeList}">
<p>時間を選択してください</p>
<c:forEach var="time" items="${timeList}">
<input type="radio" name="reserveTime" value="${time}" required>
<c:out value="${time}" /><br>
</c:forEach>
<input type="submit" value="この日時で予約する">
</c:if>
<c:if test="${not empty errorMsg}">
<c:out value="${errorMsg}"/>
</c:if>
</form>
</body>
</html>