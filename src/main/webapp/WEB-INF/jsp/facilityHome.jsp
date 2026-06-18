<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>${facility.facilityName}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<div class="page-container">


<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="facility-home-card">

<h1>${facility.facilityName}</h1>

<p class="welcome-message">
	店舗情報・おすすめペット・人気ランキングを確認できます。
</p>

<div class="facility-home-section facility-shop-info">

<h2>店舗情報</h2>

<div class="facility-home-info-grid">

	<p><span class="pet-dot">・</span><span class="pet-label">住所：</span>${facility.address}</p>
	<p><span class="pet-dot">・</span><span class="pet-label">電話番号：</span>${facility.tel}</p>
	<p><span class="pet-dot">・</span><span class="pet-label">営業時間：</span>${facility.openTimeDisplay} ～
${facility.closeTimeDisplay}</p>
	<p><span class="pet-dot">・</span><span class="pet-label">閲覧数：</span>${viewCount}回</p>
	<p><span class="pet-dot">・</span><span class="pet-label">定休日：</span>
<c:choose>

	<c:when test="${not empty closedDayList}">
		<c:forEach var="closedDay" items="${closedDayList}"
			varStatus="status">

			<c:choose>
				<c:when test="${closedDay == 'MONDAY'}">月曜日</c:when>
				<c:when test="${closedDay == 'TUESDAY'}">火曜日</c:when>
				<c:when test="${closedDay == 'WEDNESDAY'}">水曜日</c:when>
				<c:when test="${closedDay == 'THURSDAY'}">木曜日</c:when>
				<c:when test="${closedDay == 'FRIDAY'}">金曜日</c:when>
				<c:when test="${closedDay == 'SATURDAY'}">土曜日</c:when>
				<c:when test="${closedDay == 'SUNDAY'}">日曜日</c:when>
				<c:otherwise>${closedDay}</c:otherwise>
			</c:choose>

			<c:if test="${not status.last}">
            、 
        </c:if>

		</c:forEach>
	</c:when>

	<c:otherwise>
    定休日なし
</c:otherwise>

</c:choose>
	</p>
	<p><span class="pet-dot">・</span><span class="pet-label">メールアドレス：</span>${facility.mail}</p>

</div>

</div>

<div class="facility-home-section">

<h2>おすすめペット</h2>

<c:if test="${not empty favoritePet}">

	<div class="facility-recommend-card">

		<img class="facility-recommend-image" src="${pageContext.request.contextPath}/${favoritePet.imagePath}"
		width="200">

		<div class="facility-recommend-info">
			<p><span class="pet-dot">・</span><span class="pet-label">名前：</span>${favoritePet.name}</p>
			<p><span class="pet-dot">・</span><span class="pet-label">性別：</span>${favoritePet.genderName}</p>
			<p><span class="pet-dot">・</span><span class="pet-label">年齢：</span>${favoritePet.age}歳</p>
			<p><span class="pet-dot">・</span><span class="pet-label">価格：</span>${favoritePet.price}円</p>

			<a class="detail-button"
		href="PetDetailServlet?petID=${favoritePet.petID}&from=facilityhome&facilityId=${facility.facilityID}">
		詳細を見る </a>
		</div>

	</div>

</c:if>

<c:if test="${empty favoritePet}">
	<p class="notice">おすすめペットはまだ設定されていません。</p>
</c:if>

</div>

<div class="facility-home-section">

<h2>人気ランキング TOP3</h2>

<div class="facility-ranking-list">

<c:forEach var="pet" items="${rankingList}" varStatus="status">

	<c:if test="${status.count <= 3}">

	<div class="facility-ranking-card">

		<div class="ranking-number">${status.count}位</div>

		<img class="facility-ranking-image" src="${pageContext.request.contextPath}/${pet.imagePath}"
		width="150">

		<div class="facility-ranking-info">
			<p class="ranking-pet-name">${pet.name}</p>
			<p><span class="pet-dot">・</span><span class="pet-label">お気に入り数：</span>${pet.favoriteCount}件</p>

			<a class="detail-button"
		href="PetDetailServlet?petID=${pet.petID}&from=facilityhome&facilityId=${facility.facilityID}">
		詳細を見る </a>
		</div>

	</div>

	</c:if>

</c:forEach>

</div>

</div>

<div class="facility-home-section">

<h2>所属ペット一覧</h2>

<div class="facility-pet-list">

<c:forEach var="pet" items="${petList}">

	<div class="facility-pet-card">

		<img class="facility-pet-image" src="${pageContext.request.contextPath}/${pet.imagePath}"
		width="150">

		<div class="facility-pet-info">
			<p><span class="pet-dot">・</span><span class="pet-label">名前：</span>${pet.name}</p>
			<p><span class="pet-dot">・</span><span class="pet-label">性別：</span>${pet.genderName}</p>
			<p><span class="pet-dot">・</span><span class="pet-label">年齢：</span>${pet.age}歳</p>
			<p><span class="pet-dot">・</span><span class="pet-label">価格：</span>${pet.price}円</p>

			<a class="detail-button"
		href="PetDetailServlet?petID=${pet.petID}&from=facilityhome&facilityId=${facility.facilityID}">
		詳細を見る </a>
		</div>

	</div>

</c:forEach>

</div>

</div>

<div class="form-button-area center-button-area">
	<a class="clear-button" href="HomeServlet"> ホームへ戻る </a>
</div>

</div>


</div>

</body>
</html>
