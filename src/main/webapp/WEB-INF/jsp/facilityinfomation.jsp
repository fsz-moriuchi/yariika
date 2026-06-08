<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>店舗情報登録ページ</title>
</head>
<body>
	<h2>店舗情報登録</h2>
	<c:if test="${not empty errorMsg}">
		<p style="color: red;">
			<c:out value="${errorMsg}" />
		</p>
	</c:if>
	<form action="FacilityInfomationServlet" method="post">
		1.店舗名<br> <input type="text" name="facilityName" required><br>
		<br> 2.電話番号<br> <input type="text" name="tel"><br>
		<br> 3.住所<br> <input type="text" name="address"><br>
		<br> 4.メールアドレス<br> <input type="email" name="mail"><br>
		<br> 5.開店時間<br> <input type="time" name="openTime" required><br>
		<br> 6.閉店時間<br> <input type="time" name="closeTime" required><br>
		<br> 7.定休日<br> <input type="text" name="closedDay"><br>
		<br> <input type="submit" value="登録">
	</form>

</body>
</html>