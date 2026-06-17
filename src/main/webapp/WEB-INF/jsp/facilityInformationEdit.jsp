<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>施設情報編集</title>

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

/* ================= FORM ================= */
.form-grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 14px;
}

.form-group {
	display: flex;
	flex-direction: column;
	background: #f8fafc;
	padding: 12px;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
}

label {
	font-size: 12px;
	color: #64748b;
	margin-bottom: 6px;
}

input[type="text"], input[type="time"] {
	padding: 10px;
	border-radius: 10px;
	border: 1px solid #e2e8f0;
	font-size: 14px;
	outline: none;
}

input:focus {
	border-color: #3b82f6;
}

/* ================= CHECKBOX ================= */
.checkbox-grid {
	display: grid;
	grid-template-columns: repeat(4, 1fr);
	gap: 10px;
	margin-top: 10px;
}

.checkbox-item {
	background: #f8fafc;
	padding: 10px;
	border-radius: 10px;
	border: 1px solid #e8ecf3;
	font-size: 14px;
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
	border: none;
	cursor: pointer;
	transition: 0.2s;
}

.btn-primary {
	background: #3b82f6;
	color: white;
}

.btn-primary:hover {
	background: #2563eb;
}

.btn-secondary {
	background: #f1f5f9;
	color: #334155;
	border: 1px solid #e8ecf3;
}

.btn-secondary:hover {
	background: #e2e8f0;
}

/* ================= RESPONSIVE ================= */
@media ( max-width : 800px) {
	.form-grid {
		grid-template-columns: 1fr;
	}
	.checkbox-grid {
		grid-template-columns: repeat(2, 1fr);
	}
}
</style>

</head>

<body>

	<header class="header">
		<div class="logo">✏ 施設情報編集</div>
	</header>

	<div class="container">

		<div class="card">

			<div class="title">🏢 施設情報を編集</div>

			<form action="FacilityInformationEditServlet" method="post">

				<div class="form-grid">

					<div class="form-group">
						<label>施設名</label> <input type="text" name="facilityName"
							value="${facilityInfo.facilityName}">
					</div>

					<div class="form-group">
						<label>電話番号</label> <input type="text" name="tel"
							value="${facilityInfo.tel}">
					</div>

					<div class="form-group">
						<label>住所</label> <input type="text" name="address"
							value="${facilityInfo.address}">
					</div>

					<div class="form-group">
						<label>メールアドレス</label> <input type="text" name="mail"
							value="${facilityInfo.mail}">
					</div>

					<div class="form-group">
						<label>開店時間</label> <input type="time" name="openTime"
							value="${fn:substring(facilityInfo.openTime,0,5)}">
					</div>

					<div class="form-group">
						<label>閉店時間</label> <input type="time" name="closeTime"
							value="${fn:substring(facilityInfo.closeTime,0,5)}">
					</div>

				</div>

				<div style="margin-top: 16px;">
					<label>定休日</label>

					<div class="checkbox-grid">

						<div class="checkbox-item">
							<input type="checkbox" name="closedDay" value="MONDAY"
								${facilityClosedDayList.contains('MONDAY') ? 'checked' : ''}>
							月曜日
						</div>

						<div class="checkbox-item">
							<input type="checkbox" name="closedDay" value="TUESDAY"
								${facilityClosedDayList.contains('TUESDAY') ? 'checked' : ''}>
							火曜日
						</div>

						<div class="checkbox-item">
							<input type="checkbox" name="closedDay" value="WEDNESDAY"
								${facilityClosedDayList.contains('WEDNESDAY') ? 'checked' : ''}>
							水曜日
						</div>

						<div class="checkbox-item">
							<input type="checkbox" name="closedDay" value="THURSDAY"
								${facilityClosedDayList.contains('THURSDAY') ? 'checked' : ''}>
							木曜日
						</div>

						<div class="checkbox-item">
							<input type="checkbox" name="closedDay" value="FRIDAY"
								${facilityClosedDayList.contains('FRIDAY') ? 'checked' : ''}>
							金曜日
						</div>

						<div class="checkbox-item">
							<input type="checkbox" name="closedDay" value="SATURDAY"
								${facilityClosedDayList.contains('SATURDAY') ? 'checked' : ''}>
							土曜日
						</div>

						<div class="checkbox-item">
							<input type="checkbox" name="closedDay" value="SUNDAY"
								${facilityClosedDayList.contains('SUNDAY') ? 'checked' : ''}>
							日曜日
						</div>

					</div>
				</div>

				<div class="button-area">

					<button type="submit" class="btn btn-primary">💾 更新</button>
			</form>

			<form action="FacilityInfomationConfirmServlet" method="get">
				<button type="submit" class="btn btn-secondary">← 戻る</button>
			</form>

		</div>

	</div>

	</div>

</body>
</html>