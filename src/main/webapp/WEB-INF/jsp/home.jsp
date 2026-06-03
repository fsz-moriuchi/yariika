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
<a href="MyPageServlet">マイページへ</a><br>
<a href="FacilityPageServlet">施設専用ページへ</a><br>
<h1>ペット一覧</h1>
<c:forEach var="pet" items="${petList}">
種類:<c:out value="${pet.category}" />
性別:<c:out value="${pet.gender}" />
年齢:<c:out value="${pet.age}" />
値段:<c:out value="${pet.price}" />円
<a href="PetDetailServlet?petID=${pet.petID}">詳細を見る</a><br>
</c:forEach><br>
<a href="LogoutServlet">ログアウト</a>
</body>
</html>