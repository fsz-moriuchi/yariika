<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>アンケート修正</title>

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

			<h1>アンケート修正</h1>

			<p class="complete-lead">
				アンケート内容の修正画面です。<br>
				現在はマイページへ戻ることができます。
			</p>

			<div class="complete-action-area">
				<a href="MyPageServlet" class="main-button">マイページへ戻る</a>
			</div>

		</div>

	</section>

</main>
```

</body>
</html>
