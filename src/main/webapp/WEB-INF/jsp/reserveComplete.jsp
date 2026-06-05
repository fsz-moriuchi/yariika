<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約完了画面</title>
</head>
<body>
<h1>予約完了しました！</h1>
<p>予約情報</p>
日付<c:out value="${reserveDate}"/>
時間<c:out value="${reserveTime}"/>
<a href="HomeServlet">ホームに戻る</a> 
</body>
</html>