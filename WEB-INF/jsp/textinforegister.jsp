<%--
  処理概要: 入力確認ダイアログの表示・確定/取消制御を行う画面です。
  主な処理コード: function showConfirmDialog(), closePopup() / event onclick, addEventListener
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Subject, model.Textbook" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>教科書情報登録画面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/textinforegister.css">
</head>
<body>
    <jsp:include page="nav.jsp" />

    <main class="container">
        <div class="image-box" id="imagePreviewContainer">
            <div class="no-image" id="noImageText">No Image</div>
            <img id="imagePreview" src="" alt="教科書プレビュー"
                style="display: none; max-width: 100%; height: auto;">
        </div>

        <%
       
        List<Subject> subjectList = (List<Subject>) request.getAttribute("Subject");
        List<Textbook> textbookList = (List<Textbook>) request.getAttribute("Textbook");
        %>

        <div class="form-area">
            <h2 class="label-title">教科書情報</h2>

            <form action="TextinforegisterServlet" method="post" id="textForm" enctype="multipart/form-data">
                
                <div class="input-row">
                    <label for="subject">科目名：</label> 
                    <select class="combo-box" name="subjectnumber" id="subject">
                        <option value="">科目を選択してください</option>
                        <% if (subjectList != null) { 
                             for (Subject sub : subjectList) { %>
                                <option value="<%= sub.getSubjectsNumber() %>"><%= sub.getSubjectsName() %></option>
                        <%   } 
                           } %>
                    </select>
                </div>

                <div class="input-row">
                    <label for="bookName">教科書名：</label> 
                    <select class="combo-box" name="textbooknumber" id="bookName">
                        <option value="">教科書を選択してください</option>
                        <% if (textbookList != null) { 
                             for (Textbook text : textbookList) { %>
                                <option  value="<%= text.getTextbookNumber() %>"><%= text.getTextbookName() %></option>
                        <%   } 
                           } %>
                    </select>
                </div>

                <div class="input-row">
                    <label>教科書の状態：</label>
                    <input type="text" class="textbox" name="condition" placeholder="例：新品、書き込みあり">
                </div>

                <div class="input-row">
                    <label>教科書画像：</label>
                    <input type="file" id="userImage" name="userImage" accept="image/*">
                </div>

                <div class="btn-area">
                    <button type="button" class="btn-register" onclick="showConfirmDialog()">登録</button>
                    <button type="button" class="btn-cancel" onclick="history.back()">キャンセル</button>
                </div>
            </form>
        </div>
    </main>

    <div id="confirmDialog" class="modal-overlay">
        <div class="dialog-box">
            <div class="modal-body">
                <p>この内容で登録しますか？</p>
            </div>
            <div class="modal-footer">
                <button type="button" id="dialogOk" class="modal-ok">登録</button>
                <button type="button" id="dialogCancel" class="modal-cancel">キャンセル</button>
            </div>
        </div>
    </div>

    <script>
        // 画像プレビュー処理
        document.getElementById('userImage').addEventListener('change', function(e) {
            const file = e.target.files[0];
            const preview = document.getElementById('imagePreview');
            const noImage = document.getElementById('noImageText');

            if (file) {
                const reader = new FileReader();
                reader.onload = function(event) {
                    preview.src = event.target.result;
                    preview.style.display = 'block';
                    noImage.style.display = 'none';
                };
                reader.readAsDataURL(file);
            } else {
                preview.src = "";
                preview.style.display = 'none';
                noImage.style.display = 'block';
            }
        });

        // ダイアログ制御
        function showConfirmDialog() {
            document.getElementById('confirmDialog').style.display = 'flex';
        }

        function closePopup() {
            document.getElementById('confirmDialog').style.display = 'none';
        }

        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('dialogOk').addEventListener('click', function() {
                document.getElementById('textForm').submit();
            });
            document.getElementById('dialogCancel').addEventListener('click', closePopup);
            document.getElementById('confirmDialog').addEventListener('click', function(e) {
                if (e.target === this) closePopup();
            });
        });
    </script>
</body>
</html>
