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

	<!-- ユーザー用メニュー -->
	<c:if test="${not empty sessionScope.userId}">
		<a href="MyPageServlet">マイページへ</a>
		<br>
	</c:if>

	<!-- 施設用メニュー -->
	<c:if test="${not empty sessionScope.facilityId}">
		<a href="FacilityPageServlet">施設専用ページへ</a>
		<br>
	</c:if>

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
			色柄： <br> <label> <input type="checkbox" name="color"
				value="white"
				${selectedColorArrayList.contains('white') ? 'checked' : ''}>
				白
			</label> <label> <input type="checkbox" name="color" value="black"
				${selectedColorArrayList.contains('black') ? 'checked' : ''}>
				黒
			</label> <label> <input type="checkbox" name="color" value="brown"
				${selectedColorArrayList.contains('brown') ? 'checked' : ''}>
				茶色
			</label> <label> <input type="checkbox" name="color" value="yellow"
				${selectedColorArrayList.contains('yellow') ? 'checked' : ''}>
				黄
			</label> <label> <input type="checkbox" name="color" value="gray"
				${selectedColorArrayList.contains('gray') ? 'checked' : ''}>
				グレー
			</label> <br> <label> <input type="checkbox" name="color"
				value="spotted"
				${selectedColorArrayList.contains('spotted') ? 'checked' : ''}>
				斑点模様
			</label> <label> <input type="checkbox" name="color" value="brindle"
				${selectedColorArrayList.contains('brindle') ? 'checked' : ''}>
				虎柄模様
			</label> <label> <input type="checkbox" name="color"
				value="curlyHair"
				${selectedColorArrayList.contains('curlyHair') ? 'checked' : ''}>
				巻き毛
			</label> <label> <input type="checkbox" name="color" value="longHair"
				${selectedColorArrayList.contains('longHair') ? 'checked' : ''}>
				長毛
			</label> <label> <input type="checkbox" name="color"
				value="shortHair"
				${selectedColorArrayList.contains('shortHair') ? 'checked' : ''}>
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
				<option value="age0" ${selectedAgeRange == "age0" ? "selected" : ""}>0歳</option>
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

			<button type="submit">検索</button>
		</form>

		<form action="HomeServlet" method="get">
			<button type="submit">クリア</button>
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

				<h1>検索結果：${searchResultCount}件</h1>
				<c:forEach var="pet" items="${searchPetList}">
					店舗：
		            <a href="FacilityHomeServlet?facilityId=${pet.facilityID}">
						<c:out value="${pet.facilityName}" />
					</a>
					<br>
					<img src="${pet.imagePath}" width="200">
					<br>
            			名前：
            		    <c:out
						value="${not empty pet.name ? pet.name : '名付けてください！'}" />
					<br>カテゴリー：
            			<c:out value="${pet.categoryName}" />
					<br>性別：
		            <c:out value="${pet.genderName}" />
					<br>年齢：
            			<c:out value="${pet.age}" />歳
            			<br>色柄：
            			<c:out value="${pet.colorName}" />
					<br>サイズ：
            			<c:out value="${pet.petSizeName}" />
					<br>価格：
      				<c:out value="${pet.price}" />円
            			<br>マッチング度：
					<c:out value="${empty pet.matchRate ? 0 : pet.matchRate}" />%
            			<br>
					<a href="PetDetailServlet?petID=${pet.petID}&from=search">
						詳細を見る </a>
					<hr>
				</c:forEach>

			</c:when>

			<%-- 初期表示・検索0件 --%>
			<c:otherwise>

				<c:if test="${clickSearch and searchResultCount == 0}">
					<h1>検索結果 0 件</h1>
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
						<option value="priceDesc" ${sort == "priceDesc" ? "selected" : ""}>
							価格高い順</option>
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

				<c:forEach var="pet" items="${favoritePetList}">
		
		            店舗：
		            <a href="FacilityHomeServlet?facilityId=${pet.facilityID}">
						<c:out value="${pet.facilityName}" />
					</a>
					<br>
					<img src="${pet.imagePath}" width="200">
					<br>名前：
		            <c:out
						value="${not empty pet.name ? pet.name : '名付けてください！'}" />
					<br>性別：
		            <c:out value="${pet.genderName}" />
					<br>年齢：
		            <c:out value="${pet.age}" />歳
		            <br>価格：
            			<c:out value="${pet.price}" />円
            			<br>マッチング度：
					<c:out value="${empty pet.matchRate ? 0 : pet.matchRate}" />%
            			<br>

					<a href="PetDetailServlet?petID=${pet.petID}&from=home">詳細を見る </a>
					<hr>
				</c:forEach>

			</c:otherwise>
		</c:choose>
	</c:if>

	<!-- ================= 施設側 ================= -->
	<c:if test="${not empty sessionScope.facilityId}">

		<h2>ダッシュボード</h2>

		本日の予約数：
		<c:out value="${countTodayReserve}" />件
		<br>

		<!-- 次の予約 -->
		<c:if test="${not empty reserve}">
			<br>
			次の予約
			<br>
			予約ID：${reserve.reservationID}
			<br>
			ペットID：${reserve.petID}
			<br>
			ユーザーID：${reserve.userID}
			<br>
			予約日時：${reserve.formattedReserveTime}
			<br>
		</c:if>

		<br>
		登録しているペット数：
		<c:out value="${petCount}" />匹
		<br>

		<!-- 最近追加したペット -->
		<c:if test="${not empty latestPet}">
			<br>
			最近追加したペット
			<br>
			<img src="${latestPet.imagePath}" width="200">
			<br>
			名前：
			<c:out value="${latestPet.name}" />
			<br>
			性別：
			<c:out value="${latestPet.gender}" />
			<br>
			年齢：
			<c:out value="${latestPet.age}" />歳
			<br>
			価格：
			<c:out value="${latestPet.price}" />円
			<br>
		</c:if>
		施設ページの累計アクセス数：
		${viewCount}回
		<br>
	</c:if>

	<!-- ログアウト -->
	<a href="LogoutServlet">ログアウト</a>

</body>
</html>