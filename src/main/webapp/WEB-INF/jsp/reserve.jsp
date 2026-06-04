<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約画面</title>
</head>
<body>
<h1>日時を予約します</h1>
<p>所要時間は2時間程度です。前後のお時間に余裕をもってご予約ください。</p>
<form action="ReserveServlet" method="post">
<input type="datetime-local"><br>
<input type="submit" value="この時間で予約する">
</form>
</body>
</html>