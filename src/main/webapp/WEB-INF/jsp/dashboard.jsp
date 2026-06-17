<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pet Shop Dashboard</title>

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

.menu {
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
}

.menu a {
	text-decoration: none;
	background: #f1f5f9;
	color: #334155;
	padding: 8px 14px;
	border-radius: 10px;
	font-weight: 600;
	font-size: 14px;
	transition: 0.2s;
}

.menu a:hover {
	background: #3b82f6;
	color: white;
}

.logout {
	background: #fee2e2 !important;
	color: #991b1b !important;
}

.logout:hover {
	background: #ef4444 !important;
	color: white !important;
}

.unread {
	color: #ef4444;
	font-weight: 700;
}

/* ================= LAYOUT ================= */
.container {
	width: 95%;
	max-width: 1300px;
	margin: 20px auto;
}

/* ================= TOP STATS ================= */
.dashboard-top {
	display: grid;
	grid-template-columns: repeat(4, 1fr);
	gap: 16px;
	margin-bottom: 18px;
}

.mini-card {
	background: white;
	border-radius: 14px;
	padding: 18px;
	border: 1px solid #e8ecf3;
	transition: 0.2s;
}

.mini-card:hover {
	transform: translateY(-2px);
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.06);
}

.icon {
	font-size: 20px;
	margin-bottom: 6px;
}

.label {
	font-size: 13px;
	color: #64748b;
}

.number {
	font-size: 28px;
	font-weight: 700;
	color: #0f172a;
	margin-top: 4px;
}

/* ================= MAIN ================= */
.dashboard-main {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 16px;
}

/* ================= CARD ================= */
.card {
	background: white;
	border-radius: 14px;
	padding: 18px;
	border: 1px solid #e8ecf3;
}

.card:hover {
	box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
}

.title {
	font-size: 16px;
	font-weight: 700;
	color: #0f172a;
	margin-bottom: 12px;
}

/* ================= PET LAYOUT (重要修正) ================= */
.pet-box {
	display: flex;
	gap: 16px;
	align-items: center;
	justify-content: center;
	margin-top: 10px;
}

.pet-image {
	width: 140px;
	height: 140px;
	object-fit: cover;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
}

.pet-info {
	text-align: left;
	font-size: 14px;
	color: #475569;
	line-height: 1.8;
}

/* ================= RESPONSIVE ================= */
@media ( max-width : 900px) {
	.dashboard-top {
		grid-template-columns: repeat(2, 1fr);
	}
	.dashboard-main {
		grid-template-columns: 1fr;
	}
	.pet-box {
		flex-direction: column;
		text-align: center;
	}
	.pet-info {
		text-align: center;
	}
}
</style>

</head>

<body>

	<c:if test="${not empty sessionScope.facilityId}">

		<!-- ================= HEADER ================= -->
		<header class="header">

			<div class="logo">🐾 Pet Shop Dashboard</div>

			<nav class="menu">

				<a href="FacilityPageServlet">🏠 施設</a> <a href="MessageListServlet">
					✉ メッセージ <span class="unread">(${facilityUnreadCount})</span>
				</a> <a href="LogoutServlet" class="logout">🚪 ログアウト</a>

			</nav>

		</header>

		<div class="container">

			<!-- ================= TOP STATS ================= -->
			<div class="dashboard-top">

				<div class="mini-card">
					<div class="icon">📅</div>
					<div class="label">本日の予約</div>
					<div class="number">${countTodayReserve}</div>
				</div>

				<div class="mini-card">
					<div class="icon">🐾</div>
					<div class="label">登録ペット</div>
					<div class="number">${petCount}</div>
				</div>

				<div class="mini-card">
					<div class="icon">✉</div>
					<div class="label">未読メッセージ</div>
					<div class="number">${facilityUnreadCount}</div>
				</div>

				<div class="mini-card">
					<div class="icon">👀</div>
					<div class="label">アクセス数</div>
					<div class="number">${viewCount}</div>
				</div>
			</div>

			<!-- ================= MAIN ================= -->
			<div class="dashboard-main">
				<!-- 次の予約 -->
				<div class="card">
					<div class="title">🐶 次の予約</div>
					<c:if test="${not empty reserve}">
                予約ID：${reserve.reservationID}<br>
                ペットID：${reserve.petID}<br>
                ユーザーID：${reserve.userID}<br>
                予約日時：${reserve.formattedReserveTime}
            </c:if>

				</div>

				<!-- 最近追加したペット -->
				<div class="card">
					<div class="title">🌟 最近追加したペット</div>
					<c:if test="${not empty latestPet}">
						<div class="pet-box">
							<img src="${latestPet.imagePath}" class="pet-image">

							<div class="pet-info">

								名前：
								<c:out value="${latestPet.name}" />
								<br> 性別：
								<c:choose>
									<c:when test="${latestPet.gender == 'male'}">オス</c:when>
									<c:when test="${latestPet.gender == 'female'}">メス</c:when>
									<c:otherwise>${latestPet.gender}</c:otherwise>
								</c:choose>
								<br> 年齢：
								<c:out value="${latestPet.age}" />
								歳<br> 価格：
								<c:out value="${latestPet.price}" />
								円
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</c:if>

</body>
</html>