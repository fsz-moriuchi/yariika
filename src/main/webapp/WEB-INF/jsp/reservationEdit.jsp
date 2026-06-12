<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約日時変更画面</title>
</head>

<body>

	<h1>予約日時の変更</h1>

	<c:if test="${not empty errorMsg}">
		<p>${errorMsg}</p>
	</c:if>

	<p>現在の予約日時：${reserveTime}</p>

	<h2>変更する日付を選択してください</h2>

	<form action="ReservationEditServlet" method="post">

		<input type="hidden" name="reservationID" value="${reservationID}">

		<input type="hidden" name="currentReserveTime" value="${reserveTime}">

		<input type="date" name="reserveDateStr" value="${reserveDate}" required>

		<button type="submit" name="action" value="showTimes">
			空き時間を表示</button>

	</form>

	<c:if test="${not empty timeList}">

		<h2>変更する時間を選択してください</h2>

		<form action="ReservationEditServlet" method="post">

			<input type="hidden" name="reservationID" value="${reservationID}">

			<input type="hidden" name="reserveDateStr" value="${reserveDate}">

			<c:forEach var="time" items="${timeList}">
				<label> <input type="radio" name="reserveTimeStr"
					value="${time}" required> ${time}
				</label>
				<br>
			</c:forEach>

			<button type="submit" name="action" value="update">変更を確定</button>

		</form>

	</c:if>
	<form action="ReservationConfirmServlet" method="get">
		<button type="submit">変更をキャンセルして戻る</button>
	</form>

</body>
</html>