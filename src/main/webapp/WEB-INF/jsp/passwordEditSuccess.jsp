<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>パスワード変更完了</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<meta http-equiv="refresh" content="2;url=WelcomeServlet">
</head>

<body>

```
<main class="container">

	<section class="complete-section">

		<div class="complete-card">

			<div class="complete-icon">✓</div>

			<h1>パスワード変更完了</h1>

			<p class="complete-lead">
				パスワードの変更が完了しました。<br>
				安全のため、再度ログインしてください。
			</p>

			<div class="complete-action-area">
				<a href="WelcomeServlet" class="main-button">ログイン画面へ戻る</a>
			</div>

		</div>

	</section>

</main>
```

</body>
</html>
