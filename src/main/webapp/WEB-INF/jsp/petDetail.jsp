<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット詳細画面</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header class="site-header">
		<div class="header-inner">
			<a href="HomeServlet" class="logo">Pet Matching</a>

			<nav class="header-nav">
				<a href="HomeServlet">ホーム</a>

				<c:if test="${not empty sessionScope.userId}">
					<a href="MyPageServlet">マイページ</a>
				</c:if>

				<c:if test="${not empty sessionScope.facilityId}">
					<a href="FacilityPageServlet">施設専用ページ</a>
				</c:if>

				<a href="LogoutServlet" class="logout-link">ログアウト</a>
			</nav>
		</div>
	</header>

	<main class="container">

		<section class="pet-detail-page">

			<div class="section-heading">
				<h1>ペットプロフィール</h1>
				<p>気になるペットの詳しい情報を確認できます。</p>
			</div>

			<div class="pet-detail-layout">

				<div class="pet-detail-photo-card">
					<c:choose>
						<c:when test="${not empty petDetail.imagePath}">
							<img src="${pageContext.request.contextPath}/${petDetail.imagePath}"
								class="pet-detail-image">
						</c:when>

						<c:otherwise>
							<div class="no-image-box">No Image</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="pet-detail-info-card">

					<div class="pet-detail-title">
						<p class="pet-category">
							<c:out value="${petDetail.categoryName}" />
						</p>

						<h2>
							<c:choose>
								<c:when test="${not empty petDetail.name}">
									<c:out value="${petDetail.name}" />
								</c:when>
								<c:otherwise>
									名付けてください！
								</c:otherwise>
							</c:choose>
						</h2>
					</div>

					<div class="detail-info-grid">
						<div class="detail-info-item">
							<span>性別</span>
							<strong><c:out value="${petDetail.gender}" /></strong>
						</div>

						<div class="detail-info-item">
							<span>年齢</span>
							<strong><c:out value="${petDetail.age}" />歳</strong>
						</div>

						<div class="detail-info-item">
							<span>毛色</span>
							<strong><c:out value="${petDetail.color}" /></strong>
						</div>

						<div class="detail-info-item">
							<span>サイズ</span>
							<strong><c:out value="${petDetail.petSizeName}" /></strong>
						</div>

						<div class="detail-info-item">
							<span>ワクチン</span>
							<strong><c:out value="${petDetail.vaccineName}" /></strong>
						</div>

						<div class="detail-info-item price-item">
							<span>価格</span>
							<strong><c:out value="${petDetail.price}" />円</strong>
						</div>
					</div>

					<div class="detail-actions">

						<c:if test="${not empty sessionScope.userId}">
							<form action="FavoriteServlet" method="post" class="favorite-form">
								<input type="hidden" name="petID" value="${petDetail.petID}">

								<c:choose>
									<c:when test="${favorite}">
										<input type="submit" value="♥ お気に入り解除" class="sub-button">
									</c:when>
									<c:otherwise>
										<input type="submit" value="♡ お気に入り登録" class="sub-button">
									</c:otherwise>
								</c:choose>
							</form>
						</c:if>

						<c:choose>
							<c:when test="${reserved}">
								<p class="reserved-message">このペットは現在予約済みです</p>
							</c:when>

							<c:otherwise>
								<a href="QuizWarningServlet?petID=${petDetail.petID}"
									class="main-button">予約する</a>
							</c:otherwise>
						</c:choose>

					</div>
				</div>
			</div>

			<div class="detail-section-card">
				<h2>紹介文</h2>
				<p class="comment-text">
					<c:out value="${petDetail.commentText}" />
				</p>
			</div>

			<div class="detail-section-card">
				<h2>施設情報</h2>

				<div class="facility-detail-list">
					<p>
						<span>施設名</span>
						<strong><c:out value="${petDetail.facilityName}" /></strong>
					</p>

					<p>
						<span>住所</span>
						<strong><c:out value="${petDetail.address}" /></strong>
					</p>

					<p>
						<span>電話番号</span>
						<strong><c:out value="${petDetail.tel}" /></strong>
					</p>
				</div>
			</div>

			<div class="back-link-area">
				<a href="HomeServlet" class="back-link">ホームに戻る</a>
			</div>

		</section>

	</main>

</body>
</html>