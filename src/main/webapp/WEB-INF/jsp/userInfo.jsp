<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>個人情報の確認</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>


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

	<section class="user-info-section">

		<div class="section-heading">
			<h1>個人情報の確認</h1>
			<p>登録しているユーザー情報を確認できます。</p>
		</div>

		<div class="user-info-card">

			<h2>ユーザー情報</h2>

			<div class="user-info-list">

				<div class="user-info-row">
					<span>ID</span>
					<strong><c:out value="${userInfo.userId}" /></strong>
				</div>

				<div class="user-info-row">
					<span>名前</span>
					<strong><c:out value="${userInfo.userName}" /></strong>
				</div>

				<div class="user-info-row">
					<span>性別</span>
					<strong><c:out value="${userInfo.userGender}" /></strong>
				</div>

				<div class="user-info-row">
					<span>生年月日</span>
					<strong><c:out value="${userInfo.userBirthday}" /></strong>
				</div>

				<div class="user-info-row">
					<span>電話番号</span>
					<strong><c:out value="${userInfo.userTel}" /></strong>
				</div>

				<div class="user-info-row">
					<span>メール</span>
					<strong><c:out value="${userInfo.userMail}" /></strong>
				</div>

				<div class="user-info-row">
					<span>住所</span>
					<strong><c:out value="${userInfo.userAddress}" /></strong>
				</div>

			</div>

			<div class="user-info-actions">

				<form action="UserEditServlet" method="get">
					<button type="submit" class="main-button">修正</button>
				</form>

				<a href="MyPageServlet" class="back-link">マイページに戻る</a>

			</div>

		</div>

	</section>

</main>


</body>
</html>
