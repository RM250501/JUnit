<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>教科書譲渡ホーム</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/style/admini.css">
</head>
<body>

		<!-- ▼ 神ヘッダーここから -->
		<jsp:include page="nav.jsp" />
		<!-- ▲ 神ヘッダーここまで -->
		<main class="content-wrapper">

			<div class="content-section">
				<div class="column">
					<h2>管理者一覧の方はこちら</h2>
					<div class="action-box">
						<a href="${pageContext.request.contextPath}/AdminServlet" class="action-box">管理者管理</a>
					</div>
				</div>
				<div class="column">
					<h2>利用者一覧の方はこちら</h2>
					<div class="action-box">
						<a href="${pageContext.request.contextPath}/UserListServlet" class="action-box">利用者管理</a>
					</div>
				</div>
				<div class="column">
					<h2>科目一覧の方はこちら</h2>
					<div class="action-box">
						<a href="${pageContext.request.contextPath}/AdminServlet" class="action-box">科目管理</a>
					</div>
				</div>
			</div>
	</div>
</body>
</html>