<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット詳細画面</title>
</head>
<body>
	<h1>プロフィール</h1>
	<c:if test="${not empty petDetail.imagePath}">
		<img src="${pageContext.request.contextPath}/${petDetail.imagePath}"
			width="300">
	</c:if>
	<hr>
	<p>
		種類：
		<c:out value="${petDetail.categoryName}" />
	</p>
	<p>
		名前：
		<c:choose>
			<c:when test="${not empty petDetail.name}">
				<c:out value="${petDetail.name}" />
			</c:when>
			<c:otherwise>
        名付けてください！
    </c:otherwise>
		</c:choose>
	</p>
	<p>
		性別：
		<c:out value="${petDetail.genderName}" />
	</p>
	<p>
		年齢：
		<c:out value="${petDetail.age}" />
		歳
	</p>
	<p>
		毛色：
		<c:out value="${petDetail.colorName}" />
	</p>
	<p>
		サイズ：
		<c:out value="${petDetail.petSizeName}" />
	</p>
	<p>
		ワクチン：
		<c:out value="${petDetail.vaccineName}" />
	</p>
	<p>
		価格：
		<c:out value="${petDetail.price}" />
		円
	</p>
	<hr>
	<h2>紹介文</h2>
	<p>
		<c:out value="${petDetail.commentText}" />
	</p>
	<hr>
	<h2>施設情報</h2>
	<p>
		施設名：
		<c:out value="${petDetail.facilityName}" />
	</p>
	<p>
		住所：
		<c:out value="${petDetail.address}" />
	</p>
	<p>
		電話番号：
		<c:out value="${petDetail.tel}" />
	</p>
	<hr>

	<!--	お気に入り-->
	<c:if test="${not empty sessionScope.userId}">
		<form action="FavoriteServlet" method="post">
			<input type="hidden" name="petID" value="${petDetail.petID}">
			<c:choose>
				<c:when test="${favorite}">
					<input type="submit" value="♥ お気に入り解除">
				</c:when>
				<c:otherwise>
					<input type="submit" value="♡ お気に入り登録">
				</c:otherwise>
			</c:choose>
		</form>
		<br>
	</c:if>

	<%--メッセージ機能の追加--%>
	<a
		href="MessageServlet?petID=${petDetail.petID}&facilityId=${petDetail.facilityID}&from=detail&backFrom=${from}">
		<br> <!--	予約--> <c:choose>
			<c:when test="${reserved}">
				<h3>★このペットは現在予約済みです★</h3>
			</c:when>
			<c:otherwise>
				<a href="QuizWarningServlet?petID=${petDetail.petID}">予約する</a>
				<br>
				<br>
			</c:otherwise>
		</c:choose> <br> <c:choose>
			<c:when test="${from == 'home'}">
				<a href="HomeServlet">戻る</a>
			</c:when>

			<c:when test="${from == 'favorite'}">
				<a href="FavoriteListServlet">戻る</a>
			</c:when>

			<c:when test="${from == 'search'}">
				<a href="HomeServlet?clickSearch=true">戻る</a>
			</c:when>

			<c:otherwise>
				<a href="HomeServlet">戻る</a>
			</c:otherwise>
		</c:choose>
</body>
</html>