<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.TransactionView, model.User" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>取引履歴</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style/transactionHistory.css">
</head>
<body>
<div class="container">

    <jsp:include page="nav.jsp"/>

    <main class="main-content">
         <jsp:include page="givehomebtn.jsp" />

        <div class="textbook-list">

<%
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser != null) {
%>
            <p class="login-user-name">
                ログイン中： <strong><%= loginUser.getFullName() %></strong> さん
            </p>
<%
    }
%>

            <table>
                <thead>
                    <tr>
                        <th>取引履歴一覧</th>
                    </tr>
                </thead>
                <tbody>

<%
    List<TransactionView> list = (List<TransactionView>) request.getAttribute("transactionList");

    if (list == null || list.isEmpty()) {
%>
                    <tr>
                        <td>取引履歴はありません。</td>
                    </tr>
<%
    } else {
        for (TransactionView t : list) {
        	String statusClass = "status-default";
            // DBの値（アルファベット）を取得して小文字に統一
            String status = (t.getStatusName() != null) ? t.getStatusName().toLowerCase().trim() : "";
            
            if ("completed".equals(status)) {
                statusClass = "status-completed";
            } else if ("ongoing".equals(status)) {
                statusClass = "status-pending";
            } else if ("canceled".equals(status)) {
                statusClass = "status-canceled";
            }
%>
                    <tr>
                        <td>
                            <div class="textbook-item <%= statusClass %>">
                                <div class="textbook-info">
                                    教科書名：<%= t.getTextbookName() %><br><br>
                                    科目　　：<%= t.getSubjectsName() %><br><br>
                                    提供者　：<%= t.getProviderName() %><br><br>
                                    取引日時：<%= t.getTradingDatetime() %><br><br>
                                    状態　　：<strong><%= t.getStatusName() %></strong><br><br>
<%
            if (t.getComment() != null) {
%>
                                    コメント：<%= t.getComment() %>
<%
            }
%>
                                </div>

<%
            if (t.getTextbookPhoto() != null) {
%>
                                <div class="textbook-image-area">
                                    <img src="<%= request.getContextPath() + "/" + t.getTextbookPhoto() %>" class="textbook-image">
                                </div>
<%
            }
%>
                            </div>
                        </td>
                    </tr>
<%
        }
    }
%>
                </tbody>
            </table>
        </div>
    </main>
</div>
</body>
</html>