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

	<section class="survey-section">

		<div class="section-heading">
			<h1>ペットアンケート</h1>
			<p>ユーザーとのマッチングに使うペットの特徴を選択してください。</p>
		</div>

		<div class="survey-card">

			<form action="SurveyServlet" method="post" class="survey-form">

				<%
				for (Question q : petQuestionList) {
				%>

				<div class="question-card">

					<h2 class="question-title">
						<span>Q<%=q.getQuestionID()%></span>
						<%=q.getPetQuestion()%>
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
						if (petSurveyList != null) {
							for (PetSurvey ps : petSurveyList) {
								if (ps.getQuestionID() == q.getQuestionID()
								&& ps.getSurveyChoiceID() == c.getSurveyChoiceID()) {
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
						<c:when test="${empty petID}">
							<input type="submit" value="登録" class="main-button">
						</c:when>

						<c:otherwise>
							<input type="submit" value="更新" class="main-button">
							<input type="hidden" name="petID" value="${petID}">
						</c:otherwise>
					</c:choose>

					<a href="StoreServlet" class="back-link">ペット一覧に戻る</a>

				</div>

			</form>

		</div>

	</section>

</main>