<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット管理</title>

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

/* HEADER */
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

/* CONTAINER */
.container {
	width: 95%;
	max-width: 1100px;
	margin: 30px auto;
}

/* CARD */
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

/* TOP BAR */
.top-bar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	flex-wrap: wrap;
	gap: 10px;
	margin-bottom: 16px;
}

.btn-primary {
	background: #3b82f6;
	color: white;
	border: none;
	padding: 10px 14px;
	border-radius: 10px;
	font-weight: 600;
	cursor: pointer;
	transition: 0.2s;
}

.btn-primary:hover {
	background: #2563eb;
}

/* GRID */
.pet-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
	gap: 14px;
	align-items: stretch; /* ★重要 */
}

/* CARD */
.pet-card {
	background: #f8fafc;
	border: 1px solid #e8ecf3;
	border-radius: 14px;
	padding: 14px;
	display: flex;
	flex-direction: column;
	gap: 10px;
	transition: 0.2s;
}

.pet-card:hover {
	transform: translateY(-2px);
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.06);
}

/* IMAGE */
.pet-img {
	width: 100%;
	height: 180px;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
	display: flex;
	align-items: center;
	justify-content: center;
	background: #fff;
	font-size: 13px;
	color: #64748b;
	font-weight: 600;
}

.pet-img img {
	width: 100%;
	height: 100%;
	object-fit: cover;
	border-radius: 12px;
}

/* INFO */
.info {
	font-size: 13px;
	line-height: 1.6;
	color: #334155;
}

.info strong {
	color: #0f172a;
}

/* ACTION */
.action-area {
	display: flex;
	flex-direction: column;
	gap: 8px;
}

.btn {
	padding: 8px 10px;
	border-radius: 10px;
	border: none;
	font-weight: 600;
	cursor: pointer;
	font-size: 13px;
	transition: 0.2s;
}

.btn-edit {
	background: #3b82f6;
	color: white;
}

.btn-edit:hover {
	background: #2563eb;
}

.btn-fav {
	background: #f1f5f9;
	color: #334155;
	border: 1px solid #e8ecf3;
}

.btn-fav:hover {
	background: #e2e8f0;
}

.badge {
	font-size: 12px;
	color: #ef4444;
	font-weight: 700;
	text-align: center;
}

/* RESPONSIVE */
@media ( max-width : 900px) {
	.pet-grid {
		grid-template-columns: repeat(2, 1fr);
	}
}

@media ( max-width : 600px) {
	.pet-grid {
		grid-template-columns: 1fr;
	}
}
</style>

</head>

<body>

	<header class="header">
		<div class="logo">🐾 ペット管理</div>
	</header>

	<div class="container">

		<div class="card">

			<div class="top-bar">

				<div class="title">
					<c:out value="${facilityId}" />
					: ペット情報管理
				</div>

				<form action="PetRegisterServlet" method="get">
					<button class="btn-primary" type="submit">＋ 新規ペット登録</button>
				</form>

			</div>

			<div class="pet-grid">

				<c:forEach var="pet" items="${facilityList}">

					<div class="pet-card">

						<div class="pet-img">
							<c:choose>
								<c:when test="${not empty pet.imagePath}">
									<img src="${pageContext.request.contextPath}/${pet.imagePath}"
										alt="ペット画像">
								</c:when>
								<c:otherwise>
								🐶 画像なし
							</c:otherwise>
							</c:choose>
						</div>

						<div class="info">
							<strong>ID:</strong> ${pet.petID}<br> <strong>種類:</strong>
							${pet.categoryIdName}<br> <strong>性別:</strong>
							${pet.genderName}<br> <strong>年齢:</strong> ${pet.age}歳<br>
							<strong>価格:</strong> ${pet.price}円<br> <strong>お気に入り:</strong>
							${pet.favoriteCount}人
						</div>

						<div class="action-area">

							<form action="PetEditServlet" method="get">
								<input type="hidden" name="petID" value="${pet.petID}">
								<button class="btn btn-edit" type="submit">修正</button>
							</form>

							<c:choose>
								<c:when test="${pet.petID == favoritePetId}">
									<div class="badge">★ 現在おすすめ中</div>
								</c:when>
								<c:otherwise>
									<form action="FavoritePetServlet" method="post">
										<input type="hidden" name="petID" value="${pet.petID}">
										<button class="btn btn-fav" type="submit">★ おすすめに設定</button>
									</form>
								</c:otherwise>
							</c:choose>

							<form action="PetEditServlet" method="post">
								<input type="hidden" name="petID" value="${pet.petID}">
								<button class="btn btn-fav" type="submit">アンケート確認</button>
							</form>

						</div>

					</div>

				</c:forEach>

			</div>
		</div>

		<form action="FacilityPageServlet" method="get"
			style="margin-top: 15px;">
			<button class="btn-primary" type="submit">← 戻る</button>
		</form>

	</div>

</body>
</html>