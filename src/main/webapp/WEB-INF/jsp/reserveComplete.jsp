<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約完了画面</title>
</head>
<body>
<h1>ご予約を承りました</h1>
<p>予約情報</p>
<p>日付：<c:out value="${reserveDate}" /></p>
<p>時間：<c:out value="${reserveTime}" /></p>

<p>施設情報</p>
<p>施設名：<c:out value="${facilityInformation.facilityName}" /></p>
<p>住所：<c:out value="${facilityInformation.address}" /></p>
<p>電話番号：<c:out value="${facilityInformation.tel}" /></p>

<a href="HomeServlet">ホームに戻る</a> 
</body>
</html>