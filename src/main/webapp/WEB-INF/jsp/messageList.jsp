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
<h1>メッセージ一覧</h1>
<c:forEach var="m" items="${messageList}">
 <p>
    <a href="MessageServlet?userId=${m.userId}&petID=${m.petID}&facilityId=${sessionScope.facilityId}">
      ${m.userId}：${m.userName}さん / ペットID：${m.petID}(${m.petName})
      <br>
      ${m.latestTime}
    </a>
 </p>
</c:forEach>
</body>
</html>