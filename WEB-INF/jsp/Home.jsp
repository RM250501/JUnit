<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>教科書提供システム</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/home.css">
</head>
<body>

	<jsp:include page="nav.jsp" />

<div class="main-content">
	<div class="container">
		<div class="banner">
			<div class="banner-text">
				<p class="main-text">KAGOSHIMA</p>
				<p class="sub-text">KCS鹿児島情報専門学校</p>				
			</div>
		</div>

		<div class="content">
			<p class="warning-text">※取引完了の際には、取引完了を押してください</p>

			<!--  
			<div class="inquiry-section">
				<p>システムに関するお問い合わせはこちら</p>
				<input type="text" placeholder="Text box" class="inquiry-textbox">
				<button class="submit-button">送信</button>
			</div>
			-->
		</div>
	</div>
</div>
</body>
</html>
