<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>施設ダッシュボード</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>

<div class="page-container">

<div class="site-header">

    <div class="site-logo-wrap">
        <h1 class="site-logo">PET MATCH</h1>
    </div>

    <div class="top-menu">
        <a class="menu-button" href="FacilityPageServlet">施設情報を管理</a>

        <a class="menu-button" href="MessageListServlet">
            メッセージ確認
            <span class="unread-badge">未読${facilityUnreadCount}件</span>
        </a>

        <a class="menu-button logout-button" href="LogoutServlet">ログアウト</a>
    </div>

</div>

<div class="facility-dashboard-card">

<!-- 施設用メニュー -->
<c:if test="${not empty sessionScope.facilityId}">

    <div class="dashboard-title-area">
        <h1>施設ダッシュボード</h1>
        <p class="welcome-message">
            予約・メッセージ・登録ペット・アクセス状況を確認できます。
        </p>
    </div>

    <div class="dashboard-summary-grid">

        <div class="dashboard-summary-card">
            <div class="summary-icon">📅</div>
            <div class="summary-label">本日の予約数</div>
            <div class="summary-number">
                <c:out value="${countTodayReserve}" />
                <span>件</span>
            </div>

            <c:choose>
                <c:when test="${countTodayReserve > 0}">
                    <div class="summary-status good-status">本日予約あり</div>
                </c:when>
                <c:otherwise>
                    <div class="summary-status normal-status">本日予約なし</div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="dashboard-summary-card">
            <div class="summary-icon">💬</div>
            <div class="summary-label">未読メッセージ</div>
            <div class="summary-number">
                <c:out value="${facilityUnreadCount}" />
                <span>件</span>
            </div>

            <c:choose>
                <c:when test="${facilityUnreadCount > 0}">
                    <div class="summary-status alert-status">対応が必要</div>
                </c:when>
                <c:otherwise>
                    <div class="summary-status good-status">未読なし</div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="dashboard-summary-card">
            <div class="summary-icon">🐾</div>
            <div class="summary-label">登録ペット数</div>
            <div class="summary-number">
                <c:out value="${petCount}" />
                <span>匹</span>
            </div>

            <c:choose>
                <c:when test="${petCount > 0}">
                    <div class="summary-status good-status">掲載中</div>
                </c:when>
                <c:otherwise>
                    <div class="summary-status alert-status">登録なし</div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="dashboard-summary-card">
            <div class="summary-icon">👀</div>
            <div class="summary-label">累計アクセス数</div>
            <div class="summary-number">
                ${viewCount}
                <span>回</span>
            </div>
            <div class="summary-status normal-status">ページ閲覧</div>
        </div>

    </div>

    <div class="dashboard-status-grid">

        <div class="dashboard-status-panel">
            <h2>本日の運営状況</h2>

            <div class="status-row">
                <div class="status-row-main">
                    <span class="status-icon">📅</span>
                    <span class="status-title">予約対応</span>
                </div>
                <div class="status-value">
                    <c:out value="${countTodayReserve}" />件
                </div>
            </div>

            <div class="status-track">
                <c:choose>
                    <c:when test="${countTodayReserve > 0}">
                        <div class="status-fill reserve-fill active-fill"></div>
                    </c:when>
                    <c:otherwise>
                        <div class="status-fill reserve-fill"></div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="status-row">
                <div class="status-row-main">
                    <span class="status-icon">💬</span>
                    <span class="status-title">メッセージ対応</span>
                </div>
                <div class="status-value">
                    <c:out value="${facilityUnreadCount}" />件
                </div>
            </div>

            <div class="status-track">
                <c:choose>
                    <c:when test="${facilityUnreadCount > 0}">
                        <div class="status-fill unread-fill active-fill"></div>
                    </c:when>
                    <c:otherwise>
                        <div class="status-fill unread-fill clear-fill"></div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="status-row">
                <div class="status-row-main">
                    <span class="status-icon">🐾</span>
                    <span class="status-title">ペット掲載</span>
                </div>
                <div class="status-value">
                    <c:out value="${petCount}" />匹
                </div>
            </div>

            <div class="status-track">
                <c:choose>
                    <c:when test="${petCount > 0}">
                        <div class="status-fill pet-fill active-fill"></div>
                    </c:when>
                    <c:otherwise>
                        <div class="status-fill pet-fill"></div>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>

        <div class="dashboard-status-panel">
            <h2>次の予約</h2>

            <c:if test="${not empty reserve}">
                <div class="next-reserve-timeline">
                    <div class="timeline-dot">📌</div>
                    <div class="timeline-content">
                        <p><span class="pet-label">予約ID：</span>${reserve.reservationID}</p>
                        <p><span class="pet-label">ペットID：</span>${reserve.petID}</p>
                        <p><span class="pet-label">ユーザーID：</span>${reserve.userID}</p>
                        <p><span class="pet-label">予約日時：</span>${reserve.formattedReserveTime}</p>
                    </div>
                </div>
            </c:if>

            <c:if test="${empty reserve}">
                <div class="empty-dashboard-box">
                    <div class="empty-icon">🌿</div>
                    <p>次の予約はありません。</p>
                </div>
            </c:if>
        </div>

    </div>

    <div class="dashboard-main-grid">

        <div class="dashboard-panel">
            <h2>最近追加したペット</h2>

            <c:if test="${not empty latestPet}">
	            <div class="latest-pet-box">

	                <img class="latest-pet-image" src="${latestPet.imagePath}" width="200">

		            <p>
                        <span class="pet-dot">・</span><span class="pet-label">名前：</span>
		                <c:out value="${latestPet.name}" />
                    </p>

		            <p>
                        <span class="pet-dot">・</span><span class="pet-label">性別：</span>
		                <c:choose>
		                    <c:when test="${latestPet.gender == 'male'}">
    			                オス
			                </c:when>
		                    <c:when test="${latestPet.gender == 'female'}">
    			                メス
			                </c:when>
		                    <c:otherwise>
			                    <c:out value="${latestPet.gender}" />
		                    </c:otherwise>
	                    </c:choose>
                    </p>

		            <p>
                        <span class="pet-dot">・</span><span class="pet-label">年齢：</span>
		                <c:out value="${latestPet.age}" />歳
                    </p>

		            <p>
                        <span class="pet-dot">・</span><span class="pet-label">価格：</span>
		                <c:out value="${latestPet.price}" />円
                    </p>
	            </div>
            </c:if>

            <c:if test="${empty latestPet}">
                <div class="empty-dashboard-box">
                    <div class="empty-icon">🐾</div>
                    <p>最近追加したペットはありません。</p>
                </div>
            </c:if>
        </div>

        <div class="dashboard-panel">
            <h2>施設ページ状況</h2>

            <div class="access-visual-box">
                <div class="access-ring">
                    <div class="access-ring-inner">
                        <span>${viewCount}</span>
                        <small>回</small>
                    </div>
                </div>

                <div class="access-text">
                    <p class="panel-section-title">累計アクセス数</p>
                    <p>施設ページが閲覧された合計回数です。</p>
                </div>
            </div>
        </div>

    </div>

</c:if>

</div>


</div>

</body>
</html>
