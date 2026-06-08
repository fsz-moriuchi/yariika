<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>猫の基本知識問題の結果</title>
</head>


<body>
<h1>猫の基本知識問題の結果</h1>

<%--クイズ結果一覧表示 --%>
<c:forEach var="cqr" items="${catResultList}">
<p>問題：${cqr.question}</p>
<p>あなたの回答：${cqr.catUserAnswer}(${cqr.userAnswerText})</p>
<p>正解：${cqr.answer}(${cqr.correctAnswerText})</p>
<p>結果：
<c:choose>
<c:when test="${cqr.correct}">○</c:when>
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