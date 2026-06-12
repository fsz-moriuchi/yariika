<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>お気に入り一覧</title>
</head>
<body>

	<h1>お気に入り一覧</h1>
	<c:choose>
		<c:when test="${empty favoriteList}">
		お気に入り登録されているペットはありません。
	</c:when>
		<c:otherwise>
			<c:forEach var="pet" items="${favoriteList}">
				<c:if test="${not empty pet.imagePath}">
					<img src="${pageContext.request.contextPath}/${pet.imagePath}"
						width="200">
					<br>
				</c:if>
			名前：${pet.name}<br>
			性別：${pet.genderName}<br>
			年齢：${pet.age}歳<br>
			価格：${pet.price}円<br>
				<a href="PetDetailServlet?petID=${pet.petID}"> 詳細を見る </a>
				<hr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<form action="MyPageServlet" method="get">
		<button type="submit">戻る</button>
	</form>

</body>
</html>