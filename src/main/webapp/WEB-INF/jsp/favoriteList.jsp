<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>お気に入り一覧</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>


<header class="site-header">
	<div class="header-inner">
		<a href="HomeServlet" class="logo">Pet Matching</a>

		<nav class="header-nav">
			<a href="HomeServlet">ホーム</a>
			<a href="MyPageServlet">マイページ</a>
			<a href="LogoutServlet" class="logout-link">ログアウト</a>
		</nav>
	</div>
</header>

<main class="container">

	<section class="favorite-section">

		<div class="section-heading">
			<h1>お気に入り一覧</h1>
			<p>お気に入り登録したペットを確認できます。</p>
		</div>

		<c:choose>

			<c:when test="${empty favoriteList}">
				<div class="empty-card">
					<p>お気に入り登録されているペットはありません。</p>
					<a href="HomeServlet" class="main-button">ペットを探す</a>
				</div>
			</c:when>

			<c:otherwise>

				<div class="pet-grid">

					<c:forEach var="pet" items="${favoriteList}">

						<div class="pet-card">

							<c:choose>
								<c:when test="${not empty pet.imagePath}">
									<img src="${pageContext.request.contextPath}/${pet.imagePath}"
										class="pet-image">
								</c:when>

								<c:otherwise>
									<div class="pet-no-image">No Image</div>
								</c:otherwise>
							</c:choose>

							<div class="pet-card-body">

								<h2 class="pet-name">
									<c:out value="${pet.name}" />
								</h2>

								<p>性別：<c:out value="${pet.genderName}" /></p>
								<p>年齢：<c:out value="${pet.age}" />歳</p>
								<p>価格：<c:out value="${pet.price}" />円</p>

								<a href="PetDetailServlet?petID=${pet.petID}"
									class="detail-button">
									詳細を見る
								</a>

							</div>

						</div>

					</c:forEach>

				</div>

			</c:otherwise>

		</c:choose>

		<div class="back-link-area">
			<a href="MyPageServlet" class="back-link">マイページに戻る</a>
		</div>

	</section>

</main>

</body>
</html>
