<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>メッセージ画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body class="chat-page">

<c:if test="${not empty errorMessage}">

<script>
alert("${errorMessage}");
window.location.href = "MessageListServlet";
</script>

</c:if>

<h1>メッセージ</h1>

<div class="container">

<%-- ★ ① タイトルを出し分け --%>
<c:choose>
<c:when test="${not empty sessionScope.userId}">
<c:choose>
<c:when test="${not empty messageList}"> <h2>店舗(${messageList[0].facilityName})とのメッセージ</h2>
</c:when>
<c:otherwise> <h2>店舗(${param.facilityId})とのメッセージ</h2>
</c:otherwise>
</c:choose>
</c:when>


<c:otherwise>
	<c:choose>
		<c:when test="${not empty messageList}">
			<h2>${messageList[0].userName}さんとのメッセージ</h2>
		</c:when>
		<c:otherwise>
			<h2>${param.userId}さんとのメッセージ</h2>
		</c:otherwise>
	</c:choose>
</c:otherwise>


</c:choose>

<div class="chat-box">

<%-- ★ ② メッセージ表示 --%>
<c:forEach var="m" items="${messageList}">
	<c:choose>

		<%-- ユーザー --%>
		<c:when test="${m.senderType == 'USER'}">
			<div class="message user">
				<div class="text">${m.messageText}</div>
				<div class="time">${m.formattedTime}</div>
			</div>
		</c:when>

		<%-- 店舗 --%>
		<c:otherwise>
			<div class="message facility">
				<div class="text">${m.messageText}</div>
				<div class="time">${m.formattedTime}</div>
			</div>
		</c:otherwise>

	</c:choose>
</c:forEach>


</div>

<%-- 入力フォーム --%>

<div class="chat-footer">
<form action="MessageServlet" method="post" class="chat-form">
	<input type="hidden" name="petID" value="${petDetail.petID}">
	<input type="hidden" name="userId" value="${param.userId}">
	<input type="hidden" name="facilityId" value="${param.facilityId}">
	<input type="hidden" name="from" value="${from}">


<input type="text" name="messageText">
<button type="submit">送信</button>


</form>

<%-- 戻る --%>
<c:choose>
<c:when test="${from != 'list'}"> <a href="PetDetailServlet?petID=${petDetail.petID}&from=${from}" class="back-link">
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
