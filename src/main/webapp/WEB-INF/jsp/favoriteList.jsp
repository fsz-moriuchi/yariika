<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>お気に入り一覧</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="favorite-card">

<h1>お気に入り一覧</h1>

<p class="welcome-message">

</p>

<c:choose>
	<c:when test="${empty favoriteList}">
	    <p class="notice">
	        お気に入り登録されているペットはありません。
	    </p>
    </c:when>

	<c:otherwise>

        <div class="pet-card-list">

		<c:forEach var="pet" items="${favoriteList}">

            <div class="pet-card">

			<c:if test="${not empty pet.imagePath}">
				<img class="pet-image" src="${pageContext.request.contextPath}/${pet.imagePath}"
					width="200">
				<br>
			</c:if>

            <div class="pet-info">

		<p><span class="pet-dot">・</span><span class="pet-label">名前：</span>${pet.name}</p>
		<p><span class="pet-dot">・</span><span class="pet-label">性別：</span>${pet.genderName}</p>
		<p><span class="pet-dot">・</span><span class="pet-label">年齢：</span>${pet.age}歳</p>
		<p><span class="pet-dot">・</span><span class="pet-label">価格：</span>${pet.price}円</p>

			<a class="detail-button" href="PetDetailServlet?petID=${pet.petID}&from=favorite">
				詳細を見る </a>

            </div>

            </div>

		</c:forEach>

        </div>

	</c:otherwise>
</c:choose>

<div class="form-button-area center-button-area">
<form action="MyPageServlet" method="get">
	<button class="back-button" type="submit">戻る</button>
</form>
</div>

</div>

</div>

</body>
</html>
