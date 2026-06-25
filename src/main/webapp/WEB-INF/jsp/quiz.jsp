<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>基本知識クイズ</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="quiz-card">


<h1>ペットを飼う前に知っておくべき基本知識クイズ</h1>

<p class="welcome-message">
    問題を読んで、正しいと思う選択肢を選んでください。
</p>

<form class="quiz-form" action="QuizAnswerServlet" method="post">
<c:forEach var="q" items="${quizList}">

<div class="quiz-question-box">

<p class="quiz-question-title">${q.question}</p>

<label>
<input type="radio" name="q${q.quizId}" value="1" required><c:out value="${q.choice1}" />
</label><br>

<label>
<input type="radio" name="q${q.quizId}" value="2" required><c:out value="${q.choice2}" />
</label><br>

<label>
<input type="radio" name="q${q.quizId}" value="3" required><c:out value="${q.choice3}" />
</label><br>

<label>
<input type="radio" name="q${q.quizId}" value="4" required><c:out value="${q.choice4}" />
</label><br>

</div>

</c:forEach>

<div class="form-button-area center-button-area">
<input type="submit" value="回答する">
</div>

</form>

</div>

</div>

</body>
</html>
