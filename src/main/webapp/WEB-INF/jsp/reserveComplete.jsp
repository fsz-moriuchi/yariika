<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約完了画面</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
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

	<section class="complete-section">

		<div class="complete-card">

			<div class="complete-icon">✓</div>

			<h1>ご予約を承りました</h1>

			<p class="complete-lead">
				ご予約ありがとうございます。以下の内容で予約を受け付けました。
			</p>

			<div class="complete-info-area">

				<div class="complete-info-block">
					<h2>予約情報</h2>

					<div class="complete-info-row">
						<span>日付</span>
						<strong><c:out value="${reserveDate}" /></strong>
					</div>

					<div class="complete-info-row">
						<span>時間</span>
						<strong><c:out value="${reserveTime}" /></strong>
					</div>
				</div>

				<div class="complete-info-block">
					<h2>施設情報</h2>

					<div class="complete-info-row">
						<span>施設名</span>
						<strong><c:out value="${facilityInformation.facilityName}" /></strong>
					</div>

					<div class="complete-info-row">
						<span>住所</span>
						<strong><c:out value="${facilityInformation.address}" /></strong>
					</div>

					<div class="complete-info-row">
						<span>電話番号</span>
						<strong><c:out value="${facilityInformation.tel}" /></strong>
					</div>
				</div>

			</div>

			<div class="notice-card complete-notice">
				<h2>ご来店時のお願い</h2>
				<p>
					予約日時に遅れる場合やキャンセルされる場合は、直接店舗へお問い合わせください。
				</p>
			</div>

			<div class="complete-action-area">
				<a href="HomeServlet" class="main-button">ホームに戻る</a>
				<a href="ReserveCheckServlet" class="back-link">予約内容を確認する</a>
			</div>

		</div>

	</section>

</main>
```

</body>
</html>
