<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>基本知識クイズ</title>
</head>


<body>
<h1>ペットを飼う前に知っておくべき基本知識クイズ</h1>

<form action="QuizAnswerServlet" method="post">
<c:forEach var="q" items="${quizList}">
<p>${q.question}</p>
<input type="radio" name="q${q.quizId}" value="1" required>${q.choice1}<br>
<input type="radio" name="q${q.quizId}" value="2" required>${q.choice2}<br>
<input type="radio" name="q${q.quizId}" value="3" required>${q.choice3}<br>
<input type="radio" name="q${q.quizId}" value="4" required>${q.choice4}<br>
</c:forEach>

<input type="submit" value="回答する">

</form>

</body>
</html>