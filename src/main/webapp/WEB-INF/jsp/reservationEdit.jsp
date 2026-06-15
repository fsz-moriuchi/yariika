<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約日時変更画面</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
<header class="site-header">
	<div class="header-inner">
		<a href="HomeServlet" class="logo">Pet Matching</a>

		<nav class="header-nav">
			<a href="HomeServlet">ホーム</a>
			<a href="FacilityPageServlet">店舗管理ページ</a>
			<a href="ReservationConfirmServlet">予約確認</a>
			<a href="LogoutServlet" class="logout-link">ログアウト</a>
		</nav>
	</div>
</header>

<main class="container">

	<section class="reservation-edit-section">

		<div class="section-heading">
			<h1>予約日時の変更</h1>
			<p>現在の予約日時を確認し、変更後の日付と時間を選択してください。</p>
		</div>

		<c:if test="${not empty errorMsg}">
			<div class="error-message-card">
				<c:out value="${errorMsg}" />
			</div>
		</c:if>

		<div class="reserve-guide-card">
			<h2>現在の予約日時</h2>
			<p class="current-reserve-time">
				<c:out value="${reserveTime}" />
			</p>
		</div>


		<div class="reserve-step-card">

			<div class="step-label">STEP 1</div>

			<h2>変更する日付を選択してください</h2>

			<form action="ReservationEditServlet" method="post" class="reserve-date-form">

				<input type="hidden" name="reservationID" value="${reservationID}">
				<input type="hidden" name="currentReserveTime" value="${reserveTime}">

				<input type="date" name="reserveDateStr" value="${reserveDate}"
					class="form-input short-input" required>

				<button type="submit" name="action" value="showTimes"
					class="main-button">
					空き時間を表示
				</button>

			</form>

		</div>


		<c:if test="${not empty timeList}">

			<div class="reserve-step-card">

				<div class="step-label">STEP 2</div>

				<h2>変更する時間を選択してください</h2>

				<form action="ReservationEditServlet" method="post">

					<input type="hidden" name="reservationID" value="${reservationID}">
					<input type="hidden" name="reserveDateStr" value="${reserveDate}">

					<div class="time-choice-grid">

						<c:forEach var="time" items="${timeList}">
							<label class="time-choice-item">
								<input type="radio" name="reserveTimeStr"
									value="${time}" required>
								<span>
									<c:out value="${time}" />
								</span>
							</label>
						</c:forEach>

					</div>

					<div class="reserve-submit-area">
						<button type="submit" name="action" value="update"
							class="main-button">
							変更を確定
						</button>
					</div>

				</form>

			</div>

		</c:if>


		<div class="form-action-card">
			<a href="ReservationConfirmServlet" class="back-link">
				変更をキャンセルして戻る
			</a>
		</div>

	</section>

</main>
```

</body>
</html>
