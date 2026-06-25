<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>施設情報編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<div class="page-container">

<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="facility-edit-card">

	<h1>施設情報編集</h1>

	<p class="welcome-message">
		施設名・営業時間・定休日などを変更できます。
	</p>

<form action="FacilityInformationEditServlet" method="post" class="facility-edit-form">

	<div class="facility-form-grid">

		<div class="facility-form-row">
			<label>施設名</label>
			<input type="text" name="facilityName"
		value="${facilityInfo.facilityName}">
		</div>

		<div class="facility-form-row">
			<label>電話番号</label>
			<input
		type="text" name="tel" value="${facilityInfo.tel}">
		</div>

		<div class="facility-form-row facility-form-full">
			<label>住所</label>
			<input type="text" name="address" value="${facilityInfo.address}">
		</div>

		<div class="facility-form-row facility-form-full">
			<label>メール</label>
			<input type="text" name="mail" value="${facilityInfo.mail}">
		</div>

		<div class="facility-form-row">
			<label>開店時間</label>
			<input type="time" name="openTime"
		value="${fn:substring(facilityInfo.openTime,0,5)}">
		</div>

		<div class="facility-form-row">
			<label>閉店時間</label>
			<input type="time" name="closeTime"
		value="${fn:substring(facilityInfo.closeTime,0,5)}">
		</div>

	</div>

	<div class="closed-day-box">

		<p class="closed-day-title">定休日</p>

		<div class="closed-day-list">

			<label class="closed-day-item">
				<input type="checkbox" name="closedDay" value="MONDAY"
		${facilityClosedDayList.contains('MONDAY') ? 'checked' : ''}>月曜日
			</label>

			<label class="closed-day-item">
				<input type="checkbox" name="closedDay" value="TUESDAY"
		${facilityClosedDayList.contains('TUESDAY') ? 'checked' : ''}>火曜日
			</label>

			<label class="closed-day-item">
				<input type="checkbox" name="closedDay" value="WEDNESDAY"
		${facilityClosedDayList.contains('WEDNESDAY') ? 'checked' : ''}>水曜日
			</label>

			<label class="closed-day-item">
				<input type="checkbox" name="closedDay" value="THURSDAY"
		${facilityClosedDayList.contains('THURSDAY') ? 'checked' : ''}>木曜日
			</label>

			<label class="closed-day-item">
				<input type="checkbox" name="closedDay" value="FRIDAY"
		${facilityClosedDayList.contains('FRIDAY') ? 'checked' : ''}>金曜日
			</label>

			<label class="closed-day-item">
				<input type="checkbox" name="closedDay" value="SATURDAY"
		${facilityClosedDayList.contains('SATURDAY') ? 'checked' : ''}>土曜日
			</label>

			<label class="closed-day-item">
				<input type="checkbox" name="closedDay" value="SUNDAY"
		${facilityClosedDayList.contains('SUNDAY') ? 'checked' : ''}>日曜日
			</label>

		</div>

	</div>

	<div class="form-button-area center-button-area">
		<input type="submit" value="更新">
	</div>

</form>

<form action="FacilityInformationConfirmServlet" method="get" class="facility-back-form">
	<div class="form-button-area center-button-area">
		<input type="submit" value="戻る">
	</div>
</form>

</div>


</div>

</body>
</html>
