<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ユーザー情報更新完了</title>

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

			<h1>ユーザー情報更新完了</h1>

			<p class="complete-lead">
				ユーザー情報の更新が完了しました。<br>
				2秒後にマイページへ移動します。
			</p>

			<div class="complete-action-area">
				<a href="MyPageServlet" class="main-button">マイページへ戻る</a>
				<a href="UserInfoServlet" class="back-link">個人情報確認へ戻る</a>
			</div>

		</div>

	</section>

</main>
```

</body>
</html>
