<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
<form action="LoginServlet" method="post">
<p>ユーザーID:<input type="text" name="userId"></p>
<p>パスワード:<input tupe="pass" name="password"></p>
<input type="submit" value="ログイン">
</form>
</body>
</html>