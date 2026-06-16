<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約確認</title>
</head>

<body>
	<h1>予約確認</h1>


	現在の予約情報一覧
	<form action="ReservationConfirmServlet" method="get">

		<button type="submit" name="dateStatus" value="all">すべての予約</button>
		<button type="submit" name="dateStatus" value="today">今日の予約</button>
		<button type="submit" name="dateStatus" value="tomorrow">明日の予約</button>

	</form>
	<p>表示件数：${reservedDataList.size()}件</p>
	<table border="1" style="width: 100%">

		<tr>
			<th>予約番号</th>
			<th>写真</th>
			<th>ペット情報</th>
			<th>お客様情報</th>
			<th>お客様連絡先</th>
			<th>予約日時</th>
			<th>操作</th>
		</tr>

		<c:forEach var="reserveView" items="${reserveViewList}">

			<tr>

				<td>予約番号:${reserveView.reservationID}</td>

				<td><c:choose>
						<c:when test="${not empty reserveView.imagePath}">
							<img
								src="${pageContext.request.contextPath}/${reserveView.imagePath}"
								alt="ペット画像" width="120" height="120" style="object-fit: cover;">
						</c:when>

						<c:otherwise>
                画像なし
            </c:otherwise>
					</c:choose></td>

				<td>ペットID：${reserveView.petID}<br>
					名前：${reserveView.petName}<br> 種類：${reserveView.categoryName}<br>
					性別：${reserveView.genderName} <br> 年齢：${reserveView.petAge}歳
				</td>

				<td>ユーザーID：${reserveView.userID}<br>
					名前：${reserveView.userName}<br> 年齢：${reserveView.userAge}歳<br>
					性別：${reserveView.userGender}
				</td>

				<td>電話番号：${reserveView.userTel}<br>
					メールアドレス：${reserveView.userMail}
				</td>

				<td>${reserveView.formattedReserveTime}</td>

				<td>
					<form action="ReservationEditServlet" method="get">
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

	<p>予約の対応完了およびキャンセルの場合は、修正から予約の削除を行ってください。</p>

	<form action="FacilityPageServlet" method="get">
		<button type="submit">戻る</button>
	</form>
</body>
</html>