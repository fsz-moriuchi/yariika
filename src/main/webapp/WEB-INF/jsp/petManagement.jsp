<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>petManagement</title>
</head>
<body>
<h1>ペット情報管理</h1>

<form action="PetRegisterServlet" method="get">
<input type="submit" value="新規ペット情報作成">
</form>

<table border="1" style="width:100%">
<tr>
<th>写真</th><th>ペットID</th><th>種類</th><th>性別</th><th>年齢</th><th>生体価格</th><th>操作</th>
</tr>

<c:forEach var="pet" items="${petList}">

<tr>
<td>写真</td><td>${pet.petID}</td><td>${pet.category}</td><td>${pet.gender}</td><td>${pet.age}</td><td>${pet.price}</td>
<td>
<form action="PetEditServlet" method="get">
<input type="hidden" name="petID" value="${pet.petID}">
<input type="submit" value="修正">
</form>
</td>
</tr>

</c:forEach>
</table>
</body>
</html>