```jsp
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
<title>ユーザーアンケート</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

	<header class="site-header">
		<div class="header-inner">
			<a href="HomeServlet" class="logo">Pet Matching</a>

			<nav class="header-nav">
				<a href="HomeServlet">ホーム</a>
				<a href="MyPageServlet">マイページ</a>
				<a href="LogoutServlet" class="logout-link">ログアウト</a>
			</nav>
		</div>
	</header>

	<main class="container">

		<section class="survey-section">

			<div class="section-heading">
				<h1>ユーザーアンケート</h1>
				<p>あなたに合ったペットをおすすめするためのアンケートです。</p>
			</div>

			<div class="survey-card">

				<form action="UserSuveyServlet" method="post" class="survey-form">

					<%
					for (Question q : questionList) {
					%>

					<div class="question-card">

						<h2 class="question-title">
							<span>Q<%=q.getQuestionID()%></span>
							<%=q.getUserQuestion()%>
						</h2>

						<div class="choice-list">

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
									if (us.getQuestionID() == q.getQuestionID()
									&& us.getSurveyChoiceID() == c.getSurveyChoiceID()) {
										checked = true;
										break;
									}
								}
							}
							%>

							<label class="choice-item">
								<input type="radio" name="q<%=q.getQuestionID()%>"
									value="<%=c.getSurveyChoiceID()%>"
									<%=checked ? "checked" : ""%> required>
								<span><%=c.getChoice()%></span>
							</label>

							<%
							}
							%>

							<%
							}
							%>

						</div>

					</div>

					<%
					}
					%>

					<div class="survey-submit-area">
						<c:choose>
							<c:when test="${empty userSurveyList}">
								<input type="submit" name="action" value="登録" class="main-button">
							</c:when>

							<c:otherwise>
								<input type="submit" name="action" value="更新" class="main-button">
							</c:otherwise>
						</c:choose>
					</div>

				</form>

			</div>

			<div class="back-link-area">
				<a href="MyPageServlet" class="back-link">マイページに戻る</a>
			</div>

		</section>

	</main>

</body>
</html>
```
