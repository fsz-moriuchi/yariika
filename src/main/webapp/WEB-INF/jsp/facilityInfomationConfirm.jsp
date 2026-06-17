<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>施設情報確認</title>

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
	max-width: 1000px;
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

/* ================= INFO GRID ================= */
.info-grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 14px;
}

.info-item {
	background: #f8fafc;
	padding: 14px;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
}

.label {
	font-size: 12px;
	color: #64748b;
	margin-bottom: 6px;
}

.value {
	font-size: 15px;
	font-weight: 600;
	color: #0f172a;
}

/* ================= BUTTONS ================= */
.button-area {
	display: flex;
	gap: 12px;
	margin-top: 20px;
	flex-wrap: wrap;
}

.btn {
	text-decoration: none;
	padding: 12px 16px;
	border-radius: 12px;
	font-weight: 600;
	font-size: 14px;
	border: 1px solid #e8ecf3;
	transition: 0.2s;
	display: inline-block;
}

.btn-primary {
	background: #3b82f6;
	color: white;
	border: none;
}

.btn-primary:hover {
	background: #2563eb;
}

.btn-secondary {
	background: #f1f5f9;
	color: #334155;
}

.btn-secondary:hover {
	background: #e2e8f0;
}

/* ================= RESPONSIVE ================= */
@media ( max-width : 800px) {
	.info-grid {
		grid-template-columns: 1fr;
	}
}
</style>

</head>

<body>

	<!-- ================= HEADER ================= -->
	<header class="header">
		<div class="logo">🏢 施設情報</div>
	</header>

	<div class="container">

		<div class="card">

			<div class="title">📋 施設情報確認</div>

			<div class="info-grid">

				<div class="info-item">
					<div class="label">施設名</div>
					<div class="value">${facilityInfo.facilityName}</div>
				</div>

				<div class="info-item">
					<div class="label">電話番号</div>
					<div class="value">${facilityInfo.tel}</div>
				</div>

				<div class="info-item">
					<div class="label">住所</div>
					<div class="value">${facilityInfo.address}</div>
				</div>

				<div class="info-item">
					<div class="label">メールアドレス</div>
					<div class="value">${facilityInfo.mail}</div>
				</div>

				<div class="info-item">
					<div class="label">開店時間</div>
					<div class="value">${fn:substring(facilityInfo.openTime, 0, 5)}</div>
				</div>

				<div class="info-item">
					<div class="label">閉店時間</div>
					<div class="value">${fn:substring(facilityInfo.closeTime, 0, 5)}</div>
				</div>

				<div class="info-item" style="grid-column: 1/-1;">
					<div class="label">定休日</div>
					<div class="value">
						<c:choose>
							<c:when test="${not empty facilityClosedDayList}">
								<c:forEach var="closedDay" items="${facilityClosedDayList}"
									varStatus="status">

									<c:choose>
										<c:when test="${closedDay == 'MONDAY'}">月曜日</c:when>
										<c:when test="${closedDay == 'TUESDAY'}">火曜日</c:when>
										<c:when test="${closedDay == 'WEDNESDAY'}">水曜日</c:when>
										<c:when test="${closedDay == 'THURSDAY'}">木曜日</c:when>
										<c:when test="${closedDay == 'FRIDAY'}">金曜日</c:when>
										<c:when test="${closedDay == 'SATURDAY'}">土曜日</c:when>
										<c:when test="${closedDay == 'SUNDAY'}">日曜日</c:when>
									</c:choose>

									<c:if test="${not status.last}">、</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
							定休日なし
						</c:otherwise>
						</c:choose>
					</div>
				</div>

			</div>

			<!-- ================= BUTTONS ================= -->
			<div class="button-area">

				<a class="btn btn-primary" href="FacilityInformationEditServlet">
					✏ 施設情報を変更 </a> <a class="btn btn-secondary" href="FacilityPageServlet">
					🏠 戻る </a>

			</div>

		</div>

	</div>

</body>
</html>