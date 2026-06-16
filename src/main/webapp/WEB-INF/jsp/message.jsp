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

	<c:if test="${not empty errorMessage}">
		<script>
			alert("${errorMessage}");
			window.location.href = "MessageListServlet";
		</script>
	</c:if>

	<h1>メッセージ</h1>

	<c:forEach var="m" items="${messageList}">
		<c:choose>
			<c:when test="${m.senderType == 'USER'}">
				<p style="text-align: right;">
					ユーザー：${m.messageText}(${m.createdAt })</p>
			</c:when>

			<%--店舗側 --%>
			<c:otherwise>
				<p style="text-align: left;">店舗：${m.messageText}
					(${m.createdAt})</p>
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

		<c:when test="${from == 'detail'}">
			<a href="PetDetailServlet?petID=${petDetail.petID}&from=${backFrom}">
				戻る </a>
		</c:when>

		<c:otherwise>
			<a href="MessageListServlet">戻る</a>
		</c:otherwise>

	</c:choose>

</body>
</html>