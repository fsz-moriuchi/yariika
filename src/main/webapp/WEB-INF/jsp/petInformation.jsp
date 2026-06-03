<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    


    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RegisterPetInformation</title>
</head>
<body>

<h1>ペット情報登録</h1>

<form action="PetRegisterServlet" method="post">

1.ペットID：<input type="text" name="petID" required autofocus autocomplete="off"><br>
2.カテゴリー：
犬<input type="radio" name="category" value="dog" required>
猫<input type="radio" name="category" value="cat"><br>

3.ペットインフォメーションID：<input type="text" name="petInformationID" required autocomplete="off"><br>
4.名前：<input type="text" name="name" autocomplete="off"><br>
5.性別：
<input type="radio" name="gender" value="male">男の子
<input type="radio" name="gender" value="female">女の子<br>
6.年齢：<input type="text" name="age" ><br>
7.色と柄：<br>
<input type="checkbox" name="color" value="white" >白
<input type="checkbox" name="color" value="black">黒
<input type="checkbox" name="color" value="brown">茶色
<input type="checkbox" name="color" value="yellow">黄
<input type="checkbox" name="color" value="gray">グレー<br>
<input type="checkbox" name="color" value="spotted">斑点模様
<input type="checkbox" name="color" value="brindle">虎柄模様
<input type="checkbox" name="color" value="curlyHair">巻き毛
<input type="checkbox" name="color" value="longHair">長毛
<input type="checkbox" name="color" value="shortHair">短毛<br>
8.サイズ：
<input type="radio" name="pet_size" value="small">小型
<input type="radio" name="pet_size" value="medium">中型
<input type="radio" name="pet_size" value="large">大型<br>
9.ワクチン：
<input type="radio" name="vaccine" value="vaccineDone">接種済み
<input type="radio" name="vaccine" value="vaccineYet">未接種<br>
10.生体価格：<input type="text" name="price" ><br>
11.コメント:<br>
<textarea name="commentText" maxlength="300" autocomplete="off"></textarea>
<input type="submit" value="next">
</form>



</body>
</html>