<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
String colorText = "";
if (request.getAttribute("petDetail") != null) {
	colorText = ((model.PetDetail) request.getAttribute("petDetail")).getColor();
	if (colorText == null) {
		colorText = "";
	}
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット登録</title>

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

.container {
	width: 95%;
	max-width: 900px;
	margin: 30px auto;
}

.card {
	background: white;
	border-radius: 14px;
	padding: 24px;
	border: 1px solid #e8ecf3;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.03);
}

.title {
	font-size: 18px;
	font-weight: 700;
	margin-bottom: 20px;
	color: #0f172a;
}

.form-group {
	margin-bottom: 18px;
}

.section-title {
	font-weight: 700;
	font-size: 14px;
	margin-bottom: 6px;
	color: #0f172a;
}

/* INPUT */
input[type="text"], input[type="number"], textarea {
	width: 100%;
	padding: 12px;
	margin-top: 6px;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
	font-size: 14px;
	background: #fff;
}

textarea {
	min-height: 110px;
	resize: vertical;
}

/* ===================== */
/* 🔥 タイル型ラジオUI */
/* ===================== */
.radio-group {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
	gap: 10px;
	margin-top: 6px;
}

.radio-group input[type="radio"] {
	display: none;
}

.radio-tile {
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 14px;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
	background: #f8fafc;
	cursor: pointer;
	font-weight: 700;
	font-size: 14px;
	transition: 0.2s;
	user-select: none;
}

.radio-tile:hover {
	background: #e2e8f0;
}

.radio-group input[type="radio"]:checked+.radio-tile {
	background: #3b82f6;
	color: white;
	border-color: #3b82f6;
}

/* ===================== */
/* チェックボックス */
/* ===================== */
.checkbox-group {
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	margin-top: 6px;
}

.checkbox-group label {
	background: #f8fafc;
	padding: 8px 12px;
	border-radius: 10px;
	border: 1px solid #e8ecf3;
	cursor: pointer;
	font-size: 13px;
}

/* ===================== */
/* ファイルアップロード */
/* ===================== */
input[type="file"] {
	display: none;
}

.file-label {
	display: inline-block;
	padding: 14px 16px;
	background: #f1f5f9;
	border: 1px solid #e8ecf3;
	border-radius: 12px;
	cursor: pointer;
	font-weight: 700;
	transition: 0.2s;
	margin-top: 6px;
}

.file-label:hover {
	background: #e2e8f0;
}

/* ===================== */
/* 画像プレビュー */
/* ===================== */
.preview-img {
	margin-top: 10px;
	max-width: 240px;
	border-radius: 12px;
	border: 1px solid #e8ecf3;
}

/* ===================== */
/* ボタン */
/* ===================== */
.btn-area {
	display: flex;
	flex-wrap: wrap;
	gap: 12px;
	margin-top: 24px;
	padding: 14px;
	background: #f8fafc;
	border: 1px solid #e8ecf3;
	border-radius: 12px;
}

.btn {
	padding: 12px 16px;
	border-radius: 12px;
	border: none;
	font-weight: 700;
	cursor: pointer;
	font-size: 14px;
	min-height: 44px;
	transition: 0.2s;
}

.btn-primary {
	background: #3b82f6;
	color: white;
}

.btn-primary:hover {
	background: #2563eb;
}

.btn-danger {
	background: #ef4444;
	color: white;
}

.btn-danger:hover {
	background: #dc2626;
}

/* mobile */
@media ( max-width : 600px) {
	.btn {
		width: 100%;
	}
}
</style>

</head>

<body>

	<div class="header">
		<div class="logo">🐾 ペット登録 / 編集</div>
	</div>

	<div class="container">

		<div class="card">

			<div class="title">ペット情報新規登録 / 修正</div>

			<form action="PetRegisterServlet" method="post"
				enctype="multipart/form-data">

				<!-- カテゴリー -->
				<div class="form-group">
					<div class="section-title">1. カテゴリー</div>

					<div class="radio-group">

						<label> <input type="radio" name="categoryId" value="1"
							${not empty petDetail and petDetail.categoryId == 1?"checked":""}
							required>
							<div class="radio-tile">犬</div>
						</label> <label> <input type="radio" name="categoryId" value="2"
							${not empty petDetail and petDetail.categoryId == 2?"checked":""}>
							<div class="radio-tile">猫</div>
						</label> <label> <input type="radio" name="categoryId" value="3"
							${not empty petDetail and petDetail.categoryId == 3?"checked":""}>
							<div class="radio-tile">鳥</div>
						</label> <label> <input type="radio" name="categoryId" value="4"
							${not empty petDetail and petDetail.categoryId == 4?"checked":""}>
							<div class="radio-tile">小動物</div>
						</label>

					</div>
				</div>

				<!-- 店舗ID -->
				<div class="form-group">
					<div class="section-title">2. 店舗ID</div>
					<div>${loginFacilityId}</div>
					<input type="hidden" name="facilityId" value="${loginFacilityId}">
				</div>

				<!-- 名前 -->
				<div class="form-group">
					<div class="section-title">3. 名前</div>
					<input type="text" name="name"
						value="${empty petDetail ? '' : petDetail.name}">
				</div>

				<!-- 性別 -->
				<div class="form-group">
					<div class="section-title">4. 性別</div>

					<div class="radio-group">

						<label> <input type="radio" name="gender" value="male"
							${not empty petDetail and petDetail.gender == "male"?"checked":""}>
							<div class="radio-tile">男の子</div>
						</label> <label> <input type="radio" name="gender" value="female"
							${not empty petDetail and petDetail.gender == "female"?"checked":""}>
							<div class="radio-tile">女の子</div>
						</label>

					</div>
				</div>

				<!-- 年齢 -->
				<div class="form-group">
					<div class="section-title">5. 年齢</div>
					<input type="number" name="age" min="0"
						value="${empty petDetail ? '' : petDetail.age}" required>
				</div>

				<!-- 色 -->
				<div class="form-group">
					<div class="section-title">6. 色と柄</div>

					<div class="checkbox-group">
						<label><input type="checkbox" name="color" value="white"
							<%=colorText.contains("white") ? "checked" : ""%>> 白</label> <label><input
							type="checkbox" name="color" value="black"
							<%=colorText.contains("black") ? "checked" : ""%>> 黒</label> <label><input
							type="checkbox" name="color" value="brown"
							<%=colorText.contains("brown") ? "checked" : ""%>> 茶</label> <label><input
							type="checkbox" name="color" value="yellow"
							<%=colorText.contains("yellow") ? "checked" : ""%>> 黄</label> <label><input
							type="checkbox" name="color" value="gray"
							<%=colorText.contains("gray") ? "checked" : ""%>> グレー</label>
					</div>
				</div>

				<!-- サイズ -->
				<div class="form-group">
					<div class="section-title">7. サイズ</div>

					<div class="radio-group">

						<label> <input type="radio" name="pet_size" value="small"
							${not empty petDetail and petDetail.pet_size == "small"?"checked":""}>
							<div class="radio-tile">小型</div>
						</label> <label> <input type="radio" name="pet_size"
							value="medium"
							${not empty petDetail and petDetail.pet_size == "medium"?"checked":""}>
							<div class="radio-tile">中型</div>
						</label> <label> <input type="radio" name="pet_size" value="large"
							${not empty petDetail and petDetail.pet_size == "large"?"checked":""}>
							<div class="radio-tile">大型</div>
						</label>

					</div>
				</div>

				<!-- ワクチン -->
				<div class="form-group">
					<div class="section-title">8. ワクチン</div>

					<div class="radio-group">

						<label> <input type="radio" name="vaccine"
							value="vaccineDone"
							${not empty petDetail and petDetail.vaccine == "vaccineDone"?"checked":""}>
							<div class="radio-tile">接種済み</div>
						</label> <label> <input type="radio" name="vaccine"
							value="vaccineYet"
							${not empty petDetail and petDetail.vaccine == "vaccineYet"?"checked":""}>
							<div class="radio-tile">未接種</div>
						</label>

					</div>
				</div>

				<!-- 価格 -->
				<div class="form-group">
					<div class="section-title">9. 価格</div>
					<input type="number" name="price" min="0"
						value="${empty petDetail ? '' : petDetail.price}" required>
				</div>

				<!-- コメント -->
				<div class="form-group">
					<div class="section-title">10. コメント</div>
					<textarea name="commentText">${empty petDetail ? '' : petDetail.commentText}</textarea>
				</div>

				<!-- 画像 -->
				<div class="form-group">
					<div class="section-title">11. 画像</div>

					<label class="file-label"> 📁 画像を選択する <input type="file"
						name="imageFile" accept="image/*">
					</label>

					<c:if test="${not empty petDetail.imagePath}">
						<div style="margin-top: 10px;">現在の画像：</div>
						<img class="preview-img"
							src="${pageContext.request.contextPath}/${petDetail.imagePath}">
					</c:if>
				</div>

				<!-- ボタン -->
				<div class="btn-area">

					<c:choose>
						<c:when test="${empty petDetail}">
							<button class="btn btn-primary" type="submit" name="action"
								value="アンケートへ">アンケートへ</button>
						</c:when>

						<c:otherwise>
							<button class="btn btn-primary" type="submit" name="action"
								value="アンケート修正">アンケート修正</button>

							<button class="btn btn-primary" type="submit" name="action"
								value="更新">更新</button>

							<button class="btn btn-danger" type="submit" name="action"
								value="削除" onclick="return confirm('削除してもよろしいですか？');">
								削除</button>

							<input type="hidden" name="petID" value="${petDetail.petID}">
						</c:otherwise>
					</c:choose>

				</div>

			</form>

		</div>

	</div>

</body>
</html>