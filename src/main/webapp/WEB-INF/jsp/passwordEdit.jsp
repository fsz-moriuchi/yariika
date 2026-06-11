<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>passwordEdit</title>
</head>
<body>
<h1>パスワード変更</h1>

<form action="PasswordEditServlet" method="post">
元パスワード：<input type="password" name="oldPassword" required><br>
新しいパスワード：<input type="password" name="newPassword" required><br>
もう一度入力：<input type="password" name="newPasswordConfirm" required><br>

<c:if test="${not empty errorMsg}">
	<p style="color:red;">
	<c:out value="${errorMsg}"/><br>
	</p>
</c:if>

<input type="submit" value="パスワードを変更、再度ログイン">
</form>
<c:choose>
	<c:when test="${not empty sessionScope.userId}">
		<form action="MyPageServlet" method="get">
			<button type="submit">キャンセル</button>
		</form>
	</c:when>

	<c:when test="${not empty sessionScope.facilityId}">
		<form action="FacilityPageServlet" method="get">
			<button type="submit">キャンセル</button>
		</form>
	</c:when>
</c:choose>

</body>
</html>