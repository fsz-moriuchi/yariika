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
<c:forEach var="m" items="${messageList}">
 <p>
    <a href="MessageServlet?userId=${m.userId}&petID=${m.petID}&facilityId=${m.facilityId}&from=list">
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