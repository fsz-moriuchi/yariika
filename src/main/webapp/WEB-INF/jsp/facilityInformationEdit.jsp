<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="FacilityInformationEditServlet" method="post">
		施設名 <input type="text" name="facilityName"
			value="${facilityInfo.facilityName}"><br> 電話番号 <input
			type="text" name="tel" value="${facilityInfo.tel}"><br>
		住所 <input type="text" name="address" value="${facilityInfo.address}"><br>
		メール <input type="text" name="mail" value="${facilityInfo.mail}"><br>
		開店時間 <input type="time" name="openTime"
			value="${fn:substring(facilityInfo.openTime,0,5)}"><br>
		閉店時間 <input type="time" name="closeTime"
			value="${fn:substring(facilityInfo.closeTime,0,5)}"><br>
		定休日 <input type="checkbox" name="closedDay" value="MONDAY"
			${facilityClosedDayList.contains('MONDAY') ? 'checked' : ''}>月曜日

		<input type="checkbox" name="closedDay" value="TUESDAY"
			${facilityClosedDayList.contains('TUESDAY') ? 'checked' : ''}>火曜日

		<input type="checkbox" name="closedDay" value="WEDNESDAY"
			${facilityClosedDayList.contains('WEDNESDAY') ? 'checked' : ''}>水曜日

		<input type="checkbox" name="closedDay" value="THURSDAY"
			${facilityClosedDayList.contains('THURSDAY') ? 'checked' : ''}>木曜日

		<input type="checkbox" name="closedDay" value="FRIDAY"
			${facilityClosedDayList.contains('FRIDAY') ? 'checked' : ''}>金曜日

		<input type="checkbox" name="closedDay" value="SATURDAY"
			${facilityClosedDayList.contains('SATURDAY') ? 'checked' : ''}>土曜日

		<input type="checkbox" name="closedDay" value="SUNDAY"
			${facilityClosedDayList.contains('SUNDAY') ? 'checked' : ''}>日曜日
		<br> <input type="submit" value="更新">
	</form>
	<form action="FacilityInfomationConfirmServlet" method="get">
		<input type="submit" value="戻る">
	</form>
</body>
</html>