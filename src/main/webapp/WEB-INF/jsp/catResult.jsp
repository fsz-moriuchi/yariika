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
<c:forEach var="q" items="${quizList}">
    <p>${q.question}</p>

    <input type="radio" name="q${q.catQuizId}" value="1">${q.choice1}<br>
    <input type="radio" name="q${q.catQuizId}" value="2">${q.choice2}<br>
    <input type="radio" name="q${q.catQuizId}" value="3">${q.choice3}<br>
    <input type="radio" name="q${q.catQuizId}" value="4">${q.choice4}<br>
</c:forEach>


<%--正答率表示 --%>
<c:if test="${not empty percent}">
<h3>結果</h3>
<p>正解数：${count} / ${totalCount}</p>
<p>正答率：${percent} %</p>
</c:if>
</body>
</html>