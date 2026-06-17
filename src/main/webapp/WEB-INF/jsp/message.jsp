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

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="chat-card">

<c:if test="${not empty errorMessage}">
	<script>
		alert("${errorMessage}");
		window.location.href = "MessageListServlet";
	</script>
</c:if>

<h1>メッセージ</h1>

<p class="welcome-message">
    メッセージを入力してください。。
</p>

<div class="chat-message-area">

<c:forEach var="m" items="${messageList}">
	<c:choose>
		<c:when test="${m.senderType == 'USER'}">
			<p class="chat-user-message" style="text-align: right;">
				${m.userId}：${m.messageText}(${m.createdAt })</p>
		</c:when>

		<%--店舗側 --%>
		<c:otherwise>
			<p class="chat-facility-message" style="text-align: left;">
				店舗(${m.facilityId})：${m.messageText} (${m.createdAt})</p>
		</c:otherwise>
	</c:choose>
</c:forEach>

</div>


<form class="chat-form" action="MessageServlet" method="post">
	<input type="hidden" name="petID" value="${petDetail.petID}">
	<input type="hidden" name="userId" value="${param.userId}"> <input
		type="hidden" name="facilityId" value="${param.facilityId}">
	<input type="hidden" name="from" value="${from}">

	<p>
		<input type="text" name="messageText">
	</p>

    <div class="form-button-area center-button-area">
	    <button type="submit">送信</button>
    </div>
</form>

<%--<c:choose>


<c:when test="${sessionScope.user != null}"> <a href="MessageListServlet?petID=${petDetail.petID}">戻る</a>
</c:when>
<c:when test="${sessionScope.facilityId != null}"> <a href="MessageListServlet?petID=${petDetail.petID}">戻る</a>
</c:when>
</c:choose>--%>

<div class="form-button-area center-button-area">

<c:choose>

	<c:when test="${from != 'list'}">
		<a class="clear-button" href="PetDetailServlet?petID=${petDetail.petID}&from=${from}">
			戻る </a>
	</c:when>

	<c:otherwise>
		<a class="clear-button" href="MessageListServlet"> 戻る </a>
	</c:otherwise>

</c:choose>

</div>

</div>


</div>

</body>
</html>
