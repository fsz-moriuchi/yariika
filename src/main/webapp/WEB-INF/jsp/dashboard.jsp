<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>施設ダッシュボード</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css?v=13">
</head>

<body>

<header class="site-header">
    <div class="header-inner">
        <span class="logo">Pet Matching 管理</span>


    <nav class="header-nav">
        <a href="FacilityPageServlet">施設ページ</a>

        <span class="nav-text">
            メッセージ
            <span class="unread-badge">未読${facilityUnreadCount}件</span>
        </span>

        <span class="nav-text">ログアウト</span>
    </nav>
</div>


</header>

<main class="container">

```
<section class="hero-section">
    <div class="hero-card">
        <p class="small-title">Facility Management</p>
        <h1>施設ダッシュボード</h1>
        <p>
            予約状況、登録ペット数、施設ページのアクセス数を確認できます。
        </p>
    </div>
</section>

<section class="dashboard-section">
    <div class="section-header">
        <p class="small-title">Today</p>
        <h2 class="section-heading">本日の状況</h2>
    </div>

    <div class="dashboard-grid">

        <div class="dashboard-card">
            <p class="dashboard-label">本日の予約数</p>
            <p class="dashboard-number">
                <c:out value="${countTodayReserve}" />
                <span>件</span>
            </p>
        </div>

        <div class="dashboard-card">
            <p class="dashboard-label">登録しているペット数</p>
            <p class="dashboard-number">
                <c:out value="${petCount}" />
                <span>匹</span>
            </p>
        </div>

        <div class="dashboard-card">
            <p class="dashboard-label">施設ページ累計アクセス数</p>
            <p class="dashboard-number">
                <c:out value="${viewCount}" />
                <span>回</span>
            </p>
        </div>

    </div>
</section>

<section class="dashboard-section">
    <div class="dashboard-two-column">

        <div class="management-card">
            <div class="card-title-row">
                <p class="small-title">Next reservation</p>
                <h2>次の予約</h2>
            </div>

            <c:choose>
                <c:when test="${not empty reserve}">
                    <div class="info-list">
                        <p>
                            <span>予約ID</span>
                            <strong><c:out value="${reserve.reservationID}" /></strong>
                        </p>
                        <p>
                            <span>ペットID</span>
                            <strong><c:out value="${reserve.petID}" /></strong>
                        </p>
                        <p>
                            <span>ユーザーID</span>
                            <strong><c:out value="${reserve.userID}" /></strong>
                        </p>
                        <p>
                            <span>予約日時</span>
                            <strong><c:out value="${reserve.formattedReserveTime}" /></strong>
                        </p>
                    </div>

                    <p class="notice-box">
                        予約の詳細確認は、発表時には施設ページから確認します。
                    </p>
                </c:when>

                <c:otherwise>
                    <div class="empty-box">
                        <h2>予約はありません</h2>
                        <p>現在、直近の予約はありません。</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="management-card">
            <div class="card-title-row">
                <p class="small-title">Latest pet</p>
                <h2>最近追加したペット</h2>
            </div>

            <c:choose>
                <c:when test="${not empty latestPet}">
                    <img src="${latestPet.imagePath}" alt="最近追加したペットの画像" class="dashboard-pet-image">

                    <div class="info-list">
                        <p>
                            <span>名前</span>
                            <strong>
                                <c:out value="${not empty latestPet.name ? latestPet.name : '名付けてください！'}" />
                            </strong>
                        </p>

                        <p>
                            <span>性別</span>
                            <strong>
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
                            </strong>
                        </p>

                        <p>
                            <span>年齢</span>
                            <strong><c:out value="${latestPet.age}" />歳</strong>
                        </p>

                        <p>
                            <span>価格</span>
                            <strong><c:out value="${latestPet.price}" />円</strong>
                        </p>
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="empty-box">
                        <h2>登録ペットはありません</h2>
                        <p>まだペットが登録されていません。</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</section>

<section class="dashboard-section">
    <div class="section-header">
        <p class="small-title">Menu</p>
        <h2 class="section-heading">管理メニュー</h2>
    </div>

    <div class="menu-grid">

        <a href="FacilityPageServlet" class="menu-card">
            <h3>施設ページ</h3>
            <p>施設情報や公開ページを確認します。</p>
        </a>

        <div class="menu-card menu-card-disabled">
            <h3>ペット管理</h3>
            <p>登録ペットの確認・修正を行います。</p>
        </div>

        <div class="menu-card menu-card-disabled">
            <h3>予約確認</h3>
            <p>ユーザーから入った予約を確認します。</p>
        </div>

        <div class="menu-card menu-card-disabled">
            <h3>メッセージ</h3>
            <p>ユーザーとのやり取りを確認します。</p>
        </div>

    </div>
</section>


</main>

</body>
</html>
