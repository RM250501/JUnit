<%--
  処理概要: 画面表示とユーザー操作の受付を行い、必要に応じてJavaScriptで表示制御します。
  主な処理コード: function toggleActionButtons() / event onclick
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.ProvisionView, model.User"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>教科書受領者ホーム</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/style/giveHome.css">
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
							<th>提供教科書一覧</th>
						</tr>
					</thead>
					<tbody>
						<form action="Textinfodelete" method="post">
							<%
							List<ProvisionView> list = (List<ProvisionView>) request.getAttribute("provisionList");

							if (list == null || list.isEmpty()) {
							%>
						
						<tr>
							<td style="text-align: center; padding: 20px;">現在、提供中の教科書はありません。</td>
						</tr>
						<%
						} else {
						for (ProvisionView p : list) {
						%>

						<tr>
							<td>
								<div class="textbook-item">
									<div class="radio-column">
										<input type="radio" name="selectedId"
											value="<%=p.getProvisionNumber()%>"
											onclick="toggleActionButtons()">
									</div>

									<div class="textbook-info">
										教科書名：<%=p.getTextbookName()%><br> 科目 ：<%=p.getSubjectsName()%><br>
										状態 ：<%=p.getTextbookDetail()%>
									</div>

									<%
									if (p.getTextbookPhoto() != null) {
									%>
									<div class="textbook-image-area">
										<img
											src="<%=request.getContextPath() + "/" + p.getTextbookPhoto()%>"
											class="textbook-image" alt="教科書画像">
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
						<button type="submit">削除</button>
						</form>
					</tbody>
				</table>
			</div>
		</main>
	</div>

	<script>
		/**
		 * ラジオボタンが選択されたときの処理
		 * givehomebtn.jsp内にある更新・削除ボタンのdisabledを解除します
		 */
		function toggleActionButtons() {
			const updateBtn = document.getElementById('updateBtn');
			const deleteBtn = document.getElementById('deleteBtn');

			// いずれかのラジオボタンがチェックされているか確認
			const isChecked = document
					.querySelector('input[name="selectedId"]:checked') !== null;

			if (updateBtn && deleteBtn) {
				updateBtn.disabled = !isChecked;
				deleteBtn.disabled = !isChecked;
			}
		}
	</script>
</body>
</html>
