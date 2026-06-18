<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約確認画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="reserve-card">

<h1>予約確認</h1>

<p class="welcome-message">
    現在の予約内容を確認できます。
</p>

<c:choose>

<c:when test="${not empty reserve}">

    <table class="reserve-table" border="1" style="width: 100%">

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
            <td><img class="reserve-image" src="${reserve.imagePath}"width="100"></td>
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
    
<div class="facility-info-box">

<h3>店舗情報</h3>  
<hr>  
<p><span class="pet-dot">・</span><span class="pet-label">店舗名：</span>${reserve.facilityName}</p>
<p><span class="pet-dot">・</span><span class="pet-label">住所：</span>${reserve.address}</p>
<p><span class="pet-dot">・</span><span class="pet-label">電話：</span>${reserve.tel}</p>
<p><span class="pet-dot">・</span><span class="pet-label">メール：</span>${reserve.mail}</p>
<p><span class="pet-dot">・</span><span class="pet-label">営業時間：</span>${reserve.openTime}～${reserve.closeTime}</p>
<p><span class="pet-dot">・</span><span class="pet-label">休日：</span>${reserve.closedDay}</p>
<hr>

</div>

</c:when>

<c:otherwise>
    <p class="notice">現在、予約情報はありません。</p>
</c:otherwise>

</c:choose>

<p class="notice reserve-warning-note">予約日時の変更・キャンセルについては、直接店舗へお問い合わせください。<br>
無断キャンセルや遅刻など、他のお客様や店舗の運営に支障をきたす行為はおやめ下さい。</p>

<div class="form-button-area center-button-area">
		<form action="MyPageServlet" method="get">
    <button class="back-button" type="submit">戻る</button>
</form>
</div>


</div>


</div>

</body>
</html>
