<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>基本知識クイズ</title>

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

	<section class="quiz-section">

		<div class="section-heading">
			<h1>基本知識クイズ</h1>
			<p>ペットを迎える前に、基本的な知識を確認しましょう。</p>
		</div>

		<div class="quiz-guide-card">
			<h2>予約前の確認</h2>
			<p>
				ペットとの生活には、時間・費用・責任が必要です。<br>
				クイズに回答してから、予約手続きへ進んでください。
			</p>
		</div>

		<div class="survey-card">

			<form action="QuizAnswerServlet" method="post" class="quiz-form">

				<c:forEach var="q" items="${quizList}" varStatus="status">

					<div class="question-card">

						<h2 class="question-title">
							<span>Q${status.count}</span>
							<c:out value="${q.question}" />
						</h2>

						<div class="choice-list">

							<label class="choice-item">
								<input type="radio" name="q${q.quizId}" value="1" required>
								<span><c:out value="${q.choice1}" /></span>
							</label>

							<label class="choice-item">
								<input type="radio" name="q${q.quizId}" value="2" required>
								<span><c:out value="${q.choice2}" /></span>
							</label>

							<label class="choice-item">
								<input type="radio" name="q${q.quizId}" value="3" required>
								<span><c:out value="${q.choice3}" /></span>
							</label>

							<label class="choice-item">
								<input type="radio" name="q${q.quizId}" value="4" required>
								<span><c:out value="${q.choice4}" /></span>
							</label>

						</div>

					</div>

				</c:forEach>

				<div class="survey-submit-area">
					<input type="submit" value="回答する" class="main-button">
					<a href="HomeServlet" class="back-link">ホームに戻る</a>
				</div>

			</form>

		</div>

	</section>

</main>
```

</body>
</html>
