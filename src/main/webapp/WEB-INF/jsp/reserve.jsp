<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約画面</title>

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

		<section class="reserve-section">

			<div class="section-heading">
				<h1>見学予約</h1>
				<p>ご希望の日付と時間を選択してください。</p>
			</div>

			<div class="reserve-guide-card">
				<h2>予約について</h2>
				<p>
					見学のご予約は、3日後から1週間以内の日程で受け付けております。<br>
					所要時間は30分程度です。前後のお時間に余裕をもってご予約ください。
				</p>
			</div>

			<div class="reserve-step-card">
				<div class="step-label">STEP 1</div>
				<h2>日付を選択</h2>

				<form action="ReserveServlet" method="post" class="reserve-date-form">
					<label for="reserveDateStr">日付を選択してください</label>

					<input type="date" id="reserveDateStr" name="reserveDateStr"
						value="${reserveDate}" min="${minDate}" max="${maxDate}" required>

					<input type="submit" value="この日付で検索する" class="main-button">
				</form>
			</div>

			<c:if test="${not empty errorMsg}">
				<div class="error-message-card">
					<c:out value="${errorMsg}" />
				</div>
			</c:if>

			<c:if test="${not empty timeList}">
				<div class="reserve-step-card">
					<div class="step-label">STEP 2</div>
					<h2>時間を選択</h2>

					<form action="ReserveCompleteServlet" method="post" class="reserve-time-form">

						<div class="time-choice-grid">
							<c:forEach var="time" items="${timeList}">
								<label class="time-choice-item">
									<input type="radio" name="reserveTime" value="${time}" required>
									<span><c:out value="${time}" /></span>
								</label>
							</c:forEach>
						</div>

						<div class="reserve-submit-area">
							<input type="submit" value="この日時で予約する" class="main-button">
						</div>

					</form>
				</div>
			</c:if>

			<div class="back-link-area">
				<a href="ReserveCancelServlet" class="back-link">キャンセルしてホームに戻る</a>
			</div>

		</section>

	</main>

</body>
</html>