<%--
  処理概要: JavaScriptで表示要素の表示/非表示や状態切替を行う画面です。
  主な処理コード: event addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/style/givehomebtn.css">
<div class="action-buttons">
    <!-- 取引履歴（POSTで TransactionHistoryServlet） -->
    <form action="<%= request.getContextPath() %>/TextinforegisterServlet" method="get" style="display:inline;">
        <button type="submit" class="action-btn" id="btn-provision">教科書登録</button>
    </form>
    <form action="<%= request.getContextPath() %>/transactionHistory" method="post" style="display:inline;">
        <button type="submit" class="action-btn" id="btn-history">取引履歴</button>
    </form>

    <form action="<%= request.getContextPath() %>/provisionList" method="post" style="display:inline;">
        <button type="submit" class="action-btn" id="btn-provision">教科書提供者情報</button>
    </form>

    <form action="<%= request.getContextPath() %>/SuggestedListServlet" method="get" style="display:inline;">
        <button type="submit" class="action-btn" id="SuggestedList">マーキング教科書一覧</button>
    </form>
</div>
<script>
    window.addEventListener('DOMContentLoaded', () => {
        const path = window.location.pathname;
        
        if (path.includes('transactionHistory')) {
            document.getElementById('btn-history').classList.add('active');
        } else if (path.includes('provisionList')) {
            document.getElementById('btn-provision').classList.add('active');
        } else if (path.includes('provisionList')) {
            document.getElementById('btn-provision').classList.add('active');
        } else if (path.includes('SuggestedListServlet')) {
            document.getElementById('SuggestedList').classList.add('active');
        }
    });
</script>
