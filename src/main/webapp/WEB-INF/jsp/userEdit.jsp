<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>個人情報の修正</title>
</head>


<body>
<h1>個人情報の修正</h1>

<form action="UserEditServlet" method="post">

<input type="hidden" name="userInfoId" value="${userInfo.userInfoId}">

<p>名前：<input type="text" name="userName" value="${userInfo.userName}"></p>

<p>性別：
<select name="userGender">
  <option value="男" ${userInfo.userGender == '男' ? 'selected' : ''}>男</option>
  <option value="女" ${userInfo.userGender == '女' ? 'selected' : ''}>女</option>
</select></p>

<p>生年月日：<input type="date" name="userBirthday" value="<fmt:formatDate value='${userInfo.userBirthday}' pattern='yyyy-MM-dd'/>"></p>

<p>電話番号：<input type="text" name="userTel" value="${userInfo.userTel}"></p>
<p>メール：<input type="email" name="userMail" value="${userInfo.userMail}"></p>
<p>住所：<input type="text" name="userAddress" value="${userInfo.userAddress}"></p>

<button type="submit">更新</button>
</form>

</body>
</html>