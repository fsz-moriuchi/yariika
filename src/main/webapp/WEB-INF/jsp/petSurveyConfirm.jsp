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
<title>ペットアンケート確認</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

```
<header class="site-header">
	<div class="header-inner">
		<a href="HomeServlet" class="logo">Pet Matching</a>

		<nav class="header-nav">
			<a href="HomeServlet">ホーム</a>
			<a href="FacilityPageServlet">店舗管理ページ</a>
			<a href="StoreServlet">ペット一覧</a>
			<a href="LogoutServlet" class="logout-link">ログアウト</a>
		</nav>
	</div>
</header>

<main class="container">

	<section class="survey-confirm-section">

		<div class="section-heading">
			<h1>ペットアンケート確認</h1>
			<p>
				ペットID：
				<c:out value="${petID}" />
				のアンケート内容を確認できます。
			</p>
		</div>

		<div class="management-card">

			<c:choose>
				<c:when test="${not empty petSurveyList}">

					<table class="management-table survey-confirm-table">
						<thead>
							<tr>
								<th>質問</th>
								<th>回答内容</th>
							</tr>
						</thead>

						<tbody>
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
									if (c.getSurveyChoiceID() == ps.getSurveyChoiceID()) {
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
			<a href="StoreServlet" class="back-link">ペット一覧に戻る</a>
		</div>

	</section>

</main>
```

</body>
</html>
