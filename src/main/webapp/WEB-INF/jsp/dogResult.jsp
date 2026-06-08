<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>犬の基本知識問題の結果</title>
</head>
<body>
<h1>犬の基本知識問題の結果</h1>

<%--クイズ結果一覧表示 --%>
<c:forEach var="dqr" items="${dogResultList}">
<p>問題：${dqr.question}</p>
<p>あなたの回答：${dqr.dogUserAnswer}(${dqr.userAnswerText})</p>
<p>正解：${dqr.answer}(${dqr.correctAnswerText})</p>
<p>結果：
<c:choose>
<c:when test="${dqr.correct}">○</c:when>
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

</body>
</html>