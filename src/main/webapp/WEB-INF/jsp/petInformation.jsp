<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<%
String colorText = "";
if(request.getAttribute("petDetail") != null){
  	colorText = ((model.PetDetail)request.getAttribute("petDetail")).getColor();
    if(colorText == null){
        colorText = "";
    }
}
%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RegisterPetInformation</title>
</head>
<body>

<h1>ペット情報新規登録/修正</h1>



<form action="PetRegisterServlet" method="post">

1.カテゴリー：
<label>
	<input type="radio" name="category" value="dog" ${not empty petDetail and petDetail.category == "dog"?"checked":""} required  >犬
</label>
<label>
	<input type="radio" name="category" value="cat" ${not empty petDetail and petDetail.category == "cat"?"checked":""}>猫<br>
</label>

2.名前：
<label>
	<input type="text" name="name" value="${empty petDetail ? '' : petDetail.name}" autocomplete="off"><br>
</label>
3.性別：
<label>
	<input type="radio" name="gender" value="male" ${not empty petDetail and petDetail.gender == "male"?"checked":"" }>男の子
</label>
<label>
	<input type="radio" name="gender" value="female" ${not empty petDetail and petDetail.gender == "female"?"checked":"" }>女の子<br>
</label>
4.年齢：
<label>
	<input type="number" name="age" min="0" value="${empty petDetail ? '' : petDetail.age}" required autocomplete="off"><br>
</label>
5.色と柄：<br>
<label>
	<input type="checkbox" name="color" value="white" <%= colorText.contains("white") ? "checked" : "" %>>白
</label>
<label>
	<input type="checkbox" name="color" value="black" <%= colorText.contains("black") ? "checked" : "" %>>黒
</label>
<label>
	<input type="checkbox" name="color" value="brown" <%= colorText.contains("brown") ? "checked" : "" %>>茶色
</label>
<label>
	<input type="checkbox" name="color" value="yellow" <%= colorText.contains("yellow") ? "checked" : "" %>>黄
</label>
<label>
	<input type="checkbox" name="color" value="gray" <%= colorText.contains("gray") ? "checked" : "" %>>グレー<br>
</label>
<label>
	<input type="checkbox" name="color" value="spotted" <%= colorText.contains("spotted") ? "checked" : "" %>>斑点模様
</label>
<label>
	<input type="checkbox" name="color" value="brindle" <%= colorText.contains("brindle") ? "checked" : "" %>>虎柄模様
</label>
<label>
	<input type="checkbox" name="color" value="curlyHair" <%= colorText.contains("curlyHair") ? "checked" : "" %>>巻き毛
</label>
<label>
	<input type="checkbox" name="color" value="longHair" <%= colorText.contains("longHair") ? "checked" : "" %>>長毛
</label>
<label>
	<input type="checkbox" name="color" value="shortHair" <%= colorText.contains("shortHair") ? "checked" : "" %>>短毛<br>
</label>
6.サイズ：
<label>
	<input type="radio" name="pet_size" value="small" ${not empty petDetail and petDetail.pet_size == "small"?"checked":"" }>小型
</label>
<label>
	<input type="radio" name="pet_size" value="medium" ${not empty petDetail and petDetail.pet_size == "medium"?"checked":"" }>中型
</label>
<label>
	<input type="radio" name="pet_size" value="large" ${not empty petDetail and petDetail.pet_size == "large"?"checked":"" }>大型<br>
</label>
7.ワクチン：
<label>
	<input type="radio" name="vaccine" value="vaccineDone" ${not empty petDetail and petDetail.vaccine == "vaccineDone"?"checked":"" }>接種済み
</label>
<label>
	<input type="radio" name="vaccine" value="vaccineYet" ${not empty petDetail and petDetail.vaccine == "vaccineYet"?"checked":"" }>未接種<br>
</label>
8.生体価格：
<label>
	<input type="number" name="price" min="0" value="${empty petDetail ? '' : petDetail.price}" required><br>
</label>
9.コメント:<br>
<textarea name="commentText" maxlength="300" autocomplete="off">${empty petDetail ? '' : petDetail.commentText}</textarea><br>

<c:choose>
<c:when test="${empty petDetail}">
	<input type="submit" name="action" value="アンケートへ">
</c:when>
<c:otherwise>
	<input type="submit" name="action" value="アンケート修正">
	<input type="submit" name="action" value="更新">
	<input type="submit" name="action" value="削除">
	<input type="hidden" name="petID" value="${petDetail.petID}">
</c:otherwise>
</c:choose>
</form>

</body>
</html>