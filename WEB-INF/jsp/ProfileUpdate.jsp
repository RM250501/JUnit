<%--
  処理概要: 入力確認ダイアログの表示・確定/取消制御を行う画面です。
  主な処理コード: function showConfirmDialog(), setupImagePreview() / event onclick, addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>プロフィール更新画面</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/profileupdate.css">
</head>
<body>

	<div class="container-wrapper">
		<jsp:include page="nav.jsp" />

		<form action="User_UpdateServlet" method="post"
			enctype="multipart/form-data" class="manager-register-form">
			<main class="content-wrapper">
				<!-- ▼ 左カラム（プロフィール入力） -->
				<div class="input-image-container">

					<div class="input-area">
						<input type="hidden" name="managerId" value="${loginUser.userId}">

						<div class="form-row">
							<label>氏名</label> <input type="text" id="fullName" name="name"
								value="${loginUser.fullName}">
						</div>

						<div class="form-row">
							<label>学籍番号</label> <input type="text"
								value="${loginUser.userId}" readonly>
						</div>

						<div class="form-row">
							<label>役割</label> <input type="text" id="role" name="role"
								value="${loginUser.role}" readonly>
						</div>

						<div class="form-row">
							<label>メールアドレス</label> <input type="text" id="emailAddress"
								name="emailAddress" value="${loginUser.emailAddress}">
						</div>

						<div class="form-row">
							<label>電話番号</label> <input type="text" id="phoneNumber"
								name="phoneNumber" value="${loginUser.phoneNumber}">
						</div>

						<div class="form-row">
							<label>パスワードの設定</label> <input type="password" name="password"
								id="password" required>
						</div>

						<div class="form-row">
							<label>パスワードの再入力</label> <input type="password" name="pass2"
								id="pass2" required>
						</div>
						<div class="form-row file-upload">
							<label for="userImage">本人画像:</label> <input type="file"
								id="userImage" name="userImage" accept="image/*">
						</div>

						<div class="form-row file-upload">
							<label for="studentCardImage">証明画像:</label> <input type="file"
								id="studentCardImage" name="studentCardImage" accept="image/*">
						</div>

					</div>

					<div class="image-previews">
						<div class="image-box">
							<p>本人</p>
							<div class="image-placeholder user-icon">
								<img id="userImagePreview" src="" alt="本人画像プレビュー"
									style="width: 100%; height: 100%; object-fit: cover; display: none;">
							</div>
						</div>
					</div>
					<div class="image-previews">
						<div class="image-box">
							<p>証明画像</p>
							<div class="image-placeholder">
								<img id="studentCardImagePreview" src="" alt="証明画像プレビュー"
									style="width: 100%; height: 100%; object-fit: cover; display: none;">
							</div>
						</div>
					</div>

					<div id="confirmDialog" class="modal-overlay">
						<div class="dialog-box">
							<p class="dialog-message">更新しますか？</p>
							<div class="dialog-buttons">
								<button id="dialogOk" class="button dialog-ok-button">更新</button>
								<button id="dialogCancel" class="button dialog-cancel-button">キャンセル</button>
							</div>
						</div>
					</div>

				</div>

			</main>

			<div class="action-buttons-bottom">
				<button type="button" class="button register-button"
					onclick="showConfirmDialog()">更新</button>
				<button type="button" class="button cancel-button"
					onclick="history.back()">キャンセル</button>
			</div>

		</form>
		

	</div>

	<script>
	function showConfirmDialog() {

	    const pass1 = document.getElementById("password").value;
	    const pass2 = document.getElementById("pass2").value;

	    if (pass1 !== pass2) {
	        alert("パスワードが一致しません");
	        return;
	    }

	    document.getElementById('confirmDialog').style.display = 'flex';
	}

		// ----------------------------------------------------
		// 💥 リアルタイム画像プレビュー機能 (汎用化)
		// ----------------------------------------------------
		// placeholderElementIdは今回は使用しませんが、構造をシンプルに保つために引数に残します。
		function setupImagePreview(inputElementId, previewElementId,
				placeholderElementId) {
			const inputElement = document.getElementById(inputElementId);
			const previewImage = document.getElementById(previewElementId);
			const placeholder = document.getElementById(placeholderElementId); // プレースホルダ要素を取得

			if (!inputElement || !previewImage)
				return;

			inputElement.addEventListener('change', function(event) {
				const file = event.target.files[0];

				if (file && file.type.startsWith('image/')) {
					const reader = new FileReader();

					reader.onload = function(e) {
						previewImage.src = e.target.result;
						previewImage.style.display = 'block';

						// プレースホルダがあれば非表示にする (今回はIDがないので仮の処理)
						// 例: プレースホルダの親要素の背景画像をクリアするなど
						if (placeholder) {
							// プレースホルダテキストを非表示にするなどの処理をここに追加
						}
					};
					reader.readAsDataURL(file);
				} else {
					// ファイルが選択されなかった場合、画像を非表示に戻す
					previewImage.src = "";
					previewImage.style.display = 'none';
					if (placeholder) {
						// プレースホルダを再表示するなどの処理をここに追加
					}
				}
			});
		}

		// ページロード時にプレビュー機能を設定
		document
				.addEventListener(
						'DOMContentLoaded',
						function() {

							// 💥 本人画像: 'userImage' → 'userImagePreview'
							// プレースホルダテキストがないため、第3引数はnullに
							setupImagePreview('userImage', 'userImagePreview',
									null);

							// 💥 証明画像: 'studentCardImage' → 'studentCardImagePreview'
							setupImagePreview('studentCardImage',
									'studentCardImagePreview', null);

							// ----------------------------------------------------
							// フォーム送信機能はそのまま維持
							// ----------------------------------------------------

							function showConfirmDialog() {
								document.getElementById('confirmDialog').style.display = 'flex';
							}

							document
									.getElementById('dialogOk')
									.addEventListener(
											'click',
											function() {
												document
														.getElementById('confirmDialog').style.display = 'none';
												document
														.querySelector(
																'.manager-register-form')
														.submit();
											});

							document
									.getElementById('dialogCancel')
									.addEventListener(
											'click',
											function() {
												document
														.getElementById('confirmDialog').style.display = 'none';
											});

							// グローバルスコープから呼び出せるように関数を定義（確認ダイアログ用）
							window.showConfirmDialog = showConfirmDialog;
						});
	</script>

</body>
</html>

