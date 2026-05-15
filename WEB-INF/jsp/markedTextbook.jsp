<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="model.SuggestedTextbookView, model.User" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>マーキング教科書一覧</title>
    <link rel="stylesheet"
        href="<%= request.getContextPath() %>/style/transactionHistory.css">
</head>
<body>
<div class="container">

    <jsp:include page="nav.jsp" />

    <main class="main-content">

        <jsp:include page="givehomebtn.jsp" />

        <div class="textbook-list">

<%
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser != null) {
%>
            <p class="login-user-name">
                ログイン中：
                <strong><%= loginUser.getFullName() %></strong> さんの
                マーキング教科書
            </p>
<%
    }
%>

            <table>
                <thead>
                    <tr>
                        <th>マーキング教科書一覧</th>
                    </tr>
                </thead>
                <tbody>

<%
    List<SuggestedTextbookView> list =
        (List<SuggestedTextbookView>) request.getAttribute("suggestedList");

    if (list == null || list.isEmpty()) {
%>
                    <tr>
                        <td>マーキングした教科書はありません。</td>
                    </tr>
<%
    } else {
        for (SuggestedTextbookView v : list) {
%>
                    <tr>
                        <td>
                            <div class="textbook-item">

                                <div class="textbook-info">
                                    教科書名：<%= v.getTextbookName() %><br><br>
                                    提供者　：<%= v.getProviderName() %>
                                </div>

<%
            if (v.getTextbookPhoto() != null) {
%>
                                <div class="textbook-image-area">
                                    <img
                                      src="<%= request.getContextPath() + "/" + v.getTextbookPhoto() %>"
                                      class="textbook-image">
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
