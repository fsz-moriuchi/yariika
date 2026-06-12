<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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

	<form action="PasswordEditServlet" method="get">
		<button type="submit">パスワード変更</button>
	</form>

	<c:choose>
		<c:when test="${empty userSurveyList}">
			<form action="UserSuveyServlet" method="get">
				<button type="submit">アンケート回答</button>
			</form>
		</c:when>

		<c:otherwise>
			<form action="SurveyConfirmServlet" method="get">
				<button type="submit">アンケート確認</button>
			</form>
		</c:otherwise>
	</c:choose>

	<form action="ReserveCheckServlet" method="get">
		<button type="submit">予約確認</button>
	</form>


	<form action="FavoriteListServlet" method="get">
		<button type="submit">お気に入り一覧へ</button>
	</form>

	<form action="HomeServlet" method="get">
		<button type="submit">戻る</button>
	</form>

</body>
</html>