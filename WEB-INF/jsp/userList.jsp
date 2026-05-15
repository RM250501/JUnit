<%--
  処理概要: 入力確認ダイアログの表示・確定/取消制御を行う画面です。
  主な処理コード: function showConfirmDialog(), closePopup(), checkSelection() / event onclick, addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="model.User, java.util.*"%>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>利用者情報一覧画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/userList.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

	<div class="container-wrapper">
		
        <jsp:include page="nav.jsp" />

		<main class="content-wrapper">
			<div class="user-list-section">
				<h2>利用者一覧（承認待ち）</h2>

				<form action="userAppoveServlet" method="POST" id="approveForm">
					<div class="user-cards-container">
						<%
						List<User> userList = (List<User>) request.getAttribute("UserInfoList");
						List<String> identIdPhoto = (List<String>) request.getAttribute("identId");
						if (userList != null && identIdPhoto != null) {
							for (User user : userList) {
								if (user.getStatusId() == 1) {
						%>
						<div class="user-card">
							<input type="radio" name="studentId"
								value="<%=user.getUserId()%>" class="select-checkbox">

							<h4 class="card-title">利用者情報</h4>
							<div class="card-info">
								<p>氏名: <%=user.getFullName()%></p>
								<p>学籍番号: <%=user.getUserId()%></p>
							</div>

							<div class="card-images">
								<div class="image-box">
									<p>本人</p>
									<div class="image-placeholder">
										<% if (user.getUsersPhoto() != null) { %>
										<img src="<%=user.getUsersPhoto()%>"
											style="width: 100%; height: 100%; object-fit: cover;">
										<% } %>
									</div>
								</div>
								<div class="image-box">
									<p>学生証</p>
									<div class="image-placeholder">
										<% if (identIdPhoto != null) { %>
										<img src="<%=identIdPhoto%>"
											style="width: 100%; height: 100%; object-fit: cover;">
										<% } %>
									</div>
								</div>
							</div>
						</div>
						<%
						    }
						  }
						}
						%>
					</div>

					<div class="action-buttons-top">
						<button type="submit" class="approve-button" onclick="return checkSelection()">承認</button>
					</div>
				</form>
			</div>

			<div class="user-list-section">
				<h2>利用者一覧（承認済み）</h2>

				<form action="UserActionServlet" method="POST">
					<div class="user-cards-container">
						<%
						List<User> userList1 = (List<User>) request.getAttribute("UserInfoList");
						if (userList1 != null) {
							for (User user : userList1) {
								if (user.getStatusId() == 2) {
						%>
						<div class="user-card">
							<input type="radio" name="studentId"
								value="<%=user.getUserId()%>" class="select-checkbox">

							<h4 class="card-title">利用者情報</h4>
							<div class="card-info">
								<p>氏名: <%=user.getFullName()%></p>
								<p>学籍番号: <%=user.getUserId()%></p>
							</div>
							<div class="card-images">
								<div class="image-box">
									<p>本人</p>
									<div class="image-placeholder">
										<% if (user.getUsersPhoto() != null) { %>
										<img src="<%=user.getUsersPhoto()%>"
											style="width: 100%; height: 100%; object-fit: cover;">
										<% } %>
									</div>
								</div>
								<div class="image-box">
									<p>学生証</p>
									<div class="image-placeholder"></div>
								</div>
							</div>
						</div>
						<%
						    }
						  }
						}
						%>
					</div>
				</form>
			</div>
		</main>

		<div id="confirmDialog" class="modal-overlay" style="display: none;">
			<div class="dialog-box">
				<div class="modal-body">
					<p>承認する利用者を選択してください</p>
				</div>
				<div class="modal-footer" style="margin-top: 15px;">
					<button type="button" class="approve-button" onclick="closePopup()" style="padding: 5px 20px;">戻る</button>
				</div>
			</div>
		</div>

		<script>
			// ダイアログを表示する（JSからスタイルを上書き）
			function showConfirmDialog() {
				document.getElementById('confirmDialog').style.display = 'flex';
			}

			// ダイアログを閉じる
			function closePopup() {
				document.getElementById('confirmDialog').style.display = 'none';
			}

			/**
			 * 承認ボタンが押された時のチェックロジック
			 */
			function checkSelection() {
				// 承認待ちフォーム内のラジオボタンが選択されているか
				const selected = document.querySelector('#approveForm input[name="studentId"]:checked');
				
				if (!selected) {
					// 選択されていない場合は自作ダイアログを表示
					showConfirmDialog();
					// falseを返して送信(submit)をキャンセルする
					return false;
				}
				// 選択されている場合はそのままServletに送信
				return true;
			}

			// 背景部分をクリックしたときにもダイアログを閉じるように設定
			document.addEventListener('DOMContentLoaded', function() {
				const overlay = document.getElementById('confirmDialog');
				overlay.addEventListener('click', function(e) {
					if (e.target === overlay) {
						closePopup();
					}
				});
			});
		</script>
	</div>
</body>
</html>
