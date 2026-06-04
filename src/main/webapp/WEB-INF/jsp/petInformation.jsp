<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    
<%
String colorText = "";
if(request.getAttribute("petDetail") != null){
    colorText = ((model.PetDetail)request.getAttribute("petDetail"))
                    .getColor();
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
<input type="radio" name="category" value="dog" ${petDetail.category == "dog"?"checked":""} required  >犬
<input type="radio" name="category" value="cat" ${petDetail.category == "cat"?"checked":""}>猫<br>

2.名前：<input type="text" name="name" value="${petDetail.name}" autocomplete="off"><br>
3.性別：
<input type="radio" name="gender" value="male" ${petDetail.gender == "male"?"checked":"" }>男の子
<input type="radio" name="gender" value="female" ${petDetail.gender == "female"?"checked":"" }>女の子<br>
4.年齢：<input type="text" name="age" value="${petDetail.age}" required><br>
5.色と柄：<br>
<input type="checkbox" name="color" value="white" <%= colorText.contains("white") ? "checked" : "" %>>白
<input type="checkbox" name="color" value="black" <%= colorText.contains("black") ? "checked" : "" %>>黒
<input type="checkbox" name="color" value="brown" <%= colorText.contains("brown") ? "checked" : "" %>>茶色
<input type="checkbox" name="color" value="yellow" <%= colorText.contains("yellow") ? "checked" : "" %>>黄
<input type="checkbox" name="color" value="gray" <%= colorText.contains("gray") ? "checked" : "" %>>グレー<br>
<input type="checkbox" name="color" value="spotted" <%= colorText.contains("spotted") ? "checked" : "" %>>斑点模様
<input type="checkbox" name="color" value="brindle" <%= colorText.contains("brindle") ? "checked" : "" %>>虎柄模様
<input type="checkbox" name="color" value="curlyHair" <%= colorText.contains("curlyHair") ? "checked" : "" %>>巻き毛
<input type="checkbox" name="color" value="longHair" <%= colorText.contains("longHair") ? "checked" : "" %>>長毛
<input type="checkbox" name="color" value="shortHair" <%= colorText.contains("shortHair") ? "checked" : "" %>>短毛<br>
6.サイズ：
<input type="radio" name="pet_size" value="small" ${petDetail.pet_size == "small"?"checked":"" }>小型
<input type="radio" name="pet_size" value="medium" ${petDetail.pet_size == "medium"?"checked":"" }>中型
<input type="radio" name="pet_size" value="large" ${petDetail.pet_size == "large"?"checked":"" }>大型<br>
7.ワクチン：
<input type="radio" name="vaccine" value="vaccineDone" ${petDetail.vaccine == "vaccineDone"?"checked":"" }>接種済み
<input type="radio" name="vaccine" value="vaccineYet" ${petDetail.vaccine == "vaccineYet"?"checked":"" }>未接種<br>
8.生体価格：
<input type="text" name="price" value="${petDetail.price}" required><br>
9.コメント:<br>
<textarea name="commentText" maxlength="300" autocomplete="off">${petDetail.commentText}</textarea><br>

<c:choose>
<c:when test="${empty petDetail}">
<input type="submit" name="action" value="登録">
</c:when>
<c:otherwise>
<input type="submit" name="action" value="更新">
<input type="submit" name="action" value="削除">
<input type="hidden" name="petID" value="${petDetail.petID}">
</c:otherwise>
</c:choose>
</form>



</body>
</html>