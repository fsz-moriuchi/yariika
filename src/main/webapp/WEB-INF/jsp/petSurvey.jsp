<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="java.util.List" %>
    <%@ page import="model.Question" %>
    <%@ page import="model.Choice" %>
    
    <%
    List<Question> questionList =(List<Question>)request.getAttribute("questionList");
    List<Choice> allChoiceList =(List<Choice>)request.getAttribute("allChoiceList");
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>petSurvey</title>
</head>
<body>
<h1>ペットの情報一覧</h1>


<form action="SurveyServlet" method="">
<%for(Question q : questionList) {%>
<%=q.getQuestionID() %>.	<%=q.getPetQuestion() %> <br>
<%for(Choice c : allChoiceList) {%>
<% if(c.getQuestionID() == q.getQuestionID()){%>
<input type="radio" name="q<%=q.getQuestionID() %>" value="<%=c.getSurveyChoiceID()%>">
<%=c.getChoice() %>
<%}%>
<%} %><br>
<%} %><br>
<input type="submit" value="送信">
</form>
</body>
</html>