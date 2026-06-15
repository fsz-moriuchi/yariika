<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>パスワード変更</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
<main class="login-page">

	<div class="login-card password-card">

		<div class="login-heading">
			<a href="HomeServlet" class="logo">Pet Matching</a>
			<h1>パスワード変更</h1>
			<p>現在のパスワードと新しいパスワードを入力してください。</p>
		</div>

		<c:if test="${not empty errorMsg}">
			<div class="error-message-card">
				<c:out value="${errorMsg}" />
			</div>
		</c:if>

		<form action="PasswordEditServlet" method="post" class="login-form">

			<div class="login-form-row">
				<label for="oldPassword">元パスワード</label>
				<input type="password" id="oldPassword" name="oldPassword"
					class="form-input" required>
			</div>

			<div class="login-form-row">
				<label for="newPassword">新しいパスワード</label>
				<input type="password" id="newPassword" name="newPassword"
					class="form-input" required>
			</div>

			<div class="login-form-row">
				<label for="newPasswordConfirm">もう一度入力</label>
				<input type="password" id="newPasswordConfirm"
					name="newPasswordConfirm" class="form-input" required>
			</div>

			<div class="password-notice">
				<p>
					パスワード変更後は、再度ログインが必要になります。
				</p>
			</div>

			<div class="login-action-area">
				<input type="submit" value="パスワードを変更" class="main-button">

				<c:choose>
					<c:when test="${not empty sessionScope.userId}">
						<a href="MyPageServlet" class="back-link">キャンセル</a>
					</c:when>

					<c:when test="${not empty sessionScope.facilityId}">
						<a href="FacilityPageServlet" class="back-link">キャンセル</a>
					</c:when>
				</c:choose>
			</div>

		</form>

	</div>

</main>
```

</body>
</html>
