<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規店舗登録画面</title>
</head>
<body>
<h1>新規店舗店舗登録画面</h1>

<p>全項目登録してください。</p>

<form action="FacilityRegisterServlet" method="post">
<p>店舗ID:<input type="text" name="facilityId" required></p>
<p>パスワード:<input type="password" name="password" required></p>

<!-- 新規店舗情報もここで登録 -->
1.店舗名<br> <input type="text" name="facilityName" required><br>
		<br> 2.電話番号<br> <input type="text" name="tel"><br>
		<br> 3.住所<br> <input type="text" name="address"><br>
		<br> 4.メールアドレス<br> <input type="email" name="mail"><br>
		<br> 5.開店時間<br> <input type="time" name="openTime" required><br>
		<br> 6.閉店時間<br> <input type="time" name="closeTime" required><br>
		<br> 7.定休日 
		<input type="checkbox" name="closedDay" value="MONDAY">月曜日
		<input type="checkbox" name="closedDay" value="TUESDAY">火曜日
		<input type="checkbox" name="closedDay" value="WEDNESDAY">水曜日
		<input type="checkbox" name="closedDay" value="THURSDAY">木曜日
		<input type="checkbox" name="closedDay" value="FRIDAY">金曜日
		<input type="checkbox" name="closedDay" value="SATURDAY">土曜日
		<input type="checkbox" name="closedDay" value="SUNDAY">日曜日
		<br><br>
		
<input type="submit" value="登録"><br>
</form>
<form action="WelcomeServlet" method="get">
		<input type="submit" value="戻る">
	</form>
	
<p>施設情報の変更の際は、施設専用ページから変更を行ってください。</p>
<c:if test="${not empty errorMsg}">
<c:out value="${errorMsg}"/>
</c:if>
</body>
</html>