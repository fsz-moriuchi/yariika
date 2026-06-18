<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>

<meta charset="UTF-8">
<title>店舗ページ</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<div class="page-container">


<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="facility-page-card">

	<h1>店舗ページ</h1>

	<p class="welcome-message">
		施設情報・予約・ペット情報・メッセージを管理できます。
	</p>

	<div class="facility-menu-grid">

		<a class="facility-menu-card" href="FacilityInfomationConfirmServlet">
			<span class="facility-menu-icon">🏠</span>
			<span class="facility-menu-title">施設情報の確認・変更へ</span>
			<span class="facility-menu-text">店舗情報を確認・編集できます</span>
		</a>
		<br>

		<a class="facility-menu-card" href="PasswordEditServlet">
			<span class="facility-menu-icon">🔑</span>
			<span class="facility-menu-title">パスワード変更</span>
			<span class="facility-menu-text">ログイン用パスワードを変更できます</span>
		</a>
		<br>

		<a class="facility-menu-card" href="ReservationConfirmServlet">
			<span class="facility-menu-icon">📅</span>
			<span class="facility-menu-title">予約確認へ</span>
			<span class="facility-menu-text">来店予約の内容を確認できます</span>
		</a>
		<br>

		<a class="facility-menu-card" href="StoreServlet">
			<span class="facility-menu-icon">🐾</span>
			<span class="facility-menu-title">ペット一覧へ</span>
			<span class="facility-menu-text">登録済みペットを管理できます</span>
		</a>
		<br>

		<a class="facility-menu-card" href="MessageListServlet">
			<span class="facility-menu-icon">💬</span>
			<span class="facility-menu-title">メッセージ一覧へ</span>
			<span class="facility-menu-text">ユーザーとのメッセージを確認できます</span>
		</a>
		<br>

	</div>

	<div class="form-button-area center-button-area">
		<a class="clear-button" href="DashboardServlet">もどる</a>
		<br>
	</div>

</div>


</div>

</body>
</html>
