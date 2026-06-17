<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">


<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="reserve-input-card">


<h1>日時の予約</h1>

<p class="notice">見学のご予約は、3日後から1週間以内の日程で受け付けております。<br>
所要時間は30分程度です。前後のお時間に余裕をもってご予約ください。</p>

<form class="reserve-input-form" action="ReserveServlet" method="post">
<p>日付を選択してください<input type="date" name="reserveDateStr" value="${reserveDate}" min="${minDate}" max="${maxDate}" required><input type="submit" value="この日付で検索する"></p>
</form>

<form class="reserve-input-form" action ="ReserveCompleteServlet" method="post">
<c:if test="${not empty timeList}">
<p>時間を選択してください</p>

<div class="reserve-time-list">
<c:forEach var="time" items="${timeList}">
<label>
<input type="radio" name="reserveTime" value="${time}" required>
<c:out value="${time}" />
</label><br>
</c:forEach>
</div>

<div class="form-button-area center-button-area">
<input type="submit" value="この日時で予約する">
</div>
</c:if>

<c:if test="${not empty errorMsg}">

<p class="error-message">
<c:out value="${errorMsg}"/>
</p>
</c:if>
</form>

<div class="form-button-area center-button-area">
<a class="clear-button" href="ReserveCancelServlet">キャンセルしてホームに戻る</a>
</div>

</div>


</div>

</body>
</html>
