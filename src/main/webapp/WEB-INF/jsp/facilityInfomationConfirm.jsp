<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>施設情報確認</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<div class="page-container">

<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="facility-info-card">

<h1>施設情報確認</h1>

<p class="welcome-message">
	登録されている施設情報を確認できます。
</p>

<div class="facility-info-table-wrap">

<table border="1" class="facility-info-table">
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

</div>

<br>

<div class="form-button-area center-button-area">
<form action="FacilityInformationEditServlet" method="get">
	<input type="submit" value="施設情報を変更">
</form>
<br>
<form action="FacilityPageServlet" method="get">
	<input type="submit" value="戻る">
</form>
</div>

</div>


</div>

</body>
</html>
