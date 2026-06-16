<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メッセージ一覧</title>
</head>


<body>

<c:if test="${not empty errorMessage}">
<script>
alert("${errorMessage}");
window.location.href="MessageListServlet";
</script>
</c:if>

<h1>メッセージ一覧</h1>

<form action="MessageListServlet" method="get">
既読状態：
<button type="submit" name="readStatus" value="all">すべて</button>
<button type="submit" name="readStatus" value="unread">未読のみ</button>
<button type="submit" name="readStatus" value="read">既読のみ</button>

<input type="hidden" name="keyword" value="${keyword}">
<input type="hidden" name="sort" value="${sort}">
</form>

<form action="MessageListServlet" method="get">
<input type="hidden" name="readStatus" value="${readStatus}">

絞り込み検索：
<c:choose>
	<c:when test="${role == 'facility'}">
		<input type="text" name="keyword" value="${keyword}" placeholder="顧客ID・顧客名・ペットID・ペット名で検索">
	</c:when>
	<c:otherwise>
		<input type="text" name="keyword" value="${keyword}" placeholder="施設ID・施設名・ペットID・ペット名で検索">
	</c:otherwise>
</c:choose>
<br>

並び順：
<select name="sort">
	<option value="timeDesc" ${sort == "timeDesc" ? "selected" : ""}>新しい順</option>
	<option value="timeAsc" ${sort == "timeAsc" ? "selected" : ""}>古い順</option>
</select>
<br>
<button type="submit">検索</button>
</form>

<form action="MessageListServlet" method="get">
<button type="submit">クリア</button>
</form>

<p>表示件数： ${messageCount} 件</p>

<hr>
<c:forEach var="m" items="${messageList}">
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
    <span style="color:red;">未読${m.unreadCount}</span>
    </c:if>

    </c:when>
    <c:otherwise>
   ${m.facilityName}
   <c:if test="${m.unreadCount > 0}">
       <span style="color:red;">未読${m.unreadCount}</span>
   </c:if>
   </c:otherwise>
   </c:choose>
   
    / ペットID：${m.petID}(${m.petName})
    ${m.latestTime}
</a>
 </p>
</c:forEach>


<c:choose>
  <c:when test="${role == 'facility'}">
    <a href="FacilityPageServlet">戻る</a>
  </c:when>
  <c:otherwise>
    <a href="MyPageServlet">戻る</a>
  </c:otherwise>
</c:choose>


</body>
</html>