<%--
  処理概要: 入力確認ダイアログの表示・確定/取消制御を行う画面です。
  主な処理コード: function setupImagePreview(), displayErrorMessage(), clearAllErrors(), clearErrorMessage() / event onclick, addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>管理者情報登録画面</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/administrator_registration.css">
<style>
/* 💥 プレビューエリアを横並びにするための追加CSS */
.image-previews {
	display: flex; /* 横並びにする */
	flex-direction: row; /* 横方向に配置 */
	gap: 20px; /* 画像と画像の間隔 */
	justify-content: flex-start; /* 左寄せ（中央にしたい場合は center） */
	align-items: flex-start;
	margin-top: 20px;
}

/* 各画像ボックスの幅を固定 */
.image-box {
	text-align: center;
	width: 150px;
}

/* 画像を表示する枠のサイズ */
.image-placeholder {
	width: 150px;
	height: 200px;
	border: 1px solid #ccc;
	background-color: #f9f9f9;
	display: flex;
	justify-content: center;
	align-items: center;
	overflow: hidden;
}
</style>
</head>
<body>
	<div class="container-wrapper">
		<jsp:include page="nav.jsp" />

		<form id="adminForm"
			action="${pageContext.request.contextPath}/AdminRegistrationServlet"
			method="post" enctype="multipart/form-data"
			class="manager-register-form">

			<main class="content-wrapper">
				<div class="input-image-container">

					<div class="input-area">
						<h2>管理者情報登録画面</h2>
						<div class="form-row">
							<label for="name">氏名:</label> <input type="text" id="name"
								name="namae" required> <span id="error-name"
								class="error-message"></span>
						</div>
						<div class="form-row">
							<label for="managerId">管理者ID:</label> <input type="text"
								id="managerId" name="managerId" value="自動付与されます" readonly
								style="background-color: #eee; cursor: not-allowed;"
								tabindex="-1">
						</div>
						<div class="form-row">
							<label for="role">権限:</label> <input type="text" id="role"
								name="role" value="admin" required>
						</div>
						<div class="form-row">
							<label for="emailAddress">メールアドレス:</label> <input type="email"
								id="emailAddress" name="emailAddress" required> <span
								id="error-emailAddress" class="error-message"></span>
						</div>
						<div class="form-row">
							<label for="phoneNumber">電話番号:</label> <input type="tel"
								id="phoneNumber" name="phoneNumber" required> <span
								id="error-phoneNumber" class="error-message"></span>
						</div>
						<div class="form-row">
							<label for="password">パスワード:</label> <input type="password"
								id="password" name="password" required> <span
								id="error-password" class="error-message"></span>
						</div>
						<div class="form-row">
							<label for="passwordConfirm">パスワード確認:</label> <input
								type="password" id="passwordConfirm" name="passwordConfirm"
								required> <span id="error-passwordConfirm"
								class="error-message"></span>
						</div>

						<div class="form-row file-upload">
							<label for="userImage">本人画像:</label> <input type="file"
								id="userImage" name="userImage" accept="image/*"> <span
								id="error-userImage" class="error-message"></span>
						</div>
						<div class="form-row file-upload">
							<label for="studentCardImage">証明画像:</label> <input type="file"
								id="studentCardImage" name="studentCardImage" accept="image/*">
							<span id="error-studentCardImage" class="error-message"></span>
						</div>
					</div>

					<%-- 💥 ここが横並びになります --%>
					<div class="image-previews">
						<div class="image-box">
							<p>本人</p>
							<div class="image-placeholder">
								<img id="userImagePreview"
									src="${pageContext.request.contextPath}/images/no-image.png"
									alt="プレビュー"
									style="width: 100%; height: 100%; object-fit: cover;">
							</div>
						</div>
						<div class="image-box">
							<p>証明画像</p>
							<div class="image-placeholder">
								<img id="studentCardImagePreview"
									src="${pageContext.request.contextPath}/images/no-image.png"
									alt="プレビュー"
									style="width: 100%; height: 100%; object-fit: cover;">
							</div>
						</div>
					</div>
				</div>

				<div id="confirmDialog" class="modal-overlay" style="display: none;">
					<div class="dialog-box">
						<p class="dialog-message">登録しますか？</p>
						<div class="dialog-buttons">
							<button type="button" id="dialogOk"
								class="button dialog-ok-button">登録</button>
							<button type="button" id="dialogCancel"
								class="button dialog-cancel-button">キャンセル</button>
						</div>
					</div>
				</div>
			</main>

			<div class="action-buttons-bottom">
				<button type="button" class="button register-button"
					onclick="showConfirmDialog()">登録</button>
				<button type="button" class="button cancel-button"
					onclick="history.back()">キャンセル</button>
			</div>
		</form>
	</div>

	<script>
    // --- 画像プレビュー ---
    function setupImagePreview(inputId, previewId) {
        const input = document.getElementById(inputId);
        const preview = document.getElementById(previewId);
        input.addEventListener('change', function() {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = e => preview.src = e.target.result;
                reader.readAsDataURL(file);
                clearErrorMessage(inputId); // 画像が選ばれたらエラーを消す
            }
        });
    }

    // --- エラー操作用関数 ---
    function displayErrorMessage(fieldId, message) {
        const errorElement = document.getElementById('error-' + fieldId);
        if (errorElement) errorElement.textContent = message;
    }

    function clearAllErrors() {
        document.querySelectorAll('.error-message').forEach(el => el.textContent = '');
    }

    function clearErrorMessage(fieldId) {
        const errorElement = document.getElementById('error-' + fieldId);
        if (errorElement) errorElement.textContent = '';
    }

    // --- バリデーションチェック ---
    function validateForm() {
        clearAllErrors();
        let isValid = true;

        const name = document.getElementById('name').value.trim();
        const email = document.getElementById('emailAddress').value.trim();
        const pass = document.getElementById('password').value;
        const conf = document.getElementById('passwordConfirm').value;
        const userImg = document.getElementById('userImage').files.length;
        const cardImg = document.getElementById('studentCardImage').files.length;

        if (name === "") { displayErrorMessage('name', "氏名を入力してください。"); isValid = false; }
        if (email === "") { displayErrorMessage('emailAddress', "メールアドレスを入力してください。"); isValid = false; }
        if (pass === "") { displayErrorMessage('password', "パスワードを入力してください。"); isValid = false; }
        if (conf === "") { displayErrorMessage('passwordConfirm', "確認用を入力してください。"); isValid = false; }
        else if (pass !== conf) { displayErrorMessage('passwordConfirm', "パスワードが一致しません。"); isValid = false; }
        if (userImg === 0) { displayErrorMessage('userImage', "本人画像を選択してください。"); isValid = false; }
        if (cardImg === 0) { displayErrorMessage('studentCardImage', "証明画像を選択してください。"); isValid = false; }

        return isValid;
    }

    document.addEventListener('DOMContentLoaded', function() {
        setupImagePreview('userImage', 'userImagePreview');
        setupImagePreview('studentCardImage', 'studentCardImagePreview');

        // Enterキー移動
        document.getElementById('adminForm').addEventListener('keydown', function(e) {
            if (e.key === 'Enter') {
                const target = e.target;
                if (target.tagName === 'INPUT' && (target.type === 'text' || target.type === 'password' || target.type === 'email' || target.type === 'tel')) {
                    e.preventDefault();
                    const fields = Array.from(this.elements).filter(el => 
                        el.tagName === 'INPUT' && !el.readOnly && el.tabIndex !== -1 && 
                        (el.type === 'text' || el.type === 'password' || el.type === 'email' || el.type === 'tel')
                    );
                    const index = fields.indexOf(target);
                    if (index > -1 && index < fields.length - 1) {
                        fields[index + 1].focus();
                    } else {
                        window.showConfirmDialog(); // 最後の項目でEnter
                    }
                }
            }
        });

        // 登録実行
        document.getElementById('dialogOk').addEventListener('click', function() {
            document.getElementById('adminForm').submit();
        });

        document.getElementById('dialogCancel').addEventListener('click', closePopup);
    });

    // 確認ダイアログ表示（バリデーションを通った場合のみ）
    function showConfirmDialog() { 
        if (validateForm()) {
            document.getElementById('confirmDialog').style.display = 'flex';
            document.getElementById('dialogOk').focus();
        }
    }
    function closePopup() { document.getElementById('confirmDialog').style.display = 'none'; }
    window.showConfirmDialog = showConfirmDialog;
</script>
</body>
</html>
