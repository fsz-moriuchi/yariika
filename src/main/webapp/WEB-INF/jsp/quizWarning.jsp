<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>予約前の注意</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="warning-card">


<h1>予約前の注意事項</h1>

<p class="notice">
※予約に進むには、クイズで70％以上の正答率が必要です。<br>
※クイズ回答中や予約操作中に、他の方の予約が先に完了する場合があります。<br>
　その場合、このペットの予約を確定できないことがあります。</p>

<p class="quiz-message">
クイズは全10問の選択式で、所要時間は約2分です。<br>
ペットたちとの出会いに向けて、がんばりましょう！<br>
</p>

<form class="warning-form" action="QuizServlet" method="get">
<input type="submit" value="同意してクイズへ進む">
</form>

<br>

<div class="form-button-area center-button-area">
<a class="clear-button" href="PetDetailServlet?petID=${petID}">戻る</a>
</div>


</div>


</div>

</body>
</html>
