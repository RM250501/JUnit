<%--
  処理概要: 入力確認ダイアログの表示・確定/取消制御を行う画面です。
  主な処理コード: function toggleMenu(), openPopup(), closePopup() / event onclick
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.User"%>

<%
// ★ セッションから loginUser を取得（超重要）
User loginUser = (User) session.getAttribute("loginUser");

// 本人確認画像パス
String identityImagePath = (String) request.getAttribute("identityImagePath");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>マイページ</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/style/profile.css">
</head>

<body>

	<!-- ▼ 神ヘッダー -->
	<jsp:include page="nav.jsp" />

	<main class="content-wrapper">

		<!-- ハンバーガー -->
		<button class="hamburger" onclick="toggleMenu()">☰</button>

		<!-- サイドメニュー -->
		<div class="side-menu" id="sideMenu">
			<button class="close-btn" onclick="toggleMenu()">&times;</button>
			<a
				href="${pageContext.request.contextPath}/Profile_UpdateTransitionServlet">プロフィール更新
				&gt;</a> <a href="#" onclick="openPopup(); return false;">プロフィール削除
				&gt;</a>

		</div>

		<h2 class="title">マイページ</h2>

		<div class="profile-flex-container">

			<!-- 左側：プロフィール -->
			<div class="left-content">
				<h2 class="profile-header">プロフィール</h2>

				<div class="profile-item">
					<label>氏名</label> <input type="text"
						value="<%=loginUser.getFullName()%>" readonly>
				</div>

				<div class="profile-item">
					<label>学籍番号</label> <input type="text"
						value="<%=loginUser.getUserId()%>" readonly>
				</div>

				<div class="profile-item">
					<label>役割</label> <input type="text"
						value="<%=loginUser.getRole()%>" readonly>
				</div>

				<div class="profile-item">
					<label>メールアドレス</label> <input type="text"
						value="<%=loginUser.getEmailAddress()%>" readonly>
				</div>

				<div class="profile-item">
					<label>電話番号</label> <input type="text"
						value="<%=loginUser.getPhoneNumber()%>" readonly>
				</div>
			</div>

			<!-- 右側：画像 -->
			<div class="right-content">

				<!-- 本人画像 -->
				<div class="image-box">
					<p>本人</p>
					<div class="image-placeholder">
						<%
						if (loginUser.getUsersPhoto() != null && !loginUser.getUsersPhoto().isEmpty()) {
						%>
						<img
							src="<%=request.getContextPath() + "/" + loginUser.getUsersPhoto()%>"
							style="max-width: 100%; height: auto;">
						<%
						} else {
						%>
						<p>画像なし</p>
						<%
						}
						%>
					</div>
				</div>

				<!-- 証明画像 -->
				<div class="image-box">
					<p>証明画像</p>
					<div class="image-placeholder">
						<%
						if (identityImagePath != null && !identityImagePath.isEmpty()) {
						%>
						<img src="<%=request.getContextPath() + "/" + identityImagePath%>"
							style="max-width: 100%; height: auto;">
						<%
						} else {
						%>
						<p>証明画像は未登録です</p>
						<%
						}
						%>
					</div>
				</div>

			</div>
		</div>


	</main>

	<!-- ▼ ポップアップ -->
	<div class="popup-bg" id="popupBg" style="display: none;">
		<div class="popup-box">
			<p>プロフィールを削除しますか？</p>
			<div class="popup-buttons">
				<form action="Profile_DeleteServlet" method="post">
					<button class="btn-delete-confirm">削除</button>
				</form>
				<button class="btn-cancel" onclick="closePopup()">キャンセル</button>
			</div>
		</div>
	</div>
	<!-- ▲ ポップアップ -->

	<script>
		function toggleMenu() {
			const sideMenu = document.getElementById("sideMenu");
			sideMenu.classList.toggle("open");
		}
		function openPopup() {
			document.getElementById("popupBg").style.display = "flex";
		}
		function closePopup() {
			document.getElementById("popupBg").style.display = "none";
		}

		// 画面のどこかをクリックした時の処理
		window.onclick = function(event) {
			const sideMenu = document.getElementById("sideMenu");
			const hamburger = document.querySelector(".hamburger");

			// クリックされた場所が「メニュー」でも「ハンバーガーボタン」でもない場合
			if (!sideMenu.contains(event.target)
					&& !hamburger.contains(event.target)) {
				if (sideMenu.classList.contains("open")) {
					sideMenu.classList.remove("open");
				}
			}
		}
	</script>

</body>
</html>

