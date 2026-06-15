<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${facility.facilityName}</title>
</head>
<body>
	<h1>${facility.facilityName}</h1>
	<hr>
	<h2>店舗情報</h2>

	住所：${facility.address}
	<br> 電話番号：${facility.tel}
	<br> 営業時間：${facility.openTimeDisplay} ～
	${facility.closeTimeDisplay}
	<br>閲覧数：${viewCount}回
	<br> 定休日：
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
	<br> メールアドレス： ${facility.mail}
	<br>
	<hr>

	<h2>おすすめペット</h2>
	<c:if test="${not empty favoritePet}">
		<img src="${pageContext.request.contextPath}/${favoritePet.imagePath}"
			width="200">
		<br>
		名前：${favoritePet.name}<br>
		性別：${favoritePet.gender}<br>
		年齢：${favoritePet.age}歳<br>
		価格：${favoritePet.price}円<br>
		<a href="PetDetailServlet?petID=${favoritePet.petID}"> 詳細を見る </a>
	</c:if>
	<hr>

	<h2>人気ランキング</h2>
	<c:forEach var="pet" items="${rankingList}" varStatus="status">
		${status.count}位<br>
		<img src="${pageContext.request.contextPath}/${pet.imagePath}"
			width="150">
		<br>
		${pet.name}<br>
		お気に入り数
		${pet.favoriteCount}件
		<br>
		<a href="PetDetailServlet?petID=${pet.petID}"> 詳細を見る </a>
		<hr>

	</c:forEach>
	<h2>所属ペット一覧</h2>
	<c:forEach var="pet" items="${petList}">
		<img src="${pageContext.request.contextPath}/${pet.imagePath}"
			width="150">
		<br>
		名前：${pet.name}<br>
		性別：${pet.genderName}<br>
		年齢：${pet.age}歳<br>
		価格：${pet.price}円<br>
		<a href="PetDetailServlet?petID=${pet.petID}"> 詳細を見る </a>
		<hr>
	</c:forEach>

	<a href="HomeServlet"> ホームへ戻る </a>

</body>
</html>