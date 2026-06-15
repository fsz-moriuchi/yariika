<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Pet Matching</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
<main class="welcome-page">

	<div class="welcome-card">

		<div class="welcome-heading">
			<p class="welcome-label">Pet Matching Service</p>
			<h1>Pet Matching</h1>
			<p>
				あなたに合ったペットとの出会いをサポートします。
			</p>
		</div>

		<div class="welcome-menu-grid">

			<a href="UserLoginServlet" class="welcome-menu-card">
				<span class="welcome-menu-title">ユーザーログイン</span>
				<span class="welcome-menu-text">
					登録済みの方はこちらからログインできます。
				</span>
			</a>

			<a href="UserRegisterServlet" class="welcome-menu-card">
				<span class="welcome-menu-title">新規ユーザー登録</span>
				<span class="welcome-menu-text">
					初めて利用する方はこちらから登録してください。
				</span>
			</a>

			<a href="FacilityLoginServlet" class="welcome-menu-card facility-card">
				<span class="welcome-menu-title">店舗ログイン</span>
				<span class="welcome-menu-text">
					店舗・施設の管理画面へログインできます。
				</span>
			</a>

			<a href="FacilityRegisterServlet" class="welcome-menu-card facility-card">
				<span class="welcome-menu-title">新規店舗登録</span>
				<span class="welcome-menu-text">
					新しく店舗情報を登録する方はこちらです。
				</span>
			</a>

		</div>

	</div>

</main>
```

</body>
</html>
