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
                <th>写真</th>
                <th>ペット情報</th>
                <th>お客様情報</th>
                <th>お客様連絡先</th>
                <th>予約日時</th>
            </tr>

            <tr>
                <td>予約番号：${reserve.reservationID}</td>
                <td><img src="${reserve.imagePath}"width="100"></td>
                <td>ペットID：${reserve.petID}<br>
                	名前：${reserve.petName}<br>
                	種類：${reserve.categoryName}<br>
                    性別：${reserve.gender}<br>
                    年齢：${reserve.age}歳</td>
                <td>ユーザーID：${reserve.userID}<br>
                	名前：${reserve.userName}<br>
                	年齢：${reserve.userAge}歳<br>
                	性別：${reserve.userGenderJa}</td>
                <td>電話番号：${reserve.userTel}<br>
                	メールアドレス：${reserve.userMail}</td>
                <td>${reserve.formattedReserveTime}</td>
            </tr>

        </table>
        
    <h3>店舗情報</h3>    
    <p>店舗名：${reserve.facilityName}</p>
    <p>住所：${reserve.address}</p>
    <p>電話：${reserve.tel}</p>
    <p>メール：${reserve.mail}</p>
    <p>営業時間：${reserve.openTime}～${reserve.closeTime}</p>
    <p>休日：${reserve.closedDay}</p>
    <hr>

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