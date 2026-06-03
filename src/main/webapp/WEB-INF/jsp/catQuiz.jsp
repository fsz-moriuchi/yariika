<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>猫の基本知識問題</title>
</head>


<body>
<h1>猫の基本知識問題</h1>
<form action="CatAnswerServlet" method="post">
<c:forEach var="cq" items="${catQuizList}">
<p>${cq.question}</p>
<input type="radio" name="q${cq.id}" value="1" required>${cq.choice1}<br>
<input type="radio" name="q${cq.id}" value="2" required>${cq.choice2}<br>
<input type="radio" name="q${cq.id}" value="3" required>${cq.choice3}<br>
<input type="radio" name="q${cq.id}" value="4" required>${cq.choice4}<br>
</c:forEach>

<input type="submit" value="回答する">

</form>
</body>
</html>