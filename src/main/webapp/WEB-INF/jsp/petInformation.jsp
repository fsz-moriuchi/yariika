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
<title>RegisterPetInformation</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>

<div class="page-container">

<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="pet-register-card">

<h1>ペット情報新規登録/修正</h1>

<p class="welcome-message">
	ペットの基本情報・特徴・画像を登録してください。。
</p>

<form action="PetRegisterServlet" method="post"
	enctype="multipart/form-data" class="pet-register-edit-form">

	<div class="pet-register-section">

		<h2>基本情報</h2>

		<div class="pet-form-row">
			<p class="pet-form-label">1.カテゴリー</p>
			<div class="pet-choice-list">
				<label class="pet-choice-item"> <input type="radio" name="categoryId"
		value="1"
		${not empty petDetail and petDetail.categoryId == 1?"checked":""}
		required>犬
				</label> 
				<label class="pet-choice-item"> <input type="radio" name="categoryId" value="2"
		${not empty petDetail and petDetail.categoryId == 2?"checked":""}>猫
				</label> 
				<label class="pet-choice-item"> <input type="radio" name="categoryId" value="3"
		${not empty petDetail and petDetail.categoryId == 3?"checked":""}>鳥
				</label> 
				<label class="pet-choice-item"> <input type="radio" name="categoryId" value="4"
		${not empty petDetail and petDetail.categoryId == 4?"checked":""}>小動物<br>
				</label>
			</div>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">2.店舗ID</p>
			<div class="facility-id-box">
				${loginFacilityId} <input type="hidden"
		name="facilityId" value="${loginFacilityId}">
			</div>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">3.名前</p>
			<label> <input type="text" name="name"
		value="${empty petDetail ? '' : petDetail.name}" autocomplete="off"><br>
			</label>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">4.性別</p>
			<div class="pet-choice-list">
				<label class="pet-choice-item"> <input type="radio" name="gender" value="male"
		${not empty petDetail and petDetail.gender == "male"?"checked":"" }>男の子
				</label> 
				<label class="pet-choice-item"> <input type="radio" name="gender" value="female"
		${not empty petDetail and petDetail.gender == "female"?"checked":"" }>女の子<br>
				</label>
			</div>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">5.年齢</p>
			<label> <input type="number" name="age" min="0"
		value="${empty petDetail ? '' : petDetail.age}" required
		autocomplete="off"><br>
			</label>
		</div>

	</div>

	<div class="pet-register-section">

		<h2>見た目・特徴</h2>

		<div class="pet-form-row">
			<p class="pet-form-label">6.色と柄</p>
			<div class="pet-choice-list color-choice-list">
				<label class="pet-choice-item"> <input type="checkbox" name="color"
		value="white" <%=colorText.contains("white") ? "checked" : ""%>>白
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="black"
		<%=colorText.contains("black") ? "checked" : ""%>>黒
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="brown"
		<%=colorText.contains("brown") ? "checked" : ""%>>茶色
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="yellow"
		<%=colorText.contains("yellow") ? "checked" : ""%>>黄
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="gray"
		<%=colorText.contains("gray") ? "checked" : ""%>>グレー<br>
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="spotted"
		<%=colorText.contains("spotted") ? "checked" : ""%>>斑点模様
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="brindle"
		<%=colorText.contains("brindle") ? "checked" : ""%>>虎柄模様
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="curlyHair"
		<%=colorText.contains("curlyHair") ? "checked" : ""%>>巻き毛
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="longHair"
		<%=colorText.contains("longHair") ? "checked" : ""%>>長毛
				</label> 
				<label class="pet-choice-item"> <input type="checkbox" name="color" value="shortHair"
		<%=colorText.contains("shortHair") ? "checked" : ""%>>短毛<br>
				</label>
			</div>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">7.サイズ</p>
			<div class="pet-choice-list">
				<label class="pet-choice-item"> <input type="radio" name="pet_size"
		value="small"
		${not empty petDetail and petDetail.pet_size == "small"?"checked":"" }>小型
				</label> 
				<label class="pet-choice-item"> <input type="radio" name="pet_size" value="medium"
		${not empty petDetail and petDetail.pet_size == "medium"?"checked":"" }>中型
				</label> 
				<label class="pet-choice-item"> <input type="radio" name="pet_size" value="large"
		${not empty petDetail and petDetail.pet_size == "large"?"checked":"" }>大型<br>
				</label>
			</div>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">8.ワクチン</p>
			<div class="pet-choice-list">
				<label class="pet-choice-item"> <input type="radio" name="vaccine"
		value="vaccineDone"
		${not empty petDetail and petDetail.vaccine == "vaccineDone"?"checked":"" }>接種済み
				</label> 
				<label class="pet-choice-item"> <input type="radio" name="vaccine" value="vaccineYet"
		${not empty petDetail and petDetail.vaccine == "vaccineYet"?"checked":"" }>未接種<br>
				</label>
			</div>
		</div>

	</div>

	<div class="pet-register-section">

		<h2>価格・コメント・画像</h2>

		<div class="pet-form-row">
			<p class="pet-form-label">8.生体価格</p>
			<label> <input type="number" name="price" min="0"
		value="${empty petDetail ? '' : petDetail.price}" required><br>
			</label>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">9.コメント</p>
			<textarea name="commentText" maxlength="300" autocomplete="off">${empty petDetail ? '' : petDetail.commentText}</textarea>
		</div>

		<div class="pet-form-row">
			<p class="pet-form-label">10.画像</p>
			<input type="file" name="imageFile" accept="image/*"><br>
		</div>

		<c:if test="${not empty petDetail.imagePath}">
			<div class="current-pet-image-box">
				<p class="pet-form-label">現在の画像</p>
				<img src="${pageContext.request.contextPath}/${petDetail.imagePath}"
			width="200">
			</div>
			<br>
		</c:if>

	</div>

	<div class="pet-register-button-area">

	<c:choose>
		<c:when test="${empty petDetail}">
			<input type="submit" name="action" value="アンケートへ">
		</c:when>
		<c:otherwise>
			<input type="submit" name="action" value="アンケート修正">
			<input type="submit" name="action" value="更新">
			<input type="submit" name="action" value="削除" onclick="return confirm('このペット情報（ID：${petDetail.petID}）を削除してもよろしいですか？');">
			<input type="hidden" name="petID" value="${petDetail.petID}">
		</c:otherwise>
	</c:choose>

	</div>
</form>

</div>


</div>

</body>
</html>
