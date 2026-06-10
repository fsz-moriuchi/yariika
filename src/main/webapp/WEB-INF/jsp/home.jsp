<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
</head>
<body>
	<a href="MyPageServlet">マイページへ</a>
	<br>
	<a href="FacilityPageServlet">施設専用ページへ</a>
	<br>
	<h1>おすすめのペット</h1>

	<c:forEach var="pet" items="${favoritePetList}">

	店舗：<c:out value="${pet.facilityName}" />
		<br>
		<img src="${pet.imagePath}" width="200">
		<br>

	名前：<c:out value="${pet.name}" />
		<br>

	性別：<c:out value="${pet.gender}" />
		<br>

	年齢：<c:out value="${pet.age}" />歳<br>

	価格：<c:out value="${pet.price}" />円<br>

		<a href="PetDetailServlet?petID=${pet.petID}"> 詳細を見る </a>

		<hr>

	</c:forEach>
	<br>
	<a href="LogoutServlet">ログアウト</a>
</body>
</html>