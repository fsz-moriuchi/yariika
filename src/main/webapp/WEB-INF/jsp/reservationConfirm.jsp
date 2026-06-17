<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約確認</title>

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
	max-width: 1200px;
	margin: 30px auto;
}

/* ================= CARD ================= */
.card {
	background: white;
	border-radius: 14px;
	padding: 20px;
	border: 1px solid #e8ecf3;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.03);
}

.title {
	font-size: 18px;
	font-weight: 700;
	margin-bottom: 16px;
	color: #0f172a;
}

/* ================= FILTER ================= */
.filter-bar {
	display: flex;
	gap: 10px;
	flex-wrap: wrap;
	margin-bottom: 10px;
}

.filter-btn {
	background: #f1f5f9;
	border: 1px solid #e8ecf3;
	padding: 10px 14px;
	border-radius: 10px;
	font-weight: 600;
	cursor: pointer;
	transition: 0.2s;
}

.filter-btn:hover {
	background: #3b82f6;
	color: white;
}

.count {
	margin: 10px 0 20px;
	font-weight: 600;
	color: #64748b;
}

/* ================= RESERVATION CARD ================= */
.reserve-grid {
	display: grid;
	grid-template-columns: 1fr;
	gap: 14px;
}

.reserve-card {
	background: #f8fafc;
	border: 1px solid #e8ecf3;
	border-radius: 14px;
	padding: 16px;
	display: grid;
	grid-template-columns: 140px 1fr 1fr 1fr auto;
	gap: 14px;
	align-items: center;
}

.pet-img {
	width: 120px;
	height: 120px;
	object-fit: cover;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
}

/* ================= BLOCKS ================= */
.block {
	font-size: 13px;
	line-height: 1.7;
	color: #334155;
}

.block strong {
	color: #0f172a;
}

/* ================= BUTTON ================= */
.btn {
	background: #3b82f6;
	color: white;
	border: none;
	padding: 10px 14px;
	border-radius: 10px;
	font-weight: 600;
	cursor: pointer;
	transition: 0.2s;
}

.btn:hover {
	background: #2563eb;
}

/* ================= FOOT ================= */
.note {
	margin-top: 20px;
	color: #64748b;
	font-size: 13px;
}

/* ================= RESPONSIVE ================= */
@media ( max-width : 1000px) {
	.reserve-card {
		grid-template-columns: 1fr;
		text-align: center;
	}
	.pet-img {
		margin: 0 auto;
	}
}
</style>

</head>

<body>

	<header class="header">
		<div class="logo">📅 予約管理</div>
	</header>

	<div class="container">

		<div class="card">

			<div class="title">📋 予約確認</div>

			<!-- ================= FILTER ================= -->
			<form action="ReservationConfirmServlet" method="get"
				class="filter-bar">

				<button class="filter-btn" type="submit" name="dateStatus"
					value="all">すべて</button>
				<button class="filter-btn" type="submit" name="dateStatus"
					value="today">今日</button>
				<button class="filter-btn" type="submit" name="dateStatus"
					value="tomorrow">明日</button>

			</form>

			<div class="count">表示件数：${reserveViewList.size()}件</div>

			<!-- ================= LIST ================= -->
			<div class="reserve-grid">

				<c:forEach var="reserveView" items="${reserveViewList}">

					<div class="reserve-card">

						<!-- IMAGE -->
						<div>
							<c:choose>
								<c:when test="${not empty reserveView.imagePath}">
									<img class="pet-img"
										src="${pageContext.request.contextPath}/${reserveView.imagePath}"
										alt="ペット画像">
								</c:when>
								<c:otherwise>
									<div class="pet-img"
										style="display: flex; align-items: center; justify-content: center; background: #e2e8f0;">
										画像なし</div>
								</c:otherwise>
							</c:choose>
						</div>

						<!-- PET INFO -->
						<div class="block">
							<strong>🐶 ペット情報</strong><br> ID：${reserveView.petID}<br>
							名前：${reserveView.petName}<br> 種類：${reserveView.categoryName}<br>
							性別：${reserveView.genderName}<br> 年齢：${reserveView.petAge}歳
						</div>

						<!-- USER INFO -->
						<div class="block">
							<strong>👤 お客様情報</strong><br> ID：${reserveView.userID}<br>
							名前：${reserveView.userName}<br> 年齢：${reserveView.userAge}歳<br>
							性別：${reserveView.userGender}
						</div>

						<!-- CONTACT -->
						<div class="block">
							<strong>📞 連絡先</strong><br> ${reserveView.userTel}<br>
							${reserveView.userMail}<br>
							<br> <strong>🕒 予約日時</strong><br>
							${reserveView.formattedReserveTime}
						</div>

						<!-- ACTION -->
						<div>
							<form action="ReservationEditServlet" method="get">
								<input type="hidden" name="reservationID"
									value="${reserveView.reservationID}"> <input
									type="hidden" name="reserveTime"
									value="${reserveView.reserveTime}">
								<button class="btn" type="submit">変更</button>
							</form>
						</div>

					</div>

				</c:forEach>

			</div>

			<div class="note">※ 予約のキャンセル・削除は編集画面から行ってください</div>

			<form action="FacilityPageServlet" method="get"
				style="margin-top: 15px;">
				<button class="filter-btn" type="submit">← 戻る</button>
			</form>

		</div>

	</div>

</body>
</html>