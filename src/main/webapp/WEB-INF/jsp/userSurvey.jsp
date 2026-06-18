<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.Question"%>
<%@ page import="model.Choice"%>
<%@ page import="model.UserSurvey"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
List<Question> questionList = (List<Question>) request.getAttribute("questionList");
List<Choice> allChoiceList = (List<Choice>) request.getAttribute("allChoiceList");
List<UserSurvey> userSurveyList = (List<UserSurvey>) request.getAttribute("userSurveyList");
%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>userSurvey</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">


<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

</div>

<div class="survey-card">

    <h1>ユーザーアンケート</h1>

    <p class="welcome-message">
        あなたに合ったペットを探すために、アンケートに回答してください。
    </p>

<form class="survey-form" action="UserSuveyServlet" method="post">
	<%
	for (Question q : questionList) {
	%>

    <div class="survey-question">

	<p class="survey-question-title">
        <%=q.getQuestionID()%>.
	    <%=q.getUserQuestion()%>
    </p>

	<%
	for (Choice c : allChoiceList) {
	%>
	<%
	if (c.getQuestionID() == q.getQuestionID()) {
	%>
	<%
	boolean checked = false;
	if (userSurveyList != null) {
		for (UserSurvey us : userSurveyList) {
			if (us.getQuestionID() == q.getQuestionID() && us.getSurveyChoiceID() == c.getSurveyChoiceID()) {
		checked = true;
		break;
			}
		}
	}
	%>
	<label> <input type="radio" name="q<%=q.getQuestionID()%>"
		value="<%=c.getSurveyChoiceID()%>" <%=checked ? "checked" : ""%>
		required> <%=c.getChoice()%>
	</label>
	<%
	}
	%>
	<%
	}
	%>

    </div>

	<%
	}
	%>

    <div class="form-button-area center-button-area">

	<c:choose>
		<c:when test="${empty userSurveyList}">
			<input type="submit" name="action" value="登録">
		</c:when>
		<c:otherwise>
			<input type="submit" name="action" value="更新">
		</c:otherwise>
	</c:choose>

    </div>

</form>

<div class="form-button-area center-button-area">
    <form action="MyPageServlet" method="get">
	    <input class="back-button" type="submit" value="戻る">
    </form>
</div>

</div>

</div>

</body>
</html>
