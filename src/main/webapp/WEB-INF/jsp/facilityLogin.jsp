<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>店舗ログイン画面</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
<main class="login-page">

	<div class="login-card">

		<div class="login-heading">
			<a href="WelcomeServlet" class="logo">Pet Matching</a>
			<h1>店舗ログイン</h1>
			<p>店舗IDとパスワードを入力してください。</p>
		</div>

		<c:if test="${not empty errorMsg}">
			<div class="error-message-card">
				<c:out value="${errorMsg}" />
			</div>
		</c:if>

		<form action="FacilityLoginServlet" method="post" class="login-form">

			<div class="login-form-row">
				<label for="facilityId">店舗ID</label>
				<input type="text" id="facilityId" name="facilityId"
					class="form-input" autocomplete="off" required>
			</div>

			<div class="login-form-row">
				<label for="password">パスワード</label>
				<input type="password" id="password" name="password"
					class="form-input" required>
			</div>

			<div class="login-action-area">
				<input type="submit" value="ログイン" class="main-button">
				<a href="WelcomeServlet" class="back-link">戻る</a>
			</div>

		</form>

	</div>

</main>
```

</body>
</html>
