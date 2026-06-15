<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約前の注意</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
<header class="site-header">
	<div class="header-inner">
		<a href="HomeServlet" class="logo">Pet Matching</a>

		<nav class="header-nav">
			<a href="HomeServlet">ホーム</a>
			<c:if test="${not empty sessionScope.userId}">
				<a href="MyPageServlet">マイページ</a>
			</c:if>
			<a href="LogoutServlet" class="logout-link">ログアウト</a>
		</nav>
	</div>
</header>

<main class="container">

	<section class="warning-section">

		<div class="warning-card">

			<div class="warning-icon">!</div>

			<h1>予約前の注意事項</h1>

			<p class="warning-lead">
				予約へ進む前に、以下の内容を確認してください。
			</p>

			<div class="warning-list-card">
				<ul>
					<li>クイズに合格しないと予約できません。</li>
					<li>クイズ回答中や予約操作中に、他の方の予約が先に完了する場合があります。</li>
					<li>その場合、このペットの予約を確定できないことがあります。</li>
				</ul>
			</div>

			<div class="warning-action-area">

				<form action="QuizServlet" method="get" class="inline-form">
					<input type="submit" value="同意してクイズへ進む" class="main-button">
				</form>

				<a href="PetDetailServlet?petID=${petID}" class="back-link">ペット詳細に戻る</a>

			</div>

		</div>

	</section>

</main>
```

</body>
</html>
