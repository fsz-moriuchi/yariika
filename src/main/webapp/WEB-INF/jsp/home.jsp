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
	
	<h1>条件で探す</h1>
	
	<form action="HomeServlet" method="get">
		<input type="hidden" name="clickSearch" value="true">
	
	カテゴリー：
	<select name="categoryId">
		<option value="">未指定</option>
		<option value="1" ${selectedCategoryId == "1" ? "selected" : ""}>犬</option>
		<option value="2" ${selectedCategoryId == "2" ? "selected" : ""}>猫</option>
		<option value="3" ${selectedCategoryId == "3" ? "selected" : ""}>鳥</option>
		<option value="4" ${selectedCategoryId == "4" ? "selected" : ""}>小動物</option>
	</select>
	<br>
	性別
	色柄
	サイズ
	年齢
	価格
	<button type="submit">検索</button>
	</form>
	
	<form action="HomeServlet" method="get">
		<button type="submit">クリア</button>
	</form>

	<h1>おすすめのペット</h1>

	<c:if test="${not empty sessionScope.userId}">

		<form action="HomeServlet" method="get">
			並び順： <select name="sort">
				<option value="">並び順を選択ください</option>
				<option value="matchRateDesc"
					${sort == "matchRateDesc" ? "selected" : ""}>マッチング度高い順</option>
				<option value="matchRateAsc"
					${sort == "matchRateAsc" ? "selected" : ""}>マッチング度低い順</option>
				<option value="priceDesc"
					${sort == "priceDesc" ? "selected" : ""}>価格高い順</option>
				<option value="priceAsc"
					${sort == "priceAsc" ? "selected" : ""}>価格低い順</option>
				<option value="ageDesc"
					${sort == "ageDesc" ? "selected" : ""}>年齢高い順</option>
				<option value="ageAsc"
					${sort == "ageAsc" ? "selected" : ""}>年齢低い順</option>
			</select>
			<button type="submit">並び替え</button>
		</form>
		<br>
	</c:if>

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
	
	マッチング度：<c:out value="${pet.matchRate}" /> % <br>

		<a href="PetDetailServlet?petID=${pet.petID}"> 詳細を見る </a>

		<hr>

	</c:forEach>
	<br>
	<a href="LogoutServlet">ログアウト</a>
</body>
</html>