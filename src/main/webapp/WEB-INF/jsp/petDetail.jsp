<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ペット詳細画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="pet-detail-card">

<h1>プロフィール</h1>

<div class="pet-detail-main">

    <div class="pet-detail-image-area">
<c:if test="${not empty petDetail.imagePath}">
	<img class="pet-detail-image" src="${pageContext.request.contextPath}/${petDetail.imagePath}"
		width="300">
</c:if>
    </div>

    <div class="pet-detail-info">
<p>
	<span class="pet-dot">・</span><span class="pet-label">種類：</span>
	<c:out value="${petDetail.categoryName}" />
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">名前：</span>
	<c:choose>
		<c:when test="${not empty petDetail.name}">
			<c:out value="${petDetail.name}" />
		</c:when>
		<c:otherwise>
    名付けてください！
</c:otherwise>
	</c:choose>
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">性別：</span>
	<c:out value="${petDetail.genderName}" />
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">年齢：</span>
	<c:out value="${petDetail.age}" />
	歳
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">毛色：</span>
	<c:out value="${petDetail.colorName}" />
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">サイズ：</span>
	<c:out value="${petDetail.petSizeName}" />
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">ワクチン：</span>
	<c:out value="${petDetail.vaccineName}" />
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">価格：</span>
	<c:out value="${petDetail.price}" />
	円
</p>
    </div>

</div>

<hr>

<div class="pet-detail-section">
<h2>紹介文</h2>
<p>
	<c:out value="${petDetail.commentText}" />
</p>
</div>

<hr>

<div class="pet-detail-section">
<h2>施設情報</h2>
<p>
	<span class="pet-dot">・</span><span class="pet-label">施設名：</span>
	<c:out value="${petDetail.facilityName}" />
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">住所：</span>
	<c:out value="${petDetail.address}" />
</p>
<p>
	<span class="pet-dot">・</span><span class="pet-label">電話番号：</span>
	<c:out value="${petDetail.tel}" />
</p>
</div>

<hr>

<!--	お気に入り-->
<c:if test="${not empty sessionScope.userId}">
	<form class="pet-detail-action-form" action="FavoriteServlet" method="post">
		<input type="hidden" name="petID" value="${petDetail.petID}">
		<c:choose>
			<c:when test="${favorite}">
				<input type="submit" value="♥ お気に入り解除">
			</c:when>
			<c:otherwise>
				<input type="submit" value="♡ お気に入り登録">
			</c:otherwise>
		</c:choose>
	</form>
	<br>
</c:if>

<div class="pet-detail-action-area">

<%--メッセージ機能の追加--%>
<a class="detail-button"
	href="MessageServlet?petID=${petDetail.petID}&facilityId=${petDetail.facilityID}&from=${from}">メッセージを送る</a>
<br>

<!--	予約-->
<c:choose>
	<c:when test="${reserved}">
		<h3 class="reserved-message">★このペットは現在予約済みです★</h3>
	</c:when>
	<c:otherwise>
		<a class="detail-button" href="QuizWarningServlet?petID=${petDetail.petID}">予約する</a>
		<br>
		<br>
	</c:otherwise>
</c:choose>

</div>

<br>

<div class="form-button-area center-button-area">

<c:choose>

	<c:when test="${sessionScope.detailFrom == 'favorite'}">
		<a class="clear-button" href="FavoriteListServlet">戻る</a>
	</c:when>

	<c:when test="${sessionScope.detailFrom == 'search'}">
		<a class="clear-button" href="HomeServlet?clickSearch=true">戻る</a>
	</c:when>

	<c:when test="${sessionScope.detailFrom == 'home'}">
		<a class="clear-button" href="HomeServlet">戻る</a>
	</c:when>

	<c:when test="${sessionScope.detailFrom == 'facilityhome'}">
		<a class="clear-button"
			href="FacilityHomeServlet?facilityId=${sessionScope.detailFacilityId}">
			戻る </a>
	</c:when>

	<c:otherwise>
		<a class="clear-button" href="HomeServlet">戻る</a>
	</c:otherwise>

</c:choose>

</div>

</div>

</div>

</body>
</html>
