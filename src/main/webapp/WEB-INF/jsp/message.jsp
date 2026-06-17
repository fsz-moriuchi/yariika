<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メッセージ画面</title>
<link rel="stylesheet" href="style.css">
</head>


<body>

	<c:if test="${not empty errorMessage}">
		<script>
			alert("${errorMessage}");
			window.location.href = "MessageListServlet";
		</script>
	</c:if>

	<h1>メッセージ</h1>
	
	<div class="container">
	
	<div class="chat-box">
	<c:forEach var="m" items="${messageList}">
		<c:choose>
			<c:when test="${m.senderType == 'USER'}">
				<div class="message user">
					${m.userId}：${m.messageText}(${m.createdAt })
					</div>
			</c:when>

			<%--店舗側 --%>
			<c:otherwise>
				<div class="message facility">
					店舗(${m.facilityId})：${m.messageText} (${m.createdAt})</div>
			</c:otherwise>
		</c:choose>
	</c:forEach>


	<form action="MessageServlet" method="post">
		<input type="hidden" name="petID" value="${petDetail.petID}">
		<input type="hidden" name="userId" value="${param.userId}"> <input
			type="hidden" name="facilityId" value="${param.facilityId}">
		<input type="hidden" name="from" value="${from}">

		<p>
			<input type="text" name="messageText">
		</p>
		<button type="submit">送信</button>
	</form>

	<%--<c:choose>
<c:when test="${sessionScope.user != null}">
	<a href="MessageListServlet?petID=${petDetail.petID}">戻る</a>
</c:when>
<c:when test="${sessionScope.facilityId != null}">
	<a href="MessageListServlet?petID=${petDetail.petID}">戻る</a>
</c:when>
</c:choose>--%>


	<c:choose>

		<c:when test="${from != 'list'}">
			<a href="PetDetailServlet?petID=${petDetail.petID}&from=${from}">
				戻る </a>
		</c:when>

		<c:otherwise>
			<a href="MessageListServlet"> 戻る </a>
		</c:otherwise>

	</c:choose>

</body>
</html>-->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メッセージ画面</title>
<link rel="stylesheet" href="style.css">
</head>

<body>

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
	<c:when test="${sessionScope.user != null}">
		<h2>店舗(${param.facilityId})とのメッセージ</h2>
	</c:when>
	<c:otherwise>
		<h2>${userName}様 とのメッセージ</h2>
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
		<c:when test="${from != 'list'}">
			<a href="PetDetailServlet?petID=${petDetail.petID}&from=${from}" class="back-link">
				戻る
			</a>
		</c:when>
		<c:otherwise>
			<a href="MessageListServlet" class="back-link">戻る</a>
		</c:otherwise>
	</c:choose>
	</div>
</div>

<script>
window.onload = function() {
    const chatBox = document.querySelector(".chat-box");
    chatBox.scrollTop = chatBox.scrollHeight;
};
</script>


<script>
function scrollToBottom() {
    const chatBox = document.querySelector(".chat-box");
    chatBox.scrollTop = chatBox.scrollHeight;
}

window.onload = scrollToBottom;
</script>


</body>
</html>