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
<title>userSurvey</title>
</head>
<body>
<h1>ユーザーアンケート</h1>

<form action="UserSuveyServlet" method="post">
<%for(Question q : questionList) {%>
<%=q.getQuestionID() %>.	<%=q.getUserQuestion() %> <br>
<%for(Choice c : allChoiceList) {%>
<% if(c.getQuestionID() == q.getQuestionID()){%>
    <%
    boolean checked = false;
    if(userSurveyList != null){
        for(UserSurvey us : userSurveyList){
        	if(us.getQuestionID() == q.getQuestionID()&& us.getSurveyChoiceID() == c.getSurveyChoiceID()){
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
<c:when test="${empty userSurveyList}">
    <input type="submit" name="action" value="登録">
</c:when>
<c:otherwise>
    <input type="submit" name="action" value="更新">
</c:otherwise>
</c:choose>

</form>

</body>
</html>