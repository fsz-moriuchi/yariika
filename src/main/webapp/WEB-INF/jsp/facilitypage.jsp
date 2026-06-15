<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>店舗ページ</title>
</head>
<body>
	<c:if test="${!registered}">
		<a href="FacilityInfomationServlet"> 施設情報の入力へ </a>
		<br>
	</c:if>
	<a href="FacilityInfomationConfirmServlet">施設情報の確認・変更へ</a>
	<br>
	<a href="PasswordEditServlet">パスワード変更</a>
	<br>
	<a href="ReservationConfirmServlet">予約確認へ</a>
	<br>
	<a href="StoreServlet">ペット一覧へ</a>
	<br>
	<a href="MessageListServlet">メッセージ一覧へ</a>
	<br>
	<a href="HomeServlet">もどる</a>
	<br>
</body>
</html>