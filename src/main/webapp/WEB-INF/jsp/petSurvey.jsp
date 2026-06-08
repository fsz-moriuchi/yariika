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
<title>petSurvey</title>
</head>
<body>
<h1>ペットのアンケート</h1>


<form action="SurveyServlet" method="post">
<%for(Question q : questionList) {%>
<%=q.getQuestionID() %>.	<%=q.getPetQuestion() %> <br>
<%for(Choice c : allChoiceList) {%>
<% if(c.getQuestionID() == q.getQuestionID()){%>
    <%
    boolean checked = false;
    if(petSurveyList != null){
        for(PetSurvey ps : petSurveyList){
        	if(ps.getQuestionID() == q.getQuestionID()&& ps.getSurveyChoiceID() == c.getSurveyChoiceID()){
        		checked = true;
        		break;
            }
        }
    }
    %>
<label>
	<input type="radio" name="q<%=q.getQuestionID() %>" value="<%=c.getSurveyChoiceID()%>" <%=checked ? "checked" : ""  %> required>
<%=c.getChoice() %>
</label>
<%}%>
<%} %><br>
<%} %><br>

<c:choose>
<c:when test="${empty petID}">
    <input type="submit" value="登録">
</c:when>
<c:otherwise>
    <input type="submit" value="更新">
    <input type="hidden" name="petID" value="${petID}">
</c:otherwise>
</c:choose>

</form>
</body>
</html>