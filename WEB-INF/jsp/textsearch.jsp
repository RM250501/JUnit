<%--
  処理概要: 入力変更イベントに応じて表示やボタン状態を更新する画面です。
  主な処理コード: function filterBooks() / event addEventListener
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page
	import="java.util.List, model.Subject, model.Textbook, model.Provision"%>

<%!// Provision から Textbook を取得するユーティリティ
	public Textbook getBook(int textbookNumber, List<Textbook> tList) {
		if (tList == null)
			return null;
		for (Textbook t : tList) {
			if (t.getTextbookNumber() == textbookNumber)
				return t;
		}
		return null;
	}%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>教科書・科目検索</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/textsearch.css">

</head>
<body>

	<jsp:include page="nav.jsp" />

	<%
	List<Subject> sList = (List<Subject>) request.getAttribute("subjectList");
	List<Textbook> tList = (List<Textbook>) request.getAttribute("textbookList");
	List<Provision> pList = (List<Provision>) request.getAttribute("provisionList");
	%>

	<!-- 検索フォーム -->
	<div class="search-section">
		<form action="TextsSearchServlet" method="post">
			<input type="text" name="keyword" class="search-box"
				placeholder="科目名を入力してください"
				value="<%=request.getParameter("keyword") != null ? request.getParameter("keyword") : ""%>">
			<button type="submit" class="search-button">🔍</button>
		</form>

		<!-- 検索結果なしメッセージ -->
		<%
		if (pList != null && pList.isEmpty() && request.getParameter("keyword") != null
				&& !request.getParameter("keyword").isBlank()) {
		%>
		<div style="color: red; font-weight: bold; margin-top: 10px;">
			該当する情報は見つかりませんでした。</div>
		<%
		}
		%>
	</div>

	<!-- 科目一覧テーブル -->
	<div class="table-container">
		<div class="table-scroll">
			<table>
				<thead>
					<tr>
						<th>絞り込み</th>
						<th>科目名</th>
						<th>詳細</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (sList != null && !sList.isEmpty()) {
						for (Subject sub : sList) {
					%>
					<tr>
						<td><input type="checkbox" class="subject-check"
							value="<%=sub.getSubjectsNumber()%>"></td>
						<td><%=sub.getSubjectsName()%></td>
						<td><%=sub.getSubjectsDetail()%></td>
					</tr>
					<%
					}
					} else {
					%>
					<tr>
						<td colspan="3">科目情報が見つかりません。</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

	<!-- Provision 教科書情報表示 -->
	<form action="marking" method="post">
		<div class="info-section">
			<%
			if (pList != null && !pList.isEmpty()) {
				for (Provision prov : pList) {
					Textbook book = getBook(prov.getTextbookNumber(), tList);
			%>
			<div class="info-panel" data-subject="<%=prov.getSubjectsNumber()%>">
				<p>
					教科書情報 <input type="checkbox" name="provisionNumber"
						value="<%=prov.getProvisionNumber()%>"> 欲しい！
				</p>

				<div class="info-box">
					教科書名：<%=(book != null) ? book.getTextbookName() : "不明"%><br>
					教科書状態：<%=prov.getTextbookDetail()%><br> 写真：
					<%
				if (prov.getTextbookPhoto() != null && !prov.getTextbookPhoto().isEmpty()) {
				%>
					<div class="image-area">
						<img
							src="<%=request.getContextPath() + "/" + prov.getTextbookPhoto()%>"
							style="max-width: 100%; height: auto;">
					</div>
					<%
					} else {
					%>
					なし
					<%
					}
					%>

				</div>
			</div>
			<%
			}
			} else {
			%>
			<div class="info-panel">
				<p>教科書情報</p>
				<div class="info-box">提供されている教科書はありません。</div>
			</div>
			<%
			}
			%>
			<div class="marking">
				<button type="submit" class="markingbutton">登録</button>
			</div>
		</div>
	</form>

	<script>
document.addEventListener("DOMContentLoaded", function() {
    const subjectChecks = document.querySelectorAll(".subject-check");
    const bookPanels = document.querySelectorAll(".info-panel");

    subjectChecks.forEach(check => {
        check.addEventListener("change", filterBooks);
    });

    function filterBooks() {
        const selectedSubjects = Array.from(subjectChecks)
            .filter(c => c.checked)
            .map(c => c.value);

        bookPanels.forEach(panel => {
            const panelSubject = panel.dataset.subject;
            if (selectedSubjects.length === 0 || selectedSubjects.includes(panelSubject)) {
                panel.style.display = "block";
            } else {
                panel.style.display = "none";
            }
        });
    }
});
</script>

</body>
</html>

