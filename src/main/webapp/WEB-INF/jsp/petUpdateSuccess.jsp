<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ペット情報更新完了</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<meta http-equiv="refresh" content="2;url=StoreServlet">
</head>

<body>

```
<header class="site-header">
	<div class="header-inner">
		<a href="HomeServlet" class="logo">Pet Matching</a>

		<nav class="header-nav">
			<a href="HomeServlet">ホーム</a>
			<a href="FacilityPageServlet">店舗管理ページ</a>
			<a href="StoreServlet">ペット一覧</a>
			<a href="LogoutServlet" class="logout-link">ログアウト</a>
		</nav>
	</div>
</header>

<main class="container">

	<section class="complete-section">

		<div class="complete-card">

			<div class="complete-icon">✓</div>

			<h1>ペット情報更新完了</h1>

			<p class="complete-lead">
				ペット情報の更新が完了しました。<br>
				2秒後にペット一覧へ移動します。
			</p>

			<div class="complete-action-area">
				<a href="StoreServlet" class="main-button">ペット一覧へ戻る</a>
				<a href="FacilityPageServlet" class="back-link">店舗管理ページへ戻る</a>
			</div>

		</div>

	</section>

</main>
```

</body>
</html>
