<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>クイズ結果</title>

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

	<section class="quiz-result-section">

		<div class="section-heading">
			<h1>クイズ結果</h1>
			<p>ペットを迎える前の基本知識クイズの結果です。</p>
		</div>

		<c:if test="${not empty percent}">
			<div class="quiz-score-card">

				<c:choose>
					<c:when test="${percent >= 70}">
						<span class="result-badge pass-badge">合格</span>
					</c:when>
					<c:otherwise>
						<span class="result-badge fail-badge">不合格</span>
					</c:otherwise>
				</c:choose>

				<h2>正解数：${count} / ${totalCount}</h2>

				<p class="score-percent">
					正答率：${percent}%
				</p>

				<c:choose>
					<c:when test="${percent >= 70}">
						<p class="score-message">
							基本知識の確認ができました。予約手続きへ進めます。
						</p>
					</c:when>
					<c:otherwise>
						<p class="score-message">
							もう一度内容を確認してから、再チャレンジしてください。
						</p>
					</c:otherwise>
				</c:choose>

			</div>
		</c:if>


		<div class="quiz-result-list">

			<c:forEach var="qr" items="${resultList}" varStatus="status">

				<div class="quiz-result-card">

					<div class="quiz-result-header">
						<h2>Q${status.count}</h2>

						<c:choose>
							<c:when test="${qr.correct}">
								<span class="answer-label correct-label">正解</span>
							</c:when>
							<c:otherwise>
								<span class="answer-label wrong-label">不正解</span>
							</c:otherwise>
						</c:choose>
					</div>

					<p class="quiz-question">
						<c:out value="${qr.question}" />
					</p>

					<div class="answer-compare-grid">

						<div class="answer-box">
							<span>あなたの回答</span>
							<strong>
								${qr.userAnswer}：
								<c:out value="${qr.userAnswerText}" />
							</strong>
						</div>

						<div class="answer-box">
							<span>正解</span>
							<strong>
								${qr.answer}：
								<c:out value="${qr.correctAnswerText}" />
							</strong>
						</div>

					</div>

				</div>

			</c:forEach>

		</div>


		<div class="quiz-final-card">

			<c:choose>

				<c:when test="${percent >= 70}">

					<h2>予約へ進めます</h2>

					<c:choose>
						<c:when test="${reserved}">
							<p class="reserved-message">
								このペットは現在予約済みです。
							</p>

							<a href="HomeServlet" class="back-link">ホームに戻る</a>
						</c:when>

						<c:otherwise>
							<p>
								クイズに合格しました。続けて見学予約に進んでください。
							</p>

							<a href="ReserveServlet" class="main-button">予約する</a>
						</c:otherwise>
					</c:choose>

				</c:when>

				<c:otherwise>

					<h2>今回は不合格です</h2>
					<p>
						ペットを迎える前に大切な内容です。もう一度確認してから予約へ進みましょう。
					</p>

					<a href="HomeServlet" class="back-link">ホームに戻る</a>

				</c:otherwise>

			</c:choose>

		</div>

	</section>

</main>
```

</body>
</html>
