<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット詳細画面</title>
</head>
<body>
<h1>プロフィール</h1>
<c:out value="${petDetail.category}"/><br>
<c:choose>
<c:when test="${not empty petDetail.name}">
<c:out value="${petDetail.name}"/>
</c:when>
<c:otherwise>
名付けてください！
</c:otherwise>
</c:choose><br>
<c:out value="${petDetail.gender}"/><br>
<c:out value="${petDetail.age}"/><br>
<c:out value="${petDetail.color}"/><br>
<c:out value="${petDetail.pet_size}"/><br>
<c:out value="${petDetail.vaccine}"/><br>
<c:out value="${petDetail.price}"/><br>
<c:out value="${petDetail.commentText}"/><br>
<a href="ReserveServlet">予約する</a>
<a href="HomeServlet">ホームに戻る</a>
</body>
</html>