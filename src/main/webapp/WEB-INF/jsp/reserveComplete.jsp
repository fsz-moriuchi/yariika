<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約完了画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="complete-card">


<h1>ご予約を承りました</h1>

<p class="welcome-message">
    ご予約ありがとうございます。以下の内容をご確認ください。
</p>

<div class="complete-info-box">

<p class="complete-section-title">予約情報</p>
<p><span class="pet-dot">・</span><span class="pet-label">日付：</span><c:out value="${reserveDate}" /></p>
<p><span class="pet-dot">・</span><span class="pet-label">時間：</span><c:out value="${reserveTime}" /></p>

</div>

<div class="complete-info-box">

<p class="complete-section-title">施設情報</p>
<p><span class="pet-dot">・</span><span class="pet-label">施設名：</span><c:out value="${facilityInformation.facilityName}" /></p>
<p><span class="pet-dot">・</span><span class="pet-label">住所：</span><c:out value="${facilityInformation.address}" /></p>
<p><span class="pet-dot">・</span><span class="pet-label">電話番号：</span><c:out value="${facilityInformation.tel}" /></p>

</div>

<div class="form-button-area center-button-area">
<a class="clear-button" href="HomeServlet">ホームに戻る</a> 
</div>

</div>


</div>

</body>
</html>
