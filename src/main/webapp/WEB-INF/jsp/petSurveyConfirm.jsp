<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.List" %>
    <%@ page import="model.Question" %>
    <%@ page import="model.Choice" %>
    <%@ page import="model.PetSurvey" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
   
    <%
    List<Question> questionList =(List<Question>)request.getAttribute("questionList");
    List<Choice> allChoiceList =(List<Choice>)request.getAttribute("allChoiceList");
    List<PetSurvey> petSurveyList =(List<PetSurvey>)request.getAttribute("petSurveyList");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>petSurveyConfirm</title>
</head>
<body>
<h1>ペットID‐<c:out value="${petID}"/> </h1>
<h2>アンケート内容一覧:</h2>
<table border="3" style="width:100%">

<tr>
<th>問題</th><th>回答内容</th>
</tr>
<%
for(PetSurvey ps : petSurveyList){
	String qText = "";
	String cText = "";
	for(Question q : questionList){
		if(q.getQuestionID() == ps.getQuestionID()){
			qText = q.getPetQuestion();
			break;
		}
	}
	for(Choice c : allChoiceList){
		if(c.getSurveyChoiceID() == ps.getSurveyChoiceID()){
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

<form action="StoreServlet" method="get">
<button type="submit">戻る</button>
</form>

</body>
</html>