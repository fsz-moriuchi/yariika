<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メッセージ画面</title>
</head>


<body>
<h1>メッセージ</h1>

<c:forEach var="m" items="${messageList}">
<c:choose>
<c:when test="${m.senderType == 'USER'}">
    <p style="text-align:right;">
	ユーザー：${m.messageText}(${m.createdAt })
</p>
</c:when>

<%--店舗側 --%>
<c:otherwise>
<p style="text-align:left;">
店舗：${m.messageText} (${m.createdAt})
</p>
</c:otherwise>
</c:choose>
</c:forEach>


<form action="MessageServlet" method="post">
<input type="hidden" name="petID" value="${petDetail.petID}">
<input type="hidden" name="userId" value="${param.userId}">
<%--<input type="hidden" name="facilityId" value="${petDetail.facilityID}">--%>

<p><input type="text" name="messageText"></p>
<button type="submit">送信</button>
</form>

</body>
</html>