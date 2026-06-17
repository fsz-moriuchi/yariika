<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>店舗メニュー</title>

<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: "Segoe UI", "Yu Gothic", "Hiragino Kaku Gothic ProN",
		sans-serif;
	background: #f4f6fb;
	color: #2b2f38;
}

/* ================= HEADER ================= */
.header {
	background: #ffffff;
	padding: 14px 24px;
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-wrap: wrap;
	border-bottom: 1px solid #e8ecf3;
}

.logo {
	font-size: 20px;
	font-weight: 700;
	color: #3b82f6;
}

/* ================= CONTAINER ================= */
.container {
	width: 95%;
	max-width: 900px;
	margin: 30px auto;
}

/* ================= CARD ================= */
.card {
	background: white;
	border-radius: 14px;
	padding: 22px;
	border: 1px solid #e8ecf3;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.03);
}

.title {
	font-size: 18px;
	font-weight: 700;
	margin-bottom: 18px;
	color: #0f172a;
}

/* ================= MENU GRID ================= */
.menu-list {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 14px;
}

/* ================= MENU BUTTON ================= */
.menu-btn {
	display: flex;
	align-items: center;
	justify-content: center;
	text-decoration: none;
	background: #f1f5f9;
	color: #334155;
	padding: 16px;
	border-radius: 12px;
	font-weight: 600;
	font-size: 14px;
	border: 1px solid #e8ecf3;
	transition: all 0.2s ease;
}

.menu-btn:hover {
	background: #3b82f6;
	color: white;
	transform: translateY(-2px);
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.08);
}

/* ================= ICON ================= */
.icon {
	margin-right: 8px;
	font-size: 16px;
}

/* ================= RESPONSIVE ================= */
@media ( max-width : 700px) {
	.menu-list {
		grid-template-columns: 1fr;
	}
}
</style>

</head>

<body>

	<!-- ================= HEADER ================= -->
	<header class="header">
		<div class="logo">🐾 Pet Shop Seller Menu</div>
	</header>

	<!-- ================= MAIN ================= -->
	<div class="container">

		<div class="card">

			<div class="title">📋 店舗管理メニュー</div>

			<div class="menu-list">

				<a class="menu-btn" href="FacilityInfomationConfirmServlet"> <span
					class="icon">🏢</span>施設情報の確認・変更
				</a> <a class="menu-btn" href="PasswordEditServlet"> <span
					class="icon">🔑</span>パスワード変更
				</a> <a class="menu-btn" href="ReservationConfirmServlet"> <span
					class="icon">📅</span>予約確認
				</a> <a class="menu-btn" href="StoreServlet"> <span class="icon">🐶</span>ペット一覧
				</a> <a class="menu-btn" href="MessageListServlet"> <span
					class="icon">✉</span>メッセージ一覧
				</a> <a class="menu-btn" href="DashboardServlet"> <span class="icon">🏠</span>ダッシュボードへ戻る
				</a>

			</div>

		</div>

	</div>

</body>
</html>