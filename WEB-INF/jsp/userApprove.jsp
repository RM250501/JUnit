<%--
  処理概要: 入力確認ダイアログの表示・確定/取消制御を行う画面です。
  主な処理コード: event onclick, addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.User, java.util.*"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>利用者情報更新画面</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/userApprove.css">
</head>
<body>

	<div class="container-wrapper">
		<!-- ▼ 神ヘッダーここから -->
		<jsp:include page="nav.jsp" />
		<!-- ▲ 神ヘッダーここまで -->
		<main class="content-wrapper">

			<div class="container">
				<h2 class="screen-title">利用者認証</h2>
				<%
				User userList = (User) request.getAttribute("user");
				List<String> identIdPhoto = (List<String>) request.getAttribute("identId");
				%>

				<form id="authForm" action="userAppoveServlet" method="POST">
					<input type="hidden" name="action" id="formAction" value="">
					<input type="hidden" name="studentId" id="formStudentId"
						value="<%=userList.getUserId()%>">
				</form>

				<div class="auth-content-wrapper">


					<div class="auth-card">
						<p class="card-title">本人</p>
						<div class="image-box"><img src="<%=userList.getUsersPhoto()%>"style="width: 100%; height: 100%; object-fit: cover;"></div>
						<button type="button" class="button confirm-button"
							onclick="showApproveDialog()">認証</button>
					</div>

					<div class="auth-card">
						<p class="card-title">学生証</p>
						<div class="image-box"><img src="<%=identIdPhoto%>"
											style="width: 100%; height: 100%; object-fit: cover;"></div>
						<button type="button" class="button reject-button"
							onclick="showRejectDialog()">拒否</button>
					</div>

				</div>



				<div id="confirmDialog" class="modal-overlay">
					<div class="dialog-box">
						<p id="dialogMessage" class="dialog-message">メッセージがここに入ります</p>
						<div class="dialog-buttons">
							<button id="dialogOk" class="button">はい</button>
							<button id="dialogCancel" class="button">いいえ</button>
						</div>
					</div>
				</div>

				<div class="back-action">
					<button class="button back-to-list" onclick="history.back()">一覧に戻る</button>
				</div>
			</div>
			<script>
				document.addEventListener('DOMContentLoaded', function() {
					const confirmDialog = document
							.getElementById('confirmDialog');
					const dialogMessage = document
							.getElementById('dialogMessage');
					const dialogOk = document.getElementById('dialogOk');
					const dialogCancel = document
							.getElementById('dialogCancel');

					// 💥 認証ボタンが押されたとき
					window.showApproveDialog = function() {
						dialogMessage.textContent = "この利用者を認証します。よろしいですか？";
						confirmDialog.style.display = 'flex';

						// OKボタンの動作を設定
						dialogOk.onclick = function() {
							formAction.value = "approve";
							authForm.submit();
						};
					};

					// 💥 拒否ボタンが押されたとき
					window.showRejectDialog = function() {
						dialogMessage.textContent = "この利用者を拒否します。よろしいですか？";
						confirmDialog.style.display = 'flex';

						// OKボタンの動作を設定
						dialogOk.onclick = function() {
							formAction.value = "reject";
							authForm.submit();
						};
					};

					// キャンセルボタン（いいえ）
					dialogCancel.onclick = function() {
						confirmDialog.style.display = 'none';
					};

					// 背景クリックで閉じる
					confirmDialog.addEventListener('click', function(e) {
						if (e.target === confirmDialog) {
							confirmDialog.style.display = 'none';
						}
					});
				});
			</script>
</body>
</html>
