<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約確認</title>
</head>

<body>
<h1>予約確認</h1>

現在の予約情報一覧
<form action="ReservationConfirmServlet" method="get">

<button type="submit" name="dateStatus" value="all">すべての予約</button>
<button type="submit" name="dateStatus" value="today">今日の予約</button>
<button type="submit" name="dateStatus" value="tomorrow">明日の予約</button>

</form>
<p>表示件数：${reservedDataList.size()}件</p>
<table border="1" style="width: 100%">

    <tr>
        <th>予約番号</th>
        <th>ペットID</th>
        <th>お客様ID</th>
        <th>予約日時</th>
        <th>操作</th>
    </tr>

    <c:forEach var="reserved" items="${reservedDataList}">

        <tr>
            <td>${reserved.reservationID}</td>
            <td>${reserved.petID}</td>
            <td>${reserved.userID}</td>
            <td>${reserved.formattedReserveTime}</td>

            <td>
                <form action="ReservationConfirmServlet" method="post">

                    <input type="hidden"
                           name="reservationID"
                           value="${reserved.reservationID}">

                    <input type="submit"
                           value="この予約を削除">

                </form>
                <form action="ReservationEditServlet" method="get">
                <input type="hidden"
                           name="reservationID"
                           value="${reserved.reservationID}">
                           
                           <input type="hidden"
                           name="reserveTime"
                           value="${reserved.reserveTime}">

                    <input type="submit"
                           value="予約日時の変更">
                </form>
            </td>
        </tr>

    </c:forEach>

</table>

<p>
    予約の対応完了およびキャンセルの場合は、予約の削除を行ってください。
</p>

<form action="FacilityPageServlet" method="get">
    <button type="submit">戻る</button>
</form>
</body>
</html>