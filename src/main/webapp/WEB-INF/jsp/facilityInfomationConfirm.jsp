<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>施設情報確認</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header class="site-header">
		<div class="header-inner">
			<a href="HomeServlet" class="logo">Pet Matching</a>

			<nav class="header-nav">
				<a href="HomeServlet">ホーム</a>
				<a href="FacilityPageServlet">店舗管理ページ</a>
				<a href="LogoutServlet" class="logout-link">ログアウト</a>
			</nav>
		</div>
	</header>

	<main class="container">

		<section class="facility-info-section">

			<div class="section-heading">
				<h1>施設情報確認</h1>
				<p>登録されている店舗情報を確認できます。</p>
			</div>

			<div class="facility-info-card">

				<h2>
					<c:out value="${facilityInfo.facilityName}" />
				</h2>

				<div class="facility-info-list">

					<div class="facility-info-row">
						<span>施設名</span>
						<strong><c:out value="${facilityInfo.facilityName}" /></strong>
					</div>

					<div class="facility-info-row">
						<span>電話番号</span>
						<strong><c:out value="${facilityInfo.tel}" /></strong>
					</div>

					<div class="facility-info-row">
						<span>住所</span>
						<strong><c:out value="${facilityInfo.address}" /></strong>
					</div>

					<div class="facility-info-row">
						<span>メールアドレス</span>
						<strong><c:out value="${facilityInfo.mail}" /></strong>
					</div>

					<div class="facility-info-row">
						<span>営業時間</span>
						<strong>
							${fn:substring(facilityInfo.openTime, 0, 5)}
							〜
							${fn:substring(facilityInfo.closeTime, 0, 5)}
						</strong>
					</div>

					<div class="facility-info-row">
						<span>定休日</span>
						<strong>
							<c:choose>
								<c:when test="${not empty facilityClosedDayList}">
									<c:forEach var="closedDay" items="${facilityClosedDayList}" varStatus="status">
										<c:choose>
											<c:when test="${closedDay == 'MONDAY'}">月曜日</c:when>
											<c:when test="${closedDay == 'TUESDAY'}">火曜日</c:when>
											<c:when test="${closedDay == 'WEDNESDAY'}">水曜日</c:when>
											<c:when test="${closedDay == 'THURSDAY'}">木曜日</c:when>
											<c:when test="${closedDay == 'FRIDAY'}">金曜日</c:when>
											<c:when test="${closedDay == 'SATURDAY'}">土曜日</c:when>
											<c:when test="${closedDay == 'SUNDAY'}">日曜日</c:when>
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

				</div>

				<div class="facility-info-actions">

					<form action="FacilityInformationEditServlet" method="get">
						<input type="submit" value="施設情報を変更" class="main-button">
					</form>

					<a href="FacilityPageServlet" class="back-link">店舗管理ページに戻る</a>

				</div>

			</div>

		</section>

	</main>

</body>
</html>