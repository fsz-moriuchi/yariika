<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header class="site-header">
		<div class="header-inner">
			<a href="HomeServlet" class="logo">Pet Matching</a>

			<nav class="header-nav">
				<c:if test="${not empty sessionScope.userId}">
					<a href="MyPageServlet">マイページ</a>
				</c:if>

				<c:if test="${not empty sessionScope.facilityId}">
					<a href="FacilityPageServlet">施設専用ページ</a>
				</c:if>

				<a href="LogoutServlet" class="logout-link">ログアウト</a>
			</nav>
		</div>
	</header>

	<main class="container">

		<c:if test="${not empty sessionScope.userId}">

			<section class="page-section">
				<div class="section-heading">
					<h1>おすすめのペット</h1>
					<p>あなたに合いそうなペットを表示しています。</p>
				</div>

				<form action="HomeServlet" method="get" class="sort-form">
					<label for="sort">並び順：</label>

					<select name="sort" id="sort">
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

				<div class="pet-grid">
					<c:forEach var="pet" items="${favoritePetList}">

						<div class="pet-card">
							<img src="${pageContext.request.contextPath}/${pet.imagePath}" class="pet-image">

							<div class="pet-card-body">
								<p class="facility-name">
									店舗：
									<a href="FacilityHomeServlet?facilityId=${pet.facilityID}">
										<c:out value="${pet.facilityName}" />
									</a>
								</p>

								<h2 class="pet-name">
									<c:out value="${pet.name}" />
								</h2>

								<p>性別：<c:out value="${pet.gender}" /></p>
								<p>年齢：<c:out value="${pet.age}" />歳</p>
								<p>価格：<c:out value="${pet.price}" />円</p>

								<p class="match-rate">
									マッチング度：<c:out value="${pet.matchRate}" />%
								</p>

								<a href="PetDetailServlet?petID=${pet.petID}" class="detail-button">
									詳細を見る
								</a>
							</div>
						</div>

					</c:forEach>
				</div>
			</section>

		</c:if>


		<c:if test="${not empty sessionScope.facilityId}">

			<section class="page-section">
				<div class="section-heading">
					<h1>施設ダッシュボード</h1>
					<p>予約状況や登録ペットの情報を確認できます。</p>
				</div>

				<div class="dashboard-grid">

					<div class="dashboard-card">
						<h2>本日の予約数</h2>
						<p class="dashboard-number">
							<c:out value="${countTodayReserve}" />
							<span>件</span>
						</p>
					</div>

					<div class="dashboard-card">
						<h2>登録ペット数</h2>
						<p class="dashboard-number">
							<c:out value="${petCount}" />
							<span>匹</span>
						</p>
					</div>

					<div class="dashboard-card">
						<h2>施設ページ累計アクセス数</h2>
						<p class="dashboard-number">
							<c:out value="${viewCount}" />
							<span>回</span>
						</p>
					</div>

					<div class="dashboard-card wide-card">
						<h2>次の予約</h2>

						<c:choose>
							<c:when test="${not empty reserve}">
								<div class="info-list">
									<p>予約ID：${reserve.reservationID}</p>
									<p>ペットID：${reserve.petID}</p>
									<p>ユーザーID：${reserve.userID}</p>
									<p>予約日時：${reserve.formattedReserveTime}</p>
								</div>
							</c:when>

							<c:otherwise>
								<p class="empty-message">次の予約はありません。</p>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="dashboard-card wide-card">
						<h2>最近追加したペット</h2>

						<c:choose>
							<c:when test="${not empty latestPet}">
								<div class="latest-pet">
									<img src="${pageContext.request.contextPath}/${latestPet.imagePath}"
										class="latest-pet-image">

									<div class="info-list">
										<p>名前：<c:out value="${latestPet.name}" /></p>
										<p>性別：<c:out value="${latestPet.gender}" /></p>
										<p>年齢：<c:out value="${latestPet.age}" />歳</p>
										<p>価格：<c:out value="${latestPet.price}" />円</p>
									</div>
								</div>
							</c:when>

							<c:otherwise>
								<p class="empty-message">登録されているペットはありません。</p>
							</c:otherwise>
						</c:choose>
					</div>

				</div>
			</section>

		</c:if>

	</main>

</body>
</html>