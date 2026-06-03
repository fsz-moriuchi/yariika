<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ</title>
</head>


<body>
<h1>マイページ</h1>

<form action="UserInfoServlet" method="get">
<button type="submit">個人情報確認</button>
</form>


<form action="SurveyServlet" method="get">
<button type="submit">アンケート回答</button>
</form>

<form action="SurveyConfirmServlet" method="get">
<button type="submit">アンケート確認</button>
</form>


<form action="ReservationConfirmServlet" method="get">
<button type="submit">予約確認</button>
</form>


<form action="" method="get">
<button type="submit">戻る</button>
</form>

</body>
</html>