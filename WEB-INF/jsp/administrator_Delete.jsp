<%--
  処理概要: 入力確認ダイアログの表示・確定/取消制御を行う画面です。
  主な処理コード: function showConfirmDialog() / event onclick, addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>管理者情報削除確認</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/administrator_Delete.css">
</head>
<body>

	<div class="container-wrapper">
		
		<jsp:include page="nav.jsp" />

		<main class="content-wrapper">

			<!-- ★ POSTで削除を実行するフォーム -->
			<form action="AdminDeleteServlet" method="post"
				class="user_delete_form">

				<div class="input-image-container">

					<div class="input-area">
						<h2>管理者情報削除画面</h2>

						<div class="form-row">
							<label>氏名:</label> <span class="display-value">${admin.fullName}</span>
						</div>

						<div class="form-row">
							<label>管理者ID:</label> <span class="display-value">${admin.userId}</span>
						</div>

						<div class="form-row">
							<label>権限:</label> <span class="display-value">${admin.role}</span>
						</div>

						<div class="form-row">
							<label>パスワード:</label> <span class="display-value">${admin.password}</span>
						</div>

						<!-- ★ 削除実行に必要なIDを送信 -->
						<input type="hidden" name="managerId" value="${admin.userId}">
					</div>

					<!-- 本人画像表示 -->
					<div class="image-previews">
						<div class="image-box">
							<p>本人</p>
							<!-- 画像を表示 -->
							<img src="${pageContext.request.contextPath}/${admin.usersPhoto}"
								alt="管理者画像" class="user-icon">
						</div>
					</div>


					<!-- 証明画像表示（必要なら追加） -->
					<div class="image-previews">
						<div class="image-box">
							<p>証明画像</p>
							<!-- 画像を表示 -->
							<img src="${pageContext.request.contextPath}/${admin.usersPhoto}"
								alt="証明画像" class="proof-image">
						</div>
					</div>

				</div>

			</form>

			<!-- 確認ダイアログ -->
			<div id="confirmDialog" class="modal-overlay">
				<div class="dialog-box">
					<p class="dialog-message">削除しますか？</p>
					<div class="dialog-buttons">
						<button id="dialogOk" class="button dialog-ok-button">削除</button>
						<button id="dialogCancel" class="button dialog-cancel-button">キャンセル</button>
					</div>
				</div>
			</div>

		</main>

		<div class="action-buttons-bottom">
			<button type="button" class="button delete-button"
				onclick="showConfirmDialog()">削除</button>
			<button type="button" class="button cancel-button"
				onclick="history.back()">キャンセル</button>
		</div>

	</div>

	<script>
    //-------------------------------------------------------------------------
    // 削除確認ダイアログ制御
    //-------------------------------------------------------------------------
    function showConfirmDialog() {
        document.getElementById('confirmDialog').style.display = 'flex';
    }

    document.addEventListener('DOMContentLoaded', () => {

        document.getElementById('dialogOk').addEventListener('click', () => {
            document.getElementById('confirmDialog').style.display = 'none';
            document.querySelector('.user_delete_form').submit(); // ★ 修正済み
        });

        document.getElementById('dialogCancel').addEventListener('click', () => {
            document.getElementById('confirmDialog').style.display = 'none';
        });

        // グローバル公開
        window.showConfirmDialog = showConfirmDialog;
    });
</script>

</body>
</html>

