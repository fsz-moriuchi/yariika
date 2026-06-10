<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クイズ結果</title>
</head>


<body>
<%--クイズ結果一覧表示 --%>
<c:forEach var="qr" items="${resultList}">
<p>問題：${qr.question}</p>
<p>あなたの回答：${qr.userAnswer}(${qr.userAnswerText})</p>
<p>正解：${qr.answer}(${qr.correctAnswerText})</p>
<p>結果：
<c:choose>
<c:when test="${qr.correct}">○</c:when>
<c:otherwise>×</c:otherwise>
</c:choose>
</p>
<hr>
</c:forEach>


<%--正答率表示 --%>
<c:if test="${not empty percent}">
<h3>結果</h3>
<p>正解数：${count} / ${totalCount}</p>
<p>正答率：${percent} %</p>
</c:if>


<%--クイズ結果の合否判定 --%>
<c:choose>
<c:when test="${percent>=70}">

<h3>合格</h3>

<c:choose>
<c:when test="${reserved}">
<p>★このペットは現在予約済みです★</p>
</c:when>
<c:otherwise>
<a href="QuizServlet">予約する</a><br>
</c:otherwise>
</c:choose>


</c:when>
<c:otherwise>
<h3>不合格</h3>
<form action="HomeServlet" method="get">
<button type="submit">戻る</button>
</form>
</c:otherwise>
</c:choose>
</body>
</html>