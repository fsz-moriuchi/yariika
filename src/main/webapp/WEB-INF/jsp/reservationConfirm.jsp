<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約確認</title>

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

		<section class="reservation-management-section">

			<div class="section-heading">
				<h1>予約確認</h1>
				<p>現在入っている予約の確認・変更・削除ができます。</p>
			</div>

			<div class="management-card">

				<h2 class="table-title">現在の予約情報一覧</h2>

				<c:choose>

					<c:when test="${not empty reservedDataList}">

						<table class="management-table reservation-table">
							<thead>
								<tr>
									<th>予約番号</th>
									<th>ペットID</th>
									<th>お客様ID</th>
									<th>予約日時</th>
									<th>操作</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="reserved" items="${reservedDataList}">

									<tr>
										<td class="id-cell">${reserved.reservationID}</td>
										<td>${reserved.petID}</td>
										<td>${reserved.userID}</td>
										<td class="date-cell">${reserved.formattedReserveTime}</td>

										<td>
											<div class="table-action-area">

												<form action="ReservationEditServlet" method="get">
													<input type="hidden" name="reservationID"
														value="${reserved.reservationID}">

													<input type="hidden" name="reserveTime"
														value="${reserved.reserveTime}">

													<input type="submit" value="予約日時の変更"
														class="table-button">
												</form>

												<form action="ReservationConfirmServlet" method="post">
													<input type="hidden" name="reservationID"
														value="${reserved.reservationID}">

													<input type="submit" value="この予約を削除"
														class="table-button danger-table-button">
												</form>

											</div>
										</td>
									</tr>

								</c:forEach>
							</tbody>
						</table>

					</c:when>

					<c:otherwise>
						<p class="empty-message">現在、予約情報はありません。</p>
					</c:otherwise>

				</c:choose>

			</div>

			<div class="notice-card">
				<h2>予約対応について</h2>
				<p>
					予約の対応完了およびキャンセルの場合は、予約の削除を行ってください。<br>
					日時変更を行う場合は、対象予約の「予約日時の変更」から変更できます。
				</p>
			</div>

			<div class="back-link-area">
				<a href="FacilityPageServlet" class="back-link">店舗管理ページに戻る</a>
			</div>

		</section>

	</main>

</body>
</html>