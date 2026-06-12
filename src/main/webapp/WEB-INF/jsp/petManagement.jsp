<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>petManagement</title>
</head>
<body>

	<h1>
		<c:out value="${facilityId}" />
		: ペット情報管理
	</h1>

	<form action="PetRegisterServlet" method="get">
		<input type="submit" value="新規ペット情報作成">
	</form>

	<table border="1" style="width: 100%">
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
								alt="ペット画像" width="120" height="120" style="object-fit: cover;">
						</c:when>

						<c:otherwise>
                画像なし
            </c:otherwise>
					</c:choose></td>

				<td>${pet.petID}</td>
				<td>${pet.categoryIdName}</td>
				<td>${pet.genderName}</td>
				<td>${pet.age}</td>
				<td>${pet.price}</td>
				<td>${pet.favoriteCount}人</td>
				<td>

					<form action="PetEditServlet" method="get">
						<input type="hidden" name="petID" value="${pet.petID}"> <input
							type="submit" value="修正">
					</form> <c:choose>
						<c:when test="${pet.petID == favoritePetId}">
                ★現在おすすめ中
            </c:when>

						<c:otherwise>
							<form action="FavoritePetServlet" method="post">
								<input type="hidden" name="petID" value="${pet.petID}">
								<input type="submit" value="★おすすめに設定">
							</form>
						</c:otherwise>
					</c:choose>

					<form action="PetEditServlet" method="post">
						<input type="hidden" name="petID" value="${pet.petID}"> <input
							type="submit" value="アンケート確認">
					</form>

				</td>

			</tr>
		</c:forEach>
	</table>

	<form action="HomeServlet" method="get">
		<input type="submit" value="戻る">
	</form>

</body>
</html>