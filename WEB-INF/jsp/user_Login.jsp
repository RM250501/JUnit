<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/user_Login.css">
</head>
<body class="login-page">

	<jsp:include page="nav.jsp" />

	<div class="login-container">
		<h2>ログイン</h2>

		<form action="User_LoginServlet" method="post" class="login-form">
			<div class="form-row">
				<label>氏名</label> <input type="text" name="full_name" required>
			</div>

			<div class="form-row">
				<label>パスワード</label> <input type="password" name="password" required>
			</div>

			<div class="form-actions">
				<button type="submit">ログイン</button>
				<button type="button" onclick="location.href='user_Register.jsp'">新規登録</button>
			</div>
		</form>

		<%
		// session は暗黙オブジェクトとして利用可能
		String errorMsg = null;
		if (session != null) { // nullチェック
			Object obj = session.getAttribute("errorMsg");
			if (obj != null) {
				errorMsg = (String) obj;
				session.removeAttribute("errorMsg"); // 一度表示したら削除
			}
		}

		String loginError = request.getAttribute("loginError") != null ? (String) request.getAttribute("loginError") : null;
		%>

		<%
		if (errorMsg != null) {
		%>
		<div class="error-message"><%=errorMsg%></div>
		<%
		}
		%>

		<%
		if (loginError != null) {
		%>
		<div class="error-message"><%=loginError%></div>
		<%
		}
		%>


	</div>

</body>
</html>
