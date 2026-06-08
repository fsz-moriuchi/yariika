<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="FacilityInformationEditServlet" method="post">
		施設名 <input type="text" name="facilityName" value="${facilityInfo.facilityName}"><br> 
		電話番号 <input type="text" name="tel" value="${facilityInfo.tel}"><br>
		住所 <input type="text" name="address" value="${facilityInfo.address}"><br>
		メール <input type="text" name="mail" value="${facilityInfo.mail}"><br>
		開店時間 <input type="time" name="openTime"value="${fn:substring(facilityInfo.openTime,0,5)}"><br>
		閉店時間 <input type="time" name="closeTime" value="${fn:substring(facilityInfo.closeTime,0,5)}"><br>
		定休日 <input type="text" name="closedDay" value="${facilityInfo.closedDay}"><br>
		<input type="submit" value="更新">
	</form>
</body>
</html>