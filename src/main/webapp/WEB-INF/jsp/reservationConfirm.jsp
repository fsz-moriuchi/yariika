<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約確認</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

	<div class="site-logo-wrap">
		<h1 class="site-logo">PET MATCH</h1>
	</div>

</div>

<div class="reservation-confirm-card">

<h1>予約確認</h1>

<p class="welcome-message">

</p>


<div class="reservation-filter-box">

	<p class="reservation-section-title">現在の予約情報一覧</p>

<form action="ReservationConfirmServlet" method="get" class="reservation-filter-form">

	<button type="submit" name="dateStatus" value="all">すべての予約</button>
	<button type="submit" name="dateStatus" value="today">今日の予約</button>
	<button type="submit" name="dateStatus" value="tomorrow">明日の予約</button>

</form>

</div>

<div class="reservation-count-box">
	<span class="reservation-count-label">表示件数</span>
	<span class="reservation-count-number">${reserveViewList.size()}</span>
	<span class="reservation-count-unit">件</span>
</div>

<div class="reservation-table-wrap">

<table border="1" style="width: 100%" class="reservation-table">

	<tr>
		<th>予約情報</th>
		<th>ペット情報</th>
		<th>お客様情報</th>
		<th>お客様連絡先</th>
		<th>予約日時</th>
		<th>操作</th>
	</tr>

	<c:forEach var="reserveView" items="${reserveViewList}">

		<tr>

			<td>
				<div class="reservation-photo-cell">

					<span class="reservation-id-badge">予約番号:${reserveView.reservationID}</span>

					<c:choose>
						<c:when test="${not empty reserveView.imagePath}">
							<img
								src="${pageContext.request.contextPath}/${reserveView.imagePath}"
								alt="ペット画像" width="120" height="120" style="object-fit: cover;" class="reservation-pet-image">
						</c:when>

						<c:otherwise>
            				<div class="no-image-box">画像なし</div>
        				</c:otherwise>
					</c:choose>

				</div>
			</td>

			<td>
				<p><span class="pet-dot">・</span><span class="pet-label">ペットID：</span>${reserveView.petID}</p>
				<p><span class="pet-dot">・</span><span class="pet-label">名前：</span>${reserveView.petName}</p>
				<p><span class="pet-dot">・</span><span class="pet-label">種類：</span>${reserveView.categoryName}</p>
				<p><span class="pet-dot">・</span><span class="pet-label">性別：</span>${reserveView.genderName}</p>
				<p><span class="pet-dot">・</span><span class="pet-label">年齢：</span>${reserveView.petAge}歳</p>
			</td>

			<td>
				<p><span class="pet-dot">・</span><span class="pet-label">ユーザーID：</span>${reserveView.userID}</p>
				<p><span class="pet-dot">・</span><span class="pet-label">名前：</span>${reserveView.userName}</p>
				<p><span class="pet-dot">・</span><span class="pet-label">年齢：</span>${reserveView.userAge}歳</p>
				<p><span class="pet-dot">・</span><span class="pet-label">性別：</span>${reserveView.userGender}</p>
			</td>

			<td>
				<p><span class="pet-dot">・</span><span class="pet-label">電話番号：</span>${reserveView.userTel}</p>
				<p><span class="pet-dot">・</span><span class="pet-label">メールアドレス：</span>${reserveView.userMail}</p>
			</td>

			<td>
				<span class="reservation-time-badge">${reserveView.formattedReserveTime}</span>
			</td>

			<td>
				<form action="ReservationEditServlet" method="get" class="reservation-edit-form">
					<input type="hidden" name="reservationID"
						value="${reserveView.reservationID}"> <input
						type="hidden" name="reserveTime"
						value="${reserveView.reserveTime}"> <input type="submit"
						value="予約日時の変更">
				</form>
			</td>
		</tr>

	</c:forEach>

</table>

</div>

<p class="reservation-note">予約の対応完了およびキャンセルの場合は、修正から予約の削除を行ってください。</p>

<form action="FacilityPageServlet" method="get" class="reservation-back-form">
	<div class="form-button-area center-button-area">
		<button type="submit">戻る</button>
	</div>
</form>

</div>


</div>

</body>
</html>
