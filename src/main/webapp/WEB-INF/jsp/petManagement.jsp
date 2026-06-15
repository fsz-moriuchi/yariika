<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット情報管理</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header class="site-header">
		<div class="header-inner">
			<a href="HomeServlet" class="logo">Pet Matching</a>

			<nav class="header-nav">
				<a href="HomeServlet">ホーム</a>
				<a href="FacilityPageServlet">店舗管理ページ</a>
				<a href="LogoutServlet" class="logout-link">ログアウト</a>
			</nav>
		</div>
	</header>

	<main class="container">

		<section class="management-section">

			<div class="section-heading management-heading">
				<div>
					<h1>ペット情報管理</h1>
					<p>
						施設ID：
						<c:out value="${facilityId}" />
					</p>
				</div>

				<form action="PetRegisterServlet" method="get">
					<input type="submit" value="新規ペット情報作成" class="main-button">
				</form>
			</div>

			<div class="management-card">

				<table class="management-table">
					<thead>
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
					</thead>

					<tbody>
						<c:forEach var="pet" items="${facilityList}">
							<tr>

								<td>
									<c:choose>
										<c:when test="${not empty pet.imagePath}">
											<img src="${pageContext.request.contextPath}/${pet.imagePath}"
												alt="ペット画像" class="management-pet-image">
										</c:when>

										<c:otherwise>
											<div class="management-no-image">画像なし</div>
										</c:otherwise>
									</c:choose>
								</td>

								<td class="id-cell">${pet.petID}</td>
								<td>${pet.categoryIdName}</td>
								<td>${pet.genderName}</td>
								<td>${pet.age}歳</td>
								<td>${pet.price}円</td>
								<td>${pet.favoriteCount}人</td>

								<td>
									<div class="table-action-area">

										<form action="PetEditServlet" method="get">
											<input type="hidden" name="petID" value="${pet.petID}">
											<input type="submit" value="修正" class="table-button">
										</form>

										<c:choose>
											<c:when test="${pet.petID == favoritePetId}">
												<span class="recommended-label">★現在おすすめ中</span>
											</c:when>

											<c:otherwise>
												<form action="FavoritePetServlet" method="post">
													<input type="hidden" name="petID" value="${pet.petID}">
													<input type="submit" value="★おすすめに設定" class="table-button sub-table-button">
												</form>
											</c:otherwise>
										</c:choose>

										<form action="PetEditServlet" method="post">
											<input type="hidden" name="petID" value="${pet.petID}">
											<input type="submit" value="アンケート確認" class="table-button gray-table-button">
										</form>

									</div>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>

				<c:if test="${empty facilityList}">
					<p class="empty-message">登録されているペットはありません。</p>
				</c:if>

			</div>

			<div class="back-link-area">
				<a href="FacilityPageServlet" class="back-link">店舗管理ページに戻る</a>
			</div>

		</section>

	</main>

</body>
</html>