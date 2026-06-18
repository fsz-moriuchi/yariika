<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>メッセージ一覧</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="message-card">

<c:if test="${not empty errorMessage}">

<script>
alert("${errorMessage}");
window.location.href="MessageListServlet";
</script>

</c:if>

<h1>メッセージ一覧</h1>

<p class="welcome-message">
    メッセージの既読状態やキーワードで絞り込みできます。
</p>

<form class="message-filter-form" action="MessageListServlet" method="get">
既読状態：
<div class="form-button-area">
<button type="submit" name="readStatus" value="all">すべて</button>
<button type="submit" name="readStatus" value="unread">未読のみ</button>
<button type="submit" name="readStatus" value="read">既読のみ</button>
</div>

<input type="hidden" name="keyword" value="${keyword}">
<input type="hidden" name="sort" value="${sort}">
</form>

<form class="message-search-form" action="MessageListServlet" method="get">
<input type="hidden" name="readStatus" value="${readStatus}">

<p>
絞り込み検索：
<c:choose>
	<c:when test="${role == 'facility'}">
		<input type="text" name="keyword" value="${keyword}" placeholder="顧客ID・顧客名・ペットID・ペット名で検索">
	</c:when>
	<c:otherwise>
		<input type="text" name="keyword" value="${keyword}" placeholder="施設ID・施設名・ペットID・ペット名で検索">
	</c:otherwise>
</c:choose>
</p>

<p>
並び順：
<select name="sort">
	<option value="timeDesc" ${sort == "timeDesc" ? "selected" : ""}>新しい順</option>
	<option value="timeAsc" ${sort == "timeAsc" ? "selected" : ""}>古い順</option>
</select>
</p>

<div class="form-button-area">
<button type="submit">検索</button>
</div>
</form>

<div class="form-button-area center-button-area">
<form action="MessageListServlet" method="get">
<button class="clear-button" type="submit">クリア</button>
</form>
</div>

<p class="result-count">表示件数： ${messageCount} 件</p>

<hr>

<div class="message-list">

<c:forEach var="m" items="${messageList}">

 <div class="message-item">
 <p>
    <c:choose>
	    <c:when test="${role == 'facility'}">
	        <a href="MessageServlet?userId=${m.userId}&petID=${m.petID}&facilityId=${facilityId}&from=list">
	    </c:when>
	    <c:otherwise>
	        <a href="MessageServlet?userId=${sessionScope.user.userId}&petID=${m.petID}&facilityId=${m.facilityId}&from=list">
	    </c:otherwise>
    </c:choose>    


<c:choose>
<c:when test="${role == 'facility'}">
${m.userName}さん

<c:if test="${m.unreadCount > 0}">
<span class="message-unread">未読${m.unreadCount}</span>
</c:if>

</c:when>
<c:otherwise>


${m.facilityName}
<c:if test="${m.unreadCount > 0}"> <span class="message-unread">未読${m.unreadCount}</span>
</c:if>
</c:otherwise>
</c:choose>


/ ペットID：${m.petID}(${m.petName})
${m.latestTime}


</a>
 </p>
 </div>

</c:forEach>

</div>

<div class="form-button-area center-button-area">
<c:choose>
  <c:when test="${role == 'facility'}">
    <a class="clear-button" href="FacilityPageServlet">戻る</a>
  </c:when>
  <c:otherwise>
    <a class="clear-button" href="MyPageServlet">戻る</a>
  </c:otherwise>
</c:choose>
</div>

</div>


</div>

</body>
</html>
