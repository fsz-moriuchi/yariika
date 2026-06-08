<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

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
			<td>${facilityInfo.openTime}</td>
		</tr>
		<tr>
			<th>閉店時間</th>
			<td>${facilityInfo.closeTime}</td>
		</tr>
		<tr>
			<th>定休日</th>
			<td>${facilityInfo.closedDay}</td>
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