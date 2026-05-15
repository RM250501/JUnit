<%@ page contentType="text/html; charset=UTF-8" %>

<div class="system-title">教科書譲渡システム</div>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/nav.css">
<nav>
	<div class="nav-inner">
		<a href="${pageContext.request.contextPath}/Home">ホーム</a>
		<a href="${pageContext.request.contextPath}/TextsSearchServlet">科目・教科書検索</a>
		<a href="${pageContext.request.contextPath}/TradeHomeServlet">教科書譲渡ホーム</a>
		<a href="${pageContext.request.contextPath}/MypageServlet">マイページ</a>
		<a href="${pageContext.request.contextPath}/User_LoginServlet">ログイン</a>
		<a href="${pageContext.request.contextPath}/AdminHomeServlet">管理者用</a>
		<!--<p>${loginUser.fullName}</p> -->
	</div>
</nav>
 

 