<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>施設情報確認</title>
</head>
<body>
	<h1>施設情報確認</h1>
	<table border="1">
		<tr>
			<th>施設名</th>
			<td>${facilityInfo.facilityName}</td>
		</tr>
		<tr>
			<th>電話番号</th>
			<td>${facilityInfo.tel}</td>
		</tr>
		<tr>
			<th>住所</th>
			<td>${facilityInfo.address}</td>
		</tr>
		<tr>
			<th>メールアドレス</th>
			<td>${facilityInfo.mail}</td>
		</tr>
		<tr>
			<th>開店時間</th>
			<td>${fn:substring(facilityInfo.openTime, 0, 5)}</td>
		</tr>
		<tr>
			<th>閉店時間</th>
			<td>${fn:substring(facilityInfo.closeTime, 0, 5)}</td>
		</tr>
		<tr>
			<th>定休日</th>
			<td><c:choose>
					<c:when test="${not empty facilityClosedDayList}">
						<c:forEach var="closedDay" items="${facilityClosedDayList}" varStatus="status">
			<c:choose>
						<c:when test="${closedDay == 'MONDAY'}">月曜日</c:when>
						<c:when test="${closedDay == 'TUESDAY'}">火曜日</c:when>
						<c:when test="${closedDay == 'WEDNESDAY'}">水曜日</c:when>
						<c:when test="${closedDay == 'THURSDAY'}">木曜日</c:when>
						<c:when test="${closedDay == 'FRIDAY'}">金曜日</c:when>
						<c:when test="${closedDay == 'SATURDAY'}">土曜日</c:when>
						<c:when test="${closedDay == 'SUNDAY'}">日曜日</c:when>
					</c:choose>
					<c:if test="${not status.last}">、</c:if>
				</c:forEach></c:when>
					<c:otherwise>
				定休日なし
			</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
	<br>
	<form action="FacilityInformationEditServlet" method="get">
		<input type="submit" value="施設情報を変更">
	</form>
	<br>
	<form action="FacilityPageServlet" method="get">
		<input type="submit" value="戻る">
	</form>
</body>
</html>