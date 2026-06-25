<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.Question"%>
<%@ page import="model.Choice"%>
<%@ page import="model.PetSurvey"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
List<Question> petQuestionList = (List<Question>) request.getAttribute("petQuestionList");
List<Choice> allChoiceList = (List<Choice>) request.getAttribute("allChoiceList");
List<PetSurvey> petSurveyList = (List<PetSurvey>) request.getAttribute("petSurveyList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>petSurvey</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css">
</head>
<body>

	<div class="page-container">

		<div class="site-header">
			<div class="site-logo-wrap">
				<h1 class="site-logo">PET MATCH</h1>
			</div>
		</div>

		<div class="pet-survey-card">

			<h1>ペットのアンケート</h1>

			<p class="welcome-message">ペットの性格や飼育条件に関する情報を登録してください。</p>

			<form action="SurveyServlet" method="post" class="pet-survey-form">
				<%
				for (Question q : petQuestionList) {
				%>

				<div class="pet-survey-question-box">

					<p class="pet-survey-question-title">
						<%=q.getQuestionID()%>.<%=q.getPetQuestion()%>
					</p>

					<div class="pet-survey-choice-list">

						<%
						for (Choice c : allChoiceList) {
						%>
						<%
						if (c.getQuestionId() == q.getQuestionID()) {
						%>
						<%
						boolean checked = false;
						if (petSurveyList != null) {
							for (PetSurvey ps : petSurveyList) {
								if (ps.getQuestionID() == q.getQuestionID()
								&& ps.getSurveyChoiceID() == c.getSurveyChoiceId()) {
							checked = true;
							break;
								}
							}
						}
						%>
						<label class="pet-survey-choice-item"> <input type="radio"
							name="q<%=q.getQuestionID()%>"
							value="<%=c.getSurveyChoiceId()%>"
							<%=checked ? "checked" : ""%> required> <%=c.getChoice()%>
						</label>
						<%
						}
						%>
						<%
						}
						%><br>

					</div>

				</div>

				<%
				}
				%><br>

				<div class="form-button-area center-button-area">
					<c:choose>
						<c:when test="${empty petID}">
							<input type="submit" value="登録">
						</c:when>
						<c:otherwise>
							<input type="submit" value="更新">
							<input type="hidden" name="petID" value="${petID}">
						</c:otherwise>
					</c:choose>
				</div>

			</form>

		</div>
	</div>

</body>
</html>