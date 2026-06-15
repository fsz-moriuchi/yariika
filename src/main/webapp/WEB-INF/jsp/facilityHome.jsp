<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>${facility.facilityName}</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
<header class="site-header">
	<div class="header-inner">
		<a href="HomeServlet" class="logo">Pet Matching</a>

		<nav class="header-nav">
			<a href="HomeServlet">ホーム</a>
			<c:if test="${not empty sessionScope.userId}">
				<a href="MyPageServlet">マイページ</a>
			</c:if>
			<a href="LogoutServlet" class="logout-link">ログアウト</a>
		</nav>
	</div>
</header>

<main class="container">

	<section class="facility-public-section">

		<div class="facility-public-hero">
			<div>
				<p class="facility-public-label">PET SHOP</p>
				<h1>${facility.facilityName}</h1>
				<p class="facility-public-lead">
					店舗情報やおすすめペット、人気のペットを確認できます。
				</p>
			</div>

			<div class="facility-view-count">
				<span>閲覧数</span>
				<strong>${viewCount}回</strong>
			</div>
		</div>

		<div class="facility-public-card">

			<h2>店舗情報</h2>

			<div class="facility-public-info-list">

				<div class="facility-public-info-row">
					<span>住所</span>
					<strong>${facility.address}</strong>
				</div>

				<div class="facility-public-info-row">
					<span>電話番号</span>
					<strong>${facility.tel}</strong>
				</div>

				<div class="facility-public-info-row">
					<span>営業時間</span>
					<strong>${facility.openTimeDisplay} ～ ${facility.closeTimeDisplay}</strong>
				</div>

				<div class="facility-public-info-row">
					<span>定休日</span>
					<strong>
						<c:choose>
							<c:when test="${not empty closedDayList}">
								<c:forEach var="closedDay" items="${closedDayList}" varStatus="status">

									<c:choose>
										<c:when test="${closedDay == 'MONDAY'}">月曜日</c:when>
										<c:when test="${closedDay == 'TUESDAY'}">火曜日</c:when>
										<c:when test="${closedDay == 'WEDNESDAY'}">水曜日</c:when>
										<c:when test="${closedDay == 'THURSDAY'}">木曜日</c:when>
										<c:when test="${closedDay == 'FRIDAY'}">金曜日</c:when>
										<c:when test="${closedDay == 'SATURDAY'}">土曜日</c:when>
										<c:when test="${closedDay == 'SUNDAY'}">日曜日</c:when>
										<c:otherwise>${closedDay}</c:otherwise>
									</c:choose>

									<c:if test="${not status.last}">、</c:if>

								</c:forEach>
							</c:when>

							<c:otherwise>
								定休日なし
							</c:otherwise>
						</c:choose>
					</strong>
				</div>

				<div class="facility-public-info-row">
					<span>メールアドレス</span>
					<strong>${facility.mail}</strong>
				</div>

			</div>

		</div>


		<section class="facility-public-block">

			<div class="section-heading compact-heading">
				<h2>おすすめペット</h2>
				<p>この店舗がおすすめしているペットです。</p>
			</div>

			<c:choose>
				<c:when test="${not empty favoritePet}">

					<div class="featured-pet-card">

						<img src="${pageContext.request.contextPath}/${favoritePet.imagePath}"
							class="featured-pet-image">

						<div class="featured-pet-body">
							<p class="featured-label">おすすめ</p>

							<h3>${favoritePet.name}</h3>

							<p>性別：${favoritePet.gender}</p>
							<p>年齢：${favoritePet.age}歳</p>
							<p>価格：${favoritePet.price}円</p>

							<a href="PetDetailServlet?petID=${favoritePet.petID}"
								class="detail-button">
								詳細を見る
							</a>
						</div>

					</div>

				</c:when>

				<c:otherwise>
					<div class="empty-card">
						<p>現在おすすめ中のペットはいません。</p>
					</div>
				</c:otherwise>
			</c:choose>

		</section>


		<section class="facility-public-block">

			<div class="section-heading compact-heading">
				<h2>人気ランキング</h2>
				<p>お気に入り登録数が多いペットです。</p>
			</div>

			<c:choose>
				<c:when test="${not empty rankingList}">

					<div class="ranking-grid">

						<c:forEach var="pet" items="${rankingList}" varStatus="status">

							<div class="ranking-card">

								<div class="ranking-badge">
									${status.count}位
								</div>

								<img src="${pageContext.request.contextPath}/${pet.imagePath}"
									class="ranking-pet-image">

								<div class="ranking-card-body">
									<h3>${pet.name}</h3>
									<p>お気に入り数：${pet.favoriteCount}件</p>

									<a href="PetDetailServlet?petID=${pet.petID}"
										class="detail-button">
										詳細を見る
									</a>
								</div>

							</div>

						</c:forEach>

					</div>

				</c:when>

				<c:otherwise>
					<div class="empty-card">
						<p>ランキング対象のペットはいません。</p>
					</div>
				</c:otherwise>
			</c:choose>

		</section>


		<section class="facility-public-block">

			<div class="section-heading compact-heading">
				<h2>所属ペット一覧</h2>
				<p>この店舗に登録されているペットです。</p>
			</div>

			<c:choose>
				<c:when test="${not empty petList}">

					<div class="pet-grid">

						<c:forEach var="pet" items="${petList}">

							<div class="pet-card">

								<img src="${pageContext.request.contextPath}/${pet.imagePath}"
									class="pet-image">

								<div class="pet-card-body">

									<h2 class="pet-name">${pet.name}</h2>

									<p>性別：${pet.genderName}</p>
									<p>年齢：${pet.age}歳</p>
									<p>価格：${pet.price}円</p>

									<a href="PetDetailServlet?petID=${pet.petID}"
										class="detail-button">
										詳細を見る
									</a>

								</div>

							</div>

						</c:forEach>

					</div>

				</c:when>

				<c:otherwise>
					<div class="empty-card">
						<p>登録されているペットはいません。</p>
					</div>
				</c:otherwise>
			</c:choose>

		</section>


		<div class="back-link-area">
			<a href="HomeServlet" class="back-link">ホームへ戻る</a>
		</div>

	</section>

</main>
```

</body>
</html>
