```jsp
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
<title>ペット情報登録・修正</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header class="site-header">
		<div class="header-inner">
			<a href="HomeServlet" class="logo">Pet Matching</a>

			<nav class="header-nav">
				<a href="HomeServlet">ホーム</a>
				<a href="FacilityPageServlet">店舗管理ページ</a>
				<a href="StoreServlet">ペット一覧</a>
				<a href="LogoutServlet" class="logout-link">ログアウト</a>
			</nav>
		</div>
	</header>

	<main class="container">

		<section class="pet-form-section">

			<div class="section-heading">
				<h1>ペット情報新規登録・修正</h1>
				<p>店舗に登録するペットの情報を入力してください。</p>
			</div>

			<form action="PetRegisterServlet" method="post"
				enctype="multipart/form-data" class="pet-form">

				<div class="form-card">

					<h2>基本情報</h2>

					<div class="form-row">
						<label class="form-label">カテゴリー</label>

						<div class="choice-inline-group">
							<label class="form-choice">
								<input type="radio" name="categoryId" value="1"
									${not empty petDetail and petDetail.categoryId == 1 ? "checked" : ""}
									required>
								<span>犬</span>
							</label>

							<label class="form-choice">
								<input type="radio" name="categoryId" value="2"
									${not empty petDetail and petDetail.categoryId == 2 ? "checked" : ""}>
								<span>猫</span>
							</label>

							<label class="form-choice">
								<input type="radio" name="categoryId" value="3"
									${not empty petDetail and petDetail.categoryId == 3 ? "checked" : ""}>
								<span>鳥</span>
							</label>

							<label class="form-choice">
								<input type="radio" name="categoryId" value="4"
									${not empty petDetail and petDetail.categoryId == 4 ? "checked" : ""}>
								<span>小動物</span>
							</label>
						</div>
					</div>

					<div class="form-row">
						<label class="form-label">店舗ID</label>

						<div class="readonly-text">
							<c:out value="${loginFacilityId}" />
							<input type="hidden" name="facilityId" value="${loginFacilityId}">
						</div>
					</div>

					<div class="form-row">
						<label class="form-label" for="name">名前</label>

						<input type="text" id="name" name="name"
							value="${empty petDetail ? '' : petDetail.name}"
							autocomplete="off" class="form-input">
					</div>

					<div class="form-row">
						<label class="form-label">性別</label>

						<div class="choice-inline-group">
							<label class="form-choice">
								<input type="radio" name="gender" value="male"
									${not empty petDetail and petDetail.gender == "male" ? "checked" : ""}>
								<span>男の子</span>
							</label>

							<label class="form-choice">
								<input type="radio" name="gender" value="female"
									${not empty petDetail and petDetail.gender == "female" ? "checked" : ""}>
								<span>女の子</span>
							</label>
						</div>
					</div>

					<div class="form-row">
						<label class="form-label" for="age">年齢</label>

						<div class="input-with-unit">
							<input type="number" id="age" name="age" min="0"
								value="${empty petDetail ? '' : petDetail.age}"
								required autocomplete="off" class="form-input short-input">
							<span>歳</span>
						</div>
					</div>

					<div class="form-row">
						<label class="form-label" for="price">生体価格</label>

						<div class="input-with-unit">
							<input type="number" id="price" name="price" min="0"
								value="${empty petDetail ? '' : petDetail.price}"
								required class="form-input short-input">
							<span>円</span>
						</div>
					</div>

				</div>


				<div class="form-card">

					<h2>特徴</h2>

					<div class="form-row">
						<label class="form-label">色と柄</label>

						<div class="checkbox-grid">

							<label class="form-choice">
								<input type="checkbox" name="color" value="white"
									<%=colorText.contains("white") ? "checked" : ""%>>
								<span>白</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="black"
									<%=colorText.contains("black") ? "checked" : ""%>>
								<span>黒</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="brown"
									<%=colorText.contains("brown") ? "checked" : ""%>>
								<span>茶色</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="yellow"
									<%=colorText.contains("yellow") ? "checked" : ""%>>
								<span>黄</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="gray"
									<%=colorText.contains("gray") ? "checked" : ""%>>
								<span>グレー</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="spotted"
									<%=colorText.contains("spotted") ? "checked" : ""%>>
								<span>斑点模様</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="brindle"
									<%=colorText.contains("brindle") ? "checked" : ""%>>
								<span>虎柄模様</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="curlyHair"
									<%=colorText.contains("curlyHair") ? "checked" : ""%>>
								<span>巻き毛</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="longHair"
									<%=colorText.contains("longHair") ? "checked" : ""%>>
								<span>長毛</span>
							</label>

							<label class="form-choice">
								<input type="checkbox" name="color" value="shortHair"
									<%=colorText.contains("shortHair") ? "checked" : ""%>>
								<span>短毛</span>
							</label>

						</div>
					</div>

					<div class="form-row">
						<label class="form-label">サイズ</label>

						<div class="choice-inline-group">
							<label class="form-choice">
								<input type="radio" name="pet_size" value="small"
									${not empty petDetail and petDetail.pet_size == "small" ? "checked" : ""}>
								<span>小型</span>
							</label>

							<label class="form-choice">
								<input type="radio" name="pet_size" value="medium"
									${not empty petDetail and petDetail.pet_size == "medium" ? "checked" : ""}>
								<span>中型</span>
							</label>

							<label class="form-choice">
								<input type="radio" name="pet_size" value="large"
									${not empty petDetail and petDetail.pet_size == "large" ? "checked" : ""}>
								<span>大型</span>
							</label>
						</div>
					</div>

					<div class="form-row">
						<label class="form-label">ワクチン</label>

						<div class="choice-inline-group">
							<label class="form-choice">
								<input type="radio" name="vaccine" value="vaccineDone"
									${not empty petDetail and petDetail.vaccine == "vaccineDone" ? "checked" : ""}>
								<span>接種済み</span>
							</label>

							<label class="form-choice">
								<input type="radio" name="vaccine" value="vaccineYet"
									${not empty petDetail and petDetail.vaccine == "vaccineYet" ? "checked" : ""}>
								<span>未接種</span>
							</label>
						</div>
					</div>

				</div>


				<div class="form-card">

					<h2>紹介文・画像</h2>

					<div class="form-row">
						<label class="form-label" for="commentText">コメント</label>

						<textarea id="commentText" name="commentText" maxlength="300"
							autocomplete="off" class="form-textarea">${empty petDetail ? '' : petDetail.commentText}</textarea>
					</div>

					<div class="form-row">
						<label class="form-label" for="imageFile">画像</label>

						<input type="file" id="imageFile" name="imageFile"
							accept="image/*" class="form-file">
					</div>

					<c:if test="${not empty petDetail.imagePath}">
						<div class="form-row">
							<label class="form-label">現在の画像</label>

							<img src="${pageContext.request.contextPath}/${petDetail.imagePath}"
								class="current-pet-image">
						</div>
					</c:if>

				</div>


				<div class="form-action-card">

					<c:choose>
						<c:when test="${empty petDetail}">
							<input type="submit" name="action" value="アンケートへ"
								class="main-button">
						</c:when>

						<c:otherwise>
							<input type="hidden" name="petID" value="${petDetail.petID}">

							<input type="submit" name="action" value="アンケート修正"
								class="main-button">

							<input type="submit" name="action" value="更新"
								class="main-button">

							<input type="submit" name="action" value="削除"
								class="danger-button">
						</c:otherwise>
					</c:choose>

					<a href="StoreServlet" class="back-link">ペット一覧に戻る</a>

				</div>

			</form>

		</section>

	</main>

</body>
</html>
```
