<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>クイズ結果</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">


<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="quiz-result-card">

<h1>クイズ結果</h1>

<%--クイズ結果一覧表示 --%>

<div class="quiz-result-list">

<c:forEach var="qr" items="${resultList}">

<div class="quiz-result-item">

<p><span class="pet-dot">・</span><span class="pet-label">問題：</span>${qr.question}</p>
<p><span class="pet-dot">・</span><span class="pet-label">あなたの回答：</span>${qr.userAnswer}(${qr.userAnswerText})</p>
<p><span class="pet-dot">・</span><span class="pet-label">正解：</span>${qr.answer}(${qr.correctAnswerText})</p>
<p><span class="pet-dot">・</span><span class="pet-label">結果：</span>
<c:choose>
<c:when test="${qr.correct}"><span class="quiz-correct">○</span></c:when>
<c:otherwise><span class="quiz-wrong">×</span></c:otherwise>
</c:choose>
</p>

</div>

</c:forEach>

</div>

<%--正答率表示 --%>
<c:if test="${not empty percent}">

<div class="quiz-score-box">
<h3>結果</h3>
<p>正解数：${count} / ${totalCount}</p>
<p>正答率：${percent} %</p>
</div>
</c:if>

<%--クイズ結果の合否判定 --%>
<c:choose>
<c:when test="${percent>=70}">

<div class="quiz-pass-box">

<h3>合格</h3>

<c:choose>
<c:when test="${reserved}">

<p>★このペットは現在予約済みです★</p>
</c:when>
<c:otherwise>
<a class="detail-button" href="ReserveServlet">予約する</a><br>
</c:otherwise>
</c:choose>

</div>

</c:when>
<c:otherwise>

<div class="quiz-fail-box">

<h3>不合格</h3>
<p>クイズの合格基準は70％以上です。<br>
もう一度チャレンジしてみましょう。<br>
ペットたちも、あなたに会える日を楽しみにしています。
</p>
<form action="HomeServlet" method="get">
<button class="back-button" type="submit">戻る</button>
</form>

</div>

</c:otherwise>
</c:choose>

</div>

</div>

</body>
</html>
