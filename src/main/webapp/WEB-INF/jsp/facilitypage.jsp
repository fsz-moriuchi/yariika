<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>店舗ページ</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header class="site-header">
		<div class="header-inner">
			<a href="HomeServlet" class="logo">Pet Matching</a>

			<nav class="header-nav">
				<a href="HomeServlet">ホーム</a>
				<a href="LogoutServlet" class="logout-link">ログアウト</a>
			</nav>
		</div>
	</header>

	<main class="container">

		<section class="facility-menu-section">

			<div class="section-heading">
				<h1>店舗管理ページ</h1>
				<p>施設情報や予約、登録ペットの管理ができます。</p>
			</div>

			<div class="facility-menu-card">

				<div class="facility-menu-grid">

					<c:if test="${!registered}">
						<a href="FacilityInfomationServlet" class="facility-menu-button important-menu">
							<span class="menu-title">施設情報の入力</span>
							<span class="menu-text">まずは店舗名・住所・営業時間などを登録します</span>
						</a>
					</c:if>

					<a href="FacilityInfomationConfirmServlet" class="facility-menu-button">
						<span class="menu-title">施設情報の確認・変更</span>
						<span class="menu-text">登録済みの施設情報を確認・編集します</span>
					</a>

					<a href="ReservationConfirmServlet" class="facility-menu-button">
						<span class="menu-title">予約確認</span>
						<span class="menu-text">ユーザーから入った予約を確認します</span>
					</a>

					<a href="StoreServlet" class="facility-menu-button">
						<span class="menu-title">ペット一覧</span>
						<span class="menu-text">登録しているペットの確認・管理をします</span>
					</a>

					<a href="PasswordEditServlet" class="facility-menu-button">
						<span class="menu-title">パスワード変更</span>
						<span class="menu-text">ログイン用パスワードを変更します</span>
					</a>

				</div>

				<div class="facility-back-area">
					<a href="HomeServlet" class="back-link">ホームに戻る</a>
				</div>

			</div>

		</section>

	</main>

</body>
</html>