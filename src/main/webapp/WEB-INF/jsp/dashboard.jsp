<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 施設用メニュー -->
	<c:if test="${not empty sessionScope.facilityId}">
		<a href="FacilityPageServlet">施設専用ページへ</a>
		<br>

		<a href="MessageListServlet"> メッセージ <span style="color: red;">
				（未読${facilityUnreadCount}件） </span>
		</a>
		<br>
	
	登録しているペット数：
	<c:out value="${petCount}" />
	匹
	<br>

	<!-- 最近追加したペット -->
	<c:if test="${not empty latestPet}">
		<br>
			最近追加したペット
			<br>
		<img src="${latestPet.imagePath}" width="200">
		<br>
			名前：
			<c:out value="${latestPet.name}" />
		<br>
			性別：
			<c:choose>
			<c:when test="${latestPet.gender == 'male'}">
        			オス
    			</c:when>
			<c:when test="${latestPet.gender == 'female'}">
        			メス
    			</c:when>
			<c:otherwise>
				<c:out value="${latestPet.gender}" />
			</c:otherwise>
		</c:choose>
		<br>
			年齢：
			<c:out value="${latestPet.age}" />歳
			<br>
			価格：
			<c:out value="${latestPet.price}" />円
			<br>
	</c:if>
	施設ページの累計アクセス数： ${viewCount}回
	<br>
	</c:if>

	<!-- ログアウト -->
	<a href="LogoutServlet">ログアウト</a>

</body>
</html>