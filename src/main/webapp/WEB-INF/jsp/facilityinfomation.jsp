<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>店舗情報登録ページ</title>

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
			<a href="LogoutServlet" class="logout-link">ログアウト</a>
		</nav>
	</div>
</header>

<main class="container">

	<section class="facility-edit-section">

		<div class="section-heading">
			<h1>店舗情報登録</h1>
			<p>店舗名・連絡先・営業時間・定休日を登録してください。</p>
		</div>

		<c:if test="${not empty errorMsg}">
			<div class="error-message-card">
				<c:out value="${errorMsg}" />
			</div>
		</c:if>

		<form action="FacilityInfomationServlet" method="post"
			class="facility-edit-form">

			<div class="form-card">

				<h2>基本情報</h2>

				<div class="form-row">
					<label class="form-label" for="facilityName">店舗名</label>
					<input type="text" id="facilityName" name="facilityName"
						class="form-input" required autocomplete="off">
				</div>

				<div class="form-row">
					<label class="form-label" for="tel">電話番号</label>
					<input type="text" id="tel" name="tel"
						class="form-input" autocomplete="off">
				</div>

				<div class="form-row">
					<label class="form-label" for="address">住所</label>
					<input type="text" id="address" name="address"
						class="form-input" autocomplete="off">
				</div>

				<div class="form-row">
					<label class="form-label" for="mail">メールアドレス</label>
					<input type="email" id="mail" name="mail"
						class="form-input" autocomplete="off">
				</div>

			</div>

			<div class="form-card">

				<h2>営業時間</h2>

				<div class="form-row">
					<label class="form-label" for="openTime">開店時間</label>
					<input type="time" id="openTime" name="openTime"
						class="form-input short-input" required>
				</div>

				<div class="form-row">
					<label class="form-label" for="closeTime">閉店時間</label>
					<input type="time" id="closeTime" name="closeTime"
						class="form-input short-input" required>
				</div>

			</div>

			<div class="form-card">

				<h2>定休日</h2>

				<div class="form-row">
					<label class="form-label">定休日</label>

					<div class="weekday-checkbox-grid">

						<label class="form-choice">
							<input type="checkbox" name="closedDay" value="MONDAY">
							<span>月曜日</span>
						</label>

						<label class="form-choice">
							<input type="checkbox" name="closedDay" value="TUESDAY">
							<span>火曜日</span>
						</label>

						<label class="form-choice">
							<input type="checkbox" name="closedDay" value="WEDNESDAY">
							<span>水曜日</span>
						</label>

						<label class="form-choice">
							<input type="checkbox" name="closedDay" value="THURSDAY">
							<span>木曜日</span>
						</label>

						<label class="form-choice">
							<input type="checkbox" name="closedDay" value="FRIDAY">
							<span>金曜日</span>
						</label>

						<label class="form-choice">
							<input type="checkbox" name="closedDay" value="SATURDAY">
							<span>土曜日</span>
						</label>

						<label class="form-choice">
							<input type="checkbox" name="closedDay" value="SUNDAY">
							<span>日曜日</span>
						</label>

					</div>
				</div>

			</div>

			<div class="form-action-card">
				<input type="submit" value="登録" class="main-button">

				<a href="FacilityPageServlet" class="back-link">
					店舗管理ページに戻る
				</a>
			</div>

		</form>

	</section>

</main>
```

</body>
</html>
