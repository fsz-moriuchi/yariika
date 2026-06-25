<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>メッセージ画面</title>
<link rel="stylesheet" href="<c:out value='${pageContext.request.contextPath}'/>/style.css">
</head>

<body class="chat-page">

<!-- エラーメッセージ -->

<c:if test="${not empty errorMessage}"> <div class="error-box">
<c:out value="${errorMessage}" /> </div>
</c:if>

<h1>メッセージ</h1>

<div class="container">

<!-- タイトル -->

<c:choose>
<c:when test="${not empty sessionScope.userId}">
<c:choose>
<c:when test="${not empty messageList}"> <h2>店舗(<c:out value="${messageList[0].facilityName}"/>)とのメッセージ</h2>
</c:when>
<c:otherwise> <h2>店舗(<c:out value="${param.facilityId}"/>)とのメッセージ</h2>
</c:otherwise>
</c:choose>
</c:when>

<c:otherwise>
<c:choose>
<c:when test="${not empty messageList}"> <h2><c:out value="${messageList[0].userName}"/>さんとのメッセージ</h2>
</c:when>
<c:otherwise> <h2><c:out value="${param.userId}"/>さんとのメッセージ</h2>
</c:otherwise>
</c:choose>
</c:otherwise>
</c:choose>

<!-- チャット -->

<div class="chat-box">

<c:forEach var="m" items="${messageList}">
<c:choose>

    <%--ユーザー --%>
    <c:when test="${m.senderType == 'USER'}">
        <div class="message user">
            <div class="text"><c:out value="${m.messageText}" /></div>
            <div class="time"><c:out value="${m.formattedTime}" /></div>
        </div>
    </c:when>

    <%--店舗 --%>
    <c:otherwise>
        <div class="message facility">
            <div class="text"><c:out value="${m.messageText}" /></div>
            <div class="time"><c:out value="${m.formattedTime}" /></div>
        </div>
    </c:otherwise>

</c:choose>


</c:forEach>

</div>

<!-- フォーム -->

<div class="chat-footer">
<form action="MessageServlet" method="post" class="chat-form">

<input type="hidden" name="petID" value="<c:out value='${petDetail.petID}'/>">
<input type="hidden" name="userId" value="<c:out value='${param.userId}'/>">
<input type="hidden" name="facilityId" value="<c:out value='${param.facilityId}'/>">
<input type="hidden" name="from" value="<c:out value='${from}'/>">

<input type="text" name="messageText" required>
<button type="submit">送信</button>

</form>

<!-- 戻る -->

<c:choose>
<c:when test="${from != 'list'}"> <a href="PetDetailServlet?petID=<c:out value='${petDetail.petID}'/>&from=<c:out value='${from}'/>" class="back-link">
戻る </a>
</c:when>
<c:otherwise> <a href="MessageListServlet" class="back-link">戻る</a>
</c:otherwise>
</c:choose>

</div>

</div>

<script>
function scrollToBottom() {
    const chatBox = document.querySelector(".chat-box");
    chatBox.scrollTop = chatBox.scrollHeight;
}
window.onload = scrollToBottom;
</script>

</body>
</html>
