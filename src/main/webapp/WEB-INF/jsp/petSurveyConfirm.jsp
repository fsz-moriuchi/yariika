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
<title>petSurveyConfirm</title>
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

		<div class="pet-survey-confirm-card">

			<h1>
				ペットID‐
				<c:out value="${petID}" />
			</h1>

			<h2>アンケート内容一覧</h2>

			<div class="pet-survey-confirm-table-wrap">

				<table border="3" style="width: 100%"
					class="pet-survey-confirm-table">

					<tr>
						<th>問題</th>
						<th>回答内容</th>
					</tr>

					<%
					for (PetSurvey ps : petSurveyList) {
						String qText = "";
						String cText = "";

						for (Question q : petQuestionList) {
							if (q.getQuestionID() == ps.getQuestionID()) {
						qText = q.getPetQuestion();
						break;
							}
						}

						for (Choice c : allChoiceList) {
							if (c.getSurveyChoiceId() == ps.getSurveyChoiceID()) {
						cText = c.getChoice();
						break;
							}
						}
					%>

					<tr>
						<td><%=qText%></td>
						<td><%=cText%></td>
					</tr>

					<%
					}
					%>

				</table>

			</div>

			<form action="PetRegisterServlet" method="post"
				class="pet-register-edit-form">

				<input type="hidden" name="petID" value="${petID}">

				<div class="form-button-area center-button-area">
					<input type="submit" name="action" value="アンケート修正">
				</div>

			</form>

			<form action="StoreServlet" method="get"
				class="pet-survey-confirm-back-form">

				<div class="form-button-area center-button-area">
					<button type="submit">戻る</button>
				</div>

			</form>

		</div>

	</div>

</body>
</html>