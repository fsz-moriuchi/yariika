<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>個人情報の確認</title>
</head>
<body>
<h1>個人情報の確認</h1>
<%-- 
名前：${user.name};
メール：${user.mail};
--%>

<form action="UserEditServlet" method="post">
<button type="submit">修正</button>
</form>

<form action="MyPageServlet" method="get">
<button type="submit">戻る</button>
</form>

</body>
</html>