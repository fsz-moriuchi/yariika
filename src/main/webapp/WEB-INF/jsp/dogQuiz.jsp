<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>犬の知識クイズ</title>
</head>


<body>
<h1>犬の基本知識問題</h1>
<form action="DogAnswerServlet" method="post">
<c:forEach var="dq" items="${dogQuizList}">
<p>${dq.question}</p>
<input type="radio" name="q${dq.id}" value="1" required>${dq.choice1}<br>
<input type="radio" name="q${dq.id}" value="2" required>${dq.choice2}<br>
<input type="radio" name="q${dq.id}" value="3" required>${dq.choice3}<br>
<input type="radio" name="q${dq.id}" value="4" required>${dq.choice4}<br>
</c:forEach>

<input type="submit" value="回答する">

</form>

</body>
</html>