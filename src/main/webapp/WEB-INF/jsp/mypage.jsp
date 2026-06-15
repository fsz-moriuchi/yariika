<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ</title>

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

		<section class="mypage-section">

			<div class="section-heading">
				<h1>マイページ</h1>
				<p>登録情報や予約、お気に入りを確認できます。</p>
			</div>

			<div class="mypage-card">

				<div class="mypage-menu-grid">

					<form action="UserInfoServlet" method="get" class="mypage-menu-form">
						<button type="submit" class="mypage-menu-button">
							<span class="menu-title">個人情報確認</span>
							<span class="menu-text">登録している名前や連絡先を確認します</span>
						</button>
					</form>

					<form action="PasswordEditServlet" method="get" class="mypage-menu-form">
						<button type="submit" class="mypage-menu-button">
							<span class="menu-title">パスワード変更</span>
							<span class="menu-text">ログイン用パスワードを変更します</span>
						</button>
					</form>

					<c:choose>
						<c:when test="${empty userSurveyList}">
							<form action="UserSuveyServlet" method="get" class="mypage-menu-form">
								<button type="submit" class="mypage-menu-button">
									<span class="menu-title">アンケート回答</span>
									<span class="menu-text">おすすめ表示に使うアンケートに回答します</span>
								</button>
							</form>
						</c:when>

						<c:otherwise>
							<form action="SurveyConfirmServlet" method="get" class="mypage-menu-form">
								<button type="submit" class="mypage-menu-button">
									<span class="menu-title">アンケート確認</span>
									<span class="menu-text">回答済みアンケートの内容を確認します</span>
								</button>
							</form>
						</c:otherwise>
					</c:choose>

					<form action="ReserveCheckServlet" method="get" class="mypage-menu-form">
						<button type="submit" class="mypage-menu-button">
							<span class="menu-title">予約確認</span>
							<span class="menu-text">現在の予約状況を確認します</span>
						</button>
					</form>

					<form action="FavoriteListServlet" method="get" class="mypage-menu-form">
						<button type="submit" class="mypage-menu-button">
							<span class="menu-title">お気に入り一覧</span>
							<span class="menu-text">お気に入り登録したペットを確認します</span>
						</button>
					</form>

				</div>

				<div class="mypage-back-area">
					<form action="HomeServlet" method="get">
						<button type="submit" class="back-button">ホームに戻る</button>
					</form>
				</div>

			</div>

		</section>

	</main>

</body>
</html>