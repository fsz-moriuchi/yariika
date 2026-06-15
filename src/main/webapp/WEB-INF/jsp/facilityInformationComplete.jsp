<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>店舗情報登録完了</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
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

	<section class="complete-section">

		<div class="complete-card">

			<div class="complete-icon">✓</div>

			<h1>店舗情報登録完了</h1>

			<p class="complete-lead">
				店舗情報の登録が完了しました。<br>
				店舗管理ページから予約確認やペット情報の登録ができます。
			</p>

			<div class="complete-action-area">
				<a href="FacilityPageServlet" class="main-button">店舗管理ページへ</a>
				<a href="HomeServlet" class="back-link">ホームへ戻る</a>
			</div>

		</div>

	</section>

</main>
```

</body>
</html>
