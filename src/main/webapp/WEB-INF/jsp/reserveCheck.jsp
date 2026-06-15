<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約確認画面</title>

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

		<section class="reserve-check-section">

			<div class="section-heading">
				<h1>予約確認</h1>
				<p>現在の予約内容を確認できます。</p>
			</div>

			<c:choose>

				<c:when test="${not empty reserve}">

					<div class="reserve-card">

						<div class="reserve-card-header">
							<span class="reserve-label">予約中</span>
							<h2>予約番号：${reserve.reservationID}</h2>
						</div>

						<div class="reserve-info-grid">

							<div class="reserve-info-item">
								<span>ペットID</span>
								<strong>${reserve.petID}</strong>
							</div>

							<div class="reserve-info-item">
								<span>ユーザーID</span>
								<strong>${reserve.userID}</strong>
							</div>

							<div class="reserve-info-item wide-reserve-info">
								<span>予約日時</span>
								<strong>${reserve.formattedReserveTime}</strong>
							</div>

						</div>

					</div>

				</c:when>

				<c:otherwise>
					<div class="reserve-card empty-reserve-card">
						<p>現在、予約情報はありません。</p>
					</div>
				</c:otherwise>

			</c:choose>

			<div class="notice-card">
				<h2>予約についてのご注意</h2>
				<p>
					予約日時の変更・キャンセルについては、直接店舗へお問い合わせください。<br>
					無断キャンセルや遅刻など、他のお客様や店舗の運営に支障をきたす行為はおやめください。
				</p>
			</div>

			<div class="back-link-area">
				<form action="MyPageServlet" method="get">
					<button type="submit" class="back-button">マイページに戻る</button>
				</form>
			</div>

		</section>

	</main>

</body>
</html>