<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約確認画面</title>
</head>
<body>
<h1>予約確認</h1>
<c:choose>

    <c:when test="${not empty reserve}">

        <table border="1" style="width: 100%">

            <tr>
                <th>予約番号</th>
                <th>ペットID</th>
                <th>ユーザーID</th>
                <th>予約日時</th>
            </tr>

            <tr>
                <td>${reserve.reservationID}</td>
                <td>${reserve.petID}</td>
                <td>${reserve.userID}</td>
                <td>${reserve.formattedReserveTime}</td>
            </tr>

        </table>

    </c:when>

    <c:otherwise>
        <p>現在、予約情報はありません。</p>
    </c:otherwise>

</c:choose>

<p>予約日時の変更・キャンセルについては、直接店舗へお問い合わせください。<br>
無断キャンセルや遅刻など、他のお客様や店舗の運営に支障をきたす行為はおやめ下さい。</p>
		
		<form action="MyPageServlet" method="get">
    <button type="submit">戻る</button>
</form>
		
</body>
</html>