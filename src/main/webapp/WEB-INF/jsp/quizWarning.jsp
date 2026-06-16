<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約前の注意</title>
</head>


<body>
<h1>予約前の注意事項</h1>
<p>
※予約に進むには、クイズで70％以上の正答率が必要です。<br>
※クイズ回答中や予約操作中に、他の方の予約が先に完了する場合があります。<br>
　その場合、このペットの予約を確定できないことがあります。</p>

クイズは全10問の選択式で、所要時間は約2分です。<br>
ペットたちとの出会いに向けて、がんばりましょう！<br>

<form action="QuizServlet" method="get">
<input type="submit" value="同意してクイズへ進む">
</form>

<br>
<a href="PetDetailServlet?petID=${petID}">戻る</a>

</body>
</html>