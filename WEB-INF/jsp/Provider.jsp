<%--
  処理概要: 入力変更イベントに応じて表示やボタン状態を更新する画面です。
  主な処理コード: function enableButtons() / event onchange
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, model.TextTrade, model.Textbook, model.User, model.TradePartnerView"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>教科書受領者ホーム</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/Provider.css">
</head>
<body>
	<jsp:include page="nav.jsp" />

	<div class="main-content-wrapper">

		<div class="trade-info-container">

			<h2>教科書取引</h2>
			<form id="mainForm" method="post">
				<div class="trade-layout">

					<div class="trade-left">
						<div class="history-box">
							<textarea name="body" placeholder="時間や日時などのやり取り"></textarea>

						</div>


						<button type="submit" formaction="TransactionProviderservlet"
							class="send-btn" id="sendBtn" disabled>送信</button>

						<div class="contact-buttons">
							<button type="submit" form="mainForm" class="complete-btn"
								formaction="Providerconpservlet" id="completeBtn" disabled>
								取引完了</button>

							<button type="submit" form="mainForm"
								formaction="TradeHomeServlet" id="cancelBtn" disabled>
								キャンセル</button>
						</div>
					</div>

					<div class="trade-right">


						<div class="textbook-selection">
							<div class="list-title">取引希望者</div>
							<p class="select-hint">取引したい内容を選択</p>
							<div class="radio-list">
								<%
								List<TradePartnerView> tradeList = (List<TradePartnerView>) request.getAttribute("tradeList");

								if (tradeList != null) {
									for (TradePartnerView trade : tradeList) {
								%>

								<label> <input type="radio" name="tradeSelect"
									value="<%=trade.getTradingNumber()%>"
									onchange="enableButtons()"> <strong><%=trade.getTextbookName()%></strong><br>
									氏名： <br><%=trade.getFullName()%><br> メール： <%=trade.getEmailAddress()%>
								</label> <br>
								<%
								}
								}
								%>
							</div>

						</div>
			</form>


		</div>
	</div>
	</div>
	</div>
</body>
<script>
	function enableButtons() {
		document.getElementById("sendBtn").disabled = false;
		document.getElementById("completeBtn").disabled = false;
		document.getElementById("cancelBtn").disabled = false;
	}
</script>

</html>
