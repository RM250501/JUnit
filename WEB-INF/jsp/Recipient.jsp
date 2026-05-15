<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, model.TextTrade, model.Textbook, model.User"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>教科書受領者ホーム</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/Recipient.css">
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
							class="send-btn">送信</button>

						<div class="contact-buttons">
							<button type="submit" form="mainForm" formaction="ContactServlet">お問い合わせ</button>

							<button type="submit" form="mainForm" class="complete-btn"
								formaction="Providerconpservlet">取引完了</button>

							<button type="submit" form="mainForm" formaction="CancelServlet">
								キャンセル</button>
						</div>
					</div>

					<div class="trade-right">


						<div class="textbook-selection">
							<div class="list-title">マーキングした教科書</div>
							<p class="select-hint">取引したい内容を選択</p>
							<div class="radio-list">
								<%
								List<Textbook> textList = (List<Textbook>) request.getAttribute("textbookList");
								List<User> UserList = (List<User>) request.getAttribute("allList");
								List<Integer> tradenumList = (List<Integer>) request.getAttribute("tradenumber");

								if (textList != null && UserList != null && tradenumList != null) {

									int size = Math.min(Math.min(textList.size(), UserList.size()), tradenumList.size());

									for (int i = 0; i < size; i++) {
										Textbook tbValue = textList.get(i);
										User uLabel = UserList.get(i);
								%>

								<label> <input type="radio" name="email"
									value="<%=uLabel.getEmailAddress()%>"> <%=tbValue.getTextbookName()%>
									（<%=uLabel.getFullName()%>）
								</label> <br> <input type="hidden" name="tradenum"
									value="<%=tradenumList.get(i)%>"> <input type="hidden"
									name="textbooknum" value="<%=tbValue.getTextbookNumber()%>">
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
</html>