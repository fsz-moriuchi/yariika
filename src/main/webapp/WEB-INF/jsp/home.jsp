<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
<link rel="stylesheet" href="style.css">
</head>

<body>
	<div class="page-container">
		<div class="site-header">

			<div class="site-logo-wrap">
				<h1 class="site-logo">PET MATCH</h1>
			</div>
			<div class="top-menu">
				<c:if test="${not empty sessionScope.userId}">

					<a class="menu-button" href="MyPageServlet"> マイページへ </a>

					<a class="menu-button" href="MessageListServlet"> メッセージ <span
						class="unread-badge"> 未読${userUnreadCount}件 </span>
					</a>

				</c:if>

				<a class="menu-button logout-button" href="LogoutServlet"> ログアウト
				</a>
			</div>

		</div>

		<!-- ================= ユーザー側 ================= -->
		<c:if test="${not empty sessionScope.userId}">

			<h1>条件で探す</h1>
			<form action="HomeServlet" method="get">
				<input type="hidden" name="clickSearch" value="true">

				<!-- カテゴリー -->
				カテゴリー： <select name="categoryId">
					<option value="">未指定</option>
					<option value="1" ${selectedCategoryId == "1" ? "selected" : ""}>犬</option>
					<option value="2" ${selectedCategoryId == "2" ? "selected" : ""}>猫</option>
					<option value="3" ${selectedCategoryId == "3" ? "selected" : ""}>鳥</option>
					<option value="4" ${selectedCategoryId == "4" ? "selected" : ""}>小動物</option>
				</select> <br>

				<!-- 性別 -->
				性別： <select name="gender">
					<option value="">未指定</option>
					<option value="male" ${selectedGender == "male" ? "selected" : ""}>男の子</option>
					<option value="female"
						${selectedGender == "female" ? "selected" : ""}>女の子</option>
				</select> <br>

				<!-- 色柄 -->
				色柄：<br> <label> <input type="checkbox" name="color"
					value="white"
					${selectedColorList != null && selectedColorList.contains('white') ? 'checked' : ''}>
					白
				</label> <label> <input type="checkbox" name="color" value="black"
					${selectedColorList != null && selectedColorList.contains('black') ? 'checked' : ''}>
					黒
				</label> <label> <input type="checkbox" name="color" value="brown"
					${selectedColorList != null && selectedColorList.contains('brown') ? 'checked' : ''}>
					茶色
				</label> <label> <input type="checkbox" name="color" value="yellow"
					${selectedColorList != null && selectedColorList.contains('yellow') ? 'checked' : ''}>
					黄
				</label> <label> <input type="checkbox" name="color" value="gray"
					${selectedColorList != null && selectedColorList.contains('gray') ? 'checked' : ''}>
					グレー
				</label> <br> <label> <input type="checkbox" name="color"
					value="spotted"
					${selectedColorList != null && selectedColorList.contains('spotted') ? 'checked' : ''}>
					斑点模様
				</label> <label> <input type="checkbox" name="color" value="brindle"
					${selectedColorList != null && selectedColorList.contains('brindle') ? 'checked' : ''}>
					虎柄模様
				</label> <label> <input type="checkbox" name="color"
					value="curlyHair"
					${selectedColorList != null && selectedColorList.contains('curlyHair') ? 'checked' : ''}>
					巻き毛
				</label> <label> <input type="checkbox" name="color"
					value="longHair"
					${selectedColorList != null && selectedColorList.contains('longHair') ? 'checked' : ''}>
					長毛
				</label> <label> <input type="checkbox" name="color"
					value="shortHair"
					${selectedColorList != null && selectedColorList.contains('shortHair') ? 'checked' : ''}>
					短毛
				</label> <br>

				<!-- サイズ -->
				サイズ： <select name="pet_size">
					<option value="">未指定</option>
					<option value="small"
						${selectedPet_size == "small" ? "selected" : ""}>小型</option>
					<option value="medium"
						${selectedPet_size == "medium" ? "selected" : ""}>中型</option>
					<option value="large"
						${selectedPet_size == "large" ? "selected" : ""}>大型</option>
				</select> <br>

				<!-- 年齢 -->
				年齢： <select name="ageRange">
					<option value="">未指定</option>
					<option value="age0"
						${selectedAgeRange == "age0" ? "selected" : ""}>0歳</option>
					<option value="age1to3"
						${selectedAgeRange == "age1to3" ? "selected" : ""}>1～3歳</option>
					<option value="age4up"
						${selectedAgeRange == "age4up" ? "selected" : ""}>4歳以上</option>
				</select> <br>

				<!-- 価格 -->
				価格： <select name="priceRange">
					<option value="">未指定</option>
					<option value="price0to100000"
						${selectedPriceRange == "price0to100000" ? "selected" : ""}>
						100,000円以下</option>

					<option value="price100001to300000"
						${selectedPriceRange == "price100001to300000" ? "selected" : ""}>
						100,001円 ～ 300,000円</option>

					<option value="price300001to500000"
						${selectedPriceRange == "price300001to500000" ? "selected" : ""}>
						300,001円 ～ 500,000円</option>

					<option value="price500001up"
						${selectedPriceRange == "price500001up" ? "selected" : ""}>
						500,001円以上</option>
				</select> <br>

				<!-- 並び順 -->
				並び順： <select name="sort">
					<option value="">並び順を選択ください</option>

					<option value="matchRateDesc"
						${sort == "matchRateDesc" ? "selected" : ""}>マッチング度高い順</option>

					<option value="matchRateAsc"
						${sort == "matchRateAsc" ? "selected" : ""}>マッチング度低い順</option>

					<option value="priceDesc" ${sort == "priceDesc" ? "selected" : ""}>
						価格高い順</option>

					<option value="priceAsc" ${sort == "priceAsc" ? "selected" : ""}>
						価格低い順</option>

					<option value="ageDesc" ${sort == "ageDesc" ? "selected" : ""}>
						年齢高い順</option>

					<option value="ageAsc" ${sort == "ageAsc" ? "selected" : ""}>
						年齢低い順</option>
				</select> <br> <br>

				<div class="form-button-area">
					<button type="submit">🐾検索</button>

					<a class="clear-button" href="HomeServlet"> クリア </a>
				</div>
			</form>

			<p>
				※ 条件を指定せずに「検索」を押すと、すべてのペットを一覧で表示できます。<br> ※
				アンケート未回答の場合、マッチング度は0%と表示されます。<br> ※
				マッチング度を表示するには、マイページの「アンケート回答」からアンケートにご回答ください。
			</p>

			<hr>

			<c:choose>

				<%-- 検索結果あり --%>
				<c:when test="${clickSearch and searchResultCount > 0}">

					<div class="result-header">
						<h2>検索結果</h2>
						<span class="result-badge">${searchResultCount}件見つかりました</span>
					</div>

					<div class="pet-card-list">

						<c:forEach var="pet" items="${searchPetList}">

							<div class="pet-card">

								<img class="pet-image" src="${pet.imagePath}" alt="ペット画像">

								<div class="pet-info">

									<p>
										<span class="pet-dot">・</span><span class="pet-label">店舗：</span>
										<a href="FacilityHomeServlet?facilityId=${pet.facilityID}">
											<c:out value="${pet.facilityName}" />
										</a>
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">名前：</span>
										<c:out value="${not empty pet.name ? pet.name : '名付けてください！'}" />
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">カテゴリー：</span>
										<c:out value="${pet.categoryName}" />
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">性別：</span>
										<c:out value="${pet.genderName}" />
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">年齢：</span>
										<c:out value="${pet.age}" />
										歳
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">色柄：</span>
										<c:out value="${pet.colorName}" />
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">サイズ：</span>
										<c:out value="${pet.petSizeName}" />
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">価格：</span>
										<c:out value="${pet.price}" />
										円
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">マッチング度：</span>
										<span class="match-rate"> <c:out
												value="${empty pet.matchRate ? 0 : pet.matchRate}" />%
										</span>
									</p>

									<a class="detail-button"
										href="PetDetailServlet?petID=${pet.petID}&from=search">
										🐾詳細を見る🐾 </a>

								</div>

							</div>

						</c:forEach>

					</div>

				</c:when>
				<%-- 初期表示・検索0件 --%>
				<c:otherwise>

					<c:if test="${clickSearch and searchResultCount == 0}">
						<h2 class="section-title">検索結果：${searchResultCount}件</h2>
						<p>条件に一致するペットはいません。</p>
						<hr>
					</c:if>

					<h1>おすすめのペット</h1>

					<%-- 並び替え --%>
					<form action="HomeServlet" method="get">

						並び順： <select name="sort">
							<option value="">並び順を選択ください</option>
							<option value="matchRateDesc"
								${sort == "matchRateDesc" ? "selected" : ""}>マッチング度高い順</option>
							<option value="matchRateAsc"
								${sort == "matchRateAsc" ? "selected" : ""}>マッチング度低い順</option>
							<option value="priceDesc"
								${sort == "priceDesc" ? "selected" : ""}>価格高い順</option>
							<option value="priceAsc" ${sort == "priceAsc" ? "selected" : ""}>
								価格低い順</option>
							<option value="ageDesc" ${sort == "ageDesc" ? "selected" : ""}>
								年齢高い順</option>
							<option value="ageAsc" ${sort == "ageAsc" ? "selected" : ""}>
								年齢低い順</option>
						</select>
						<button type="submit">並び替え</button>

					</form>
					<br>

					<div class="pet-card-list">

						<c:forEach var="pet" items="${favoritePetList}">

							<div class="pet-card">

								<img class="pet-image" src="${pet.imagePath}" alt="ペット画像">

								<div class="pet-info">

									<p>
										<span class="pet-dot">・</span><span class="pet-label">店舗：</span>
										<a href="FacilityHomeServlet?facilityId=${pet.facilityID}">
											<c:out value="${pet.facilityName}" />
										</a>
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">名前：</span>
										<c:out value="${not empty pet.name ? pet.name : '名付けてください！'}" />
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">性別：</span>
										<c:out value="${pet.genderName}" />
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">年齢：</span>
										<c:out value="${pet.age}" />
										歳
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">価格：</span>
										<c:out value="${pet.price}" />
										円
									</p>

									<p>
										<span class="pet-dot">・</span><span class="pet-label">マッチング度：</span>
										<span class="match-rate"> <c:out
												value="${empty pet.matchRate ? 0 : pet.matchRate}" />%
										</span>
									</p>

									<a class="detail-button"
										href="PetDetailServlet?petID=${pet.petID}&from=home">
										🐾詳細を見る🐾 </a>

								</div>

							</div>

						</c:forEach>


					</div>
	</div>

	</c:otherwise>
	</c:choose>
	</c:if>

	</div>
</body>
</html>