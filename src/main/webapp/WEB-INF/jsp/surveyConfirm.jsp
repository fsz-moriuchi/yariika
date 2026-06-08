<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="model.Question" %>
    <%@ page import="model.Choice" %>
    <%@ page import="model.UserSurvey" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
   
    <%
    List<Question> questionList =(List<Question>)request.getAttribute("questionList");
    List<Choice> allChoiceList =(List<Choice>)request.getAttribute("allChoiceList");
    List<UserSurvey> userSurveyList =(List<UserSurvey>)request.getAttribute("userSurveyList");
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アンケート内容表示</title>
</head>


<body>
<h1>アンケート内容一覧</h1>

<table border="2" style="width:100%">

<tr>
<th>問題</th><th>回答内容</th>
</tr>
<%
for(UserSurvey us : userSurveyList){
	String qText = "";
	String cText = "";
	for(Question q : questionList){
		if(q.getQuestionID() == us.getQuestionID()){
			qText = q.getUserQuestion();
			break;
		}
	}
	for(Choice c : allChoiceList){
		if(c.getSurveyChoiceID() == us.getSurveyChoiceID()){
			cText = c.getChoice();
			break;
		}
	}

%>
<tr>
<td><%=qText %></td>
<td><%=cText %></td>
</tr>
<%} %>
</table>
<form action="UserSuveyServlet" method="get">
<button type="submit">修正</button>
</form>

<form action="MyPageServlet" method="get">
<button type="submit">戻る</button>
</form>

</body>
</html>