<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=11">
</head>

<body>

<header class="site-header">
    <div class="header-inner">
        <a href="HomeServlet" class="logo">Pet Matching</a>


    <nav class="header-nav">
        <a href="MyPageServlet">マイページ</a>
        <a href="MessageListServlet">
            メッセージ
            <span class="unread-badge">未読${userUnreadCount}件</span>
        </a>
        <a href="LogoutServlet" class="logout-link">ログアウト</a>
    </nav>
</div>


</header>

<main class="container">


<section class="hero-section">
    <div class="hero-card">
        <p class="small-title">Find your partner</p>
        <h1>あなたに合うペットを探す</h1>
        <p>
            条件検索やマッチング度を参考にしながら、気になるペットを見つけることができます。
        </p>
    </div>
</section>

<section class="search-section">
    <div class="section-header">
        <p class="small-title">Search</p>
        <h2 class="section-heading">条件で探す</h2>
    </div>

    <form action="HomeServlet" method="get" class="search-card">
        <input type="hidden" name="clickSearch" value="true">

        <div class="search-grid">

            <div class="form-row">
                <label>カテゴリー</label>
                <select name="categoryId">
                    <option value="">未指定</option>
                    <option value="1" ${selectedCategoryId == "1" ? "selected" : ""}>犬</option>
                    <option value="2" ${selectedCategoryId == "2" ? "selected" : ""}>猫</option>
                    <option value="3" ${selectedCategoryId == "3" ? "selected" : ""}>鳥</option>
                    <option value="4" ${selectedCategoryId == "4" ? "selected" : ""}>小動物</option>
                </select>
            </div>

            <div class="form-row">
                <label>性別</label>
                <select name="gender">
                    <option value="">未指定</option>
                    <option value="male" ${selectedGender == "male" ? "selected" : ""}>男の子</option>
                    <option value="female" ${selectedGender == "female" ? "selected" : ""}>女の子</option>
                </select>
            </div>

            <div class="form-row">
                <label>サイズ</label>
                <select name="pet_size">
                    <option value="">未指定</option>
                    <option value="small" ${selectedPet_size == "small" ? "selected" : ""}>小型</option>
                    <option value="medium" ${selectedPet_size == "medium" ? "selected" : ""}>中型</option>
                    <option value="large" ${selectedPet_size == "large" ? "selected" : ""}>大型</option>
                </select>
            </div>

            <div class="form-row">
                <label>年齢</label>
                <select name="ageRange">
                    <option value="">未指定</option>
                    <option value="age0" ${selectedAgeRange == "age0" ? "selected" : ""}>0歳</option>
                    <option value="age1to3" ${selectedAgeRange == "age1to3" ? "selected" : ""}>1～3歳</option>
                    <option value="age4up" ${selectedAgeRange == "age4up" ? "selected" : ""}>4歳以上</option>
                </select>
            </div>

            <div class="form-row">
                <label>価格</label>
                <select name="priceRange">
                    <option value="">未指定</option>
                    <option value="price0to100000" ${selectedPriceRange == "price0to100000" ? "selected" : ""}>100,000円以下</option>
                    <option value="price100001to300000" ${selectedPriceRange == "price100001to300000" ? "selected" : ""}>100,001円 ～ 300,000円</option>
                    <option value="price300001to500000" ${selectedPriceRange == "price300001to500000" ? "selected" : ""}>300,001円 ～ 500,000円</option>
                    <option value="price500001up" ${selectedPriceRange == "price500001up" ? "selected" : ""}>500,001円以上</option>
                </select>
            </div>

            <div class="form-row">
                <label>並び順</label>
                <select name="sort">
                    <option value="">並び順を選択ください</option>
                    <option value="matchRateDesc" ${sort == "matchRateDesc" ? "selected" : ""}>マッチング度高い順</option>
                    <option value="matchRateAsc" ${sort == "matchRateAsc" ? "selected" : ""}>マッチング度低い順</option>
                    <option value="priceDesc" ${sort == "priceDesc" ? "selected" : ""}>価格高い順</option>
                    <option value="priceAsc" ${sort == "priceAsc" ? "selected" : ""}>価格低い順</option>
                    <option value="ageDesc" ${sort == "ageDesc" ? "selected" : ""}>年齢高い順</option>
                    <option value="ageAsc" ${sort == "ageAsc" ? "selected" : ""}>年齢低い順</option>
                </select>
            </div>

        </div>

        <div class="form-row color-row">
            <label>色柄</label>

            <div class="checkbox-group">
                <label><input type="checkbox" name="color" value="white" ${selectedColorList != null && selectedColorList.contains('white') ? 'checked' : ''}>白</label>
                <label><input type="checkbox" name="color" value="black" ${selectedColorList != null && selectedColorList.contains('black') ? 'checked' : ''}>黒</label>
                <label><input type="checkbox" name="color" value="brown" ${selectedColorList != null && selectedColorList.contains('brown') ? 'checked' : ''}>茶色</label>
                <label><input type="checkbox" name="color" value="yellow" ${selectedColorList != null && selectedColorList.contains('yellow') ? 'checked' : ''}>黄</label>
                <label><input type="checkbox" name="color" value="gray" ${selectedColorList != null && selectedColorList.contains('gray') ? 'checked' : ''}>グレー</label>
                <label><input type="checkbox" name="color" value="spotted" ${selectedColorList != null && selectedColorList.contains('spotted') ? 'checked' : ''}>斑点模様</label>
                <label><input type="checkbox" name="color" value="brindle" ${selectedColorList != null && selectedColorList.contains('brindle') ? 'checked' : ''}>虎柄模様</label>
                <label><input type="checkbox" name="color" value="curlyHair" ${selectedColorList != null && selectedColorList.contains('curlyHair') ? 'checked' : ''}>巻き毛</label>
                <label><input type="checkbox" name="color" value="longHair" ${selectedColorList != null && selectedColorList.contains('longHair') ? 'checked' : ''}>長毛</label>
                <label><input type="checkbox" name="color" value="shortHair" ${selectedColorList != null && selectedColorList.contains('shortHair') ? 'checked' : ''}>短毛</label>
            </div>
        </div>

        <div class="button-row">
            <button type="submit" class="main-button">検索する</button>
            <a href="HomeServlet" class="sub-button">条件をクリア</a>
        </div>
    </form>

    <div class="notice-box">
        <p>※ 条件を指定せずに「検索」を押すと、すべてのペットを一覧で表示できます。</p>
        <p>※ アンケート未回答の場合、マッチング度は0%と表示されます。</p>
        <p>※ マッチング度を表示するには、マイページの「アンケート回答」からアンケートにご回答ください。</p>
    </div>
</section>

<section class="pet-section">

    <c:choose>

        <c:when test="${clickSearch and searchResultCount > 0}">
            <div class="section-header">
                <p class="small-title">Result</p>
                <h2 class="section-heading">検索結果：${searchResultCount}件</h2>
            </div>

            <div class="pet-grid">
                <c:forEach var="pet" items="${searchPetList}">
                    <article class="pet-card">
                        <img src="${pet.imagePath}" alt="ペット画像" class="pet-card-image">

                        <div class="pet-card-body">
                            <div class="pet-card-head">
                                <h3 class="pet-card-title">
                                    <c:out value="${not empty pet.name ? pet.name : '名付けてください！'}" />
                                </h3>

                                <span class="match-badge">
                                    <c:out value="${empty pet.matchRate ? 0 : pet.matchRate}" />%
                                </span>
                            </div>

                            <p class="shop-name">
                                店舗：
                                <a href="FacilityHomeServlet?facilityId=${pet.facilityID}">
                                    <c:out value="${pet.facilityName}" />
                                </a>
                            </p>

                            <div class="pet-info">
                                <p><span>カテゴリー</span><strong><c:out value="${pet.categoryName}" /></strong></p>
                                <p><span>性別</span><strong><c:out value="${pet.genderName}" /></strong></p>
                                <p><span>年齢</span><strong><c:out value="${pet.age}" />歳</strong></p>
                                <p><span>色柄</span><strong><c:out value="${pet.colorName}" /></strong></p>
                                <p><span>サイズ</span><strong><c:out value="${pet.petSizeName}" /></strong></p>
                                <p><span>価格</span><strong><c:out value="${pet.price}" />円</strong></p>
                            </div>

                            <a href="PetDetailServlet?petID=${pet.petID}&from=search" class="main-button card-button">
                                詳細を見る
                            </a>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </c:when>

        <c:otherwise>

            <c:if test="${clickSearch and searchResultCount == 0}">
                <div class="empty-box">
                    <h2>検索結果 0件</h2>
                    <p>条件に一致するペットはいません。</p>
                </div>
            </c:if>

            <div class="section-header sort-header">
                <div>
                    <p class="small-title">Recommendation</p>
                    <h2 class="section-heading">おすすめのペット</h2>
                </div>

                <form action="HomeServlet" method="get" class="sort-form">
                    <select name="sort">
                        <option value="">並び順を選択ください</option>
                        <option value="matchRateDesc" ${sort == "matchRateDesc" ? "selected" : ""}>マッチング度高い順</option>
                        <option value="matchRateAsc" ${sort == "matchRateAsc" ? "selected" : ""}>マッチング度低い順</option>
                        <option value="priceDesc" ${sort == "priceDesc" ? "selected" : ""}>価格高い順</option>
                        <option value="priceAsc" ${sort == "priceAsc" ? "selected" : ""}>価格低い順</option>
                        <option value="ageDesc" ${sort == "ageDesc" ? "selected" : ""}>年齢高い順</option>
                        <option value="ageAsc" ${sort == "ageAsc" ? "selected" : ""}>年齢低い順</option>
                    </select>
                    <button type="submit" class="sub-button">並び替え</button>
                </form>
            </div>

            <div class="pet-grid">
                <c:forEach var="pet" items="${favoritePetList}">
                    <article class="pet-card">
                        <img src="${pet.imagePath}" alt="ペット画像" class="pet-card-image">

                        <div class="pet-card-body">
                            <div class="pet-card-head">
                                <h3 class="pet-card-title">
                                    <c:out value="${not empty pet.name ? pet.name : '名付けてください！'}" />
                                </h3>

                                <span class="match-badge">
                                    <c:out value="${empty pet.matchRate ? 0 : pet.matchRate}" />%
                                </span>
                            </div>

                            <p class="shop-name">
                                店舗：
                                <a href="FacilityHomeServlet?facilityId=${pet.facilityID}">
                                    <c:out value="${pet.facilityName}" />
                                </a>
                            </p>

                            <div class="pet-info">
                                <p><span>性別</span><strong><c:out value="${pet.genderName}" /></strong></p>
                                <p><span>年齢</span><strong><c:out value="${pet.age}" />歳</strong></p>
                                <p><span>価格</span><strong><c:out value="${pet.price}" />円</strong></p>
                            </div>

                            <a href="PetDetailServlet?petID=${pet.petID}&from=home" class="main-button card-button">
                                詳細を見る
                            </a>
                        </div>
                    </article>
                </c:forEach>
            </div>

        </c:otherwise>
    </c:choose>

</section>


</main>

</body>
</html>
