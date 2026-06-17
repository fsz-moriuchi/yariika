<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>個人情報の修正</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">


<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="info-card">


<c:if test="${not empty errorMsg}">

<script>
alert("${errorMsg}");
window.location.href="UserEditServlet";
</script>

</c:if>

<h1>個人情報の修正</h1>

<form class="edit-form" action="UserEditServlet" method="post">

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

<div class="form-button-area center-button-area">
<button type="submit">更新</button>
</div>
</form>

<div class="form-button-area center-button-area">
<form action="UserInfoServlet" method="get">
<button class="back-button" type="submit">戻る</button>
</form>
</div>


</div>

</div>

</body>
</html>
