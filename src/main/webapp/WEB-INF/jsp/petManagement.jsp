<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>petManagement</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<div class="page-container">

<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="pet-management-card">

<h1>
	<c:out value="${facilityId}" />
	: ペット情報管理
</h1>

<p class="welcome-message">
	登録しているペット情報の確認・修正・おすすめ設定ができます。
</p>

<div class="pet-management-top">

<form action="PetRegisterServlet" method="get" class="pet-register-form">
	<input type="submit" value="新規ペット情報作成">
</form>

</div>

<div class="pet-management-table-wrap">

<table border="1" style="width: 100%" class="pet-management-table">
	<tr>
		<th>写真</th>
		<th>ペットID</th>
		<th>種類</th>
		<th>性別</th>
		<th>年齢</th>
		<th>生体価格</th>
		<th>お気に入り数</th>
		<th>操作</th>
	</tr>

	<c:forEach var="pet" items="${facilityList}">
		<tr>

			<td><c:choose>
					<c:when test="${not empty pet.imagePath}">
						<img src="${pageContext.request.contextPath}/${pet.imagePath}"
							alt="ペット画像" width="120" height="120" style="object-fit: cover;" class="pet-management-image">
					</c:when>

					<c:otherwise>
            			<div class="pet-management-no-image">画像なし</div>
        			</c:otherwise>
				</c:choose></td>

			<td>${pet.petID}</td>
			<td>${pet.categoryIdName}</td>
			<td>${pet.genderName}</td>
			<td>${pet.age}歳</td>
			<td>${pet.price}円</td>
			<td>${pet.favoriteCount}人</td>

			<td>

				<div class="pet-action-area">

				<form action="PetEditServlet" method="get" class="pet-action-form">
					<input type="hidden" name="petID" value="${pet.petID}"> <input
						type="submit" value="修正">
				</form> 

				<c:choose>
					<c:when test="${pet.petID == favoritePetId}">
            			<p class="current-recommend-text">★現在おすすめ中</p>
        			</c:when>

					<c:otherwise>
						<form action="FavoritePetServlet" method="post" class="pet-action-form">
							<input type="hidden" name="petID" value="${pet.petID}">
							<input type="submit" value="★おすすめに設定">
						</form>
					</c:otherwise>
				</c:choose>

				<form action="PetEditServlet" method="post" class="pet-action-form">
					<input type="hidden" name="petID" value="${pet.petID}"> <input
						type="submit" value="アンケート確認">
				</form>

				</div>

			</td>

		</tr>
	</c:forEach>
</table>

</div>

<form action="FacilityPageServlet" method="get" class="pet-management-back-form">
	<div class="form-button-area center-button-area">
		<input type="submit" value="戻る">
	</div>
</form>

</div>


</div>

</body>
</html>
