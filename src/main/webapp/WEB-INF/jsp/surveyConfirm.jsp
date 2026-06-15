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
<title>アンケート内容表示</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
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

	<section class="survey-confirm-section">

		<div class="section-heading">
			<h1>アンケート内容一覧</h1>
			<p>回答済みのアンケート内容を確認できます。</p>
		</div>

		<div class="management-card">

			<c:choose>
				<c:when test="${not empty userSurveyList}">

					<table class="management-table survey-confirm-table">
						<thead>
							<tr>
								<th>質問</th>
								<th>回答内容</th>
							</tr>
						</thead>

						<tbody>
							<%
							for (UserSurvey us : userSurveyList) {
								String qText = "";
								String cText = "";

								for (Question q : questionList) {
									if (q.getQuestionID() == us.getQuestionID()) {
										qText = q.getUserQuestion();
										break;
									}
								}

								for (Choice c : allChoiceList) {
									if (c.getSurveyChoiceID() == us.getSurveyChoiceID()) {
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
						</tbody>
					</table>

				</c:when>

				<c:otherwise>
					<p class="empty-message">アンケート回答はまだ登録されていません。</p>
				</c:otherwise>
			</c:choose>

		</div>

		<div class="form-action-card">
			<form action="UserSuveyServlet" method="get">
				<button type="submit" class="main-button">修正</button>
			</form>

			<a href="MyPageServlet" class="back-link">マイページに戻る</a>
		</div>

	</section>

</main>
```

</body>
</html>
