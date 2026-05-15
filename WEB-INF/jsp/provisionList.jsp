<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.ProvisionView, model.User"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>教科書提供者情報</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/style/provisionList.css">
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
					ログイン中： <strong><%=loginUser.getFullName()%></strong> さん
				</p>
				<%
				}
				%>

				<table>
					<thead>
						<tr>
							<th>取引履歴に基づく提供者情報一覧</th>
						</tr>
					</thead>
					<%-- (前略: ヘッダー部分) --%>
					<tbody>
						<%
						// サーブレットでセットした名前 "provisionList" で取得
						List<ProvisionView> list = (List<ProvisionView>) request.getAttribute("provisionList");

						if (list == null || list.isEmpty()) {
						%>
						<tr>
							<td style="text-align: center; padding: 20px;">
								取引履歴に基づく提供者情報はありません。</td>
						</tr>
						<%
						} else {
						for (ProvisionView p : list) {
							String statusClass = "status-default";
							// DBのステータス名(日本語の場合を考慮)
							String status = (p.getStatusName() != null) ? p.getStatusName() : "";

							if (status.contains("完了")) {
								statusClass = "status-completed";
							} else if (status.contains("進行") || status.contains("承認")) {
								statusClass = "status-pending";
							} else if (status.contains("キャンセル")) {
								statusClass = "status-canceled";
							}
						%>
						<tr>
							<td>
								<div class="textbook-item <%=statusClass%>">
									<div class="textbook-info">
										<span style="font-size: 1.2em; color: #336699;"> <strong>提供者：<%=p.getProviderName()%>
												さん
										</strong>
										</span><br> <span style="font-size: 0.9em; color: #555;">
											連絡先：<a href="mailto:<%=p.getProviderEmail()%>"><%=p.getProviderEmail()%></a>
										</span><br> <br> 教科書：<strong><%=p.getTextbookName()%></strong><br>
										状態：<%=status%><br>
										<%
										if (p.getTextbookDetail() != null && !p.getTextbookDetail().isEmpty()) {
										%>
										詳細 ：<%=p.getTextbookDetail()%>
										<%
										}
										%>
									</div>

									<div class="textbook-image-area">
										<%
										if (p.getProviderPhoto() != null && !p.getProviderPhoto().isEmpty()) {
										%>
										<img
											src="<%=request.getContextPath() + "/" + p.getProviderPhoto()%>"
											class="textbook-image">
										<%
										} else if (p.getTextbookPhoto() != null && !p.getTextbookPhoto().isEmpty()) {
										%>
										<img
											src="<%=request.getContextPath() + "/" + p.getTextbookPhoto()%>"
											class="textbook-image">
										<%
										}
										%>
									</div>
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