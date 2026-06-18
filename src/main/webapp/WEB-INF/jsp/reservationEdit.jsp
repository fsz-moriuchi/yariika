<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約日時変更画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="reservation-edit-card">

<h1>予約日時の変更・削除</h1>



<c:if test="${not empty errorMsg}">
	<p class="error-message">${errorMsg}</p>
</c:if>

<div class="current-reserve-box">
	<p class="current-reserve-title">現在の予約日時</p>
	<p class="current-reserve-time">${fn:replace(reserveTime, 'T', ' ')}</p>
</div>

<div class="reservation-edit-section">

<h2>変更する日付を選択してください</h2>

<form action="ReservationEditServlet" method="post" class="reservation-date-form">

	<input type="hidden" name="reservationID" value="${reservationID}">

	<input type="hidden" name="currentReserveTime" value="${reserveTime}">

	<div class="reservation-date-row">
		<label>変更日付</label>
		<input type="date" name="reserveDateStr" value="${reserveDate}" required>
	</div>

	<div class="form-button-area center-button-area">
		<button type="submit" name="action" value="showTimes">
			空き時間を表示</button>
	</div>

</form>

</div>

<c:if test="${not empty timeList}">

	<div class="reservation-edit-section">

	<h2>変更する時間を選択してください</h2>

	<form action="ReservationEditServlet" method="post" class="reservation-time-form">

		<input type="hidden" name="reservationID" value="${reservationID}">

		<input type="hidden" name="reserveDateStr" value="${reserveDate}">

		<div class="reservation-time-list">

		<c:forEach var="time" items="${timeList}">
			<label class="reservation-time-item"> <input type="radio" name="reserveTimeStr"
				value="${time}" required> ${time}
			</label>
			<br>
		</c:forEach>

		</div>

		<div class="form-button-area center-button-area">
			<button type="submit" name="action" value="update">変更を確定</button>
		</div>

	</form>

	</div>

</c:if>

<div class="reservation-edit-bottom">

<form action="ReservationConfirmServlet" method="get" class="reservation-cancel-form">
	<button type="submit">変更をキャンセルして戻る</button>
</form>

<form action="ReservationDeleteServlet" method="post" class="reservation-delete-form">
<input type="hidden" name="reservationID" value="${reservationID}">
	<input type="submit" name="action" value="この予約を削除する" onclick="return confirm('この予約情報（reservationID：${reservationID}）を削除してもよろしいですか？');">
	</form>

</div>

</div>

</div>

</body>
</html>
