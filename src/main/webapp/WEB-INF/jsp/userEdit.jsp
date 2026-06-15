<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>個人情報の修正</title>

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

	<section class="user-edit-section">

		<div class="section-heading">
			<h1>個人情報の修正</h1>
			<p>登録しているユーザー情報を変更できます。</p>
		</div>

		<form action="UserEditServlet" method="post" class="user-edit-form">

			<input type="hidden" name="userInfoId" value="${userInfo.userInfoId}">

			<div class="form-card">

				<h2>ユーザー情報</h2>

				<div class="form-row">
					<label class="form-label" for="userName">名前</label>
					<input type="text" id="userName" name="userName"
						value="${userInfo.userName}" class="form-input" required>
				</div>

				<div class="form-row">
					<label class="form-label" for="userGender">性別</label>

					<select id="userGender" name="userGender" class="form-input" required>
						<option value="男" ${userInfo.userGender == '男' ? 'selected' : ''}>男</option>
						<option value="女" ${userInfo.userGender == '女' ? 'selected' : ''}>女</option>
						<option value="選択しない" ${userInfo.userGender == '選択しない' ? 'selected' : ''}>選択しない</option>
					</select>
				</div>

				<div class="form-row">
					<label class="form-label" for="userBirthday">生年月日</label>
					<input type="date" id="userBirthday" name="userBirthday"
						value="${userInfo.userBirthday}" class="form-input short-input" required>
				</div>

				<div class="form-row">
					<label class="form-label" for="userTel">電話番号</label>
					<input type="text" id="userTel" name="userTel"
						value="${userInfo.userTel}" class="form-input" required>
				</div>

				<div class="form-row">
					<label class="form-label" for="userMail">メール</label>
					<input type="email" id="userMail" name="userMail"
						value="${userInfo.userMail}" class="form-input" required>
				</div>

				<div class="form-row">
					<label class="form-label" for="userAddress">住所</label>
					<input type="text" id="userAddress" name="userAddress"
						value="${userInfo.userAddress}" class="form-input" required>
				</div>

			</div>

			<div class="form-action-card">
				<button type="submit" class="main-button">更新</button>

				<a href="UserInfoServlet" class="back-link">個人情報確認に戻る</a>
			</div>

		</form>

	</section>

</main>
```

</body>
</html>
