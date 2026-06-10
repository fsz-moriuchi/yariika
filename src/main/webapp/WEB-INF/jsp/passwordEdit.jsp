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

<button type="button" onclick="history.back()">キャンセル</button>	
<input type="submit" value="パスワードを変更して再ログイン">
</form>


</body>
</html>