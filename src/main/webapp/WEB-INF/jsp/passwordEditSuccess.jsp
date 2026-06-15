<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>passwordEditSuccess</title>
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.userId}">
			<script>
				alert("パスワード更新成功。");
				window.location.href = "MyPageServlet";
			</script>
		</c:when>

		<c:when test="${not empty sessionScope.facilityId}">
			<script>
				alert("パスワード更新成功。");
				window.location.href = "FacilityPageServlet";
			</script>
		</c:when>
	</c:choose>
</body>
</html>