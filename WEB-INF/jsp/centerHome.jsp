<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>教科書譲渡ホーム</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/centerHome.css">
</head>
<body>

	<div>
		<jsp:include page="nav.jsp" />

		<div class="content-section">
			<div class="column">
				<h2>教科書取引の方はこちら</h2>

				<div class="action-box">
					<a href="${pageContext.request.contextPath}/TransactionProviderservlet"
						class="action-box"> 教科書取引 </a>
				</div>

			</div>

			<div class="column">
				<h2>あなたの教科書はこちら</h2>

				<div class="action-box">
					<a href="${pageContext.request.contextPath}/GiveHomeServlet"
						class="action-box"> 教科書詳細 </a>
				</div>
			</div>

		</div>
	</div>

</body>
</html>
