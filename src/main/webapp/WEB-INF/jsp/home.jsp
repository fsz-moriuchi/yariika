<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
</head>
<body>
	<a href="MyPageServlet">マイページへ</a>
	<br>
	<a href="FacilityPageServlet">施設専用ページへ</a>
	<br>
<!--条件検索  -->
	<h1>条件で探す</h1>

	<form action="HomeServlet" method="get">
		<input type="hidden" name="clickSearch" value="true"> 
		カテゴリー： 
		<select name="categoryId">
			<option value="">未指定</option>
			<option value="1" ${selectedCategoryId == "1" ? "selected" : ""}>犬</option>
			<option value="2" ${selectedCategoryId == "2" ? "selected" : ""}>猫</option>
			<option value="3" ${selectedCategoryId == "3" ? "selected" : ""}>鳥</option>
			<option value="4" ${selectedCategoryId == "4" ? "selected" : ""}>小動物</option>
		</select> <br> 
		性別：
		<select name="gender">
			<option value="">未指定</option>
			<option value="male" ${selectedGender == "male" ? "selected" : ""}>男の子</option>
			<option value="female" ${selectedGender == "female" ? "selected" : ""}>女の子</option>
		</select> <br>
		色柄：<br>
		<!--選択したものを残る  -->
		<c:set var="whiteChecked" value="false" />
		<c:set var="blackChecked" value="false" />
		<c:set var="brownChecked" value="false" />
		<c:set var="yellowChecked" value="false" />
		<c:set var="grayChecked" value="false" />
		<c:set var="spottedChecked" value="false" />
		<c:set var="brindleChecked" value="false" />
		<c:set var="curlyHairChecked" value="false" />
		<c:set var="longHairChecked" value="false" />
		<c:set var="shortHairChecked" value="false" />

		<c:forEach var="selectedColor" items="${selectedColorArray}">
			<c:if test="${selectedColor == 'white'}">
				<c:set var="whiteChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'black'}">
				<c:set var="blackChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'brown'}">
				<c:set var="brownChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'yellow'}">
				<c:set var="yellowChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'gray'}">
				<c:set var="grayChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'spotted'}">
				<c:set var="spottedChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'brindle'}">
				<c:set var="brindleChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'curlyHair'}">
				<c:set var="curlyHairChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'longHair'}">
				<c:set var="longHairChecked" value="true" />
			</c:if>

			<c:if test="${selectedColor == 'shortHair'}">
				<c:set var="shortHairChecked" value="true" />
			</c:if>

		</c:forEach>
		<!-- 色柄のチェックボックスを表示 -->
		<label> 
			<input type="checkbox" name="color" value="white" ${whiteChecked ? "checked" : ""}>白
		</label> 
		<label> 
			<input type="checkbox" name="color" value="black" ${blackChecked ? "checked" : ""}>黒
		</label> 
		<label> 
			<input type="checkbox" name="color" value="brown" ${brownChecked ? "checked" : ""}>茶色
		</label> 
		<label> 
			<input type="checkbox" name="color" value="yellow" ${yellowChecked ? "checked" : ""}>黄
		</label> 
		<label> 
			<input type="checkbox" name="color" value="gray" ${grayChecked ? "checked" : ""}>グレー
		</label> <br> 
		<label> 
			<input type="checkbox" name="color" value="spotted" ${spottedChecked ? "checked" : ""}>斑点模様
		</label> 
		<label> 
			<input type="checkbox" name="color" value="brindle" ${brindleChecked ? "checked" : ""}>虎柄模様
		</label> 
		<label> 
			<input type="checkbox" name="color" value="curlyHair" ${curlyHairChecked ? "checked" : ""}>巻き毛
		</label> 
		<label> 
			<input type="checkbox" name="color" value="longHair" ${longHairChecked ? "checked" : ""}>長毛
		</label> 
		<label> 
			<input type="checkbox" name="color" value="shortHair" ${shortHairChecked ? "checked" : ""}>短毛
		</label> <br> 
		
		サイズ： 
		<select name="pet_size">
			<option value="">未指定</option>
			<option value="small"
				${selectedPet_size == "small" ? "selected" : ""}>小型</option>
			<option value="medium"
				${selectedPet_size == "medium" ? "selected" : ""}>中型</option>
			<option value="large"
				${selectedPet_size == "large" ? "selected" : ""}>大型</option>
		</select> <br> 
		年齢： 
		<select name="ageRange">
			<option value="">未指定</option>
			<option value="age0" ${selectedAgeRange == "age0" ? "selected" : ""}>0歳</option>
			<option value="age1to3"
				${selectedAgeRange == "age1to3" ? "selected" : ""}>1~3歳</option>
			<option value="age4up"
				${selectedAgeRange == "age4up" ? "selected" : ""}>4歳以上</option>
		</select> <br> 
		価格： 
		<select name="priceRange">
			<option value="">未指定</option>
			<option value="price0to100000"
				${selectedPriceRange == "price0to100000" ? "selected" : ""}>100,000円以下</option>
			<option value="price100001to300000"
				${selectedPriceRange == "price100001to300000" ? "selected" : ""}>100,001円 ~ 300,000円</option>
			<option value="price300001to500000"
				${selectedPriceRange == "price300001to500000" ? "selected" : ""}>300,001円 ~ 500,000円</option>
			<option value="price500001up"
				${selectedPriceRange == "price500001up" ? "selected" : ""}>500,001円以上</option>
		</select> <br> 
		並び順： 
		<select name="sort">
			<option value="">並び順を選択ください</option>
			<option value="matchRateDesc" ${sort == "matchRateDesc" ? "selected" : ""}>マッチング度高い順</option>
			<option value="matchRateAsc" ${sort == "matchRateAsc" ? "selected" : ""}>マッチング度低い順</option>
			<option value="priceDesc" ${sort == "priceDesc" ? "selected" : ""}>価格高い順</option>
			<option value="priceAsc" ${sort == "priceAsc" ? "selected" : ""}>価格低い順</option>
			<option value="ageDesc" ${sort == "ageDesc" ? "selected" : ""}>年齢高い順</option>
			<option value="ageAsc" ${sort == "ageAsc" ? "selected" : ""}>年齢低い順</option>
		</select> <br>

		<button type="submit">検索</button>
	</form>

	<form action="HomeServlet" method="get">
		<button type="submit">クリア</button>
	</form>
	<p>
	※ 条件を指定せずに「検索」を押すと、すべてのペットを一覧で表示できます。<br>
	※ アンケート未回答の場合、マッチング度は0%と表示されます。<br>
	※ マッチング度を表示するには、マイページの「アンケート回答」からアンケートにご回答ください。</p>
	<hr>
	<!--結果 > 0-->
	<c:if test="${clickSearch and searchResultCount > 0}">
		<h1>
			検索結果：<c:out value="${searchResultCount}" /> 件
		</h1>

		<br>
		<c:forEach var="pet" items="${searchPetList}">
			店舗：<c:out value="${pet.facilityName}" />
			<br>

			<img src="${pet.imagePath}" width="200">
			<br>

			名前：
			<c:choose>
				<c:when test="${not empty pet.name}">
					<c:out value="${pet.name}" />
				</c:when>
				<c:otherwise>
					名付けてください！
				</c:otherwise>
			</c:choose>
			<br>

			カテゴリー：<c:out value="${pet.categoryName}" />
			<br>
			性別：<c:out value="${pet.genderName}" />
			<br>
			年齢：<c:out value="${pet.age}" />歳<br>
			色柄：<c:out value="${pet.colorName}" />
			<br>
			サイズ：<c:out value="${pet.petSizeName}" />
			<br>
			価格：<c:out value="${pet.price}" />円<br>
			<c:if test="${not empty sessionScope.userId}">
			マッチング度：<c:out value="${pet.matchRate}" />%<br>
			</c:if>

			<a href="PetDetailServlet?petID=${pet.petID}">詳細を見る</a>

			<hr>
		</c:forEach>
	</c:if>

	<!--結果 = 0 ：おすすめ-->
	<c:if test="${clickSearch and searchResultCount == 0}">
		<h1>検索結果 0 件</h1>
		<p>条件に一致するペットはいません。</p>
		<hr>
	</c:if>


	<!--最初画面と結果 = 0とき：おすすめ-->
	<c:if test="${empty clickSearch or searchResultCount == 0}">
		<h1>おすすめのペット</h1>

		<c:if test="${not empty sessionScope.userId}">
			<form action="HomeServlet" method="get">
				並び順： <select name="sort">
					<option value="">並び順を選択ください</option>
					<option value="matchRateDesc"
						${sort == "matchRateDesc" ? "selected" : ""}>マッチング度高い順</option>
					<option value="matchRateAsc"
						${sort == "matchRateAsc" ? "selected" : ""}>マッチング度低い順</option>
					<option value="priceDesc" ${sort == "priceDesc" ? "selected" : ""}>価格高い順</option>
					<option value="priceAsc" ${sort == "priceAsc" ? "selected" : ""}>価格低い順</option>
					<option value="ageDesc" ${sort == "ageDesc" ? "selected" : ""}>年齢高い順</option>
					<option value="ageAsc" ${sort == "ageAsc" ? "selected" : ""}>年齢低い順</option>
				</select>
				<button type="submit">並び替え</button>
			</form>
			
		</c:if>
		<c:forEach var="pet" items="${favoritePetList}">

	店舗：<c:out value="${pet.facilityName}" />
			<br>
			<img src="${pet.imagePath}" width="200">
			<br>

	名前：<c:out value="${pet.name}" />
			<br>

	性別：<c:out value="${pet.genderName}" />
			<br>

	年齢：<c:out value="${pet.age}" />歳<br>

	価格：<c:out value="${pet.price}" />円<br>
			<c:if test="${not empty sessionScope.userId}">
	マッチング度：<c:out value="${pet.matchRate}" /> % <br>
			</c:if>
			<a href="PetDetailServlet?petID=${pet.petID}"> 詳細を見る </a>

			<hr>

		</c:forEach>
	</c:if>
	<br>

	<a href="LogoutServlet">ログアウト</a>
</body>
</html>