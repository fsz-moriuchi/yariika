<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザー作成画面</title>
</head>

<body>
<h1>新規ユーザー作成画面</h1>

<p>全項目登録してください。</p>

<form action="UserRegisterServlet" method="post">
<p>ユーザーID:<input type="text" name="userId" required></p>
<p>パスワード:<input type="password" name="password" required></p>

<p>名前：<input type="text" name="userName" required></p>

<p>性別：<select name="userGender" required>
  <option value="男">男</option>
  <option value="女">女</option>
  <option value="選択しない">選択しない</option>
</select></p>

<p>生年月日:<input type="date" name="userBirthday" required></p>

<p>電話番号：<input type="text" name="userTel" required></p>

<p>メール：<input type="email" name="userMail" required></p>

<p>住所：<input type="text" name="userAddress" required></p>

<input type="submit" value="登録">
</form>

<form action="WelcomeServlet" method="get">
		<input type="submit" value="戻る">
	</form>
<p>登録内容の変更はマイページからいつでも行えます。</p>

<c:if test="${not empty errorMsg}">
<c:out value="${errorMsg}"/>
</c:if>

</body>

</html>