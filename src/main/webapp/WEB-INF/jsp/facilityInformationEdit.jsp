```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>施設情報編集</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

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
				<h1>施設情報編集</h1>
				<p>店舗名・連絡先・営業時間・定休日を変更できます。</p>
			</div>

			<form action="FacilityInformationEditServlet" method="post"
				class="facility-edit-form">

				<div class="form-card">

					<h2>基本情報</h2>

					<div class="form-row">
						<label class="form-label" for="facilityName">施設名</label>
						<input type="text" id="facilityName" name="facilityName"
							value="${facilityInfo.facilityName}" class="form-input" required>
					</div>

					<div class="form-row">
						<label class="form-label" for="tel">電話番号</label>
						<input type="text" id="tel" name="tel"
							value="${facilityInfo.tel}" class="form-input" required>
					</div>

					<div class="form-row">
						<label class="form-label" for="address">住所</label>
						<input type="text" id="address" name="address"
							value="${facilityInfo.address}" class="form-input" required>
					</div>

					<div class="form-row">
						<label class="form-label" for="mail">メールアドレス</label>
						<input type="email" id="mail" name="mail"
							value="${facilityInfo.mail}" class="form-input" required>
					</div>

				</div>


				<div class="form-card">

					<h2>営業時間</h2>

					<div class="form-row">
						<label class="form-label" for="openTime">開店時間</label>
						<input type="time" id="openTime" name="openTime"
							value="${fn:substring(facilityInfo.openTime, 0, 5)}"
							class="form-input short-input" required>
					</div>

					<div class="form-row">
						<label class="form-label" for="closeTime">閉店時間</label>
						<input type="time" id="closeTime" name="closeTime"
							value="${fn:substring(facilityInfo.closeTime, 0, 5)}"
							class="form-input short-input" required>
					</div>

				</div>


				<div class="form-card">

					<h2>定休日</h2>

					<div class="form-row">
						<label class="form-label">定休日</label>

						<div class="weekday-checkbox-grid">

							<label class="form-choice">
								<input type="checkbox" name="closedDay" value="MONDAY"
									${fn:contains(facilityClosedDayList, 'MONDAY') ? 'checked' : ''}>
								<span>月曜日</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="closedDay" value="TUESDAY"
									${fn:contains(facilityClosedDayList, 'TUESDAY') ? 'checked' : ''}>
								<span>火曜日</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="closedDay" value="WEDNESDAY"
									${fn:contains(facilityClosedDayList, 'WEDNESDAY') ? 'checked' : ''}>
								<span>水曜日</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="closedDay" value="THURSDAY"
									${fn:contains(facilityClosedDayList, 'THURSDAY') ? 'checked' : ''}>
								<span>木曜日</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="closedDay" value="FRIDAY"
									${fn:contains(facilityClosedDayList, 'FRIDAY') ? 'checked' : ''}>
								<span>金曜日</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="closedDay" value="SATURDAY"
									${fn:contains(facilityClosedDayList, 'SATURDAY') ? 'checked' : ''}>
								<span>土曜日</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="closedDay" value="SUNDAY"
									${fn:contains(facilityClosedDayList, 'SUNDAY') ? 'checked' : ''}>
								<span>日曜日</span>
							</label>

						</div>
					</div>

				</div>


				<div class="form-action-card">
					<input type="submit" value="更新" class="main-button">

					<a href="FacilityInfomationConfirmServlet" class="back-link">
						施設情報確認に戻る
					</a>
				</div>

			</form>

		</section>

	</main>

</body>
</html>
```
