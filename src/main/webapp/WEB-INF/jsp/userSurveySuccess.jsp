<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ユーザーアンケート登録完了</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<meta http-equiv="refresh" content="2;url=MyPageServlet">
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

			<h1>アンケート登録完了</h1>

			<p class="complete-lead">
				ユーザーアンケートの登録が完了しました。<br>
				2秒後にマイページへ移動します。
			</p>

			<div class="complete-action-area">
				<a href="MyPageServlet" class="main-button">マイページへ戻る</a>
				<a href="HomeServlet" class="back-link">ホームへ戻る</a>
			</div>

		</div>

	</section>

</main>
```

</body>
</html>
